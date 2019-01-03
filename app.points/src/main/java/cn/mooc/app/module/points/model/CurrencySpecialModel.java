package cn.mooc.app.module.points.model;

import cn.mooc.app.module.points.data.entity.CurrencySpecial;

public class CurrencySpecialModel {

	private Integer id;
	private Integer infoid;

	private String title;

	
	public CurrencySpecialModel(CurrencySpecial special){
		this.id = special.getInfo().getId();
		this.infoid = special.getInfo().getId();
		this.title = special.getInfo().getTitle();
	}


	public Integer getInfoid() {
		return infoid;
	}


	public void setInfoid(Integer infoid) {
		this.infoid = infoid;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

}
