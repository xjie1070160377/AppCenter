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
 * The persistent class for the t_draw_rule database table.
 * 
 */
@Entity
@Table(name="t_draw_rule")
public class DrawRule implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String caption;

	private Integer dayLimit;

	private Date endTime;

	private Date startTime;

	private Integer status;

	private String title;


	private List<CurrencyLog> currencyLogs;

	private List<GoodsChance> goodsChances;

	public void applyDefaultValue(){
		
	}
	
	@Id
	@Column(name = "rule_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_draw_rule", pkColumnValue = "t_draw_rule", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_draw_rule")
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@Lob
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="drawRule")
	public List<CurrencyLog> getCurrencyLogs() {
		return this.currencyLogs;
	}

	public void setCurrencyLogs(List<CurrencyLog> currencyLogs) {
		this.currencyLogs = currencyLogs;
	}

	public CurrencyLog addCurrencyLog(CurrencyLog CurrencyLog) {
		getCurrencyLogs().add(CurrencyLog);
		CurrencyLog.setDrawRule(this);

		return CurrencyLog;
	}

	public CurrencyLog removeCurrencyLog(CurrencyLog CurrencyLog) {
		getCurrencyLogs().remove(CurrencyLog);
		CurrencyLog.setDrawRule(null);

		return CurrencyLog;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="drawRule")
	public List<GoodsChance> getGoodsChances() {
		return this.goodsChances;
	}

	public void setGoodsChances(List<GoodsChance> goodsChances) {
		this.goodsChances = goodsChances;
	}

	public GoodsChance addGoodsChance(GoodsChance GoodsChance) {
		getGoodsChances().add(GoodsChance);
		GoodsChance.setDrawRule(this);

		return GoodsChance;
	}

	public GoodsChance removeGoodsChance(GoodsChance GoodsChance) {
		getGoodsChances().remove(GoodsChance);
		GoodsChance.setDrawRule(null);

		return GoodsChance;
	}

}