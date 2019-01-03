package cn.mooc.app.module.cms.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import cn.mooc.app.module.cms.support.Anchor;

/**
 * InfoComment
 * 
 * @author csmooc
 * 
 */
@Entity
@DiscriminatorValue("Info")
public class InfoComment extends Comment {
	private static final long serialVersionUID = 1L;

	@Override
	@Transient
	public Anchor getAnchor() {
		return info;
	}

	private Info info;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fid", nullable = false, insertable = false, updatable = false)
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
}
