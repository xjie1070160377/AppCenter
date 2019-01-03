package cn.mooc.app.module.service.mcenter.controller;

import cn.mooc.app.core.controller.ModuleController;


public class ServiceController extends ModuleController {
	
	/**
	 * 页面操作状态
	 */
	public static final String OPRT = "oprt";
	/**
	 * 新增状态
	 */
	public static final String CREATE = "create";
	/**
	 * 编辑状态
	 */
	public static final String EDIT = "edit";
	/**
	 * 查看状态
	 */
	public static final String VIEW = "view";

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "service";
	}

}
