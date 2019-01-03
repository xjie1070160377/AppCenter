package cn.mooc.app.module.cms.model;

import cn.mooc.app.module.cms.data.entity.Collect;
import cn.mooc.app.module.cms.data.entity.Node;

public class CollectModel {
	
	private Integer id;
	private Integer siteId;
	private Integer nodeId;
	private String nodeName;
	private Integer userId;
	private String name;
	private String charset;
	private String userAgent;
	private Integer pageBegin;
	private Integer pageEnd;
	private Integer intervalMin;
	private Integer intervalMax;
	private Boolean desc;
	private String listPattern;
	private String listNextPattern;
	private String itemAreaPattern;
	private String itemPattern;
	private String blockAreaPattern;
	private String blockPattern;
	private Boolean listNextReg;
	private Boolean itemAreaReg;
	private Boolean itemReg;
	private Boolean blockAreaReg;
	private Boolean blockReg;
	private Boolean submit;
	private Boolean downloadImage;
	private Boolean allowDuplicate;
	private Integer status;
	
	public CollectModel(Collect collect) {
		super();
		this.id = collect.getId();
		this.siteId = collect.getSite().getId();
		this.nodeId = collect.getNode().getId();
		this.nodeName = collect.getNode().getName();
		this.userId = collect.getUserId();
		this.name = collect.getName();
		this.charset = collect.getCharset();
		this.userAgent = collect.getUserAgent();
		this.pageBegin = collect.getPageBegin();
		this.pageEnd = collect.getPageEnd();
		this.intervalMin = collect.getIntervalMin();
		this.intervalMax = collect.getIntervalMax();
		this.desc = collect.getDesc();
		this.listPattern = collect.getListPattern();
		this.listNextPattern = collect.getListNextPattern();
		this.itemAreaPattern = collect.getItemAreaPattern();
		this.itemPattern = collect.getItemPattern();
		this.blockAreaPattern = collect.getBlockAreaPattern();
		this.blockPattern = collect.getBlockPattern();
		this.listNextReg = collect.getListNextReg();
		this.itemAreaReg = collect.getItemAreaReg();
		this.itemReg = collect.getItemReg();
		this.blockAreaReg = collect.getBlockAreaReg();
		this.blockReg = collect.getBlockReg();
		this.submit = collect.getSubmit();
		this.downloadImage = collect.getDownloadImage();
		this.allowDuplicate = collect.getAllowDuplicate();
		this.status = collect.getStatus();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public Integer getPageBegin() {
		return pageBegin;
	}
	public void setPageBegin(Integer pageBegin) {
		this.pageBegin = pageBegin;
	}
	public Integer getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}
	public Integer getIntervalMin() {
		return intervalMin;
	}
	public void setIntervalMin(Integer intervalMin) {
		this.intervalMin = intervalMin;
	}
	public Integer getIntervalMax() {
		return intervalMax;
	}
	public void setIntervalMax(Integer intervalMax) {
		this.intervalMax = intervalMax;
	}
	public Boolean getDesc() {
		return desc;
	}
	public void setDesc(Boolean desc) {
		this.desc = desc;
	}
	public String getListPattern() {
		return listPattern;
	}
	public void setListPattern(String listPattern) {
		this.listPattern = listPattern;
	}
	public String getListNextPattern() {
		return listNextPattern;
	}
	public void setListNextPattern(String listNextPattern) {
		this.listNextPattern = listNextPattern;
	}
	public String getItemAreaPattern() {
		return itemAreaPattern;
	}
	public void setItemAreaPattern(String itemAreaPattern) {
		this.itemAreaPattern = itemAreaPattern;
	}
	public String getItemPattern() {
		return itemPattern;
	}
	public void setItemPattern(String itemPattern) {
		this.itemPattern = itemPattern;
	}
	public String getBlockAreaPattern() {
		return blockAreaPattern;
	}
	public void setBlockAreaPattern(String blockAreaPattern) {
		this.blockAreaPattern = blockAreaPattern;
	}
	public String getBlockPattern() {
		return blockPattern;
	}
	public void setBlockPattern(String blockPattern) {
		this.blockPattern = blockPattern;
	}
	public Boolean getListNextReg() {
		return listNextReg;
	}
	public void setListNextReg(Boolean listNextReg) {
		this.listNextReg = listNextReg;
	}
	public Boolean getItemAreaReg() {
		return itemAreaReg;
	}
	public void setItemAreaReg(Boolean itemAreaReg) {
		this.itemAreaReg = itemAreaReg;
	}
	public Boolean getItemReg() {
		return itemReg;
	}
	public void setItemReg(Boolean itemReg) {
		this.itemReg = itemReg;
	}
	public Boolean getBlockAreaReg() {
		return blockAreaReg;
	}
	public void setBlockAreaReg(Boolean blockAreaReg) {
		this.blockAreaReg = blockAreaReg;
	}
	public Boolean getBlockReg() {
		return blockReg;
	}
	public void setBlockReg(Boolean blockReg) {
		this.blockReg = blockReg;
	}
	public Boolean getSubmit() {
		return submit;
	}
	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}
	public Boolean getDownloadImage() {
		return downloadImage;
	}
	public void setDownloadImage(Boolean downloadImage) {
		this.downloadImage = downloadImage;
	}
	public Boolean getAllowDuplicate() {
		return allowDuplicate;
	}
	public void setAllowDuplicate(Boolean allowDuplicate) {
		this.allowDuplicate = allowDuplicate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
}
