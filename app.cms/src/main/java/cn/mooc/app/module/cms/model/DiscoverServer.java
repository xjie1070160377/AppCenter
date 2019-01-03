package cn.mooc.app.module.cms.model;

import java.io.Serializable;
import java.util.List;

public class DiscoverServer implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4741835848765239972L;
	private String serviceName;
	private List<DiscoverServerItem> items;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public List<DiscoverServerItem> getItems() {
		return items;
	}
	public void setItems(List<DiscoverServerItem> items) {
		this.items = items;
	}
}
