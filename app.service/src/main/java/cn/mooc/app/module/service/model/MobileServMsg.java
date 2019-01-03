package cn.mooc.app.module.service.model;

import java.util.List;

public class MobileServMsg {
	private Integer msgid;
	private Integer msgType;
	private String pushTime;
	private List<MobileServMsgDetail> detail;
	
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	public List<MobileServMsgDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<MobileServMsgDetail> detail) {
		this.detail = detail;
	}
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
}
