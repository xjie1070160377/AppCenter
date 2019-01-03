package cn.mooc.app.module.cms.model;

import java.util.Date;

public class MobileSpecial {
	private Integer id;
	private Long creatorId;

	private Date creationDate;
	private String title;
	private String metaKeywords;
	private String metaDescription;
	private String specialTemplate;
	private String smallImage;
	private String largeImage;
	private String video;
	private String videoName;
	private Long videoLength;
	private String videoTime;
	private Integer refers;
	private Integer views;
	private Boolean withImage;
	private Boolean recommend;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMetaKeywords() {
		return metaKeywords;
	}
	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	public String getSpecialTemplate() {
		return specialTemplate;
	}
	public void setSpecialTemplate(String specialTemplate) {
		this.specialTemplate = specialTemplate;
	}
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getLargeImage() {
		return largeImage;
	}
	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public Long getVideoLength() {
		return videoLength;
	}
	public void setVideoLength(Long videoLength) {
		this.videoLength = videoLength;
	}
	public String getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}
	public Integer getRefers() {
		return refers;
	}
	public void setRefers(Integer refers) {
		this.refers = refers;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public Boolean getWithImage() {
		return withImage;
	}
	public void setWithImage(Boolean withImage) {
		this.withImage = withImage;
	}
	public Boolean getRecommend() {
		return recommend;
	}
	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}
}
