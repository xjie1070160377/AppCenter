package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import cn.mooc.app.module.service.util.UploadUtils;


/**
 * The persistent class for the t_serv_info_image database table.
 * 
 */
@Embeddable
public class ServInfoImage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String image;

	private String name;

	@Lob
	private String text;

	public ServInfoImage() {
	}
	
	public ServInfoImage(String name, String text, String image) {
		this.name = name;
		this.text = text;
		this.image = image;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Transient
	public String getImageMin() {
		String image = getImage();
		if (StringUtils.isBlank(image)) {
			return image;
		}
		return UploadUtils.getThumbnailPath(image);
	}
}