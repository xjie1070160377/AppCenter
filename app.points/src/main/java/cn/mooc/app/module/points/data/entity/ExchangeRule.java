package cn.mooc.app.module.points.data.entity;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the t_exchange_rule database table.
 * 
 */
@Entity
@Table(name="t_exchange_rule")
public class ExchangeRule implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String caption;

	private Integer dayLimit;

	private Date endTime;

	private String ruleName;

	private Integer ruleType;

	private Date startTime;

	private Integer status;

	private Double total;

	private List<CurrencyLog> currencyLogs;

	private List<ExchangeGood> exchangeGoods;
	
	public void applyDefaultValue(){
		
	}

	@Id
	@Column(name = "rule_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_exchange_rule", pkColumnValue = "t_exchange_rule", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_exchange_rule")
	public Integer getId() {
		return id;
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

	@Column(name="day_limit")
	public Integer getDayLimit() {
		return this.dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="end_time")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="rule_name")
	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name="rule_type")
	public Integer getRuleType() {
		return this.ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE },  mappedBy="exchangeRule")
	public List<CurrencyLog> getCurrencyLogs() {
		return this.currencyLogs;
	}

	public void setCurrencyLogs(List<CurrencyLog> currencyLogs) {
		this.currencyLogs = currencyLogs;
	}

	public CurrencyLog addCurrencyLog(CurrencyLog currencyLogs) {
		getCurrencyLogs().add(currencyLogs);
		currencyLogs.setExchangeRule(this);

		return currencyLogs;
	}

	public CurrencyLog removeCurrencyLog(CurrencyLog currencyLog) {
		getCurrencyLogs().remove(currencyLog);
		currencyLog.setExchangeRule(null);

		return currencyLog;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE },  mappedBy="exchangeRule")
	public List<ExchangeGood> getExchangeGoods() {
		return this.exchangeGoods;
	}

	public void setExchangeGoods(List<ExchangeGood> exchangeGoods) {
		this.exchangeGoods = exchangeGoods;
	}

	public ExchangeGood addExchangeGood(ExchangeGood exchangeGoods) {
		getExchangeGoods().add(exchangeGoods);
		exchangeGoods.setExchangeRule(this);

		return exchangeGoods;
	}

	public ExchangeGood removeExchangeGood(ExchangeGood exchangeGoods) {
		getExchangeGoods().remove(exchangeGoods);
		exchangeGoods.setExchangeRule(null);

		return exchangeGoods;
	}

}