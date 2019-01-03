package cn.mooc.app.module.points.model;

import cn.mooc.app.module.points.data.entity.UserPoints;

public class UserPointsModel {

	private Long id;

	private Double points;
	
	private String userName;
	
	private String levelName;
	
	public UserPointsModel(UserPoints up, String userName){
		this.setId(up.getId());
		this.setPoints(up.getPoints());
		this.setUserName(userName);
		this.setLevelName(up.getLevel().getLevelName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
}
