package cn.mooc.app.module.cms.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class MobileCommentInfo implements Serializable {

	private static final long serialVersionUID = 7336276730918814662L;

	private Integer commentId;
	private String ftype;
	private Integer fid;
	private String text;
	private String creator;
	private Long creatorid;
	private String creatorPhoto;
	private String creationDate;
	private String fromUser;
	private String image;
	private Integer hasImages = 0;
	private Integer hasVideo = 0;
	private String specialId;
	private Integer isSpecial = 0;
	private String video;
	private String file;
	
	private String title;
	private Integer infoId;
	private String description;
	private String nodeCode;
//	private List<MobileCommentInfo> evalComment;
	/**
	 * 回复的评论，子评论
	 */
//	private List<MobileCommentInfo> replyComments;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorPhoto() {
		return creatorPhoto;
	}

	public void setCreatorPhoto(String creatorPhoto) {
		this.creatorPhoto = creatorPhoto;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getHasImages() {
		return hasImages;
	}

	public void setHasImages(Integer hasImages) {
		this.hasImages = hasImages;
	}

	public Integer getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(Integer hasVideo) {
		this.hasVideo = hasVideo;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Long creatorid) {
		this.creatorid = creatorid;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}




//	public List<MobileCommentInfo> getEvalComment() {
//		return evalComment;
//	}
//
//	public void setEvalComment(List<MobileCommentInfo> evalComment) {
//		this.evalComment = evalComment;
//	}
}
