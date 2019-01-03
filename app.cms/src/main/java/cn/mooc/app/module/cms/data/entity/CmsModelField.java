package cn.mooc.app.module.cms.data.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import cn.mooc.app.module.cms.util.Strings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * ModelField
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_model_field")
public class CmsModelField implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 文本输入框
	 */
	public static final int TEXT = 1;
	/**
	 * 日期输入框
	 */
	public static final int DATE = 2;
	/**
	 * 复选框
	 */
	public static final int CHECKBOX = 3;
	/**
	 * 单选框
	 */
	public static final int RADIO = 4;
	/**
	 * 下拉框
	 */
	public static final int SELECT = 5;
	/**
	 * 文本区
	 */
	public static final int TEXTAREA = 6;
	/**
	 * 图片上传
	 */
	public static final int IMAGE = 7;
	/**
	 * 视频上传
	 */
	public static final int VIDEO = 8;
	/**
	 * 文件上传
	 */
	public static final int FILE = 9;
	/**
	 * 文库上传
	 */
	public static final int DOC = 10;
	
	public static final int audio = 11;
	/**
	 * 文本编辑器
	 */
	public static final int EDITOR = 50;
	/**
	 * 图片集上传
	 */
	public static final int IMAGES = 51;
	/**
	 * 附件集上传
	 */
	public static final int FILES = 52;
	/**
	 * 可查询的单选框
	 */
	public static final int Q_RADIO = 100;
	/**
	 * 可查询的下拉框
	 */
	public static final int Q_SELECT = 101;
	/**
	 * 可查询的复选框
	 */
	public static final int Q_CHECKBOX = 102;
	/**
	 * 用户自定义字段
	 */
	public static final int FIELD_CUSTOM = 0;
	/**
	 * 用户自定义大字段
	 */
	public static final int FIELD_CUSTOM_CLOB = 1;
	/**
	 * 系统定义字段
	 */
	public static final int FIELD_SYSTEM = 2;
	/**
	 * 可查询字段
	 */
	public static final int FIELD_QUERYABLE = 3;

	public static final String IMAGE_WIDTH = "imageWidth";
	public static final String IMAGE_HEIGHT = "imageHeight";
	public static final String IMAGE_EXACT = "imageExact";

	/**
	 * 是否预定义
	 * 
	 * @return
	 */
	@Transient
	public boolean isPredefined() {
		if (getInnerType() != null) {
			return getInnerType() != FIELD_CUSTOM
					&& getInnerType() != FIELD_CUSTOM_CLOB;
		} else {
			return false;
		}
	}

	/**
	 * 是否自定义大字段
	 * 
	 * @return
	 */
	@Transient
	public boolean isClob() {
		if (getInnerType() != null) {
			return getInnerType() == FIELD_CUSTOM_CLOB;
		} else {
			return false;
		}
	}

	/**
	 * 是否可查询字段
	 * 
	 * @return
	 */
	@Transient
	public boolean isQueryable() {
		if (getInnerType() != null) {
			return getInnerType() == FIELD_QUERYABLE;
		} else {
			return false;
		}
	}

	/**
	 * 是否自定义
	 * 
	 * @return
	 */
	@Transient
	public boolean isCustom() {
		return !isPredefined();
	}

	/**
	 * 是否编辑器
	 * 
	 * @return
	 */
	@Transient
	public boolean isEditor() {
		return getType() == EDITOR;
	}

	/**
	 * 获取选项值options
	 * 
	 * @return
	 */
	@Transient
	public Map<String, String> getOptions() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String opts = getCustoms().get("options");
		if (StringUtils.isBlank(opts)) {
			return map;
		}
		char separator = '|';
		for (String opt : Strings.splitLines(opts)) {
			int index = opt.indexOf(separator);
			if (index >= 0) {
				map.put(opt.substring(0, index), opt.substring(index + 1));
			} else {
				map.put(opt, opt);
			}
		}
		return map;
	}

	public void applyDefaultValue() {
		if (getInnerType() == null) {
			setInnerType(FIELD_CUSTOM);
		}
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
		if (getDblColumn() == null) {
			setDblColumn(false);
		}
		if (getRequired() == null) {
			setRequired(false);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	private Integer id;
	private Map<String, String> customs = new HashMap<String, String>(0);

	private CmsModel model;

	private Integer type;
	private Integer innerType;
	private String label;
	private String name;
	private String prompt;
	private String defValue;
	private Boolean required;
	private Integer seq;
	private Boolean dblColumn;
	private Boolean disabled;

	public CmsModelField() {
	}

	public CmsModelField(CmsModel model, Integer type, String label, String name) {
		this.model = model;
		this.type = type;
		this.label = label;
		this.name = name;
	}

	@Id
	@Column(name = "modefiel_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_model_field", pkColumnValue = "t_cms_model_field", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_model_field")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_model_field_custom", joinColumns = @JoinColumn(name = "modefiel_id"))
	@MapKeyColumn(name = "key_", length = 50)
	@Column(name = "value_", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	@JsonIgnore
	public CmsModel getModel() {
		return this.model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "inner_type", nullable = false)
	public Integer getInnerType() {
		return this.innerType;
	}

	public void setInnerType(Integer innerType) {
		this.innerType = innerType;
	}

	@Column(name = "label", nullable = false, length = 50)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "prompt")
	public String getPrompt() {
		return this.prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Column(name = "def_value")
	public String getDefValue() {
		return this.defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	@Column(name = "is_required", nullable = false, length = 1)
	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@Column(name = "seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "is_dbl_column", nullable = false, length = 1)
	public Boolean getDblColumn() {
		return this.dblColumn;
	}

	public void setDblColumn(Boolean dblColumn) {
		this.dblColumn = dblColumn;
	}

	@Column(name = "is_disabled", nullable = false, length = 1)
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
}
