package cn.mooc.app.module.points.data.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_user_points_log database table.
 * 
 */
@Entity
@Table(name="t_user_points_log")
public class UserPointsLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 积分规则日志
	 */
	public static final Integer TYPE_RULE = 1;
	/**
	 * 摇奖
	 */
	public static final Integer TYPE_DRAW = 2;
	/**
	 * 积分兑换
	 */
	public static final Integer TYPE_CHANGE = 3;

	private Integer id;

	private String caption;

	private Date createtime;

	private Integer infoId;

	private Integer isas;

	private Integer logtypeId;

	private Double origPoints;

	private Double points;

	private PointsRule rule;
	
	private UserPoints userPoints;

	@Id
	@Column(name = "logid", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_user_points_log", pkColumnValue = "t_user_points_log", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_user_points_log")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "caption", length = 500)
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "info_id")
	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	@Column(name = "isas", precision = 1, scale = 0)
	public Integer getIsas() {
		return isas;
	}

	public void setIsas(Integer isas) {
		this.isas = isas;
	}

	@Column(name = "logtype_id")
	public Integer getLogtypeId() {
		return logtypeId;
	}

	public void setLogtypeId(Integer logtypeId) {
		this.logtypeId = logtypeId;
	}

	@Column(name = "orig_points", precision = 8)
	public Double getOrigPoints() {
		return origPoints;
	}

	public void setOrigPoints(Double origPoints) {
		this.origPoints = origPoints;
	}

	@Column(name = "points", precision = 8)
	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rule_id")
	public PointsRule getRule() {
		return rule;
	}

	public void setRule(PointsRule rule) {
		this.rule = rule;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public UserPoints getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(UserPoints userPoints) {
		this.userPoints = userPoints;
	}
}