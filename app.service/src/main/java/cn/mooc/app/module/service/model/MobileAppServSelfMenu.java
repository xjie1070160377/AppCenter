package cn.mooc.app.module.service.model;

import java.util.List;

public class MobileAppServSelfMenu {
	private String name;
	private Integer type;
	private String value;
	private Integer order;
	private List<MobileAppServSelfMenu> child;
	
//	0   打开网址
//	1   打开服务号的新闻，消息
	public static final int type_openurl = 0;
	public static final int type_openmsg = 1;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public List<MobileAppServSelfMenu> getChild() {
		return child;
	}
	public void setChild(List<MobileAppServSelfMenu> child) {
		this.child = child;
	}
	
}
