package cn.mooc.app.core.plugin.support.model;

import java.io.Serializable;

public class PluginParam implements Serializable {

	private static final long serialVersionUID = -3654310867569236094L;
	
	private String desc;
	private Object val;
	
	public PluginParam(){}
	
	public PluginParam(Object pVal){
		this.val = pVal;
	}
	
	public PluginParam(Object pVal, String desc){
		this.val = pVal;
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

	
}
