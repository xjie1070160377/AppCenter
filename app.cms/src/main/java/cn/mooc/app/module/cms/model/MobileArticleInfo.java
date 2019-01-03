package cn.mooc.app.module.cms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.mooc.app.module.cms.data.entity.InfoImage;

public class MobileArticleInfo implements Serializable {

	private static final long serialVersionUID = 7336276730918814663L;

	private int articleId;

	private String title;

	private String listTitle;

	private String subtitle;

	private String fullTitle;

	private String creator;

	private String author;

	private String categroy;

	private Long authorId;

	private String authorPhoto;

	private Integer attention = 0;

	private String description;

	private String content;
	
	private String header;
	
	private String footer;

	private String video;

	private String videoName;

	private String videoSize;

	private String videoTime;
	
	private String audio;

	private String audioName;

	private String audioSize;

	private String audioTime;

	private String detailUrl;

	private int cateId;

	private String dates;

	private String image;

	private Integer browsers;

	private Integer diggs;

	private Integer comments;

	private Integer digged = 0;

	private Integer collected = 0;

	private Integer hasImages = 0;

	private Integer hasVideo = 0;
	
	private Integer hasAudio = 0;
	
	private Integer isReader = 0;
	
	private String readerSuffix;

	private String source;

	private String sourceUrl;
	
	private String sourceImage;
	
	private String editionUnit;
	
	private String specialId;
	
	private String specialName;
	
	private String specialImage;
	
	private Integer isSpecial = 0;
	
	private Integer isToDetail;
	/**
	 * 1：校内
	 * 2：校外，不可分享
	 * 3：校外，可分享
	 */
	private Integer infoLevel;
	/**
	 	1|左图右文章标题、摘要
		2|三张图片并排
		3|左一大图右两小图
		4|大图展现
	 */
	private Integer showType;
	
	private String file;
	private String fileName;
	private Long fileLength;
	private List<AuditOpinion> auditOpinions;
	private Integer isReject;
	
	private String shareUrl;
	
	public static class AuditOpinion{
		
		public static AuditOpinion getInstance(){
			return new AuditOpinion();
		}
		
		private String creationDate;
		private String opinion;
		private String userName;
		public String getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(String creationDate) {
			this.creationDate = creationDate;
		}
		public String getOpinion() {
			return opinion;
		}
		public void setOpinion(String opinion) {
			this.opinion = opinion;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
	}

	public Integer getInfoLevel() {
		return infoLevel;
	}

	public void setInfoLevel(Integer infoLevel) {
		this.infoLevel = infoLevel;
	}

	private List<InfoImage> images = new ArrayList<InfoImage>();

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getFullTitle() {
		return fullTitle;
	}

	public void setFullTitle(String fullTitle) {
		this.fullTitle = fullTitle;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategroy() {
		return categroy;
	}

	public void setCategroy(String categroy) {
		this.categroy = categroy;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorPhoto() {
		return authorPhoto;
	}

	public void setAuthorPhoto(String authorPhoto) {
		this.authorPhoto = authorPhoto;
	}

	public Integer getAttention() {
		return attention;
	}

	public void setAttention(Integer attention) {
		this.attention = attention;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}

	public String getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getBrowsers() {
		return browsers;
	}

	public void setBrowsers(Integer browsers) {
		this.browsers = browsers;
	}

	public Integer getDiggs() {
		return diggs;
	}

	public void setDiggs(Integer diggs) {
		this.diggs = diggs;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getDigged() {
		return digged;
	}

	public void setDigged(Integer digged) {
		this.digged = digged;
	}

	public Integer getCollected() {
		return collected;
	}

	public void setCollected(Integer collected) {
		this.collected = collected;
	}

	public Integer getHasImages() {
		return hasImages;
	}

	public void setHasImages(Integer hasImages) {
		this.hasImages = hasImages;
	}

	public List<InfoImage> getImages() {
		return images;
	}

	public void setImages(List<InfoImage> images) {
		this.images = images;
	}

	public Integer getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(Integer hasVideo) {
		this.hasVideo = hasVideo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getEditionUnit() {
		return editionUnit;
	}

	public void setEditionUnit(String editionUnit) {
		this.editionUnit = editionUnit;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}


	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getSpecialImage() {
		return specialImage;
	}

	public void setSpecialImage(String specialImage) {
		this.specialImage = specialImage;
	}

	public Integer getIsToDetail() {
		return isToDetail;
	}

	public void setIsToDetail(Integer isToDetail) {
		this.isToDetail = isToDetail;
	}

	public String getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(String sourceImage) {
		this.sourceImage = sourceImage;
	}

	public List<AuditOpinion> getAuditOpinions() {
		return auditOpinions;
	}

	public void setAuditOpinions(List<AuditOpinion> auditOpinions) {
		this.auditOpinions = auditOpinions;
	}

	public Integer getIsReject() {
		return isReject;
	}

	public void setIsReject(Integer isReject) {
		this.isReject = isReject;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public String getAudioSize() {
		return audioSize;
	}

	public void setAudioSize(String audioSize) {
		this.audioSize = audioSize;
	}

	public String getAudioTime() {
		return audioTime;
	}

	public void setAudioTime(String audioTime) {
		this.audioTime = audioTime;
	}

	public Integer getHasAudio() {
		return hasAudio;
	}

	public void setHasAudio(Integer hasAudio) {
		this.hasAudio = hasAudio;
	}

	public Integer getIsReader() {
		return isReader;
	}

	public void setIsReader(Integer isReader) {
		this.isReader = isReader;
	}

	public String getReaderSuffix() {
		return readerSuffix;
	}

	public void setReaderSuffix(String readerSuffix) {
		this.readerSuffix = readerSuffix;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	
}
