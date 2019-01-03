package cn.mooc.app.module.cms.model;

import java.io.Serializable;

public class DiscoverServerItem implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7465117607734383942L;
	private String name;
	private Integer linkType;
	private String image;
	private String url;
	private String enabled;
	private String range;
	
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLinkType() {
		return linkType;
	}
	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
}
