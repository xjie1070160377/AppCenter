package cn.mooc.app.module.points.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the t_goods_chance database table.
 * 
 */
@Entity
@Table(name="t_goods_chance")
public class GoodsChance implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Double chance;

	private Integer remianTotal;

	private Integer total;

	private Good good;

	private DrawRule drawRule;
	
	public void applyDefaultValue(){
		
	}

	@Id
	@Column(name = "chance_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_goods_chance", pkColumnValue = "t_goods_chance", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_goods_chance")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getChance() {
		return this.chance;
	}

	public void setChance(Double chance) {
		this.chance = chance;
	}

	@Column(name="remian_total")
	public Integer getRemianTotal() {
		return this.remianTotal;
	}

	public void setRemianTotal(Integer remianTotal) {
		this.remianTotal = remianTotal;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="good_id")
	public Good getGood() {
		return this.good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rule_id")
	public DrawRule getDrawRule() {
		return this.drawRule;
	}

	public void setDrawRule(DrawRule drawRule) {
		this.drawRule = drawRule;
	}

}