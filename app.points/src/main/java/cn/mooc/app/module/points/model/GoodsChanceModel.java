package cn.mooc.app.module.points.model;

import cn.mooc.app.module.points.data.entity.GoodsChance;

public class GoodsChanceModel {

	private Integer id;
	
	private Integer goodId;

	private String goodName;

	private Double chance;

	private Integer remianTotal;

	private Integer total;
	
	public GoodsChanceModel(GoodsChance good){
		this.id = good.getGood().getId();
		this.goodId = good.getGood().getId();
		this.goodName = good.getGood().getGoodName();
		this.chance = good.getChance();
		this.remianTotal = good.getRemianTotal();
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

	public Double getChance() {
		return chance;
	}

	public void setChance(Double chance) {
		this.chance = chance;
	}

	public Integer getRemianTotal() {
		return remianTotal;
	}

	public void setRemianTotal(Integer remianTotal) {
		this.remianTotal = remianTotal;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
