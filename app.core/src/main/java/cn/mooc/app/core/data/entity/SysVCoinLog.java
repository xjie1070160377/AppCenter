package cn.mooc.app.core.data.entity;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户积分/消费流水
 * @author Taven
 *
 */
@Document(collection = "t_sys_vcoin_log")
public class SysVCoinLog {

	@Id
	private ObjectId id;
	
	/**
	 * 用户ID
	 */
	@Indexed
	private long userId;
	
	
	/**
	 * 本次获得/消费积分数，正数为获得，负数为消费
	 */
	private double vcoins;
	
	
	/**
	 * 积分获得的途径/方式
	 */
	private String vcoinFrom;
	
	
	private String note;
	
	
	@Indexed(direction = IndexDirection.DESCENDING)
	private Date createTime;


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public double getVcoins() {
		return vcoins;
	}


	public void setVcoins(double vcoins) {
		this.vcoins = vcoins;
	}

	public String getVcoinFrom() {
		return vcoinFrom;
	}


	public void setVcoinFrom(String vcoinFrom) {
		this.vcoinFrom = vcoinFrom;
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
