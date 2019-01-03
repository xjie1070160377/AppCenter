package cn.mooc.app.module.points.model;

import cn.mooc.app.module.points.data.entity.Good;

public class GoodModel {

	private Integer id;

	private String caption;

	private String goodName;

	private Double price;
	
	public GoodModel(Good good){
		this.id = good.getId();
		this.caption = good.getCaption();
		this.goodName = good.getGoodName();
		this.price = good.getPrice();
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

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
