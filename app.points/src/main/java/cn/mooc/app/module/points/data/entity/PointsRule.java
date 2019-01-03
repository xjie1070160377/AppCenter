package cn.mooc.app.module.points.data.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the t_points_rule database table.
 * 
 */
@Entity
@Table(name="t_points_rule")
public class PointsRule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Fields
	public final static Integer as_up = 1;
	public final static Integer as_down = -1;
	/**
	 * 阅读文章
	 */
	public final static String RULE_READ = "read";
	/**
	 * 点赞文章
	 */
	public final static String RULE_DIGGS = "diggs";
	/**
	 * 分享文章
	 */
	public final static String RULE_SHARE = "share";
	/**
	 * 每日登录
	 */
	public final static String RULE_LOGIN = "login";
	/**
	 * 用户注册
	 */
	public final static String RULE_REGISTER = "register";
	/**
	 * 评论
	 */
	public final static String RULE_COMMENT = "comment";
	/**
	 * 被点赞
	 */
	public final static String RULE_BEDIGGS = "beDiggs";
	/**
	 * 被评论
	 */
	public final static String RULE_BECOMMENT = "beComment";

	private Integer id;

	private String caption;

	private Integer isas;

	private Integer isspecial;

	private Integer maxOpnum;

	private Double maxPoints;

	private Double points;

	private String ruleCode;

	private String ruleName;

	private Integer status;

	@Id
	@Column(name = "rule_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_points_rule", pkColumnValue = "t_points_rule", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_points_rule")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "caption", length = 200)
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Column(name = "isas", precision = 1, scale = 0)
	public Integer getIsas() {
		return isas;
	}

	public void setIsas(Integer isas) {
		this.isas = isas;
	}

	@Column(name = "isspecial", precision = 1, scale = 0)
	public Integer getIsspecial() {
		return isspecial;
	}

	public void setIsspecial(Integer isspecial) {
		this.isspecial = isspecial;
	}

	@Column(name = "max_opnum", precision = 3)
	public Integer getMaxOpnum() {
		return maxOpnum;
	}

	public void setMaxOpnum(Integer maxOpnum) {
		this.maxOpnum = maxOpnum;
	}

	@Column(name = "max_points", precision = 8)
	public Double getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(Double maxPoints) {
		this.maxPoints = maxPoints;
	}

	@Column(name = "points", precision = 8)
	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	@Column(name = "rule_code", length = 200)
	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	@Column(name = "rule_name", length = 200)
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name = "status", precision = 1, scale = 0)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}