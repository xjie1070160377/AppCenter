package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="t_cms_info_custom")
@IdClass(value = InfoCustomPk.class)
public class InfoCustom {

	@Id
	@Column(name = "info_id", nullable = false)
	private Integer infoId;
	
	@Id
	@Column(name = "key_", nullable = false)
	private String key;
	
	@Column(name = "value_", length = 2000)
	private String value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="info_id", insertable=false, updatable=false)
	private Info info;
	
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
}
