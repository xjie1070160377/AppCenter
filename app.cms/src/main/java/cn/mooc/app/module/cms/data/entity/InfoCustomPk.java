package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;

public class InfoCustomPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8647633950884095782L;
	private Integer infoId;
	private String key;
	
	public InfoCustomPk(Integer infoId, String key){
		this.infoId = infoId;
		this.key = key;
	}
	
	public InfoCustomPk(){
		
	}
	
	public Integer getInfoId() {
		return infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
