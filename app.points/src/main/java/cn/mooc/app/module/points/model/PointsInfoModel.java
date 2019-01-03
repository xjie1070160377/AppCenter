package cn.mooc.app.module.points.model;

import cn.mooc.app.module.points.data.entity.PointsInfo;


public class PointsInfoModel {

	private Integer id;

	private String hascomments;

	private String haslike;
	
	private String title;
	
	private String ruleName;
	
	private Integer infoid;
	
	public PointsInfoModel(PointsInfo pi){
		if(pi.getInfo() != null){
			this.infoid = pi.getInfo().getId();
		}
		this.id = pi.getId();
		this.hascomments=pi.getHascomments()!=null?(pi.getHascomments().intValue()==1?"包含":"不包含"):"不包含";
		this.haslike=pi.getHaslike()!=null?(pi.getHaslike().intValue()==1?"包含":"不包含"):"不包含";
		this.title = pi.getInfo()!=null?pi.getInfo().getTitle():"";
		this.ruleName = pi.getRule()!=null?pi.getRule().getRuleName():"";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHascomments() {
		return hascomments;
	}

	public void setHascomments(String hascomments) {
		this.hascomments = hascomments;
	}

	public String getHaslike() {
		return haslike;
	}

	public Integer getInfoid() {
		return infoid;
	}

	public void setInfoid(Integer infoid) {
		this.infoid = infoid;
	}

	public void setHaslike(String haslike) {
		this.haslike = haslike;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
}
