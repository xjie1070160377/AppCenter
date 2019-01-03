package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * InfoDetail
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_info_detail")
public class InfoDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Integer show_flag = 1;

	public void applyDefaultValue() {
		if (getStrong() == null) {
			setStrong(false);
		}
		if (getEm() == null) {
			setEm(false);
		}
	}

	private Integer id;

	private Info info;

	private String title;
	private String html;
	private String subtitle;
	private String fullTitle;
	private String link;
	private Boolean newWindow;
	private String color;
	private Boolean strong;
	private Boolean em;
	private String metaDescription;
	private String infoPath;
	private String infoTemplate;
	private String source;
	private String sourceUrl;
	private String author;
	private String smallImage;
	private String largeImage;
	private String video;
	private String videoName;
	private Long videoLength;
	private String videoTime;
	private String audio;
	private String audioName;
	private Long audioLength;
	private String audioTime;
	private String file;
	private String fileName;
	private Long fileLength;
	private String doc;
	private String docName;
	private Long docLength;
	private String docPdf;
	private String docSwf;
	private Boolean allowComment;
	private String stepName;
	private Integer sourceFlag;
	
	private InfoSource infoSource;

	public InfoDetail() {
	}

	public InfoDetail(Info info, String title) {
		this.info = info;
		this.title = title;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source_id", nullable = false)
	public InfoSource getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(InfoSource infoSource) {
		this.infoSource = infoSource;
	}

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@MapsId
	@OneToOne
	@JoinColumn(name = "info_id")
	public Info getInfo() {
		return this.info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@Column(name = "title", nullable = false, length = 150)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "html")
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Column(name = "subtitle", length = 150)
	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@Column(name = "full_title", length = 150)
	public String getFullTitle() {
		return this.fullTitle;
	}

	public void setFullTitle(String fullTitle) {
		this.fullTitle = fullTitle;
	}

	@Column(name = "link")
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "is_new_window", length = 1)
	public Boolean getNewWindow() {
		return this.newWindow;
	}

	public void setNewWindow(Boolean newWindow) {
		this.newWindow = newWindow;
	}

	@Column(name = "color", length = 50)
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "is_strong", nullable = false, length = 1)
	public Boolean getStrong() {
		return this.strong;
	}

	public void setStrong(Boolean strong) {
		this.strong = strong;
	}

	@Column(name = "is_em", nullable = false, length = 1)
	public Boolean getEm() {
		return this.em;
	}

	public void setEm(Boolean em) {
		this.em = em;
	}

	@Column(name = "meta_description")
	public String getMetaDescription() {
		return this.metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Column(name = "info_path")
	public String getInfoPath() {
		return infoPath;
	}

	public void setInfoPath(String infoPath) {
		this.infoPath = infoPath;
	}

	@Column(name = "info_template")
	public String getInfoTemplate() {
		return this.infoTemplate;
	}

	public void setInfoTemplate(String infoTemplate) {
		this.infoTemplate = infoTemplate;
	}

	@Column(name = "source", length = 50)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "source_url")
	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Column(name = "author", length = 50)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "small_image")
	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	@Column(name = "large_image")
	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	@Column(name = "video")
	public String getVideo() {
		return this.video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Column(name = "video_name")
	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	@Column(name = "video_length")
	public Long getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(Long videoLength) {
		this.videoLength = videoLength;
	}

	@Column(name = "video_time", length = 100)
	public String getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

	@Column(name = "audio")
	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	@Column(name = "audio_name")
	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	@Column(name = "audio_length")
	public Long getAudioLength() {
		return audioLength;
	}

	public void setAudioLength(Long audioLength) {
		this.audioLength = audioLength;
	}

	@Column(name = "audio_time", length = 100)
	public String getAudioTime() {
		return audioTime;
	}

	public void setAudioTime(String audioTime) {
		this.audioTime = audioTime;
	}

	@Column(name = "file")
	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Column(name = "file_name")
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_length")
	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	@Column(name = "doc")
	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	@Column(name = "doc_name")
	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	@Column(name = "doc_length")
	public Long getDocLength() {
		return docLength;
	}

	public void setDocLength(Long docLength) {
		this.docLength = docLength;
	}

	@Column(name = "doc_pdf")
	public String getDocPdf() {
		return docPdf;
	}

	public void setDocPdf(String docPdf) {
		this.docPdf = docPdf;
	}

	@Column(name = "doc_swf")
	public String getDocSwf() {
		return docSwf;
	}

	public void setDocSwf(String docSwf) {
		this.docSwf = docSwf;
	}

	@Column(name = "is_allow_comment", length = 1)
	public Boolean getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(Boolean allowComment) {
		this.allowComment = allowComment;
	}

	@Column(name = "step_name", length = 100)
	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	@Column(name = "source_flag")
	public Integer getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(Integer sourceFlag) {
		this.sourceFlag = sourceFlag;
	}

}
