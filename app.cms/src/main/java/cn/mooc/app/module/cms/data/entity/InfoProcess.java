package cn.mooc.app.module.cms.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * InfoComment
 * 
 * @author csmooc
 * 
 */
@Entity
@DiscriminatorValue("1")
public class InfoProcess extends WorkflowProcess {
	private static final long serialVersionUID = 1L;

	@Override
	@Transient
	public String getName() {
		return getInfo().getTitle();
	}

	private Info info;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "data_id", nullable = false, insertable = false, updatable = false)
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
}
