package cn.mooc.app.module.ad.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * The persistent class for the t_cms_ad database table.
 * 
 */
@Entity
@Table(name="t_cms_ad")
public class Ad implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 网站链接
	 */
	public static final int LINK_TYPE_URL = 1; // 网站链接
	/**
	 * 文档链接
	 */
	public static final int LINK_TYPE_INFO = 2; // 文档链接
	/**
	 * 专题链接
	 */
	public static final int LINK_TYPE_SPECIAL = 3; // 专题链接
	/**
	 * 图文链接
	 */
	public static final int LINK_TYPE_IMAGE = 4; // 图文链接
	/**
	 * 栏目链接
	 */
	public static final int LINK_TYPE_NODE = 6; // 栏目链接
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ad_id", unique = true, nullable = false)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="begin_date")
	private Date beginDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="end_date")
	private Date endDate;

	private String flash;

	private String image;

	private String name;

	@Lob
	private String script;

	private Integer seq;

	@Column(name="site_id")
	private Integer siteId;

	private String text;

	private String url;
	private Integer linkType;

	//bi-directional many-to-one association to AdSlot
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="adslot_id")
	private AdSlot adSlot;

	public Ad() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFlash() {
		return this.flash;
	}

	public void setFlash(String flash) {
		this.flash = flash;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AdSlot getAdSlot() {
		return this.adSlot;
	}

	public void setAdSlot(AdSlot adSlot) {
		this.adSlot = adSlot;
	}
	@Transient
	public void applyDefaultValue() {
		
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
}