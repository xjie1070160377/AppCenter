package cn.mooc.app.module.points.data.entity;


import java.io.Serializable;

import javax.persistence.*;

import cn.mooc.app.core.data.entity.SysUserEntity;

import java.math.BigDecimal;


/**
 * The persistent class for the t_user_points database table.
 * 
 */
@Entity
@Table(name="t_user_points")
public class UserPoints implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Double points;
	
	private Double currency;
	
	private PointsLevel level;

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "points", precision = 8)
	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id")
	public PointsLevel getLevel() {
		return level;
	}

	public void setLevel(PointsLevel level) {
		this.level = level;
	}

	@Column(name = "currency", precision = 8)
	public Double getCurrency() {
		return currency;
	}

	public void setCurrency(Double currency) {
		this.currency = currency;
	}
}