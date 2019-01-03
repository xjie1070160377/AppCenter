package cn.mooc.app.module.api.model;

import java.util.List;

public class MobileRuleInfo {


	private Integer infoId;
	private String title;
	private Integer haslike;
	private Integer hascomment;
	public String image;
	public String description;
	public String file;
	public String specialId;
	public int isSpecial;
	public int hasImages;
	public int hasVideo;
	public String video;


	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getHaslike() {
		return haslike;
	}

	public void setHaslike(Integer haslike) {
		this.haslike = haslike;
	}

	public Integer getHascomment() {
		return hascomment;
	}

	public void setHascomment(Integer hascomment) {
		this.hascomment = hascomment;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public int getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(int isSpecial) {
		this.isSpecial = isSpecial;
	}

	public int getHasImages() {
		return hasImages;
	}

	public void setHasImages(int hasImages) {
		this.hasImages = hasImages;
	}

	public int getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(int hasVideo) {
		this.hasVideo = hasVideo;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
}
