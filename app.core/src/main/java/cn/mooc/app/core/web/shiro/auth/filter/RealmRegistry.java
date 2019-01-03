package cn.mooc.app.core.web.shiro.auth.filter;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealmRegistry {

	@Autowired
	private DefaultWebSecurityManager webSecurityManager;
	
	public void addAuthRealm(Realm realm){
		webSecurityManager.getRealms().add(realm);
	}
	
	public void delAuthRealm(Realm realm){
		webSecurityManager.getRealms().remove(realm);
	}
	
}
