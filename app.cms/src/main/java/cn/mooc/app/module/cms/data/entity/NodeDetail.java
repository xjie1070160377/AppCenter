package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * NodeDetail
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_node_detail")
public class NodeDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public void applyDefaultValue() {
	}

	private Integer id;
	private Node node;
	private String link;
	private String html;
	private String metaKeywords;
	private String metaDescription;
	private Boolean newWindow;
	private String nodeTemplate;
	private String infoTemplate;
	private Boolean generateNode;
	private Boolean generateInfo;
	private String nodeExtension;
	private String infoExtension;
	private String nodePath;
	private String infoPath;
	private Boolean defPage;
	private Integer staticMethod;
	private Integer staticPage;
	private String smallImage;
	private String largeImage;

	public NodeDetail() {
	}

	public NodeDetail(Node node) {
		this.node = node;
	}

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@MapsId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id")
	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Column(name = "link")
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "html")
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Column(name = "meta_keywords", length = 150)
	public String getMetaKeywords() {
		return this.metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(name = "meta_description")
	public String getMetaDescription() {
		return this.metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Column(name = "is_new_window", length = 1)
	public Boolean getNewWindow() {
		return this.newWindow;
	}

	public void setNewWindow(Boolean newWindow) {
		this.newWindow = newWindow;
	}

	@Column(name = "node_template")
	public String getNodeTemplate() {
		return this.nodeTemplate;
	}

	public void setNodeTemplate(String nodeTemplate) {
		this.nodeTemplate = nodeTemplate;
	}

	@Column(name = "info_template")
	public String getInfoTemplate() {
		return this.infoTemplate;
	}

	public void setInfoTemplate(String infoTemplate) {
		this.infoTemplate = infoTemplate;
	}

	@Column(name = "is_generate_node", length = 1)
	public Boolean getGenerateNode() {
		return this.generateNode;
	}

	public void setGenerateNode(Boolean generateNode) {
		this.generateNode = generateNode;
	}

	@Column(name = "is_generate_info", length = 1)
	public Boolean getGenerateInfo() {
		return this.generateInfo;
	}

	public void setGenerateInfo(Boolean generateInfo) {
		this.generateInfo = generateInfo;
	}

	@Column(name = "node_extension", length = 10)
	public String getNodeExtension() {
		return this.nodeExtension;
	}

	public void setNodeExtension(String nodeExtension) {
		this.nodeExtension = nodeExtension;
	}

	@Column(name = "info_extension", length = 10)
	public String getInfoExtension() {
		return this.infoExtension;
	}

	public void setInfoExtension(String infoExtension) {
		this.infoExtension = infoExtension;
	}

	@Column(name = "node_path", length = 100)
	public String getNodePath() {
		return this.nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	@Column(name = "info_path", length = 100)
	public String getInfoPath() {
		return this.infoPath;
	}

	public void setInfoPath(String infoPath) {
		this.infoPath = infoPath;
	}

	@Column(name = "is_def_page", length = 1)
	public Boolean getDefPage() {
		return this.defPage;
	}

	public void setDefPage(Boolean defPage) {
		this.defPage = defPage;
	}

	@Column(name = "static_method")
	public Integer getStaticMethod() {
		return this.staticMethod;
	}

	public void setStaticMethod(Integer staticMethod) {
		this.staticMethod = staticMethod;
	}

	@Column(name = "static_page")
	public Integer getStaticPage() {
		return this.staticPage;
	}

	public void setStaticPage(Integer staticPage) {
		this.staticPage = staticPage;
	}

	@Column(name = "small_image")
	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	@Column(name = "large_image")
	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

}
