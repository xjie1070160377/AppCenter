package cn.mooc.app.module.ad.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_cms_ad_slot database table.
 * 
 */
@Entity
@Table(name="t_cms_ad_slot")
public class AdSlot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "adslot_id", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	private String description;

	private Integer height;

	private String name;

	private String number;

	@Column(name="site_id")
	private Integer siteId;

	private String template;

	private Integer type;

	private Integer width;

	//bi-directional many-to-one association to Ad
//	@OneToMany(mappedBy="AdSlot")
//	private List<Ad> Ads;

	public AdSlot() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getWidth() {
		
		return this.width;
		
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

//	public List<Ad> getAds() {
//		return this.Ads;
//	}
//
//	public void setAds(List<Ad> Ads) {
//		this.Ads = Ads;
//	}

//	public Ad addAd(Ad Ad) {
//		getAds().add(Ad);
//		Ad.setAdSlot(this);
//
//		return Ad;
//	}
//
//	public Ad removeAd(Ad Ad) {
//		getAds().remove(Ad);
//		Ad.setAdSlot(null);
//
//		return Ad;
//	}

}