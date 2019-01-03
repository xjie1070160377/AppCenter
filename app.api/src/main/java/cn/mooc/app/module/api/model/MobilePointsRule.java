package cn.mooc.app.module.api.model;

import java.util.List;

public class MobilePointsRule {

	private String ruleName;
	private String caption;
	private Integer isas;
	private Double points;
	private Double maxPoints;
	private Integer isspecial;
	private List<MobileRuleInfo> infos;

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Integer getIsas() {
		return isas;
	}

	public void setIsas(Integer isas) {
		this.isas = isas;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public Double getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(Double maxPoints) {
		this.maxPoints = maxPoints;
	}

	public Integer getIsspecial() {
		return isspecial;
	}

	public void setIsspecial(Integer isspecial) {
		this.isspecial = isspecial;
	}
	
	public List<MobileRuleInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<MobileRuleInfo> infos) {
		this.infos = infos;
	}
}
