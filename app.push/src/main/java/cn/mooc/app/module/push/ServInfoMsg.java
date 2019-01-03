package cn.mooc.app.module.push;

import java.io.Serializable;

public class ServInfoMsg  implements Serializable {

	private static final long serialVersionUID = -8737336489961241477L;
	
	private int spId;
	private String spName;
	private int msgId;
	
	public int getSpId() {
		return spId;
	}
	public void setSpId(int spId) {
		this.spId = spId;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	
	
	
}
