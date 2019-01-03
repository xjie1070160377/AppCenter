package cn.mooc.app.module.api.model;

import cn.mooc.app.core.utils.StringUtil;

public class MobileResultData {

	public String msg_code;
	public String reason;
	//错误状态：针对token时，0为token正常，1为token失效，2为没有token
	public String invalidToken;
	public Object result = null;

	public String getMsg_code() {
		return msg_code;
	}

	public void setMsg_code(String msg_code) {
		this.msg_code = msg_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getInvalidToken() {
		return invalidToken;
	}

	public void setInvalidToken(String invalidToken) {
		this.invalidToken = invalidToken;
	}

	public MobileResultData(String msg_code, String reason, String invalidToken, Object result) {
		this.msg_code = msg_code;
		this.reason = reason;
		this.invalidToken = invalidToken;
		if(StringUtil.isNotEmpty(result)){
			this.result = result;
		}
	}

//	public static MobileResultData createError(String reason, Object result) {
//		return new MobileResultData("1", reason, null);
//	}
	public static MobileResultData createError(String reason) {
		return new MobileResultData("1", reason, "0", null);
	}
	
	public static MobileResultData createError(String reason, String status) {
		return new MobileResultData("1", reason, status, null);
	}
	
	public static MobileResultData createInvalidToken(String reason) {
		return new MobileResultData("1", reason, "1", null);
	}

	public static MobileResultData createSuccess(Object obj) {
		return new MobileResultData("0", "", "0", obj);
	}
	
	public static MobileResultData createSuccessMsg(String msg) {
		return new MobileResultData("0", msg, "0", "");
	}

}
