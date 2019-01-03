package cn.mooc.app.module.cms.data.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Model
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_model")
public class CmsModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CmsModel.class);

	private static final String MODELTYPE_INFO = "info";
	private static final String MODELTYPEPATH_INFO = "/info/";
	private static final String MODELTYPE_NODE = "node";
	private static final String MODELTYPEPATH_NODE = "/node/";
	private static final String MODELTYPE_NODE_HOME = "node_home";
	private static final String MODELTYPEPATH_NODE_HOME = "/node/home_";
//	private static final String MODELTYPE_SPECIAL = "special";
//	private static final String MODELTYPEPATH_SPECIAL = "/special/";
//	private static final String MODELTYPE_SITE = "site";
//	private static final String MODELTYPEPATH_SITE = "/conf_site/";
//	private static final String MODELTYPE_GLOBAL = "global";
//	private static final String MODELTYPEPATH_GLOBAL = "/conf_global/";

	@Transient
	public static Map<String, String> getPaths() {
		Map<String, String> paths = new HashMap<String, String>();
		paths.put(MODELTYPE_INFO, MODELTYPEPATH_INFO);
		paths.put(MODELTYPE_NODE, MODELTYPEPATH_NODE);
		paths.put(MODELTYPE_NODE_HOME, MODELTYPEPATH_NODE_HOME);
//		paths.put(MODELTYPE_SPECIAL, MODELTYPEPATH_SPECIAL);
		return paths;
	}

	@Transient
	public static List<String> getTypes() {
		List<String> types = new ArrayList<String>();
		types.add(MODELTYPE_INFO);
		types.add(MODELTYPE_NODE);
		types.add(MODELTYPE_NODE_HOME);
//		types.add(MODELTYPE_SPECIAL);
		return types;
	}
	
	@Transient
	public Set<String> getPredefinedNames() {
		Set<String> names = new HashSet<String>();
		for (CmsModelField field : getFields()) {
			if (field.isPredefined()) {
				names.add(field.getName());
			}
		}
		return names;
	}

	@Transient
	public List<CmsModelField> getEnabledFields() {
		List<CmsModelField> fields = getFields();
		List<CmsModelField> enabledFields = new ArrayList<CmsModelField>();
		for (CmsModelField field : fields) {
			if (!field.getDisabled()) {
				enabledFields.add(field);
			}
		}
		return enabledFields;
	}

	@Transient
	public List<CmsModelField> getNormalFields() {
		List<CmsModelField> fields = getFields();
		List<CmsModelField> normalFields = new ArrayList<CmsModelField>();
		for (CmsModelField field : fields) {
			if (!field.getDisabled() && !field.isEditor()) {
				normalFields.add(field);
			}
		}
		return normalFields;
	}

	/**
	 * 获取可查询字段
	 * 
	 * @return
	 */
	@Transient
	public List<CmsModelField> getQueryableFields() {
		List<CmsModelField> fields = getFields();
		List<CmsModelField> queryableFields = new ArrayList<CmsModelField>();
		for (CmsModelField field : fields) {
			if (!field.getDisabled() && field.isQueryable()) {
				queryableFields.add(field);
			}
		}
		return queryableFields;
	}

	@Transient
	public List<CmsModelField> getEditorFields() {
		List<CmsModelField> fields = getFields();
		List<CmsModelField> enabledFields = new ArrayList<CmsModelField>();
		for (CmsModelField field : fields) {
			if (!field.getDisabled() && field.isEditor()) {
				enabledFields.add(field);
			}
		}
		return enabledFields;
	}

	@Transient
	public CmsModelField getField(String name) {
		for (CmsModelField field : getFields()) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	public void addField(CmsModelField field) {
		List<CmsModelField> fields = getFields();
		if (fields == null) {
			fields = new ArrayList<CmsModelField>();
			setFields(fields);
		}
		fields.add(field);
	}

	public void getAttachUrls(Set<String> urls, Set<String> clobEditorNames, Map<String, String> clobs, Map<String, String> customs) {
		// 自定义字段
		for (CmsModelField field : getFields()) {
			if (field.getDisabled() || field.isCustom()) {
				continue;
			}
			if (field.isEditor() && field.isClob()) {
				clobEditorNames.add(field.getName());
				continue;
			}
			int type = field.getType();
			if (type == CmsModelField.IMAGE || type == CmsModelField.VIDEO || type == CmsModelField.FILE) {
				urls.add(customs.get(field.getName()));
			}
		}
		// 取clobs图片和链接
		NodeFilter imgf = new NodeClassFilter(ImageTag.class);
		NodeFilter linkf = new NodeClassFilter(LinkTag.class);
		NodeFilter filter = new OrFilter(imgf, linkf);
		for (Entry<String, String> clob : clobs.entrySet()) {
			String name = clob.getKey();
			String html = clob.getValue();
			if (!clobEditorNames.contains(name) || StringUtils.isBlank(html)) {
				continue;
			}
			try {
				Parser parser = new Parser(new Lexer(html));
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				SimpleNodeIterator it = nodes.elements();
				while (it.hasMoreNodes()) {
					org.htmlparser.Node n = it.nextNode();
					if (n instanceof ImageTag) {
						urls.add(((ImageTag) n).getImageURL());
					}
					if (n instanceof LinkTag) {
						urls.add(((LinkTag) n).extractLink());
					}
				}
			} catch (ParserException e) {
				logger.error(null, e);
			}
		}
	}

	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}

	private Integer id;
	private List<CmsModelField> fields = new ArrayList<CmsModelField>(0);
	private Map<String, String> customs = new HashMap<String, String>(0);

	private Site site;

	private String type;
	private String name;
	private String number;
	private Integer seq;

	public CmsModel() {
	}

	@Id
	@Column(name = "model_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_model", pkColumnValue = "t_cms_model", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_model")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "model")
	@OrderBy(value = "seq asc, id asc")
	public List<CmsModelField> getFields() {
		return this.fields;
	}

	public void setFields(List<CmsModelField> fields) {
		this.fields = fields;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_model_custom", joinColumns = @JoinColumn(name = "model_id"))
	@MapKeyColumn(name = "key_", length = 50)
	@Column(name = "value_", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	@JsonBackReference
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "type", nullable = false, length = 100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "number", length = 100)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
