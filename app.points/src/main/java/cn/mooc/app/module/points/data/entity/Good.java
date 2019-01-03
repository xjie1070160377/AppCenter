package cn.mooc.app.module.points.data.entity;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the t_goods database table.
 * 
 */
@Entity
@Table(name="t_goods")
public class Good implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String caption;

	private String goodName;

	private Double price;

	private String url;

	private List<ExchangeGood> exchangeGoods;

	private List<GoodsChance> goodsChances;
	
	public void applyDefaultValue(){
		
	}

	@Id
	@Column(name = "good_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_goods", pkColumnValue = "t_goods", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_goods")
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

	@Column(name="good_name")
	public String getGoodName() {
		return this.goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="good")
	public List<ExchangeGood> getExchangeGoods() {
		return this.exchangeGoods;
	}

	public void setExchangeGoods(List<ExchangeGood> exchangeGoods) {
		this.exchangeGoods = exchangeGoods;
	}

	public ExchangeGood addExchangeGood(ExchangeGood exchangeGoods) {
		getExchangeGoods().add(exchangeGoods);
		exchangeGoods.setGood(this);

		return exchangeGoods;
	}

	public ExchangeGood removeExchangeGood(ExchangeGood exchangeGoods) {
		getExchangeGoods().remove(exchangeGoods);
		exchangeGoods.setGood(null);

		return exchangeGoods;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy="good")
	public List<GoodsChance> getGoodsChances() {
		return this.goodsChances;
	}

	public void setGoodsChances(List<GoodsChance> goodsChances) {
		this.goodsChances = goodsChances;
	}

	public GoodsChance addGoodsChance(GoodsChance goodsChances) {
		getGoodsChances().add(goodsChances);
		goodsChances.setGood(this);

		return goodsChances;
	}

	public GoodsChance removeGoodsChance(GoodsChance GoodsChance) {
		getGoodsChances().remove(GoodsChance);
		GoodsChance.setGood(null);

		return GoodsChance;
	}

}