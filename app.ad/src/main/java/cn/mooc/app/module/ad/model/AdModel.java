package cn.mooc.app.module.ad.model;

import java.io.Serializable;
import java.util.Date;

import cn.mooc.app.module.ad.data.entity.Ad;


public class AdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date beginDate;

	private Date endDate;

	private String name;

	private Integer seq;

	private Integer siteId;

	private Integer adSlotType;
	
	private String adSlotName;
	
	private Integer linkType;
	

	public AdModel() {
	}

	public AdModel(Ad entity) {
		this.setId(entity.getId());
		this.setName(entity.getName());
		this.setBeginDate(entity.getBeginDate());
		this.setEndDate(entity.getEndDate());
		this.setSeq(entity.getSeq());
		this.setSiteId(entity.getSiteId());
		this.setAdSlotType(entity.getAdSlot().getType());
		this.setAdSlotName(entity.getAdSlot().getName());
		this.setLinkType(entity.getLinkType());
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getAdSlotName() {
		return adSlotName;
	}

	public void setAdSlotName(String adSlotName) {
		this.adSlotName = adSlotName;
	}

	public Integer getAdSlotType() {
		return adSlotType;
	}

	public void setAdSlotType(Integer adSlotType) {
		this.adSlotType = adSlotType;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
}