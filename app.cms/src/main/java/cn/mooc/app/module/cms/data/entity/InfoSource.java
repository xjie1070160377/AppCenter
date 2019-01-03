package cn.mooc.app.module.cms.data.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * CmsInfoSource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cms_info_source")
public class InfoSource implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String image;
	private String url;
	
	public InfoSource(){
		
	}
	
	public InfoSource(Integer id){
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "source_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_source", pkColumnValue = "t_cms_info_source", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_source")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "source_name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "source_image", length = 100)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "source_url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}