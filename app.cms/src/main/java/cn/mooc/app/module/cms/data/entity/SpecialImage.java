package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;


/**
 * InfoImage
 * 
 * @author csmooc
 * 
 */
@Embeddable
public class SpecialImage implements Serializable {
	private static final long serialVersionUID = 1L;

	public SpecialImage() {
	}

	public SpecialImage(String name, String text, String image) {
		this.name = name;
		this.text = text;
		this.image = image;
	}

//	@Transient
//	public String getImageMin() {
//		String image = getImage();
//		if (StringUtils.isBlank(image)) {
//			return image;
//		}
//		return UploadUtils.getThumbnailPath(image);
//	}

	private String name;
	private String image;
	private String text;

	@Column(name = "name", length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "image")
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Lob
	@Column(name = "text")
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}