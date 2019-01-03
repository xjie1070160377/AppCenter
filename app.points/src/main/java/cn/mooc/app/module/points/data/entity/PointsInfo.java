package cn.mooc.app.module.points.data.entity;


import java.io.Serializable;

import javax.persistence.*;

import cn.mooc.app.module.cms.data.entity.Info;

import java.math.BigDecimal;


/**
 * The persistent class for the t_points_info database table.
 * 
 */
@Entity
@Table(name="t_points_info")
public class PointsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer hascomments;

	private Integer haslike;
	
	private Info info;
	
	private PointsRule rule;

	@Id
	@Column(name = "pinfo_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_points_info", pkColumnValue = "t_points_info", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_points_info")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "hascomments", precision = 1, scale = 0)
	public Integer getHascomments() {
		return hascomments;
	}

	public void setHascomments(Integer hascomments) {
		this.hascomments = hascomments;
	}

	@Column(name = "haslike", precision = 1, scale = 0)
	public Integer getHaslike() {
		return haslike;
	}

	public void setHaslike(Integer haslike) {
		this.haslike = haslike;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "info_id")
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rule_id")
	public PointsRule getRule() {
		return rule;
	}

	public void setRule(PointsRule rule) {
		this.rule = rule;
	}
}