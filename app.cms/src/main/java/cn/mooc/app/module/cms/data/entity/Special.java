package cn.mooc.app.module.cms.data.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;

/**
 * Special
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_special")
public class Special implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 模型类型
	 */
	public static final String MODEL_TYPE = "special";
	/**
	 * 附件类型
	 */
	public static final String ATTACH_TYPE = "node";

	@Transient
	public String getKeywords() {
		String keywords = getMetaKeywords();
		if (StringUtils.isBlank(keywords)) {
			return getTitle();
		} else {
			return keywords;
		}
	}

	@Transient
	public String getDescription() {
		String description = getMetaDescription();
		if (StringUtils.isBlank(description)) {
			return getTitle();
		} else {
			return description;
		}
	}

	@Transient
	public void applyDefaultValue() {
		setWithImage(StringUtils.isNotBlank(getSmallImage()));
		if (getCreationDate() == null) {
			setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getRefers() == null) {
			setRefers(0);
		}
		if (getViews() == null) {
			setViews(0);
		}
		if (getRecommend() == null) {
			setRecommend(false);
		}
	}

	private Integer id;
	private List<SpecialImage> images = new ArrayList<SpecialImage>(0);
	private List<SpecialFile> files = new ArrayList<SpecialFile>(0);
	private Map<String, String> customs = new HashMap<String, String>(0);
	private Map<String, String> clobs = new HashMap<String, String>(0);

	private SpecialCategory category;
	private Site site;
	private Long creatorId;
	private CmsModel model;

	private Date creationDate;
	private String title;
	private String metaKeywords;
	private String metaDescription;
	private String specialTemplate;
	private String smallImage;
	private String largeImage;
	private String video;
	private String videoName;
	private Long videoLength;
	private String videoTime;
	private Integer refers;
	private Integer views;
	private Boolean withImage;
	private Boolean recommend;

	public Special() {
	}

	public Special(String title, SpecialCategory category, Site site) {
		this.title = title;
		this.category = category;
		this.site = site;
	}

	@Id
	@Column(name = "special_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_special", pkColumnValue = "t_cms_special", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_special")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_special_image", joinColumns = @JoinColumn(name = "special_id"))
	@OrderColumn(name = "index")
	public List<SpecialImage> getImages() {
		return images;
	}

	public void setImages(List<SpecialImage> images) {
		this.images = images;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_special_file", joinColumns = @JoinColumn(name = "special_id"))
	@OrderColumn(name = "index")
	public List<SpecialFile> getFiles() {
		return files;
	}

	public void setFiles(List<SpecialFile> files) {
		this.files = files;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_special_custom", joinColumns = @JoinColumn(name = "special_id"))
	@MapKeyColumn(name = "key", length = 50)
	@Column(name = "value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ElementCollection
	@CollectionTable(name = "t_cms_special_clob", joinColumns = @JoinColumn(name = "special_id"))
	@MapKeyColumn(name = "key", length = 50)
	@MapKeyType(value = @Type(type = "string"))
	@Lob
	@Column(name = "value", nullable = false)
	public Map<String, String> getClobs() {
		return this.clobs;
	}

	public void setClobs(Map<String, String> clobs) {
		this.clobs = clobs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "speccate_id", nullable = false)
	public SpecialCategory getCategory() {
		return this.category;
	}

	public void setCategory(SpecialCategory category) {
		this.category = category;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "creator_id", nullable = false)
	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id")
	public CmsModel getModel() {
		return model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "title", nullable = false, length = 150)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "meta_keywords", length = 150)
	public String getMetaKeywords() {
		return this.metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(name = "meta_description")
	public String getMetaDescription() {
		return this.metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Column(name = "special_template")
	public String getSpecialTemplate() {
		return specialTemplate;
	}

	public void setSpecialTemplate(String specialTemplate) {
		this.specialTemplate = specialTemplate;
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

	@Column(name = "refers", nullable = false)
	public Integer getRefers() {
		return refers;
	}

	public void setRefers(Integer refers) {
		this.refers = refers;
	}

	@Column(name = "views", nullable = false)
	public Integer getViews() {
		return this.views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	@Column(name = "is_with_image", nullable = false, length = 1)
	public Boolean getWithImage() {
		return this.withImage;
	}

	public void setWithImage(Boolean withImage) {
		this.withImage = withImage;
	}

	@Column(name = "is_recommend", nullable = false, length = 1)
	public Boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}
}
