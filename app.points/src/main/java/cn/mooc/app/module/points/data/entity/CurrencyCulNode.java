package cn.mooc.app.module.points.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import cn.mooc.app.module.cms.data.entity.Node;


/**
 * The persistent class for the t_currency_cul_node database table.
 * 
 */
@Entity
@Table(name="t_currency_cul_node")
public class CurrencyCulNode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public void applyDefaultValue(){
		
	}

	private Integer id;

	private Node node;

	private CurrencyRule rule;

	@Id
	@Column(name = "cul_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_currency_cul_node", pkColumnValue = "t_currency_cul_node", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_currency_cul_node")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="node_id")
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rule_id")
	public CurrencyRule getRule() {
		return rule;
	}

	public void setRule(CurrencyRule rule) {
		this.rule = rule;
	}
	
	


}