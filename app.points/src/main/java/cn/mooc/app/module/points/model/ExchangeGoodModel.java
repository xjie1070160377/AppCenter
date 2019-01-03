package cn.mooc.app.module.points.model;

import cn.mooc.app.module.points.data.entity.ExchangeGood;
import cn.mooc.app.module.points.data.entity.Good;

public class ExchangeGoodModel {

	private Integer id;
	
	private Integer goodId;

	private String goodName;

	private Integer dayLimit;

	private Integer remainTotal;

	private Integer sumTotal;

	private Integer total;
	
	public ExchangeGoodModel(ExchangeGood good){
		this.id = good.getGood().getId();
		this.goodId = good.getGood().getId();
		this.goodName = good.getGood().getGoodName();
		this.dayLimit = good.getDayLimit();
		this.remainTotal = good.getRemainTotal();
		this.sumTotal = good.getSumTotal();
		this.total = good.getTotal();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodId() {
		return goodId;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Integer getRemainTotal() {
		return remainTotal;
	}

	public void setRemainTotal(Integer remainTotal) {
		this.remainTotal = remainTotal;
	}

	public Integer getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(Integer sumTotal) {
		this.sumTotal = sumTotal;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
