package cn.mooc.app.module.cms.data.entity;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import cn.mooc.app.module.cms.support.Constants;

/**
 * The persistent class for the t_cms_site database table.
 * 
 */
@Entity
@Table(name = "t_cms_site")
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "site_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_site", pkColumnValue = "t_cms_site", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_site")
	private int id;

	private String domain;

	@Column(name = "full_name")
	private String fullName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "global_id", nullable = false)
	@JsonBackReference
	private Global global;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "html_publishpoint_id", nullable = false)
	@JsonBackReference
	private PublishPoint htmlPublishPoint;

	/**
	 * 是否默认站点
	 */
	@Column(name = "is_def")
	private Boolean def;

	/**
	 * 域名识别
	 */
	@Column(name = "is_identify_domain")
	private Boolean identifyDomain;

	/**
	 * 静态首页
	 */
	@Column(name = "is_static_home")
	private String isStaticHome;
	
	@Column(name = "name")
	private String name;

	@Column(name = "need_login")
	private byte needLogin;

	@Column(name = "no_picture")
	private String noPicture;

	@Column(name = "number")
	private String number;

	@Column(name = "org_id")
	private Integer orgId;

	@Column(name = "point_step")
	private byte pointStep;

	/**
	 * 启用PC端页面
	 */
	@Column(name = "show_index")
	private byte showIndex;

	@Column(name = "status")
	private int status;

	@Column(name = "template_theme")
	private String templateTheme;

	@Column(name = "tree_level")
	private int treeLevel;

	@Column(name = "tree_max")
	private String treeMax;

	@Column(name = "tree_number")
	private String treeNumber;

	// bi-directional many-to-one association to CmsSite
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Site site;

	// bi-directional many-to-one association to CmsSite
	@OneToMany(mappedBy = "site")
	private List<Site> ChildSites;

	@ElementCollection
	@CollectionTable(name = "t_cms_site_custom", joinColumns = @JoinColumn(name = "site_id"))
	@MapKeyColumn(name = "key", length = 50)
	@Column(name = "value", length = 2000)
	private Map<String, String> customs = new HashMap<String, String>(0);

	@ElementCollection
	@CollectionTable(name = "t_cms_site_clob", joinColumns = @JoinColumn(name = "site_id"))
	@MapKeyColumn(name = "key", length = 50)
	@MapKeyType(value = @Type(type = "string"))
	@Lob
	@Column(name = "value", nullable = false)
	private Map<String, String> clobs = new HashMap<String, String>(0);

	public String getSiteUrl() {
		return getProtocol() + ":" + getUrl(true);
	}
	
	public String getUrl(boolean isFull) {
		StringBuilder sb = new StringBuilder();
		if (isFull) {
			sb.append("//").append(getDomain());
			if (getPort() != null) {
				sb.append(":").append(getPort());
			}
		}
		if (StringUtils.isNotBlank(getContextPath())) {
			sb.append(getContextPath());
		}
		
		return sb.toString();
	}
	
	public Site() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int siteId) {
		this.id = siteId;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Global getGlobal() {
		return this.global;
	}

	public void setGlobal(Global global) {
		this.global = global;
	}

	public PublishPoint getHtmlPublishPoint() {
		return this.htmlPublishPoint;
	}

	public void setHtmlPublishPoint(PublishPoint htmlPublishPoint) {
		this.htmlPublishPoint = htmlPublishPoint;
	}

	public Boolean getDef() {
		return this.def;
	}

	public void setDef(Boolean def) {
		this.def = def;
	}

	public Boolean getIdentifyDomain() {
		return this.identifyDomain;
	}

	public void setIdentifyDomain(Boolean identifyDomain) {
		this.identifyDomain = identifyDomain;
	}

	public String getIsStaticHome() {
		return this.isStaticHome;
	}

	public void setIsStaticHome(String isStaticHome) {
		this.isStaticHome = isStaticHome;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getNeedLogin() {
		return this.needLogin;
	}

	public void setNeedLogin(byte needLogin) {
		this.needLogin = needLogin;
	}

	public String getNoPicture() {
		return this.noPicture;
	}

	public void setNoPicture(String noPicture) {
		this.noPicture = noPicture;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public byte getPointStep() {
		return this.pointStep;
	}

	public void setPointStep(byte pointStep) {
		this.pointStep = pointStep;
	}

	public byte getShowIndex() {
		return this.showIndex;
	}

	public void setShowIndex(byte showIndex) {
		this.showIndex = showIndex;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTemplateTheme() {
		return this.templateTheme;
	}

	public void setTemplateTheme(String templateTheme) {
		this.templateTheme = templateTheme;
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

	public Site getCmsSite() {
		return this.site;
	}

	public void setCmsSite(Site site) {
		this.site = site;
	}

	public List<Site> getChildSites() {
		return this.ChildSites;
	}

	public void setChildSites(List<Site> ChildSites) {
		this.ChildSites = ChildSites;
	}

	public Site addChildCmsSite(Site ChildSites) {
		getChildSites().add(ChildSites);
		ChildSites.setCmsSite(this);

		return ChildSites;
	}

	public Site removeChildCmsSite(Site ChildSites) {
		getChildSites().remove(ChildSites);
		ChildSites.setCmsSite(null);

		return ChildSites;
	}

	public Map<String, String> getCustoms() {
		return customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	public Map<String, String> getClobs() {
		return clobs;
	}

	public void setClobs(Map<String, String> clobs) {
		this.clobs = clobs;
	}

	@Transient
	public PublishPoint getUploadsPublishPoint() {
		return getGlobal() != null ? getGlobal().getUploadsPublishPoint() : null;
	}

	@Transient
	public String getProtocol() {
		return getGlobal() != null ? getGlobal().getProtocol() : null;
	}

	@Transient
	public Integer getPort() {
		return getGlobal() != null ? getGlobal().getPort() : null;
	}

	@Transient
	public String getContextPath() {
		return getGlobal() != null ? getGlobal().getContextPath() : null;
	}

	@Transient
	public String getVersion() {
		return getGlobal() != null ? getGlobal().getVersion() : null;
	}

	@Transient
	public Boolean getWithDomain() {
		return getGlobal() != null ? getGlobal().getWithDomain() : null;
	}

	@Transient
	public String getNoPictureUrl() {
		return getFilesUrl(getNoPicture());
	}

	@Transient
	public String getFullNameOrName() {
		String fullName = getFullName();
		return StringUtils.isBlank(fullName) ? getName() : fullName;
	}

	@Transient
	public SiteWatermark getWatermark() {
		return new SiteWatermark(this);
	}

	/**
	 * 获得当前模板主题的资源文件路径
	 * 
	 * 例如：/jspxcms/template/1/bluewise/_files
	 * 
	 * @param path
	 * @return
	 */
	@Transient
	public String getFilesUrl(String path) {
		return getFiles(path, true);
	}

	@Transient
	public String getFilesPath() {
		return getFilesPath(null);
	}

	@Transient
	public String getFilesPath(String path) {
		return getFiles(path, false);
	}

	@Transient
	public String getFiles(String path, boolean forDisplay) {
		StringBuilder sb = new StringBuilder();
		if (forDisplay) {
			String displayPath = getGlobal().getTemplateDisplayPathByCtx();
			if (StringUtils.isNotBlank(displayPath)) {
				sb.append(displayPath);
			}
		}
		sb.append("/").append(getId());
		sb.append("/").append(getTemplateTheme());
		sb.append("/").append(Constants.FILES);
		if (StringUtils.isNotBlank(path)) {
			sb.append(path);
		}
		return sb.toString();
	}
	
	/**
	 * 获得模板的基础路径
	 * 
	 * 例如：/1/...
	 * 
	 * @param path
	 * @return
	 */
	@Transient
	public String getSiteBase(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("/").append(getId());
		if (StringUtils.isNotBlank(path)) {
			if (!path.startsWith("/")) {
				sb.append("/");
			}
			sb.append(path);
		}
		return sb.toString();
	}

	@Transient
	public String getTemplate(String tpl) {
		StringBuilder sb = new StringBuilder();
		sb.append("theme");
		sb.append("/").append(getId());
		sb.append("/").append(getTemplateTheme());
		if (tpl != null) {
			if (!tpl.startsWith("/")) {
				sb.append("/");
			}
			sb.append(tpl);
		}
		return sb.toString();
	}

	@Transient
	public <T> T getConf(Class<T> clazz) {
		try {
			return clazz.getConstructor(Site.class).newInstance(this);
		} catch (Exception e) {
			throw new IllegalArgumentException("Class '" + clazz.getName() + "' is not Conf Class", e);
		}
	}

	public static String getIdentityCookie(HttpServletRequest request, HttpServletResponse response) {
		String value;
		Cookie cookie = WebUtils.getCookie(request, Constants.IDENTITY_COOKIE_NAME);
		if (cookie != null && StringUtils.isNotBlank(cookie.getValue())) {
			value = cookie.getValue();
		} else {
			value = UUID.randomUUID().toString();
			value = StringUtils.remove(value, '-');
			cookie = new Cookie(Constants.IDENTITY_COOKIE_NAME, value);
			String ctx = request.getContextPath();
			if (StringUtils.isBlank(ctx)) {
				ctx = "/";
			}
			cookie.setPath(ctx);
			cookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookie);
		}
		return value;
	}
}