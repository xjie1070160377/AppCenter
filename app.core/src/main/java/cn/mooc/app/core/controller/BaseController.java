package cn.mooc.app.core.controller;

import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.core.context.WebContext;

public abstract class BaseController {

	@Autowired
	protected WebContext webContext;
	
	
	
	
	
}
