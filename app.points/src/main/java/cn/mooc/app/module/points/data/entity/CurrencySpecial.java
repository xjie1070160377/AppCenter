package cn.mooc.app.module.points.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import cn.mooc.app.module.cms.data.entity.Info;


/**
 * The persistent class for the t_currency_special database table.
 * 
 */
@Entity
@Table(name="t_currency_special")
public class CurrencySpecial implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Info info;

	private CurrencyRule currencyRule;
	
	public void applyDefaultValue(){
		
	}

	@Id
	@Column(name = "special_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_currency_special", pkColumnValue = "t_currency_special", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_currency_special")
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="info_id")
	public Info getInfo() {
		return info;
	}


	public void setInfo(Info info) {
		this.info = info;
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