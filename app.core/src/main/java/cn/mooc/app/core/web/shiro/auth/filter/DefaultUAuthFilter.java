package cn.mooc.app.core.web.shiro.auth.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUAuthFilter {

	@Autowired
	private AuthFilterRegistry authFilterRegistry;
	
	//@PostConstruct
	public void initUserFilter(){
		//建议在shiro配置文件中进行初始化
		//使用 @PostConstruct 进行动态注入
		
		AuthFilterConfig config = new AuthFilterConfig();		
		config.setUsernameParam("userName");
		config.setPasswordParam("passWord");
		config.setFilterName("uauth");
		config.setAuthLoginUrl("/ulogin");
		config.setSuccessUrl("/index");
		
		authFilterRegistry.registry(config);
		
		
	}
	
	//@PreDestroy
	public void destroy(){
		
		//执行系统shutdown前要做的事情
		
		
		
	}
	
}
