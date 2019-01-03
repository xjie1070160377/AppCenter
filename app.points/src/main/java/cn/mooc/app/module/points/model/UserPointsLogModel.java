package cn.mooc.app.module.points.model;

import java.text.SimpleDateFormat;

import cn.mooc.app.module.points.data.entity.UserPointsLog;


public class UserPointsLogModel {

	private Integer id;

	private String caption;

	private String  createtime;

	private Integer infoId;

	private Integer isas;

	private Double origPoints;

	private Double points;

	private String ruleName;
	
	private String userName;
	
	public UserPointsLogModel(UserPointsLog log, String userName){
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setId(log.getId());
		this.setCaption(log.getCaption());
		this.setCreatetime(form.format(log.getCreatetime()));
		this.setInfoId(log.getInfoId());
		this.setIsas(log.getIsas());
		this.setOrigPoints(log.getOrigPoints());
		this.setPoints(log.getPoints());
		this.setRuleName(log.getRule().getRuleName());
		this.setUserName(userName);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getIsas() {
		return isas;
	}

	public void setIsas(Integer isas) {
		this.isas = isas;
	}

	public Double getOrigPoints() {
		return origPoints;
	}

	public void setOrigPoints(Double origPoints) {
		this.origPoints = origPoints;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
