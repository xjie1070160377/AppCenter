package cn.mooc.app.module.points.data.entity;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the t_exchange_goods database table.
 * 
 */
@Entity
@Table(name="t_exchange_goods")
public class ExchangeGood implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer dayLimit;

	private Integer remainTotal;

	private Integer sumTotal;

	private Integer total;

	private ExchangeRule exchangeRule;

	private Good good;
	
	public void applyDefaultValue(){
		
	}

	@Id
	@Column(name = "detail_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_exchange_goods", pkColumnValue = "t_exchange_goods", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_exchange_goods")
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="day_limit")
	public Integer getDayLimit() {
		return this.dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	@Column(name="remain_total")
	public Integer getRemainTotal() {
		return this.remainTotal;
	}

	public void setRemainTotal(Integer remainTotal) {
		this.remainTotal = remainTotal;
	}

	@Column(name="sum_total")
	public Integer getSumTotal() {
		return this.sumTotal;
	}

	public void setSumTotal(Integer sumTotal) {
		this.sumTotal = sumTotal;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rule_id", nullable=true)
	public ExchangeRule getExchangeRule() {
		return this.exchangeRule;
	}

	public void setExchangeRule(ExchangeRule exchangeRule) {
		this.exchangeRule = exchangeRule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="good_id")
	public Good getGood() {
		return this.good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

}