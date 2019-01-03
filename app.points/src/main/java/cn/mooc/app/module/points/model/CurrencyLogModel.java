package cn.mooc.app.module.points.model;

import java.util.Date;

import cn.mooc.app.module.points.data.entity.CurrencyLog;

public class CurrencyLogModel {
	private Integer id;
	
	private Date createtime;

	private Double quantity;

	private Double remain;

	private String caption;
	
	private String userName;
	
	public CurrencyLogModel(){}
	
	public CurrencyLogModel(CurrencyLog log){
		setId(log.getId());
		setCreatetime(log.getCreatetime());
		setQuantity(log.getQuantity());
		setRemain(log.getRemain());
		setCaption(log.getCaption());
	}
	
	public CurrencyLogModel(CurrencyLog log, String userName){
		this(log);
		setUserName(userName);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRemain() {
		return remain;
	}

	public void setRemain(Double remain) {
		this.remain = remain;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
