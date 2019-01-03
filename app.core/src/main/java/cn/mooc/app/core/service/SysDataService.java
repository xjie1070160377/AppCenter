package cn.mooc.app.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.data.entity.SysConfigEntity;
import cn.mooc.app.core.data.entity.SysLogEntity;
import cn.mooc.app.core.data.entity.SysMenuEntity;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.data.entity.SysUserVCoin;
import cn.mooc.app.core.data.nosql.SysLogRepository;
import cn.mooc.app.core.data.nosql.SysUserExtRepository;
import cn.mooc.app.core.data.rds.SysConfigRepository;
import cn.mooc.app.core.data.rds.SysMenuRepository;
import cn.mooc.app.core.data.rds.SysRoleRepository;
import cn.mooc.app.core.data.rds.SysUserRepository;
import cn.mooc.app.core.data.rds.SysUserVCoinRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.enums.UStatus;
import cn.mooc.app.core.exception.ExistUserException;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.security.CredentialsDigest;
import cn.mooc.app.core.security.SaltPwdUtils;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.sys.model.UserRoleForm;

@Service
public class SysDataService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysUserRepository sysUserRepository;	
	@Autowired
	private SysRoleRepository sysRoleRepository;		
	@Autowired
	private SysConfigRepository sysConfigRepository;
	@Autowired
	private SysMenuRepository sysMenuRepository;
	@Autowired
	private SysLogRepository sysLogRepository;
	@Autowired
	private SysUserVCoinRepository sysUserVCoinRepository;
	@Autowired
	private SysUserExtRepository sysUserExtRepository;
	@Autowired
	private CredentialsDigest credentialsDigest;
	@Autowired
	private SysCacheService sysCacheService;
	@Autowired
	private SysContext sysContext;

	
	@Value("${sys.can.del.superuser}")
	private boolean canDelSuperUser = false;
	
	
	public List<SysRoleEntity> getRoles(int status){
		return this.sysRoleRepository.getRoles(status);
	}
	
	public List<SysRoleEntity> getAvailableRoles(){
		return this.getRoles(1);
	}

	public SysUserEntity getUserInfoById(long userId) {
		SysUserEntity userEntity = sysUserRepository.findOne(userId);
		return userEntity;
	}

	public SysUserEntity getUserInfo(String userName) {
		SysUserEntity userEntity = sysUserRepository.getUserInfo(userName);
		return userEntity;
	}
	
	public boolean existUser(String userName) {
		SysUserEntity userEntity = sysUserRepository.getUserInfo(userName);
		return userEntity != null;
	}
	
	public SysUserExt gettUserByNickName(String nickName) {
		SysUserExt sysUserExt = sysUserExtRepository.findUserExtByNickName(nickName);
		return sysUserExt;
	}
	
	public boolean existUserByNickName(String nickName) {
		SysUserExt sysUserExt = gettUserByNickName(nickName);
		return sysUserExt != null;
	}
	
	public List<SysUserEntity> getAllUsers(){
		return this.sysUserRepository.findAll();
	}
	
	
	public Page<SysUserEntity> findSysUserList(Map<String, Object> searchParams,PagerParam pageParam, final String rolename){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		final Specification<SysUserEntity> spec = SpecDynamic.buildSpec(filters.values());
		
		Specification<SysUserEntity> specx = new Specification<SysUserEntity>() {
			public Predicate toPredicate(Root<SysUserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = spec.toPredicate(root, query, cb);
				if(StringUtil.isNotEmpty(rolename)){
					Join path = root.join("roles");
					pred = cb.and(pred, cb.like(path.get("roleName"), "%"+rolename+"%"));
				}

				return pred;
			}
		};
		
		return sysUserRepository.findAll(specx, pageParam.getPageRequest());
		
	}
	

	public SysUserEntity saveSysUser(SysUserEntity entity) throws ExistUserException {
		long userId = entity.getId();
		if (userId > 0) {
			SysUserEntity updateEntity = this.getUserInfoById(userId);
			//更新
			if(StringUtils.isNotBlank(entity.getPassWord())){
				//要更新密码
				byte[] saltBytes = credentialsDigest.getSaltBytes(updateEntity.getPwdSalt());
				String pwd = SaltPwdUtils.md5Password(credentialsDigest, entity.getPassWord(), saltBytes);
				
				updateEntity.setPassWord(pwd);
			}
			
			updateEntity.setuType(entity.getuType());
			updateEntity.setStatus(entity.getStatus());
			updateEntity.setSuperUser(entity.isSuperUser());
			
			updateEntity.setLastUpdate(DateTimeUtil.getCurrentTime());
			
			entity = this.sysUserRepository.save(updateEntity);
			
		}else{
			//查用户名是否存在
			if(this.getUserInfo(entity.getUserName())!=null){
				throw new ExistUserException("用户名已存在");
			}
			String salt = SaltPwdUtils.getRandomSalt();
			byte[] saltBytes = credentialsDigest.getSaltBytes(salt);
			String pwd = SaltPwdUtils.md5Password(credentialsDigest, entity.getPassWord(), saltBytes);
			
			entity.setPassWord(pwd);
			entity.setPwdSalt(salt);
			
			entity.setRoles(new HashSet<SysRoleEntity>());
			
			entity.setCreateTime(DateTimeUtil.getCurrentTime());
			entity.setLastUpdate(DateTimeUtil.getCurrentTime());
			
			this.sysUserRepository.save(entity);
			
			SysUserVCoin sysUserVCoin = new SysUserVCoin();
			sysUserVCoin.setUserId(entity.getId());
			sysUserVCoin.setUserName(entity.getUserName());
			sysUserVCoin.setHistoryTotal(0);
			sysUserVCoin.setAvailableTotal(0);
			sysUserVCoin.setSysUser(entity);
			entity.setUserVCoin(sysUserVCoin);
			
			entity = this.sysUserRepository.save(entity);
			
		}
		
		sysCacheService.clearUserInfoCache(userId);
		
		return entity;
		
	}
	
	/** 批量保存导入的用户账号
	 * @param entitys
	 * @param status
	 * @param userType
	 * @param superUser
	 * @return
	 * @throws ExistUserException
	 * @throws SaveFailureException
	 */
	@Transactional
	public List<SysUserEntity> saveSysUsers(List<SysUserEntity> entitys, int status, int userType, boolean superUser) throws ExistUserException, SaveFailureException {
		List<SysUserEntity> users = new ArrayList<SysUserEntity>();
		for(SysUserEntity entity : entitys){
			//查用户名是否存在
			if(this.getUserInfo(entity.getUserName())!=null){
				String username = getAvailableUsername(entity.getUserName());
				if(StringUtils.isEmpty(username)){
					throw new SaveFailureException();
				}
				entity.setUserName(username);
			}
			String salt = SaltPwdUtils.getRandomSalt();
			byte[] saltBytes = credentialsDigest.getSaltBytes(salt);
			String pwd = SaltPwdUtils.md5Password(credentialsDigest, entity.getPassWord(), saltBytes);
			entity.setPassWord(pwd);
			entity.setPwdSalt(salt);
			entity.setRoles(new HashSet<SysRoleEntity>());
			entity.setCreateTime(DateTimeUtil.getCurrentTime());
			entity.setLastUpdate(DateTimeUtil.getCurrentTime());
			entity.setStatus(status);
			entity.setuType(userType);
			entity.setSuperUser(superUser);
			this.sysUserRepository.save(entity);
			//保存用户积分
			SysUserVCoin sysUserVCoin = new SysUserVCoin();
			sysUserVCoin.setUserId(entity.getId());
			sysUserVCoin.setUserName(entity.getUserName());
			sysUserVCoin.setHistoryTotal(0);
			sysUserVCoin.setAvailableTotal(0);
			sysUserVCoin.setSysUser(entity);
			entity.setUserVCoin(sysUserVCoin);
			entity = this.sysUserRepository.save(entity);
			//创建用户资料
			syncEmptySysUserExt(entity.getId(), entity.getUserName());
			
			users.add(entity);
		}
		return users;
	}
	
	/**
	 * @param username
	 * @return 获取不重复的用户名，在用户名后面加数字序号1-9999
	 */
	public String getAvailableUsername(String username){
		for(int i = 1; i < 9999; i++){
			if(this.getUserInfo(username+i)==null){
				return username+i;
			}
		}
		return "";
	}
		
	public boolean changeSysUserStatus(long userId, UStatus status) {
		int result = this.sysUserRepository.changeStatus(userId, status.getValue());
		return result ==1;
	}
	
	public boolean validateSysUserPwd(long userId, String passWord){
		SysUserEntity userEntity = this.getUserInfoById(userId);
		if(userEntity!=null){
			String oldPwd = userEntity.getPassWord();
			byte[] saltBytes = credentialsDigest.getSaltBytes(userEntity.getPwdSalt());
			String pwd = SaltPwdUtils.md5Password(credentialsDigest, passWord, saltBytes);
			
			return oldPwd.equals(pwd) ? true : false;
		}else {
			return false;
		}
	}
	
	public boolean checkSysUserPwdSafe(String passWord) {
		if(StringUtils.isBlank(passWord)) {
			return false;
		}
		
		if(passWord.length()<6) {
			return false;
			
		}
		
		if(passWord.contains("123456") || passWord.contains("654321")) {
			return false;
		}
		
		if (StringUtil.repeatMax(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }, passWord, 6)) {
			//不能出现连续相同的数字密码
			return false;
		}
		
		return true;
		
	}
	
	public boolean changeSysUserPwd(long userId,String passWord) {
		SysUserEntity userEntity = this.getUserInfoById(userId);
		if(userEntity!=null){
			byte[] saltBytes = credentialsDigest.getSaltBytes(userEntity.getPwdSalt());
			String pwd = SaltPwdUtils.md5Password(credentialsDigest, passWord, saltBytes);
			
			userEntity.setPassWord(pwd);
			
			userEntity = sysUserRepository.save(userEntity);
			sysCacheService.clearUserInfoCache(userId);
			return userEntity !=null ? true : false;
		}else {
			return false;
		}
		
	}
	
	public boolean changeSysUserPwd(long userId,String oldPassWord,String passWord) throws Exception{
		SysUserEntity userEntity = this.getUserInfoById(userId);
		if(userEntity!=null){
			//			
			byte[] saltBytes = credentialsDigest.getSaltBytes(userEntity.getPwdSalt());
			
			String oldpwd = SaltPwdUtils.md5Password(credentialsDigest, oldPassWord, saltBytes);
			if(!userEntity.getPassWord().equals(oldpwd)){
				throw new Exception("原密码不正确");
			}
			
			String pwd = SaltPwdUtils.md5Password(credentialsDigest, passWord, saltBytes);
			userEntity.setPassWord(pwd);
			
			userEntity = sysUserRepository.save(userEntity);
			sysCacheService.clearUserInfoCache(userId);
			return userEntity !=null ? true : false;
		}else {
			return false;
		}
		
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delSysUser(long userId) throws Exception {
		SysUserEntity entity = this.sysUserRepository.findOne(userId);
		if(entity!=null){
			if(canDelSuperUser){
				this.sysUserRepository.delete(userId);
			}else if(!canDelSuperUser && !entity.isSuperUser()){
				this.sysUserRepository.delete(userId);
			}
			
		}
		//不管有还是没有，都清空一下用户缓存，保证数据的一致性
		sysCacheService.clearUserInfoCache(userId);
		return true;
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public int delSysUsers(Long[] userIds) throws Exception {
		//return sysUserRepository.delUsers(logIds);
		List<SysUserEntity> delList = new ArrayList<SysUserEntity>();
		List<SysUserEntity> entities = this.sysUserRepository.findAll(Arrays.asList(userIds));
		for (SysUserEntity sysUserEntity : entities) {
			//
			if(canDelSuperUser){
				delList.add(sysUserEntity);
			}else if(!canDelSuperUser && !sysUserEntity.isSuperUser()){
				//如果不允许删除超级管理员，非超管用户才可以被删除
				delList.add(sysUserEntity);
			}
			
		}
		this.sysUserRepository.delete(delList);	
		for (Long userId : userIds) {
			sysCacheService.clearUserInfoCache(userId);
		}
		return entities.size();
	}
	
	public Page<SysConfigEntity> findSysConfigList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysConfigEntity> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysConfigRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	
	public SysUserVCoin getUserPoints(long userId){
		//消费积分
		SysUserVCoin sysUserVCoin = this.sysUserVCoinRepository.getPoints(userId);
		return sysUserVCoin;
		
	}
	
	public double getAvailablePoints(long userId){
		//消费积分
		SysUserVCoin sysUserVCoin = this.sysUserVCoinRepository.getPoints(userId);
		if(sysUserVCoin == null){
			return 0;
		}
		return sysUserVCoin.getAvailableTotal();
		
	}
	
	public void obtainPoints(long userId,double points){
		//用户增加积分
		SysUserVCoin sysUserVCoin = sysUserVCoinRepository.getPoints(userId);
		if(sysUserVCoin == null){
			SysUserEntity user = sysCacheService.getUserInfoCache(userId);
			sysUserVCoin = new SysUserVCoin();
			sysUserVCoin.setUserName(user.getUserName());
			sysUserVCoin.setUserId(userId);
			sysUserVCoin.setAvailableTotal(points);
			sysUserVCoin.setHistoryTotal(points);
			sysUserVCoinRepository.save(sysUserVCoin);
		}else{
			this.sysUserVCoinRepository.obtainPoints(userId, points);
		}
	}

	
	public void spendPoints(long userId,double points){
		//消费积分
		SysUserVCoin sysUserVCoin = sysUserVCoinRepository.getPoints(userId);
		if(sysUserVCoin == null){
			SysUserEntity user = sysCacheService.getUserInfoCache(userId);
			sysUserVCoin = new SysUserVCoin();
			sysUserVCoin.setUserName(user.getUserName());
			sysUserVCoin.setUserId(userId);
			sysUserVCoin.setAvailableTotal(0);
			sysUserVCoin.setHistoryTotal(0);
			sysUserVCoinRepository.save(sysUserVCoin);
		}else{
			this.sysUserVCoinRepository.obtainPoints(userId, points * -1);
		}
	}
	
	public List<SysConfigEntity> getAllSysConfig() {
		List<SysConfigEntity> dataList = sysConfigRepository.findAll();
		return dataList;
	}
	
	public List<SysConfigEntity> getSysConfigContainskey(String keyName) {
		List<SysConfigEntity> dataList = sysConfigRepository.getSysConfigs(keyName);
		return dataList;
	}
	
	public SysConfigEntity getSysConfig(String keyName) {
		SysConfigEntity entity = sysConfigRepository.findOne(keyName);
		return entity;
	}
	
	public Map<String, Object> getSysConfigMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysConfigEntity> dataList = sysConfigRepository.findAll();
		for (SysConfigEntity sysConfig : dataList) {
			map.put(sysConfig.getKeyName(), sysConfig.getKeyValue());
		}
		return map;
	}
	

	
	public SysConfigEntity saveSysConfig(SysConfigEntity entity) {
		
		if(entity.getCreateTime()==null){
			entity.setCreateTime(DateTimeUtil.getCurrentTime());
		}
		try{
			return sysConfigRepository.save(entity);
		}finally{
			sysCacheService.clearSysConfigCache(entity.getKeyName());
		}
		
		
	}
	
	public List<SysConfigEntity> saveSysConfigs(List<SysConfigEntity> entities) {
		try{
			return sysConfigRepository.save(entities);
		}finally{
			for (SysConfigEntity sysConfigEntity : entities) {
				sysCacheService.clearSysConfigCache(sysConfigEntity.getKeyName());
			}
		}		
		
	}
	
	
	public boolean delSysConfig(String keyName){
		try{
			this.sysConfigRepository.delete(keyName);
			sysCacheService.clearSysConfigCache(keyName);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delSysConfigs(String[] keyNames){
		try{
			List<SysConfigEntity> entities = this.sysConfigRepository.findAll(Arrays.asList(keyNames));
			this.sysConfigRepository.delete(entities);
			for (String keyName : keyNames) {
				sysCacheService.clearSysConfigCache(keyName);
			}
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	
	public Page<SysMenuEntity> findSysMenuList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysMenuEntity> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysMenuRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	public List<SysMenuEntity> getAllMenus(){
		
		return sysMenuRepository.findAll();
		
	}
	
	public SysMenuEntity getSysMenu(long menuId) {
		return sysMenuRepository.findOne(menuId);
	}
	
	public List<SysMenuEntity> getChildMenus(long parentId){
		return this.sysMenuRepository.getChildMenus(parentId);
	}
	
	public SysMenuEntity saveSysMenu(SysMenuEntity entity) {
		long menuId = entity.getId();
		
		if (menuId > 0) {
			SysMenuEntity updateEntity = this.sysMenuRepository.findOne(menuId);
			
			updateEntity.setMenuName(entity.getMenuName());
			updateEntity.setParentId(entity.getParentId());
			//updateEntity.setMenuCode(entity.getMenuCode());
			updateEntity.setMenuUrl(entity.getMenuUrl());
			updateEntity.setNavMenu(entity.getNavMenu());
			updateEntity.setMenuArea(entity.getMenuArea());
			updateEntity.setIconCls(entity.getIconCls());
			updateEntity.setSort(entity.getSort());
			updateEntity.setLastUpdate(DateTimeUtil.getCurrentTime());

			
			try {
				return this.sysMenuRepository.save(updateEntity);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return entity;
			}
		}else{
			//新增
			entity.setCreateTime(DateTimeUtil.getCurrentTime());
			entity.setLastUpdate(DateTimeUtil.getCurrentTime());
			
			try {
				return this.sysMenuRepository.save(entity);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return entity;
			}
			
		}
		
		
	}
	
	
	public boolean delSysMenu(long menuId){
		try{
			//this.sysMenuRepository.delete(menuId);
			//只删菜单和角色的关联关系，不删角色
			SysMenuEntity sysMenu = sysMenuRepository.findOne(menuId);
			for (SysRoleEntity role : sysMenu.getRoles()) {
				role.getMenus().remove(sysMenu);
			}
			
			sysMenuRepository.delete(sysMenu);
			return true;
		}catch(Exception e){
			logger.error("delSysMenu",e);
			return false;
		}
	}
	
	@Transactional
	public int delSysMenus(Long[] menuIds){
		try{
			List<SysMenuEntity> entities = this.sysMenuRepository.findAll(Arrays.asList(menuIds));
			this.sysMenuRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error("delSysMenus",e);
			return 0;
		}
	}
	
	public List<SysRoleEntity> findSysRoleEnabledList(){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_status", 1);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysRoleEntity> spec = SpecDynamic.buildSpec(filters.values());
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		return sysRoleRepository.findAll(spec, sort);
	}
	
	public Page<SysRoleEntity> findSysRoleList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysRoleEntity> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysRoleRepository.findAll(spec, pageParam.getPageRequest());
		
	}

	public boolean delSysRole(long roleId){
		try{
			SysRoleEntity sysRole = this.sysRoleRepository.findOne(roleId);
			sysRole.getMenus().clear();
			sysRole.getPerms().clear();
			sysRole.getUsers().clear();			
			this.sysRoleRepository.delete(sysRole);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean saveRoleResource(long roleId,long[] menuIds) {
		
		SysRoleEntity roleEntity = this.sysRoleRepository.getRole(roleId);
		
		Set<SysMenuEntity> menus = new HashSet<SysMenuEntity>();
		//提交的菜单ID
		for (long menuId : menuIds) {
			SysMenuEntity menuEntity = this.sysMenuRepository.findOne(menuId);
			menus.add(menuEntity);
			
		}
		
		roleEntity.setMenus(menus);
		
		try {
			this.sysRoleRepository.save(roleEntity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
				
		return true;
		
	}
	
	public boolean saveSysRole(SysRoleEntity roleEntity){
		long roleId = roleEntity.getId();
		String roleName = roleEntity.getRoleName();
		
		if (roleId > 0) {
			SysRoleEntity updateEntity = this.sysRoleRepository.findOne(roleId);
			
			if(!updateEntity.getRoleName().equals(roleName)){
				//角色名修改
				if(this.sysRoleRepository.getRoleByName(roleName)!=null){
					//有同名角色
					return false;
				}
				
			}
			
			updateEntity.setStatus(roleEntity.getStatus());
			updateEntity.setRoleName(roleName);
			updateEntity.setLastUpdate(DateTimeUtil.getCurrentTime());
						
			try {
				this.sysRoleRepository.save(updateEntity);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return false;
			}
		}else{
			//新增
			if(this.sysRoleRepository.getRoleByName(roleName)!=null){
				//有同名角色
				return false;
			}
			
			roleEntity.setCreateTime(DateTimeUtil.getCurrentTime());
			roleEntity.setLastUpdate(DateTimeUtil.getCurrentTime());
			
			try {
				this.sysRoleRepository.save(roleEntity);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return false;
			}
			
		}
		
		return true;
	}
	
	public boolean saveUserRole(UserRoleForm userRole) throws SaveFailureException {
		long userId =  userRole.getId();
		//取用户
		SysUserEntity userEntity = this.getUserInfoById(userId);
		Set<SysRoleEntity> userRoles = new HashSet<SysRoleEntity>();
		//提交的角色ID
		List<Long> roles = userRole.getRoles();
		for (Long roleId : roles) {
			SysRoleEntity roleEntity = this.getSysRole(roleId);
			userRoles.add(roleEntity);
		}
		
		userEntity.setRoles(userRoles);
		
		try {
			this.sysUserRepository.save(userEntity);
		} catch (Exception e) {
			throw new SaveFailureException("保存用户角色关系失败", e);
		}

		return true;
		
	}
	
	
	public SysRoleEntity getSysRole(long roleId){
		return this.sysRoleRepository.getRole(roleId);
	}
	
	
	public void sysLog(SysLogEntity sysLogEntity){
		
		this.sysLogRepository.save(sysLogEntity);
	}
	
	public Page<SysUserVCoin> findSysUCoinList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysUserVCoin> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysUserVCoinRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	public void syncEmptySysUserExt(long userId, String userName) {
		SysUserExt sysUserExt = this.getSysUserExt(userId);
		sysUserExt.setUserName(userName);
		
		this.sysUserExtRepository.save(sysUserExt);		
		this.sysCacheService.clearSysUserExtCache(sysUserExt.getUserId());
		
	}
	
	
	public SysUserExt getSysUserExt(long userId) {
		SysUserExt entity =  this.sysUserExtRepository.findUserExt(userId);
		if(entity==null){
			entity = new SysUserExt();
			entity.setUserId(userId);
		}
		return entity;
	}
	
	public void updateUserExt(SysUserExt sysUserExt){
		
		long userId = sysUserExt.getUserId();
		
		SysUserExt update = this.getSysUserExt(userId);
		
		BeanUtils.copyProperties(sysUserExt, update,"id");
		
		if(StringUtils.isBlank(update.getUserName())){
			SysUserEntity sysUser =  this.getUserInfoById(userId);
			update.setUserName(sysUser.getUserName());
		}
       logger.debug("修改："+update.getBankCard());
		this.sysUserExtRepository.save(update);
		
		this.sysCacheService.clearSysUserExtCache(sysUserExt.getUserId());
	}
	
	public List<SysUserEntity> findUserByUsername(String userName) {
		if(StringUtils.isBlank(userName)){
			return new ArrayList<SysUserEntity>();
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();

		searchParams.put(SpecOperator.LIKE + "_userName", userName);
		
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysUserEntity> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysUserRepository.findAll(spec);
	}

	public List<SysUserEntity> findUserByUsername(String[] user) {
		if(user == null){
			return new ArrayList<SysUserEntity>();
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<String> userList = Arrays.asList(user);
		searchParams.put(SpecOperator.IN + "_userName", userList);
		
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysUserEntity> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysUserRepository.findAll(spec);
	}

	public List<SysUserEntity> getUserInfoByIds(Long[] ids) {
		// TODO Auto-generated method stub
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Long> userList = Arrays.asList(ids);
		searchParams.put(SpecOperator.IN + "_id", userList);
		
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysUserEntity> spec = SpecDynamic.buildSpec(filters.values());
		return sysUserRepository.findAll(spec);
	}
	
	public SysLogEntity getLoginLog(long userId){
		return this.sysLogRepository.getUserLog(userId, LogType.UserLogin, new Sort(Direction.DESC, "createTime"));
	}
	
	public boolean getWatermarkDefaultEnable(){
		boolean defWaterMark = sysContext.getSysConfigInt("sys.watermark.default.enable", 1) == 1 ? true : false;
		return defWaterMark;
	}
	
	public SysUserInfo getSysUserFullInfo(long userId) {
		//
		SysUserEntity user = this.getUserInfoById(userId);
		SysUserExt userExt = this.getSysUserExt(userId);
		if (user == null) {
			return null;
		}

		SysUserInfo sysUserInfo = new SysUserInfo();

		sysUserInfo.setUserId(userId);
		sysUserInfo.setUserName(user.getUserName());
		sysUserInfo.setuType(user.getuType());
		
		if (userExt != null) {
			sysUserInfo.setNickName(userExt.getNickName());
			sysUserInfo.setRealName(userExt.getRealName());
			sysUserInfo.setAvatarUrl(userExt.getAvatarUrl());
			sysUserInfo.setPhone(userExt.getPhone());
			sysUserInfo.setEmail(userExt.getEmail());
			sysUserInfo.setGender(userExt.getGender());
		}		

		return sysUserInfo;
	}
}
