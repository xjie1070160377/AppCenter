package cn.mooc.app.core.data.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the t_sys_org database table.
 * 
 */
@Entity
@Table(name="t_sys_org")
public class SysOrg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 树编码长度
	 */
	public static int TREE_NUMBER_LENGTH = 4;
	
	@Transient
	public static String long2hex(long num) {
		BigInteger big = BigInteger.valueOf(num);
		String hex = big.toString(Character.MAX_RADIX);
		return StringUtils.leftPad(hex, TREE_NUMBER_LENGTH, '0');
	}
	
	@Transient
	public static long hex2long(String hex) {
		BigInteger big = new BigInteger(hex, Character.MAX_RADIX);
		return big.longValue();
	}
	
	@Transient
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();
		SysOrg org = this;
		sb.append(org.getName());
		org = org.getParent();
		while (org != null) {
			sb.insert(0, " - ");
			sb.insert(0, org.getName());
			org = org.getParent();
		}
		return sb.toString();
	}
	
	@Transient
	public long getTreeMaxLong() {
		BigInteger big = new BigInteger(getTreeMax(), Character.MAX_RADIX);
		return big.longValue();
	}
	
	@Transient
	public void addChild(SysOrg bean) {
		List<SysOrg> list = getChildrens();
		if (list == null) {
			list = new ArrayList<SysOrg>();
			setChildrens(list);
		}
		list.add(bean);
	}

	@Transient
	public void applyDefaultValue() {
	}

	@Id
	@Column(name="org_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_sys_org", pkColumnValue = "t_sys_org", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_sys_org")
	private Integer id;

	private String address;

	private String contacts;

	private String description;

	private String fax;

	@Column(name="full_name")
	private String fullName;

	private String name;

	private String number;

	private String phone;
	
	private Integer oid;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	@Column(name="tree_level")
	private Integer treeLevel;

	@Column(name="tree_max")
	private String treeMax;

	@Column(name="tree_number")
	private String treeNumber;

	//bi-directional many-to-one association to TSysOrg
	@ManyToOne
	@JoinColumn(name="parent_id")
	@JsonBackReference
	private SysOrg parent;

	//bi-directional many-to-one association to TSysOrg
	@OneToMany(mappedBy="parent")
	@JsonBackReference
	private List<SysOrg> childrens;

	public SysOrg() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getTreeLevel() {
		return this.treeLevel;
	}

	public void setTreeLevel(int treeLevel) {
		this.treeLevel = treeLevel;
	}

	public String getTreeMax() {
		return this.treeMax;
	}

	public void setTreeMax(String treeMax) {
		this.treeMax = treeMax;
	}

	public String getTreeNumber() {
		return this.treeNumber;
	}

	public void setTreeNumber(String treeNumber) {
		this.treeNumber = treeNumber;
	}

	public SysOrg getParent() {
		return this.parent;
	}

	public void setParent(SysOrg parent) {
		this.parent = parent;
	}

	public List<SysOrg> getChildrens() {
		return this.childrens;
	}

	public void setChildrens(List<SysOrg> childrens) {
		this.childrens = childrens;
	}

	public SysOrg addChildren(SysOrg org) {
		getChildrens().add(org);
		org.setParent(this);

		return org;
	}

	public SysOrg removeChildren(SysOrg org) {
		getChildrens().remove(org);
		org.setParent(null);

		return org;
	}

}