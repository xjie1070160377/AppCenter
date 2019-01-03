package cn.mooc.app.module.cms.data.entity;

// Generated 2013-6-24 15:12:04 by Hibernate Tools 4.0.0

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


/**
 * InfoTag
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_info_tag")
public class InfoTag implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;
	private Info info;
	private Tag tag;
	
	private Integer tagIndex;

	@Id
	@Column(name = "infotag_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_info_tag", pkColumnValue = "t_cms_info_tag", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_info_tag")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "info_id", nullable = false)
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id", nullable = false)
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@Column(name = "tag_index", nullable = false)
	public Integer getTagIndex() {
		return tagIndex;
	}

	public void setTagIndex(Integer tagIndex) {
		this.tagIndex = tagIndex;
	}

}
