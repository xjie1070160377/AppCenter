package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Attribute
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_attribute")
public class Attribute implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
		if (getScale() == null) {
			setScale(false);
		}
		if (getExact() == null) {
			setExact(false);
		}
		if (getWatermark() == null) {
			setWatermark(false);
		}
	}

	private Integer id;

	private Site site;

	private String number;
	private String name;
	private Integer seq;
	private Boolean withImage;
	private Boolean scale;
	private Boolean exact;
	private Boolean watermark;
	private Integer imageWidth;
	private Integer imageHeight;

	@Id
	@Column(name = "attribute_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_attribute", pkColumnValue = "t_cms_attribute", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_attribute")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	@JsonBackReference
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "number", nullable = false, length = 20)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "is_with_image", nullable = false, length = 1)
	public Boolean getWithImage() {
		return this.withImage;
	}

	public void setWithImage(Boolean withImage) {
		this.withImage = withImage;
	}

	@Column(name = "is_scale", nullable = false, length = 1)
	public Boolean getScale() {
		return scale;
	}

	public void setScale(Boolean scale) {
		this.scale = scale;
	}

	@Column(name = "is_exact", nullable = false, length = 1)
	public Boolean getExact() {
		return exact;
	}

	public void setExact(Boolean exact) {
		this.exact = exact;
	}

	@Column(name = "is_watermark", nullable = false, length = 1)
	public Boolean getWatermark() {
		return watermark;
	}

	public void setWatermark(Boolean watermark) {
		this.watermark = watermark;
	}

	@Column(name = "image_width")
	public Integer getImageWidth() {
		return this.imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	@Column(name = "image_height")
	public Integer getImageHeight() {
		return this.imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

}
