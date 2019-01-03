package cn.mooc.app.core.web.shiro.auth.db;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

public interface AuthResourceExtend {

	/**
	 * 加载模块的菜单资源到shiro中进行权限管理
	 * @param filterChainManager
	 * 
	 * 使用如下方式增加菜单资源：
	 * resourceExtend.addResource("menuUrl", "Role_ID/Role_Name");
	 * 
	 */
	void loadPermissionResource(DbResourceExtend resourceExtend) throws Exception;
	
	/**
	 * 用户登录成功后，需要执行的扩展逻辑
	 * 
	 * （例如加载额外该用户的角色信息）
	 * 
	 * 通过 loginUserInfo 取到用户的基本信息，然后在子模块中加载其对应的角色权限，通过如下方式添加：
	 * 
	 * authorizationInfo.addRole("RoleName");
	 * authorizationInfo.addStringPermission("cn.xxx.user.add");
	 * 
	 * @param loginUserInfo
	 * @param authorizationInfo
	 * @throws Exception
	 */
	void userAuthInfoExtend(LoginUserInfo loginUserInfo, SimpleAuthorizationInfo authorizationInfo) throws Exception;
	
}
