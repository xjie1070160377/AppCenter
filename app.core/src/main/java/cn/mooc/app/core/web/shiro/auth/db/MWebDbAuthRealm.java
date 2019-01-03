package cn.mooc.app.core.web.shiro.auth.db;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.core.data.entity.SysPermEntity;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.security.CredentialsDigestMatcher;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.web.shiro.model.AppUserToken;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

/**
 * 
 * 方法执行顺序：
 * 1、doGetAuthenticationInfo(AuthenticationToken token)
 * 2、doGetAuthorizationInfo(PrincipalCollection principals)
 * 
 * @author Taven
 *
 */
public class MWebDbAuthRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private DbShiroDataService dbShiroDataService;
	
	private CredentialsDigestMatcher credentialsMatcher;
	
	@PostConstruct
	public void initiated() {
		//默认加密解密的类是 SimpleCredentialsMatcher 可以通过 this.setCredentialsMatcher(credentialsMatcher); 来改变，也可以在配置文件中用属性注入
		//例如 HashedCredentialsMatcher、Md5CredentialsMatcher、PasswordMatcher、SimpleCredentialsMatcher等
		//
		//不带盐值方式
		//this.setCredentialsMatcher(new PasswordMatcher());
		//使用MD5
		//this.setCredentialsMatcher(new Md5CredentialsMatcher());
		//this.setCredentialsMatcher(new HashedCredentialsMatcher("MD5"));
		//使用明文密码方式
		//this.setCredentialsMatcher(new SimpleCredentialsMatcher());
		//
		//this.setCredentialsMatcher(new HashedCredentialsMatcher("SHA-256"));
		
		this.setCredentialsMatcher(credentialsMatcher);
		
		
	}
	
	/*
	 * 根据接收到的用户验证信息，提供可认证的信息对象和加密解密算法
	 * 
	 * 如果是用户名和密码方式，这个Token对象是表单中的信息，即：org.apache.shiro.authc.UsernamePasswordToken
	 * 如果是CAS集成，即：org.apache.shiro.cas.CasToken
	 * 也可以自己实现 AuthenticationToken 接口，或 RememberMeAuthenticationToken
	 * 
	 * 该方法的主要目的是根据传过来的用户验证信息，得到一个 AuthenticationInfo 对象，例如 SimpleAuthenticationInfo
	 *  (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if((token instanceof AppUserToken)==false){
			return null;
		}
		
		//return shiroUserService.doGetAuthenticationInfo(token);
		//从后台管理用户表中进行校验
		AppUserToken authToken = (AppUserToken) token;
		
		String userName = authToken.getUsername();
		if(StringUtils.isBlank(userName)){
			//用户名为空，登录失败
			return null;
		}
		
		SysUserEntity sysUserEntity = sysDataService.getUserInfo(userName);
		if(sysUserEntity!=null && sysUserEntity.getStatus()==1){
			String realmName = this.getName();
			//封装 SimpleAuthenticationInfo 对象，在父类 assertCredentialsMatch(token, info); 方法中会被调用
			
			//带盐值方式
			/**
			 String salt = sysUserEntity.getPwdSalt();	
			 return new SimpleAuthenticationInfo(new LoginUserInfo(sysUserEntity.getId(),	sysUserEntity.getUserName()), 
					 sysUserEntity.getPassWord(),ByteSource.Util.bytes(salt), realmName);
			 */
			
			//兼容之前CMS加密方式
			 //byte[] saltBytes = SaltPwdUtils.getSaltBytes(sysUserEntity.getPwdSalt());
			 byte[] saltBytes = credentialsMatcher.getDigest().getSaltBytes(sysUserEntity.getPwdSalt());
			 return new SimpleAuthenticationInfo(new LoginUserInfo(sysUserEntity.getId(),	sysUserEntity.getUserName()), 
					 sysUserEntity.getPassWord(),ByteSource.Util.bytes(saltBytes), realmName);
			 
			
			//不带盐值方式			
			//return new SimpleAuthenticationInfo(new LoginUserInfo(sysUserEntity.getId(),	sysUserEntity.getUserName()), sysUserEntity.getPassWord(), realmName);
			
		
		}else{
			//用户状态不合法
			return null;
		}

	}
	
	
	/* 
	 * 取得验证通过用户的权限对象信息
	 * 
	 * 该方法的主要目的是根据验证通过后的用户信息，得到一个 AuthorizationInfo 对象，例如 SimpleAuthorizationInfo
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		LoginUserInfo loginUserInfo = (LoginUserInfo) principals.getPrimaryPrincipal();
		
		SysUserEntity sysUserEntity = sysDataService.getUserInfoById(loginUserInfo.getUserId());
		
		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
		
		//设置用户的权限信息
		//authInfo.setStringPermissions(stringPermissions);
		Set<SysRoleEntity> userRoles = sysUserEntity.getRoles();
		for (SysRoleEntity userRole : userRoles) {
			//
			if (userRole.getRoleType() == 1) {
				authInfo.addRole("superUser");
			}
			
			//该用户拥有的角色
			authInfo.addRole("Role_"+userRole.getId());
			
			//该角色拥有的Perms
			for (SysPermEntity sysPermEntity : userRole.getPerms()) {
				String permission = sysPermEntity.getPermCode();
				if(StringUtils.isNotBlank(permission)){
					authInfo.addStringPermission(permission);
				}
			}
			
			
		}
		
		//所有用户默认有user角色
		authInfo.addRole("user");
		
		//如果是超级管理员
		if (sysUserEntity.isSuperUser()) {
			authInfo.addRole("superUser");
			authInfo.addStringPermission("**");
		}
		
		this.loadUserAuthInfoExtend(loginUserInfo, authInfo);
		
		return authInfo;	
		
	}
	
	public void loadUserAuthInfoExtend(LoginUserInfo loginUserInfo, SimpleAuthorizationInfo authorizationInfo){
		
		Set<AuthResourceExtend> authResourceExtends = dbShiroDataService.getAuthResourceExtends();
		for (AuthResourceExtend authResourceExtend : authResourceExtends) {
			
			try {
				authResourceExtend.userAuthInfoExtend(loginUserInfo, authorizationInfo);
			} catch (Exception e) {
				logger.error("loadUserAuthInfoExtend", e);
			}
		}
		
				
	}

	public CredentialsDigestMatcher getCredentialsMatcher() {
		return credentialsMatcher;
	}

	public void setCredentialsMatcher(CredentialsDigestMatcher credentialsMatcher) {
		this.credentialsMatcher = credentialsMatcher;
	}
	
	



}
