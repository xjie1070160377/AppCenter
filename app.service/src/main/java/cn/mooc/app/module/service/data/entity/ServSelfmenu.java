package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


/**
 * The persistent class for the t_serv_selfmenu database table.
 * 
 */
@Entity
@Table(name="t_serv_selfmenu")
public class ServSelfmenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_serv_selfmenu", pkColumnValue = "t_serv_selfmenu", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_serv_selfmenu")
	private Integer id;

	private String menuname;

	private Integer menuorder;

	private Integer menutype;

	private String menuvalue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceId")
	private AppService appService;

	//bi-directional many-to-one association to TServSelfmenu
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parentid")
	private ServSelfmenu parent;

	//bi-directional many-to-one association to TServSelfmenu
	@OneToMany(mappedBy="parent")
	private List<ServSelfmenu> childrens;

	public ServSelfmenu() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Integer getMenuorder() {
		return this.menuorder;
	}

	public void setMenuorder(Integer menuorder) {
		this.menuorder = menuorder;
	}

	public Integer getMenutype() {
		return this.menutype;
	}

	public void setMenutype(Integer menutype) {
		this.menutype = menutype;
	}

	public String getMenuvalue() {
		return this.menuvalue;
	}

	public void setMenuvalue(String menuvalue) {
		this.menuvalue = menuvalue;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public ServSelfmenu getParent() {
		return parent;
	}

	public void setParent(ServSelfmenu parent) {
		this.parent = parent;
	}

	public List<ServSelfmenu> getChildrens() {
		return this.childrens;
	}

	public void setChildrens(List<ServSelfmenu> childrens) {
		this.childrens = childrens;
	}

	public ServSelfmenu addTServSelfmenus(ServSelfmenu children) {
		getChildrens().add(children);
		children.setParent(this);

		return children;
	}

	public ServSelfmenu removeTServSelfmenus(ServSelfmenu children) {
		getChildrens().remove(children);
		children.setParent(null);

		return children;
	}

}