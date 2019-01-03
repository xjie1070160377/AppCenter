package cn.mooc.app.module.cms.model;

import java.io.Serializable;

public class MobileCategoryInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 502930596784725821L;

	private int cateId;

	private String cateName;

	private String number;

	private String description;

	private String detailUrl;//链接地址

	private String smallImage;

	private int articles;

	private int attentions;

	private Integer treeLevel;

	private String treeNumber;

	private int terminated;

	private Integer attentioned = 0;

	private Integer isSpecial = 0;

	private Integer twoColumn = 0;

	private Integer nodeModelId;

	private Integer typeId;
	private Integer showTitle;
	private String showChildNode;
	private String linkType;//链接类型
	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getArticles() {
		return articles;
	}

	public void setArticles(int articles) {
		this.articles = articles;
	}

	public Integer getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}

	public String getTreeNumber() {
		return treeNumber;
	}

	public void setTreeNumber(String treeNumber) {
		this.treeNumber = treeNumber;
	}

	public int getTerminated() {
		return terminated;
	}

	public void setTerminated(int terminated) {
		this.terminated = terminated;
	}

	public int getAttentions() {
		return attentions;
	}

	public void setAttentions(int attentions) {
		this.attentions = attentions;
	}

	public Integer getAttentioned() {
		return attentioned;
	}

	public void setAttentioned(Integer attentioned) {
		this.attentioned = attentioned;
	}

	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Integer getTwoColumn() {
		return twoColumn;
	}

	public void setTwoColumn(Integer twoColumn) {
		this.twoColumn = twoColumn;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(Integer showTitle) {
		this.showTitle = showTitle;
	}

	public String getShowChildNode() {
		return showChildNode;
	}

	public void setShowChildNode(String showChildNode) {
		this.showChildNode = showChildNode;
	}

	public Integer getNodeModelId() {
		return nodeModelId;
	}

	public void setNodeModelId(Integer nodeModelId) {
		this.nodeModelId = nodeModelId;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}



}