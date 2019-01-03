package cn.mooc.app.module.interact.event;

import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.enums.MarkType;

public class InteractEvent extends OprEvent {
	private int id;
	private MarkType fType;
	private CommentType commentType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MarkType getfType() {
		return fType;
	}
	public void setfType(MarkType fType) {
		this.fType = fType;
	}
	public CommentType getCommentType() {
		return commentType;
	}
	public void setCommentType(CommentType commentType) {
		this.commentType = commentType;
	}
}
