package cn.mooc.app.module.service.model;

/**
 * 服务号信息Model
 * @author Administrator
 *
 */
public class MobileAppServSimple {
	private Integer serviceId;
	private String serviceName;
	private String serviceFullname;
	private String appServiceType;
	private String intro;
	private String headImg;
	private int issubscribed;
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceFullname() {
		return serviceFullname;
	}
	public void setServiceFullname(String serviceFullname) {
		this.serviceFullname = serviceFullname;
	}
	public String getAppServiceType() {
		return appServiceType;
	}
	public void setAppServiceType(String appServiceType) {
		this.appServiceType = appServiceType;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public int getIssubscribed() {
		return issubscribed;
	}
	public void setIssubscribed(int issubscribed) {
		this.issubscribed = issubscribed;
	}
}
