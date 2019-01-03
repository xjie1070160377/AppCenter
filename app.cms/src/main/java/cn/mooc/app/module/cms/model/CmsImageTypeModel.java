package cn.mooc.app.module.cms.model;

import java.text.SimpleDateFormat;

import cn.mooc.app.module.cms.data.entity.CmsImageType;

public class CmsImageTypeModel {
	
	private Integer id;
	private String name;
	private String createTime;
	
	public CmsImageTypeModel(CmsImageType type) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.id = type.getId();
		this.name = type.getName();
		this.createTime = simpleDateFormat.format(type.getCreateTime());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
