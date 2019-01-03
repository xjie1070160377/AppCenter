package cn.mooc.app.module.service.web.controller;

public class PserviceController {

	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String OPERATION_SUCCESS = "operationSuccess";
	public static final String OPERATION_FAILURE = "operationFailure";
	public static final String SAVE_SUCCESS = "saveSuccess";
	public static final String DELETE_SUCCESS = "deleteSuccess";
	
	public String getTemplate(String tpl) {
		return "theme/platService/"+tpl;
	}
}
