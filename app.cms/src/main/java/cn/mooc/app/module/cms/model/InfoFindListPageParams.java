package cn.mooc.app.module.cms.model;

import java.util.Date;
import java.util.Map;

public class InfoFindListPageParams {
	private Integer[] modelId;
	private Integer[] nodeId;
	private Integer[] attrId;
	private Integer[] specialId;
	private Integer[] siteId;
	private Integer[] mainNodeId;
	private Long[] userId;
	private String[] treeNumber;
	private String[] specialTitle;
	private Integer[] priority;
	private Date beginDate;
	private Date endDate;
	private String[] title;
	private Integer[] includeId;
	private Integer[] excludeId;
	private Integer[] excludeMainNodeId;
	private String[] excludeTreeNumber;
	private Boolean isWithImage;
	private Boolean isWithVideo;
	private Boolean isAudio;
	private String[] status;
	private Integer[] p1;
	private Integer[] p2;
	private Integer[] p3;
	private Integer[] p4;
	private Integer[] p5;
	private Integer[] p6;
	private Map<String, String> customer;
	private boolean showDescendants;
	private String nodeNumber;
	
	public Integer[] getModelId() {
		return modelId;
	}
	public void setModelId(Integer[] modelId) {
		this.modelId = modelId;
	}
	public Integer[] getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer[] nodeId) {
		this.nodeId = nodeId;
	}
	public Integer[] getAttrId() {
		return attrId;
	}
	public void setAttrId(Integer[] attrId) {
		this.attrId = attrId;
	}
	public Integer[] getSpecialId() {
		return specialId;
	}
	public void setSpecialId(Integer[] specialId) {
		this.specialId = specialId;
	}
	public Integer[] getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer[] siteId) {
		this.siteId = siteId;
	}
	public Integer[] getMainNodeId() {
		return mainNodeId;
	}
	public void setMainNodeId(Integer[] mainNodeId) {
		this.mainNodeId = mainNodeId;
	}
	public Long[] getUserId() {
		return userId;
	}
	public void setUserId(Long[] userId) {
		this.userId = userId;
	}
	public String[] getTreeNumber() {
		return treeNumber;
	}
	public void setTreeNumber(String[] treeNumber) {
		this.treeNumber = treeNumber;
	}
	public String[] getSpecialTitle() {
		return specialTitle;
	}
	public void setSpecialTitle(String[] specialTitle) {
		this.specialTitle = specialTitle;
	}
	public Integer[] getPriority() {
		return priority;
	}
	public void setPriority(Integer[] priority) {
		this.priority = priority;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public Integer[] getIncludeId() {
		return includeId;
	}
	public void setIncludeId(Integer[] includeId) {
		this.includeId = includeId;
	}
	public Integer[] getExcludeId() {
		return excludeId;
	}
	public void setExcludeId(Integer[] excludeId) {
		this.excludeId = excludeId;
	}
	public Integer[] getExcludeMainNodeId() {
		return excludeMainNodeId;
	}
	public void setExcludeMainNodeId(Integer[] excludeMainNodeId) {
		this.excludeMainNodeId = excludeMainNodeId;
	}
	public String[] getExcludeTreeNumber() {
		return excludeTreeNumber;
	}
	public void setExcludeTreeNumber(String[] excludeTreeNumber) {
		this.excludeTreeNumber = excludeTreeNumber;
	}
	public Boolean getIsWithImage() {
		return isWithImage;
	}
	public void setIsWithImage(Boolean isWithImage) {
		this.isWithImage = isWithImage;
	}
	public Boolean getIsWithVideo() {
		return isWithVideo;
	}
	public void setIsWithVideo(Boolean isWithVideo) {
		this.isWithVideo = isWithVideo;
	}
	public String[] getStatus() {
		return status;
	}
	public void setStatus(String[] status) {
		this.status = status;
	}
	public Integer[] getP1() {
		return p1;
	}
	public void setP1(Integer[] p1) {
		this.p1 = p1;
	}
	public Integer[] getP2() {
		return p2;
	}
	public void setP2(Integer[] p2) {
		this.p2 = p2;
	}
	public Integer[] getP3() {
		return p3;
	}
	public void setP3(Integer[] p3) {
		this.p3 = p3;
	}
	public Integer[] getP4() {
		return p4;
	}
	public void setP4(Integer[] p4) {
		this.p4 = p4;
	}
	public Integer[] getP5() {
		return p5;
	}
	public void setP5(Integer[] p5) {
		this.p5 = p5;
	}
	public Integer[] getP6() {
		return p6;
	}
	public void setP6(Integer[] p6) {
		this.p6 = p6;
	}
	public Map<String, String> getCustomer() {
		return customer;
	}
	public void setCustomer(Map<String, String> customer) {
		this.customer = customer;
	}
	public Boolean getIsAudio() {
		return isAudio;
	}
	public void setIsAudio(Boolean isAudio) {
		this.isAudio = isAudio;
	}
	public boolean isShowDescendants() {
		return showDescendants;
	}
	public void setShowDescendants(boolean showDescendants) {
		this.showDescendants = showDescendants;
	}
	public String getNodeNumber() {
		return nodeNumber;
	}
	public void setNodeNumber(String nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
}
