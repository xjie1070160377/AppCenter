package cn.mooc.app.module.points.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_currency_log database table.
 * 
 */
@Entity
@Table(name="t_currency_log")
public class CurrencyLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Date createtime;

	private Double quantity;

	private Double remain;

	private ExchangeRule exchangeRule;

	private DrawRule drawRule;

	private ExchangeGood exchangeGood;
	
	private String caption;
	
	private CurrencyRule currencyRule;
	
	private Long userId;
	
	private Integer dataId;
	
	private String ruleCode;
	
	public void applyDefaultValue(){
		
	}

	@Id
	@Column(name = "log_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_currency_log", pkColumnValue = "t_currency_log", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_currency_log")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRemain() {
		return this.remain;
	}

	public void setRemain(Double remain) {
		this.remain = remain;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="change_id")
	public ExchangeRule getExchangeRule() {
		return this.exchangeRule;
	}

	public void setExchangeRule(ExchangeRule exchangeRule) {
		this.exchangeRule = exchangeRule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rule_id")
	public DrawRule getDrawRule() {
		return this.drawRule;
	}

	public void setDrawRule(DrawRule drawRule) {
		this.drawRule = drawRule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="detail_id")
	public ExchangeGood getExchangeGood() {
		return this.exchangeGood;
	}

	public void setExchangeGood(ExchangeGood exchangeGood) {
		this.exchangeGood = exchangeGood;
	}

	@Column(name = "caption", length = 500)
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cur_id")
	public CurrencyRule getCurrencyRule() {
		return currencyRule;
	}

	public void setCurrencyRule(CurrencyRule currencyRule) {
		this.currencyRule = currencyRule;
	}

	@Column(name = "userId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "dataId")
	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	@Column(name = "ruleCode")
	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
}