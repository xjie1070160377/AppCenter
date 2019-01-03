package cn.mooc.app.module.service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.service.data.entity.AppServiceUser;
import cn.mooc.app.module.service.data.entity.AppServiceUserPK;
import cn.mooc.app.module.service.data.rds.AppServiceUserRepository;

/**
 * AppServiceUserService
 * 服务号关注用户服务
 * 
 * @author zjj
 * @date 2016-08-16
 */
@Service
public class AppServiceUserService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppServiceUserRepository AppServiceUserRepository;
	@Autowired
	private WebContext webcontext;
	
	public AppServiceUser getAppServiceUserById(AppServiceUserPK id) {
		AppServiceUser entity = AppServiceUserRepository.findOne(id);
		return entity;
	}
	
	public List<AppServiceUser> getAllAppServiceUsers(){
		return this.AppServiceUserRepository.findAll();
	}
	
	public Page<Map> findPageByServiceId(final Integer serviceId, Pageable pageable) {
		
		Map<String, Object> searchParams = new HashMap();
		searchParams.put(SpecOperator.EQ+"_id.serviceId", serviceId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<AppServiceUser> spec = SpecDynamic.buildSpec(filters.values());
		Page<AppServiceUser> users = AppServiceUserRepository.findAll(spec, pageable);
		
		List<Map> list = new ArrayList<Map>();
		List<AppServiceUser> us = users.getContent();
		if(us != null){
			for(AppServiceUser u : us){
				
				SysUserInfo userInfo = webcontext.getSysUserFullInfo(u.getId().getUserid());
				Map params = new HashMap();
				params.put("userid", userInfo.getUserId());
				params.put("username", userInfo.getUserName());
				params.put("realname", userInfo.getRealName());
				list.add(params);
			}
		}
		 return new PageImpl<Map>(list, pageable, users.getTotalElements());
	}
	
	public Page<AppServiceUser> findAppServiceUserPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<AppServiceUser> spec = SpecDynamic.buildSpec(filters.values());

		return AppServiceUserRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public AppServiceUser saveAppServiceUser(AppServiceUser entity) throws Exception {
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServiceUserRepository.save(entity);
	}

	@Transactional
	public AppServiceUser updateAppServiceUser(AppServiceUser entity) throws Exception{
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServiceUserRepository.save(entity);
	}
	
	@Transactional
	public boolean delAppServiceUser(AppServiceUserPK id){
		try{
			this.AppServiceUserRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAppServiceUsers(AppServiceUserPK[] ids){
		try{
			List<AppServiceUser> entities = this.AppServiceUserRepository.findAll(Arrays.asList(ids));
			this.AppServiceUserRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
