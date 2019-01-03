package cn.mooc.app.plugin.task.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.plugin.PluginDataService;

@Service
//@Transactional
public class TestTransactionalService {

	@Autowired
	private PluginDataService pluginDataService;
	
	public TestTransactionalService(){
		
	}

	public void test(){
		
		System.out.println("TestBssService.test() -----------------------------");
		
	}
	
}
