package cn.mooc.app.core.plugin.support.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MsgContent {

	private List<String> destList = new LinkedList<String>();
	/**
	 * 消息模板，例如：1、验证码；2、密码找回
	 */
	private int msgTemplateId;
	
	private Map<String, Object> param = new HashMap<String, Object>();
	
	
	public int getMsgTemplateId() {
		return msgTemplateId;
	}
	public void setMsgTemplateId(int msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}
	public List<String> getDestList() {
		return destList;
	}
	public void setDestList(List<String> destList) {
		this.destList = destList;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public void addDest(String dest){
		this.destList.add(dest);
	}
	
	public void addParam(String pName, Object pVal){
		this.param.put(pName, pVal);
	}
	
	public <T> T getParam(String pName){
		if(this.param.containsKey(pName)){
			return (T)this.param.get(pName);
		}else{
			return null;
		}
	}
	
	public <T> T getParam(String pName, T def){
		if(this.param.containsKey(pName)){
			return (T)this.param.get(pName);
		}else{
			return def;
		}
		
	}
	
	
}
