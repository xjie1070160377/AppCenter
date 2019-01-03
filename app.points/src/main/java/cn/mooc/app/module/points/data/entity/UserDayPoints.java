package cn.mooc.app.module.points.data.entity;


import java.io.Serializable;

import javax.persistence.*;

import cn.mooc.app.core.data.entity.SysUserEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_user_day_points database table.
 * 
 */
@Entity
@Table(name="t_user_day_points")
public class UserDayPoints implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date lastTime;

	private Integer opnum;

	private Double points;

	private PointsRule rule;

	private UserPoints userPoints;

	@Id
	@Column(name = "day_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_user_day_points", pkColumnValue = "t_user_day_points", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_user_day_points")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "lastTime", length = 10)
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Column(name = "opnum", precision = 3)
	public Integer getOpnum() {
		return opnum;
	}

	public void setOpnum(Integer opnum) {
		this.opnum = opnum;
	}

	@Column(name = "points", precision = 8)
	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rule_id")
	public PointsRule getRule() {
		return rule;
	}

	public void setRule(PointsRule rule) {
		this.rule = rule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public UserPoints getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(UserPoints userPoints) {
		this.userPoints = userPoints;
	}
}