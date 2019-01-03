package cn.mooc.app.module.cms.data.entity;

import static cn.mooc.app.module.cms.support.Constants.DYNAMIC_SUFFIX;
import static cn.mooc.app.module.cms.support.Constants.SITE_PREFIX;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import org.springframework.util.CollectionUtils;
import cn.mooc.app.module.cms.support.Constants;
import cn.mooc.app.module.cms.support.ImageAnchor;
import cn.mooc.app.module.cms.support.ImageAnchorBean;

/**
 * Node
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_node")
public class Node implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 首页模型类型
	 */
	public static final String HOME_MODEL_TYPE = "node_home";
	/**
	 * 节点模型类型
	 */
	public static final String NODE_MODEL_TYPE = "node";
	/**
	 * 附件类型
	 */
	public static final String ATTACH_TYPE = "node";
	/**
	 * 手动生成
	 */
	public static final int STATIC_MANUAL = 0;
	/**
	 * 自动生成信息页
	 */
	public static final int STATIC_INFO = 1;
	/**
	 * 自动生成信息页、节点页
	 */
	public static final int STATIC_INFO_NODE = 2;
	/**
	 * 自动生成信息页、节点页、父节点页、首页
	 */
	public static final int STATIC_INFO_NODE_PARENT = 3;
	/**
	 * 自动生成信息页、节点页、父节点页、首页、列表页
	 */
	public static final int STATIC_INFO_NODE_PARENT_LIST = 4;

	/**
	 * 未开启
	 */
	public static final String HTML_DISABLED = "0";
	/**
	 * 待发布
	 */
	public static final String HTML_TOBE_PUBLISH = "1";
	/**
	 * 待生成
	 */
	public static final String HTML_TOBE_GENERATE = "2";
	/**
	 * 待更新
	 */
	public static final String HTML_TOBE_UPDATE = "3";
	/**
	 * 待删除
	 */
	public static final String HTML_TOBE_DELETE = "4";
	/**
	 * 已生成
	 */
	public static final String HTML_GENERATED = "5";

	/**
	 * 树编码长度
	 */
	public static int TREE_NUMBER_LENGTH = 4;
	/**
	 * 替换标识:节点ID
	 */
	public static String PATH_NODE_ID = "{node_id}";
	/**
	 * 替换标识:节点编码
	 */
	public static String PATH_NODE_NUMBER = "{node_number}";

	/**
	 * 节点正文KEY
	 */
	public static String NODE_TEXT = "text";

	public static final String COVER_TEMPLATE = "coverTemplate";
	public static final String LIST_TEMPLATE = "listTemplate";
	public static final String TEMPLATE = "template";
	public static final String GENERATE_NODE = "generateNode";
	public static final String GENERATE_INFO = "generateInfo";
	public static final String NODE_PATH = "nodePath";
	public static final String INFO_PATH = "infoPath";
	public static final String NODE_EXTENSION = "nodeExtension";
	public static final String INFO_EXTENSION = "infoExtension";
	public static final String DEF_PAGE = "defPage";
	public static final String STATIC_METHOD = "staticMethod";
	public static final String STATIC_PAGE = "staticPage";

	public static String getCoverTemplate(Map<String, String> customs) {
		return customs != null ? customs.get(COVER_TEMPLATE) : null;
	}

	public static String getListTemplate(Map<String, String> customs) {
		return customs != null ? customs.get(LIST_TEMPLATE) : null;
	}

	public static String getTemplate(Map<String, String> customs) {
		return customs != null ? customs.get(TEMPLATE) : null;
	}

	public static boolean getGenerateNode(Map<String, String> customs) {
		String value = customs != null ? customs.get(GENERATE_NODE) : null;
		return value != null ? Boolean.valueOf(value) : false;
	}

	public static boolean getGenerateInfo(Map<String, String> customs) {
		String value = customs != null ? customs.get(GENERATE_INFO) : null;
		return value != null ? Boolean.valueOf(value) : false;
	}

	public static String getNodePath(Map<String, String> customs) {
		return customs != null ? customs.get(NODE_PATH) : null;
	}

	public static String getInfoPath(Map<String, String> customs) {
		return customs != null ? customs.get(INFO_PATH) : null;
	}

	public static String getNodeExtension(Map<String, String> customs) {
		return customs != null ? customs.get(NODE_EXTENSION) : null;
	}

	public static String getInfoExtension(Map<String, String> customs) {
		return customs != null ? customs.get(INFO_EXTENSION) : null;
	}

	public static boolean getDefPage(Map<String, String> customs) {
		String value = customs != null ? customs.get(DEF_PAGE) : null;
		return value != null ? Boolean.valueOf(value) : false;
	}

	public static int getStaticMethod(Map<String, String> customs) {
		String value = customs != null ? customs.get(STATIC_METHOD) : null;
		return NumberUtils.toInt(value, Node.STATIC_INFO_NODE_PARENT_LIST);
	}

	public static int getStaticPage(Map<String, String> customs) {
		String value = customs != null ? customs.get(STATIC_PAGE) : null;
		return NumberUtils.toInt(value, 1);
	}

	// @Transient
	// public boolean isViewPerm(Collection<MemberGroup> groups,
	// Collection<Org> orgs) {
	// if (Reflections.containsAny(getViewGroups(), groups, "id")) {
	// return true;
	// }
	// if (CollectionUtils.isNotEmpty(orgs)
	// && Reflections.containsAny(getViewOrgs(), orgs, "id")) {
	// return true;
	// }
	// return false;
	// }

	// @Transient
	// public boolean isDataPerm(User user) {
	// Integer siteId = getSite().getId();
	// if (user.getAllNodePerm(siteId)
	// || Reflections.contains(user.getNodePerms(siteId), getId(),
	// "id")) {
	// return true;
	// }
	// return false;
	// }

	// @Transient
	// public boolean isContriPerm(User user, Collection<MemberGroup> groups) {
	// if (!getRealNode()) {
	// // 不是真实节点（没有信息的节点）不允许投稿
	// return false;
	// }
	// Set<MemberGroup> contriGroups = getContriGroups();
	// return Reflections.containsAny(contriGroups, groups, "id");
	// }

	@Transient
	public void updateHtmlStatus() {
		Node node = this;
		while (node != null) {
			if (node.getGenerate()) {
				if (StringUtils.isNotBlank(node.getHtml())) {
					node.setHtmlStatus(HTML_TOBE_UPDATE);
				} else {
					node.setHtmlStatus(HTML_TOBE_GENERATE);
				}
			} else {
				if (StringUtils.isNotBlank(node.getHtml())) {
					node.setHtmlStatus(HTML_TOBE_DELETE);
				} else {
					node.setHtmlStatus(HTML_DISABLED);
				}
			}
			node = node.getParent();
		}
	}

	@Transient
	public String getUrl() {
		return getUrl(1);
	}

	@Transient
	public String getUrl(Integer page) {
		return getGenerate() ? getUrlStatic(page) : getUrlDynamic(page);
	}

	@Transient
	public String getUrlDynamic() {
		return getUrlDynamic(1);
	}

	@Transient
	public String getUrlDynamicFull() {
		return getUrlDynamicFull(1);
	}

	@Transient
	public String getUrlDynamicFull(Integer page) {
		return getUrlDynamic(page, true);
	}

	@Transient
	public String getUrlDynamic(Integer page) {
		Site site = getSite();
		boolean isFull = site.getWithDomain() || site.getIdentifyDomain();
		return getUrlDynamic(page, isFull);
	}

	@Transient
	public String getUrlDynamic(Integer page, boolean isFull) {
		if (isLinked()) {
			return getLinkUrl();
		}
		Site site = getSite();
		StringBuilder sb = new StringBuilder();
		if (isFull) {
			sb.append("//").append(site.getDomain());
			if (site.getPort() != null) {
				sb.append(":").append(site.getPort());
			}
		}
		String ctx = getSite().getContextPath();
		if (StringUtils.isNotBlank(ctx)) {
			sb.append(ctx);
		}
		boolean sitePrefix = !site.getIdentifyDomain() && !site.getDef();
		if (sitePrefix) {
			sb.append(SITE_PREFIX).append(site.getNumber());
		}
		sb.append("/");
		if (getParent() != null) {
			sb.append(Constants.NODE_PATH);
			sb.append("/").append(getId());
			if (page != null && page > 1) {
				sb.append("_").append(page);
			}
			sb.append(DYNAMIC_SUFFIX);
		} else {
			if (sitePrefix) {
				sb.append(DYNAMIC_SUFFIX);
			} else {
				// 首页直接使用根路径
			}
		}
		return sb.toString();
	}

	@Transient
	public String getUrlStatic() {
		return getUrlStatic(1);
	}

	@Transient
	public String getUrlStatic(Integer page) {
		Site site = getSite();
		boolean isFull = site.getWithDomain() || site.getIdentifyDomain();
		return getUrlStatic(page, isFull, false);
	}

	@Transient
	public String getUrlStaticFull() {
		return getUrlStaticFull(1);
	}

	@Transient
	public String getUrlStaticFull(Integer page) {
		return getUrlStatic(page, true, false);
	}

	@Transient
	public String getUrlStatic(Integer page, boolean isFull, boolean forRealPath) {
		if (isLinked()) {
			return getLinkUrl();
		}
		// 超过静态化页数，则为动态页。
		if (page != null && page > getStaticPageOrDef()) {
			return getUrlDynamic(page);
		}
		String path = getNodePathOrDef();
		path = StringUtils.replace(path, PATH_NODE_ID, getId().toString());
		String number = getNumber();
		if (StringUtils.isBlank(number)) {
			number = getId().toString();
		}
		path = StringUtils.replace(path, PATH_NODE_NUMBER, number);
		String extension = getNodeExtensionOrDef();
		if (page != null && page > 1) {
			if (StringUtils.isNotBlank(extension)) {
				path += "_" + page + extension;
			} else {
				path += "_" + page;
			}
		} else if (!forRealPath && getDefPageOrDef()) {
			path = path.substring(0, path.lastIndexOf("/") + 1);
		} else {
			if (StringUtils.isNotBlank(extension)) {
				path += extension;
			}
		}

		StringBuilder sb = new StringBuilder();
		Site site = getSite();
		if (isFull && !forRealPath) {
			sb.append("//");
			String domain = site.getDomain();
			sb.append(domain);
			if (site.getPort() != null) {
				sb.append(":").append(site.getPort());
			}
		}
		if (!forRealPath) {
			PublishPoint point = getSite().getHtmlPublishPoint();
			String urlPrefix = point.getUrlPrefix();
			if (StringUtils.isNotBlank(urlPrefix)) {
				sb.append(urlPrefix);
			}
		}
		sb.append(path);
		return sb.toString();
	}

	@Transient
	public String getPageUrl(Integer page) {
		return getUrl(page);
	}

	@Transient
	public String getLinkUrl() {
		String link = getLink();
		if (StringUtils.isBlank(link)) {
			return link;
		}
		if (link.startsWith("/") && !link.startsWith("//")) {
			StringBuilder sb = new StringBuilder();
			Site site = getSite();
			if (site.getWithDomain()) {
				sb.append("//").append(site.getDomain());
				if (site.getPort() != null) {
					sb.append(":").append(site.getPort());
				}
			}
			String ctx = site.getContextPath();
			if (StringUtils.isNotBlank(ctx)) {
				sb.append(ctx);
			}
			sb.append(link);
			link = sb.toString();
		}
		return link;
	}

	@Transient
	public boolean isLinked() {
		return StringUtils.isNotBlank(getLink());
	}

	@Transient
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();
		Node node = this;
		sb.append(node.getName());
		node = node.getParent();
		while (node != null) {
			sb.insert(0, " - ");
			sb.insert(0, node.getName());
			node = node.getParent();
		}
		return sb.toString();
	}

	@Transient
	public String getTemplate() {
		String tpl = getNodeTemplate();
		if (StringUtils.isBlank(tpl)) {
			CmsModel model = getNodeModel();
			if (getParent() == null) {
				tpl = getTemplate(model.getCustoms());
			} else {
				if (CollectionUtils.isEmpty(getChildren())) {
					tpl = getListTemplate(model.getCustoms());
				} else {
					tpl = getCoverTemplate(model.getCustoms());
				}
			}
		}
		tpl = getSite().getTemplate(tpl);
		return tpl;
	}

	@Transient
	public String getInfoTemplateOrDef() {
		String tpl = getInfoTemplate();
		if (StringUtils.isBlank(tpl)) {
			CmsModel model = getInfoModel();
			tpl = getTemplate(model.getCustoms());
		}
		tpl = getSite().getTemplate(tpl);
		return tpl;
	}

	@Transient
	public boolean getGenerate() {
		if (isLinked()) {
			return false;
		}
		return getGenerateNodeOrDef();
	}

	@Transient
	public boolean getGenerateNodeOrDef() {
		Boolean gen = getGenerateNode();
		return gen != null ? gen : getGenerateNode(getNodeModel().getCustoms());
	}

	@Transient
	public boolean getGenerateInfoOrDef() {
		Boolean gen = getGenerateInfo();
		return gen != null ? gen : getGenerateInfo(getNodeModel().getCustoms());
	}

	@Transient
	public String getNodePathOrDef() {
		return getNodePath() != null ? getNodePath() : getNodePath(getNodeModel().getCustoms());
	}

	@Transient
	public String getInfoPathOrDef() {
		return getInfoPath() != null ? getInfoPath() : getInfoPath(getNodeModel().getCustoms());
	}

	@Transient
	public String getNodeExtensionOrDef() {
		return getNodeExtension() != null ? getNodeExtension() : getNodeExtension(getNodeModel().getCustoms());
	}

	@Transient
	public String getInfoExtensionOrDef() {
		return getInfoExtension() != null ? getInfoExtension() : getInfoExtension(getNodeModel().getCustoms());
	}

	@Transient
	public int getStaticMethodOrDef() {
		return getStaticMethod() != null ? getStaticMethod() : getStaticMethod(getNodeModel().getCustoms());
	}

	@Transient
	public int getStaticPageOrDef() {
		return getStaticPage() != null ? getStaticPage() : getStaticPage(getNodeModel().getCustoms());
	}

	@Transient
	public boolean getDefPageOrDef() {
		return getDefPage() != null ? getDefPage() : getDefPage(getNodeModel().getCustoms());
	}

	@Transient
	public String getTitle() {
		return getName();
	}

	@Transient
	public String getColor() {
		return null;
	}

	@Transient
	public Boolean getStrong() {
		return null;
	}

	@Transient
	public Boolean getEm() {
		return null;
	}

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
	public long getTreeMaxLong() {
		BigInteger big = new BigInteger(getTreeMax(), Character.MAX_RADIX);
		return big.longValue();
	}

	@Transient
	public CmsModel getModel() {
		return getNodeModel();
	}

	@Transient
	public List<Node> getHierarchy() {
		List<Node> hierarchy = new LinkedList<Node>();
		Node node = this;
		while (node != null) {
			hierarchy.add(0, node);
			node = node.getParent();
		}
		return hierarchy;
	}

	@Transient
	public String getText() {
		Map<String, String> clobs = getClobs();
		return clobs != null ? clobs.get(NODE_TEXT) : null;
	}

	@Transient
	public void setText(String text) {
		Map<String, String> clobs = getClobs();
		if (clobs == null) {
			clobs = new HashMap<String, String>();
			setClobs(clobs);
		}
		clobs.put(NODE_TEXT, text);
	}

	@Transient
	public Set<String> getAttachUrls() {
		Set<String> urls = new HashSet<String>();
		// 取NodeDetail字段
		urls.add(getSmallImage());
		urls.add(getLargeImage());
		Set<String> clobEditorNames = new HashSet<String>();
		clobEditorNames.add(NODE_TEXT);
		Map<String, String> clobs = getClobs();
		
		getModel().getAttachUrls(urls, clobEditorNames, clobs, this.toCustomsMap());
		return urls;
	}

	@Transient
	public String getLink() {
		return getDetail() != null ? getDetail().getLink() : null;
	}

	@Transient
	public String getHtml() {
		return getDetail() != null ? getDetail().getHtml() : null;
	}

	@Transient
	public String getMetaKeywords() {
		return getDetail() != null ? getDetail().getMetaKeywords() : null;
	}

	@Transient
	public String getMetaDescription() {
		return getDetail() != null ? getDetail().getMetaDescription() : null;
	}

	@Transient
	public String getKeywords() {
		String keywords = getMetaKeywords();
		if (StringUtils.isBlank(keywords)) {
			return getName();
		} else {
			return keywords;
		}
	}

	@Transient
	public String getDescription() {
		String description = getMetaDescription();
		if (StringUtils.isBlank(description)) {
			return getName();
		} else {
			return description;
		}
	}

	@Transient
	public Boolean getNewWindow() {
		return getDetail() != null ? getDetail().getNewWindow() : null;
	}

	@Transient
	public String getNodeTemplate() {
		return getDetail() != null ? getDetail().getNodeTemplate() : null;
	}

	@Transient
	public String getInfoTemplate() {
		return getDetail() != null ? getDetail().getInfoTemplate() : null;
	}

	@Transient
	public Boolean getGenerateNode() {
		return getDetail() != null ? getDetail().getGenerateNode() : null;
	}

	@Transient
	public Boolean getGenerateInfo() {
		return getDetail() != null ? getDetail().getGenerateInfo() : null;
	}

	@Transient
	public String getNodeExtension() {
		return getDetail() != null ? getDetail().getNodeExtension() : null;
	}

	@Transient
	public String getInfoExtension() {
		return getDetail() != null ? getDetail().getInfoExtension() : null;
	}

	@Transient
	public String getNodePath() {
		return getDetail() != null ? getDetail().getNodePath() : null;
	}

	@Transient
	public String getInfoPath() {
		return getDetail() != null ? getDetail().getInfoPath() : null;
	}

	@Transient
	public Boolean getDefPage() {
		return getDetail() != null ? getDetail().getDefPage() : null;
	}

	@Transient
	public Integer getStaticMethod() {
		return getDetail() != null ? getDetail().getStaticMethod() : null;
	}

	@Transient
	public Integer getStaticPage() {
		return getDetail() != null ? getDetail().getStaticPage() : null;
	}

	@Transient
	public String getSmallImage() {
		return getDetail() != null ? getDetail().getSmallImage() : null;
	}

	@Transient
	public String getSmallImageUrl() {
		String url = getSmallImage();
		return StringUtils.isBlank(url) ? getSite().getNoPictureUrl() : url;
	}

	@Transient
	public ImageAnchor getSmallImageBean() {
		ImageAnchorBean bean = new ImageAnchorBean();
		bean.setTitle(getTitle());
		bean.setUrl(getUrl());
		bean.setSrc(getSmallImageUrl());
		CmsModel model = getModel();
		if (model == null) {
			return bean;
		}
		CmsModelField mf = model.getField("smallImage");
		if (mf == null) {
			return bean;
		}
		Map<String, String> map = mf.getCustoms();
		String width = map.get(CmsModelField.IMAGE_WIDTH);
		if (StringUtils.isNotBlank(width)) {
			bean.setWidth(NumberUtils.createInteger(width));
		}
		String height = map.get(CmsModelField.IMAGE_HEIGHT);
		if (StringUtils.isNotBlank(height)) {
			bean.setHeight(NumberUtils.createInteger(height));
		}
		return bean;
	}

	@Transient
	public String getLargeImage() {
		return getDetail() != null ? getDetail().getLargeImage() : null;
	}

	@Transient
	public String getLargeImageUrl() {
		String url = getLargeImage();
		return StringUtils.isBlank(url) ? getSite().getNoPictureUrl() : url;
	}

	@Transient
	public ImageAnchor getLargeImageBean() {
		ImageAnchorBean bean = new ImageAnchorBean();
		bean.setTitle(getTitle());
		bean.setUrl(getUrl());
		bean.setSrc(getLargeImageUrl());
		CmsModel model = getModel();
		if (model == null) {
			return bean;
		}
		CmsModelField mf = model.getField("largeImage");
		if (mf == null) {
			return bean;
		}
		Map<String, String> map = mf.getCustoms();
		String width = map.get(CmsModelField.IMAGE_WIDTH);
		if (StringUtils.isNotBlank(width)) {
			bean.setWidth(NumberUtils.createInteger(width));
		}
		String height = map.get(CmsModelField.IMAGE_HEIGHT);
		if (StringUtils.isNotBlank(height)) {
			bean.setHeight(NumberUtils.createInteger(height));
		}
		return bean;
	}

	@Transient
	public NodeDetail getDetail() {
		Set<NodeDetail> set = getDetails();
		if (!CollectionUtils.isEmpty(set)) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	@Transient
	public void setDetail(NodeDetail detail) {
		Set<NodeDetail> set = getDetails();
		if (set == null) {
			set = new HashSet<NodeDetail>(1);
			setDetails(set);
		} else {
			set.clear();
		}
		set.add(detail);
	}

	@Transient
	public NodeBuffer getBuffer() {
		Set<NodeBuffer> set = getBuffers();
		if (!CollectionUtils.isEmpty(set)) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	@Transient
	public void setBuffer(NodeBuffer buffer) {
		Set<NodeBuffer> set = getBuffers();
		if (set == null) {
			set = new HashSet<NodeBuffer>(1);
			setBuffers(set);
		} else {
			set.clear();
		}
		set.add(buffer);
	}

	@Transient
	public void applyDefaultValue() {
		if (getCreationDate() == null) {
			setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getRefers() == null) {
			setRefers(0);
		}
		if (getViews() == null) {
			setViews(0);
		}
		if (getRealNode() == null) {
			setRealNode(true);
		}
		if (getHidden() == null) {
			setHidden(false);
		}
//		if(getIsShowTitle()==null) {
//			setIsShowTitle(false);
//		}
		if (getHtmlStatus() == null) {
			setHtmlStatus(HTML_DISABLED);
		}
	}

	private Integer id;
	private List<NodeCustom> customs = new ArrayList<NodeCustom>(0);
	private Map<String, String> clobs = new HashMap<String, String>(0);
	private List<Node> children = new ArrayList<Node>(0);
	private Set<NodeDetail> details = new HashSet<NodeDetail>(0);
	private Set<NodeBuffer> buffers = new HashSet<NodeBuffer>(0);
	private Set<NodeRole> nodeRoles = new HashSet<NodeRole>(0);
	private Set<NodeMemberGroup> nodeGroups = new HashSet<NodeMemberGroup>(0);

	private Node parent;
	private Site site;
	private Workflow workflow;
	private long creatorId;
	private CmsModel nodeModel;
	private CmsModel infoModel;

	private String number;
	private String name;
	private String treeNumber;
	private Integer treeLevel;
	private String treeMax;
	private Date creationDate;
	private Integer refers;
	private Integer views;
	private Boolean realNode;
	private Boolean hidden;
	private String htmlStatus;

	private Integer p1;
	private Integer p2;
	private Integer p3;
	private Integer p4;
	private Integer p5;
	private Integer p6;
	private Integer show_type_id;
	private Integer isShowTitle;

	public Node() {
	}

	public Node(Site site, long creatorId, CmsModel nodeModel, String name, String treeNumber, Integer treeLevel, String treeMax) {
		this.site = site;
		this.creatorId = creatorId;
		this.nodeModel = nodeModel;
		this.name = name;
		this.treeNumber = treeNumber;
		this.treeLevel = treeLevel;
		this.treeMax = treeMax;
	}

	@Id
	@Column(name = "node_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_node", pkColumnValue = "t_cms_node", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_node")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@OrderBy(value = "treeNumber asc, id asc")
	public List<Node> getChildren() {
		return this.children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy = "node")
	public List<NodeCustom> getCustoms() {
		return this.customs;
	}
	
	public Map<String, String> toCustomsMap(){
		if(customs != null && customs.size() > 0){
			Map<String, String> omap = new HashMap<String, String>();
			if(this.customs != null && this.customs.size() > 0){
				for(NodeCustom custom : this.customs){
					omap.put(custom.getKey(), custom.getValue());
				}
			}
			return omap;
		}else{
			return new HashMap<String, String>();
		}
	}

	public void setCustoms(List<NodeCustom> customs) {
		this.customs = customs;
	}
	
	@Transient
	public void setCustoms(Map<String, String> customs){
		if(customs != null && customs.size() > 0){
			Set<String> keyset = customs.keySet();
			Iterator<String> it = keyset.iterator();
			List<NodeCustom> infoCustoms = new ArrayList<NodeCustom>();
			while(it.hasNext()){
				NodeCustom custom = new NodeCustom();
				String key = it.next();
				String value = customs.get(key);
				custom.setNode(this);
				custom.setNodeId(this.getId());
				custom.setKey(key);
				custom.setValue(value);
				infoCustoms.add(custom);
			}
			this.customs = infoCustoms;
		}else{
			this.customs = new ArrayList<NodeCustom>(0);
		}
	}
	
	@Transient
	public List<Long> getInfoPerms() {
		Set<NodeRole> nodeRoles = getNodeRoles();
		List<Long> ids = new ArrayList<>();
		for (NodeRole nr : nodeRoles) {
			if (nr.getInfoPerm()) {
				ids.add(nr.getRoleId());
			}
		}
		return ids;
	}

	@Transient
	public List<Long> getNodePerms() {
		Set<NodeRole> nodeRoles = getNodeRoles();
		List<Long> ids = new ArrayList<>();
		for (NodeRole nr : nodeRoles) {
			if (nr.getNodePerm()) {
				ids.add(nr.getRoleId());
			}
		}
		return ids;
	}
	
	@Transient
	public void updateInfoCustoms(Map<String, String> customs){
		if(customs != null && customs.size() > 0){
			Set<String> keyset = customs.keySet();
			
			List<NodeCustom> infoCustoms = this.getCustoms();
			
			Map<String, String> add = new HashMap<String, String>();
			List<NodeCustom> remove = new ArrayList<NodeCustom>();
			for(NodeCustom c : infoCustoms){
				if(keyset.contains(c.getKey())){
					c.setValue(customs.get(c.getKey()));
					keyset.remove(c.getKey());
				}else{
					remove.add(c);
				}
			}
			Iterator<String> it = keyset.iterator();
			while(it.hasNext()){
				NodeCustom custom = new NodeCustom();
				String key = it.next();
				String value = customs.get(key);
				custom.setNode(this);
				custom.setNodeId(this.getId());
				custom.setKey(key);
				custom.setValue(value);
				infoCustoms.add(custom);
			}
			for(NodeCustom c : remove){
				infoCustoms.remove(c);
			}
			
		}else{
			this.customs = new ArrayList<NodeCustom>(0);
		}
	}
	
	@ElementCollection
	@CollectionTable(name = "t_cms_node_clob", joinColumns = @JoinColumn(name = "node_id"))
	@MapKeyColumn(name = "key_", length = 50)
	@MapKeyType(value = @Type(type = "string"))
	@Lob
	@Column(name = "value_", nullable = false)
	public Map<String, String> getClobs() {
		return this.clobs;
	}

	public void setClobs(Map<String, String> clobs) {
		this.clobs = clobs;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "node")
	public Set<NodeDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<NodeDetail> details) {
		this.details = details;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public Node getParent() {
		return this.parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id", nullable = false)
	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	@Column(name = "creator_id")
	public long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_model_id", nullable = false)
	public CmsModel getNodeModel() {
		return this.nodeModel;
	}

	public void setNodeModel(CmsModel nodeModel) {
		this.nodeModel = nodeModel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "info_model_id")
	public CmsModel getInfoModel() {
		return this.infoModel;
	}

	public void setInfoModel(CmsModel infoModel) {
		this.infoModel = infoModel;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "node")
	public Set<NodeBuffer> getBuffers() {
		return buffers;
	}

	public void setBuffers(Set<NodeBuffer> buffers) {
		this.buffers = buffers;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "node")
	public Set<NodeRole> getNodeRoles() {
		return nodeRoles;
	}

	public void setNodeRoles(Set<NodeRole> nodeRoles) {
		this.nodeRoles = nodeRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "node")
	public Set<NodeMemberGroup> getNodeGroups() {
		return nodeGroups;
	}

	public void setNodeGroups(Set<NodeMemberGroup> nodeGroups) {
		this.nodeGroups = nodeGroups;
	}

	@Column(name = "number", length = 100)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "name", nullable = false, length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "tree_number", nullable = false, length = 100)
	public String getTreeNumber() {
		return this.treeNumber;
	}

	public void setTreeNumber(String treeNumber) {
		this.treeNumber = treeNumber;
	}

	@Column(name = "tree_level", nullable = false)
	public Integer getTreeLevel() {
		return this.treeLevel;
	}

	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}

	@Column(name = "tree_max", nullable = false, length = 20)
	public String getTreeMax() {
		return this.treeMax;
	}

	public void setTreeMax(String treeMax) {
		this.treeMax = treeMax;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "refers", nullable = false)
	public Integer getRefers() {
		return refers;
	}

	public void setRefers(Integer refers) {
		this.refers = refers;
	}

	@Column(name = "views", nullable = false)
	public Integer getViews() {
		return this.views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	@Column(name = "is_real_node", nullable = false, length = 1)
	public Boolean getRealNode() {
		return this.realNode;
	}

	public void setRealNode(Boolean realNode) {
		this.realNode = realNode;
	}

	@Column(name = "is_hidden", nullable = false, length = 1)
	public Boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	


	@Column(name = "html_status", nullable = false, length = 1)
	public String getHtmlStatus() {
		return htmlStatus;
	}

	public void setHtmlStatus(String htmlStatus) {
		this.htmlStatus = htmlStatus;
	}

	@Column(name = "p1")
	public Integer getP1() {
		return this.p1;
	}

	public void setP1(Integer p1) {
		this.p1 = p1;
	}

	@Column(name = "p2")
	public Integer getP2() {
		return this.p2;
	}

	public void setP2(Integer p2) {
		this.p2 = p2;
	}

	@Column(name = "p3")
	public Integer getP3() {
		return this.p3;
	}

	public void setP3(Integer p3) {
		this.p3 = p3;
	}

	@Column(name = "p4")
	public Integer getP4() {
		return this.p4;
	}

	public void setP4(Integer p4) {
		this.p4 = p4;
	}

	@Column(name = "p5")
	public Integer getP5() {
		return this.p5;
	}

	public void setP5(Integer p5) {
		this.p5 = p5;
	}

	@Column(name = "p6")
	public Integer getP6() {
		return this.p6;
	}

	public void setP6(Integer p6) {
		this.p6 = p6;
	}
	@Column(name = "show_type_id")
	public Integer getShow_type_id() {
		return show_type_id;
	}

	public void setShow_type_id(Integer show_type_id) {
		this.show_type_id = show_type_id;
	}

	@Column(name = "is_show_title")
	public Integer getIsShowTitle() {
		return isShowTitle;
	}

	public void setIsShowTitle(Integer isShowTitle) {
		this.isShowTitle = isShowTitle;
	}
}
