package cn.mooc.app.core.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_sys_config")
public class SysConfigEntity implements Serializable {

	private static final long serialVersionUID = -3749586647205873245L;

	@Id	
	@Column(name = "keyName")
	private String keyName;
	
	@Column(name = "keyValue")
	private String keyValue;
	
	@Column(name = "dataType")
	private int dataType;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "createTime")
	private Date createTime;


	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
