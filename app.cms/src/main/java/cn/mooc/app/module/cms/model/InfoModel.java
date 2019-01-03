package cn.mooc.app.module.cms.model;

public class InfoModel {

	private Integer id;
	private String nodeName;
	private Long creatorId;
	private String creator;
	private String title;
	private String modeName;
	private String publishDate;
	private Integer priority;
	private Integer views;
	private Integer comments;
	private String status;
	private String smallImage;
	private String author;
	private Double columnSort;
	/**
	 * 1：校内
	 * 2：校外，不可分享
	 * 3：校外，可分享
	 */
	private Integer infoLevel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Double getColumnSort() {
		return columnSort;
	}

	public void setColumnSort(Double columnSort) {
		this.columnSort = columnSort;
	}

	public Integer getInfoLevel() {
		return infoLevel;
	}

	public void setInfoLevel(Integer infoLevel) {
		this.infoLevel = infoLevel;
	}

	
}