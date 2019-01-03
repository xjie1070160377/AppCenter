package cn.mooc.app.module.cms.data.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import cn.mooc.app.module.cms.support.Constants;

/**
 * Global
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_global")
public class Global implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 模型类型
	 */
	public static final String MODEL_TYPE = "global";
	/**
	 * 系统预定义字段前缀
	 */
	public static final String SYS_PREFIX = "sys_";

	@Transient
	public static void removeAttr(Map<String, String> map, String prefix) {
		Set<String> keysToRemove = new HashSet<String>();
		for (String key : map.keySet()) {
			if (key.startsWith(prefix)) {
				keysToRemove.add(key);
			}
		}
		for (String key : keysToRemove) {
			map.remove(key);
		}
	}

	@Transient
	public static void removeAttrExcludeSys(Map<String, String> map) {
		Set<String> keysToRemove = new HashSet<String>();
		for (String key : map.keySet()) {
			if (!key.startsWith(SYS_PREFIX)) {
				keysToRemove.add(key);
			}
		}
		for (String key : keysToRemove) {
			map.remove(key);
		}
	}

	@Transient
	public String getVersion() {
		return Constants.VERSION;
	}

	@Transient
	public boolean isDocEnabled() {
		if (StringUtils.isBlank(Constants.OPENOFFICE_HOST) || Constants.OPENOFFICE_PORT <= 0 || StringUtils.isBlank(Constants.SWFTOOLS_PDF2SWF)) {
			return false;
		} else {
			return true;
		}
	}

	@Transient
	public String getTemplateDisplayPathByCtx() {
		String ctx = getContextPath();
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(ctx) && (StringUtils.isBlank(Constants.TEMPLATE_DISPLAY_PATH) || StringUtils.startsWith(Constants.TEMPLATE_DISPLAY_PATH, "/"))) {
			sb.append(ctx);
		}
		if (StringUtils.isNotBlank(Constants.TEMPLATE_DISPLAY_PATH)) {
			sb.append(Constants.TEMPLATE_DISPLAY_PATH);
		}
		return sb.toString();
	}

	@Transient
	public String getStaticDisplayPath() {
		String ctx = getContextPath();
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(ctx) && (StringUtils.isBlank(Constants.STATIC_DISPLAY_PATH) || StringUtils.startsWith(Constants.STATIC_DISPLAY_PATH, "/"))) {
			sb.append(ctx);
		}
		if (StringUtils.isNotBlank(Constants.STATIC_DISPLAY_PATH)) {
			sb.append(Constants.STATIC_DISPLAY_PATH);
		}
		return sb.toString();
	}

	@Transient
	public String getUploadsDomain() {
		return getUploadsPublishPoint().getDisplayDomain();
	}

	@Transient
	public Set<String> getValidDomains() {
		Set<String> domains = new HashSet<String>();
		List<Site> sites = getSites();
		for (Site site : sites) {
			domains.add(site.getDomain());
		}
		return domains;
	}

	@Transient
	public Object getConf(String className) {
		try {
			return Class.forName(className).getConstructor(Map.class).newInstance(getCustoms());
		} catch (Exception e) {
			throw new IllegalArgumentException("Class '" + className + "' is not Conf Class", e);
		}
	}

	private Integer id;
	private PublishPoint uploadsPublishPoint;
	private List<Site> sites = new ArrayList<Site>(0);
	private Map<String, String> customs = new HashMap<String, String>(0);
	private Map<String, String> clobs = new HashMap<String, String>(0);

	private String protocol;
	private Integer port;
	private String contextPath;
	private Boolean withDomain;
	private String dataVersion;

	@Id
	@Column(name = "global_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploads_publishpoint_id", nullable = false)
	public PublishPoint getUploadsPublishPoint() {
		return uploadsPublishPoint;
	}

	public void setUploadsPublishPoint(PublishPoint uploadsPublishPoint) {
		this.uploadsPublishPoint = uploadsPublishPoint;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "global")
	@OrderBy("treeNumber asc")
	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_global_custom", joinColumns = @JoinColumn(name = "global_id"))
	@MapKeyColumn(name = "key", length = 50)
	@Column(name = "value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_global_clob", joinColumns = @JoinColumn(name = "global_id"))
	@MapKeyColumn(name = "key", length = 50)
	@MapKeyType(value = @Type(type = "string"))
	@Lob
	@Column(name = "value", nullable = false)
	public Map<String, String> getClobs() {
		return this.clobs;
	}

	public void setClobs(Map<String, String> clobs) {
		this.clobs = clobs;
	}

	@Length(max = 50)
	@Column(name = "protocol", length = 50)
	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "port")
	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Length(max = 255)
	@Column(name = "context_path", length = 255)
	public String getContextPath() {
		return this.contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Column(name = "is_with_domain", nullable = false, length = 1)
	public Boolean getWithDomain() {
		return this.withDomain;
	}

	public void setWithDomain(Boolean withDomain) {
		this.withDomain = withDomain;
	}

	@Length(max = 50)
	@Column(name = "version", nullable = false, length = 50)
	public String getDataVersion() {
		return this.dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}
}
