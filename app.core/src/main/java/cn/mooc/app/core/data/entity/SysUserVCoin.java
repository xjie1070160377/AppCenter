package cn.mooc.app.core.data.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 用户积分
 * 
 * @author Taven
 *
 */
@Entity
@Table(name = "t_sys_user_vcoin")
public class SysUserVCoin  implements Serializable {

	private static final long serialVersionUID = 1932258159646180279L;

	@Id
	@Column(name = "userId")
	private long userId;
	
	/**
	 * 用户名称
	 */
	@Column(name = "userName")
	private String userName;	
	
	/**
	 * 用户共获取过多少积分
	 */
	@Column(name = "historyTotal")
	private double historyTotal;
	
	/**
	 * 当前用户可以使用的积分
	 */
	@Column(name = "availableTotal")
	private double availableTotal;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL})
	@JoinColumn(name = "userId", referencedColumnName = "id", unique = true)
	private SysUserEntity sysUser;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getHistoryTotal() {
		return historyTotal;
	}

	public void setHistoryTotal(double historyTotal) {
		this.historyTotal = historyTotal;
	}

	public double getAvailableTotal() {
		return availableTotal;
	}

	public void setAvailableTotal(double availableTotal) {
		this.availableTotal = availableTotal;
	}

	public SysUserEntity getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUserEntity sysUser) {
		this.sysUser = sysUser;
	}
			
	
}
