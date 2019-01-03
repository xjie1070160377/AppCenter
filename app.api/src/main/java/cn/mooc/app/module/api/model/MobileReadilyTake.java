package cn.mooc.app.module.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MobileReadilyTake {


	private Integer id;
	private String title;
	private String longitude;
	private String latitude;
	private String address;
	private Integer diggs;
	private String createTime;
	
	private String photo;
	private String username;
	private Integer userId;
	
	private Integer digged = 0;

	private Integer collected = 0;
	
	private Integer comments;
	private String metaDescription;
	private String shwoPhotoUrl;
	private List<String> images = new ArrayList<String>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public Integer getDiggs() {
		return diggs;
	}
	public void setDiggs(Integer diggs) {
		this.diggs = diggs;
	}
	public Integer getComments() {
		return comments;
	}
	public void setComments(Integer comments) {
		this.comments = comments;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDigged() {
		return digged;
	}
	public void setDigged(Integer digged) {
		this.digged = digged;
	}
	public Integer getCollected() {
		return collected;
	}
	public void setCollected(Integer collected) {
		this.collected = collected;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getShwoPhotoUrl() {
		return shwoPhotoUrl;
	}
	public void setShwoPhotoUrl(String shwoPhotoUrl) {
		this.shwoPhotoUrl = shwoPhotoUrl;
	}
	
}
