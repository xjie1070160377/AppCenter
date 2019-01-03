package cn.mooc.app.module.guestbook.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.service.FuncLoader;
import cn.mooc.app.core.service.ViewFuncRegistry;
import cn.mooc.app.module.guestbook.web.controller.view.func.MessagePage;

@Service
public class MessageFuncLoader implements FuncLoader{

	@Autowired
	private ViewFuncRegistry viewFuncRegistry;
	
	@PostConstruct
	public void startLoading(){
		
		viewFuncRegistry.addFuncLoader(this);		
		
	}

	@Override
	public void registerFunctions() {
		viewFuncRegistry.registerFunction("MessagePage", new MessagePage());
	}
	
}

