package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.io.FilenameUtils;

import cn.mooc.app.module.cms.util.Files;

/**
 * InfoFile
 * 
 * @author csmooc
 * 
 */
@Embeddable
public class SpecialFile implements Serializable {
	private static final long serialVersionUID = 1L;

	public SpecialFile() {
	}

	public SpecialFile(String name, String file, Long length) {
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

	private String name;
	private Long length;

	private String file;
	private Integer downloads;

	@Column(name = "name", nullable = false, length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "length", nullable = false)
	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	@Column(name = "file", nullable = false)
	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Column(name = "downloads", nullable = false)
	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}
}
