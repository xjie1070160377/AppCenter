package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_serv_info_detail database table.
 * 
 */
@Entity
@Table(name="t_serv_info_detail")
public class ServInfoDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="info_id")
	private Integer infoId;

	private String author;

	private String doc;

	@Column(name="doc_length")
	private String docLength;

	@Column(name="doc_name")
	private String docName;

	@Column(name="doc_pdf")
	private String docPdf;

	@Column(name="doc_swf")
	private String docSwf;

	@Column(name="f_color")
	private String fColor;

	private String file;

	@Column(name="file_length")
	private Long fileLength;

	@Column(name="file_name")
	private String fileName;

	@Column(name="full_title")
	private String fullTitle;

	@Lob
	private String html;

	@Column(name="info_path")
	private String infoPath;

	@Column(name="info_template")
	private String infoTemplate;

	@Column(name="is_allow_comment")
	private String isAllowComment;

	@Column(name="is_em")
	private String isEm;

	@Column(name="is_new_window")
	private String isNewWindow;

	@Column(name="is_strong")
	private String isStrong;

	@Column(name="large_image")
	private String largeImage;

	private String link;

	@Column(name="meta_description")
	private String metaDescription;

	@Column(name="small_image")
	private String smallImage;

	private String source;

	@Column(name="source_url")
	private String sourceUrl;

	@Column(name="step_name")
	private String stepName;

	private String subtitle;

	private String title;

	private String video;

	@Column(name="video_length")
	private Long videoLength;

	@Column(name="video_name")
	private String videoName;

	@Column(name="video_time")
	private String videoTime;

	//bi-directional one-to-one association to TServInfo
	@MapsId
	@OneToOne
	@JoinColumn(name="info_id")
	private ServInfo info;

	public ServInfoDetail() {
	}

	public Integer getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDoc() {
		return this.doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getDocLength() {
		return this.docLength;
	}

	public void setDocLength(String docLength) {
		this.docLength = docLength;
	}

	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocPdf() {
		return this.docPdf;
	}

	public void setDocPdf(String docPdf) {
		this.docPdf = docPdf;
	}

	public String getDocSwf() {
		return this.docSwf;
	}

	public void setDocSwf(String docSwf) {
		this.docSwf = docSwf;
	}

	public String getFColor() {
		return this.fColor;
	}

	public void setFColor(String fColor) {
		this.fColor = fColor;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Long getFileLength() {
		return this.fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullTitle() {
		return this.fullTitle;
	}

	public void setFullTitle(String fullTitle) {
		this.fullTitle = fullTitle;
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getInfoPath() {
		return this.infoPath;
	}

	public void setInfoPath(String infoPath) {
		this.infoPath = infoPath;
	}

	public String getInfoTemplate() {
		return this.infoTemplate;
	}

	public void setInfoTemplate(String infoTemplate) {
		this.infoTemplate = infoTemplate;
	}

	public String getIsAllowComment() {
		return this.isAllowComment;
	}

	public void setIsAllowComment(String isAllowComment) {
		this.isAllowComment = isAllowComment;
	}

	public String getIsEm() {
		return this.isEm;
	}

	public void setIsEm(String isEm) {
		this.isEm = isEm;
	}

	public String getIsNewWindow() {
		return this.isNewWindow;
	}

	public void setIsNewWindow(String isNewWindow) {
		this.isNewWindow = isNewWindow;
	}

	public String getIsStrong() {
		return this.isStrong;
	}

	public void setIsStrong(String isStrong) {
		this.isStrong = isStrong;
	}

	public String getLargeImage() {
		return this.largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMetaDescription() {
		return this.metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getSmallImage() {
		return this.smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getStepName() {
		return this.stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideo() {
		return this.video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Long getVideoLength() {
		return this.videoLength;
	}

	public void setVideoLength(Long videoLength) {
		this.videoLength = videoLength;
	}

	public String getVideoName() {
		return this.videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoTime() {
		return this.videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

	public ServInfo getInfo() {
		return this.info;
	}

	public void setInfo(ServInfo info) {
		this.info = info;
	}

}