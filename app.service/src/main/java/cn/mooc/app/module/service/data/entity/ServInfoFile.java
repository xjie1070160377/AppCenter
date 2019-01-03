package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.io.FilenameUtils;

import cn.mooc.app.module.service.util.Files;


/**
 * The persistent class for the t_serv_info_file database table.
 * 
 */
@Embeddable
public class ServInfoFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ServInfoFile() {
	}

	public ServInfoFile(String name, String file, Long length) {
		this.name = name;
		this.file = file;
		this.length = length;
		this.downloads = 0;
	}

	@Transient
	public String getSize() {
		Long length = getLength();
		return Files.getSize(length);
	}

	@Transient
	public String getExtension() {
		return FilenameUtils.getExtension(getName());
	}
	
	private Integer downloads;

	private String file;

	private Long length;

	private String name;

	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Long getLength() {
		return this.length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}