package cn.mooc.app.module.api.model;

import java.io.Serializable;


public class ResultData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8059015756633687043L;

	/**
	 * 接口调用是否成功
	 */
	private boolean isSuccess;

	/**
	 * 成功/失败 消息
	 */
	private String msg = "";
	
	/**
	 * 错误状态：针对token时，0为token正常，1为token失效，2为没有token
	 */
	private String status;

	/**
	 * 返回给接口调用者的包装数据，可以是字符内容或JSON内容，可根据约定调整
	 */
	private String dataObj;

	public ResultData() {
	}

	public ResultData(boolean isSuccess, String msg, String dataObj) {
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.dataObj = dataObj;
	}
	
	public ResultData(boolean isSuccess, String msg, String dataObj, String status) {
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.dataObj = dataObj;
		this.status = status;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDataObj() {
		return dataObj;
	}

	public void setDataObj(String dataObj) {
		this.dataObj = dataObj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static ResultData createError(String msg) {
		return new ResultData(false, msg, null);
	}
	
	public static ResultData createError(String msg, String status) {
		return new ResultData(false, msg, null, status);
	}

	public static ResultData createSuccess(String obj) {
		return new ResultData(true, null, obj);
	}
	
	public static ResultData createSuccess(String obj, String status) {
		return new ResultData(true, null, obj, status);
	}

	public static ResultData create(boolean isSuccess, String obj, String msg) {
		return new ResultData(isSuccess, msg, obj);
	}

}
