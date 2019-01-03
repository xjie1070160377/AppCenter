package cn.mooc.app.module.points.data.entity;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the t_currency_rule database table.
 * 
 */
@Entity
@Table(name="t_currency_rule")
public class CurrencyRule implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 有效
	 */
	public final static Integer YX_STATUS = 1;
	/**
	 * 无效
	 */
	public final static Integer WX_STATUS = 0;
	
	/**
	 * 注册
	 */
	public final static String REGISTER_CODE = "register";
	/**
	 * 登录
	 */
	public final static String LOGIN_CODE = "login";
	/**
	 * 发布作品
	 */
	public final static String PUBLISH_CODE = "publish";
	/**
	 * 点赞
	 */
	public final static String DIGGIS_CODE = "diggis";
	/**
	 *评论 
	 */
	public final static String COMMENT_CODE = "comment";
	/**
	 * 被评论点赞
	 */
	public final static String BEEN_CODE = "been_things";
	/**
	 * 下载红客
	 */
	public final static String DOWN_CODE = "down_app";
	/**
	 * 特殊文章
	 */
	public final static String SPECIAL_CODE = "special";
	/**
	 * 后台报错
	 */
	public final static String ERROR_CODE = "error";

	private Integer id;

	private String caption;

	private Integer isspecial;
	
	private Double maxTotal;

	private String ruleCode;

	private String ruleName;

	private Integer status;

	private Double total;

	private List<CurrencyCulNode> currencyCulNodes;

	private List<CurrencySpecial> currencySpecials;

	private List<UserDayCur> userDayCurs;
	
	private List<CurrencyLog> currencyLogs;

	public void applyDefaultValue(){
		
	}
	
	@Id
	@Column(name = "rule_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_currency_rule", pkColumnValue = "t_currency_rule", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_currency_rule")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Integer getIsspecial() {
		return this.isspecial;
	}

	public void setIsspecial(Integer isspecial) {
		this.isspecial = isspecial;
	}

	@Column(name="max_total")
	public Double getMaxTotal() {
		return this.maxTotal;
	}

	public void setMaxTotal(Double maxTotal) {
		this.maxTotal = maxTotal;
	}

	@Column(name="rule_code")
	public String getRuleCode() {
		return this.ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	@Column(name="rule_name")
	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="rule")
	public List<CurrencyCulNode> getCurrencyCulNodes() {
		return this.currencyCulNodes;
	}

	public void setCurrencyCulNodes(List<CurrencyCulNode> currencyCulNodes) {
		this.currencyCulNodes = currencyCulNodes;
	}

	public CurrencyCulNode addCurrencyCulNode(CurrencyCulNode currencyCulNode) {
		getCurrencyCulNodes().add(currencyCulNode);
		currencyCulNode.setRule(this);

		return currencyCulNode;
	}

	public CurrencyCulNode removeCurrencyCulNode(CurrencyCulNode currencyCulNode) {
		getCurrencyCulNodes().remove(currencyCulNode);
		currencyCulNode.setRule(null);

		return currencyCulNode;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="currencyRule")
	public List<CurrencySpecial> getCurrencySpecials() {
		return this.currencySpecials;
	}

	public void setCurrencySpecials(List<CurrencySpecial> currencySpecials) {
		this.currencySpecials = currencySpecials;
	}

	public CurrencySpecial addCurrencySpecial(CurrencySpecial CurrencySpecial) {
		getCurrencySpecials().add(CurrencySpecial);
		CurrencySpecial.setCurrencyRule(this);

		return CurrencySpecial;
	}

	public CurrencySpecial removeCurrencySpecial(CurrencySpecial CurrencySpecial) {
		getCurrencySpecials().remove(CurrencySpecial);
		CurrencySpecial.setCurrencyRule(null);

		return CurrencySpecial;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="currencyRule")
	public List<UserDayCur> getUserDayCurs() {
		return this.userDayCurs;
	}

	public void setUserDayCurs(List<UserDayCur> userDayCurs) {
		this.userDayCurs = userDayCurs;
	}

	public UserDayCur addUserDayCur(UserDayCur UserDayCur) {
		getUserDayCurs().add(UserDayCur);
		UserDayCur.setCurrencyRule(this);

		return UserDayCur;
	}

	public UserDayCur removeUserDayCur(UserDayCur UserDayCur) {
		getUserDayCurs().remove(UserDayCur);
		UserDayCur.setCurrencyRule(null);

		return UserDayCur;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="currencyRule")
	public List<CurrencyLog> getCurrencyLogs() {
		return this.currencyLogs;
	}

	public void setCurrencyLogs(List<CurrencyLog> currencyLogs) {
		this.currencyLogs = currencyLogs;
	}

	public CurrencyLog addCurrencyLog(CurrencyLog CurrencyLog) {
		getCurrencyLogs().add(CurrencyLog);
		CurrencyLog.setCurrencyRule(this);

		return CurrencyLog;
	}

	public CurrencyLog removeCurrencyLog(CurrencyLog CurrencyLog) {
		getCurrencyLogs().remove(CurrencyLog);
		CurrencyLog.setCurrencyRule(null);

		return CurrencyLog;
	}

}