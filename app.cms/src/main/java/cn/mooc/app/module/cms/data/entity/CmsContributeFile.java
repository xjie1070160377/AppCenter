package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_cms_contribute_file database table.
 * 
 */
@Entity
@Table(name="t_cms_contribute_file")
public class CmsContributeFile implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String fileName;

	private int fileSize;

	private String fileType;

	private String fileUrl;
	
	private CmsContributeRec contributeRec;


	@Id
	@Column(name = "cont_file_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_contribute_file", pkColumnValue = "t_cms_contribute_file", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_contribute_file")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="file_name")
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_size")
	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="file_type")
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_url")
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rec_id")
	public CmsContributeRec getContributeRec() {
		return this.contributeRec;
	}

	public void setContributeRec(CmsContributeRec contributeRec) {
		this.contributeRec = contributeRec;
	}

}