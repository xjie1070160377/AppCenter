package cn.mooc.app.module.cms.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.mooc.app.module.cms.data.entity.CmsImage;
import cn.mooc.app.module.cms.enums.CmsImageSourceType;

public class CmsImageModel {
	
	private Integer id;
	private String type;
	private Integer typeId;
	private String createTime;
	private String url;
	private String sourceType;
	private Integer sourceId;
	private String title;
	private Integer creatorId;
	private String ip;
	private Integer fileSize;
	
	public CmsImageModel(CmsImage image) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		this.id = image.getId();
		this.type = image.getCmsImageType().getName();
		this.typeId = image.getCmsImageType().getId();
		this.createTime = simpleDateFormat.format(image.getCreateTime());
		this.url = image.getUrl();
		this.sourceType = image.getSourceType() != null ? CmsImageSourceType.getCName(image.getSourceType()) : "";
		this.sourceId = image.getSourceId();
		this.title = image.getTitle();
		this.ip = image.getIp();
		this.fileSize = image.getFileSize();
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
}
