package cn.mooc.app.module.points.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import cn.mooc.app.core.data.entity.SysUserEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_user_day_cur database table.
 * 
 */
@Entity
@Table(name="t_user_day_cur")
public class UserDayCur implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public void applyDefaultValue(){
		
	}

	private Integer id;

	private Date lastTime;

	private Double total;
	
	private Long userId;

	private CurrencyRule currencyRule;

	@Id
	@Column(name = "day_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_user_day_cur", pkColumnValue = "t_user_day_cur", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_user_day_cur")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="last_time")
	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rule_id")
	public CurrencyRule getCurrencyRule() {
		return this.currencyRule;
	}

	public void setCurrencyRule(CurrencyRule currencyRule) {
		this.currencyRule = currencyRule;
	}

}