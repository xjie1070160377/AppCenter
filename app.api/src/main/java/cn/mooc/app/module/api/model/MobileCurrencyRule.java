package cn.mooc.app.module.api.model;

import java.util.List;

public class MobileCurrencyRule {

	private String ruleName;
	private String caption;
	private Double total;
	private Double maxTotal;
	private Integer isspecial;
	private List<MobileCurrencyInfo> infos;

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

	public Integer getIsspecial() {
		return isspecial;
	}

	public void setIsspecial(Integer isspecial) {
		this.isspecial = isspecial;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Double maxTotal) {
		this.maxTotal = maxTotal;
	}

	public List<MobileCurrencyInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<MobileCurrencyInfo> infos) {
		this.infos = infos;
	}
}
