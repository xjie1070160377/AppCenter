package cn.mooc.app.module.service.data.nodel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppServiceModel {

	private Integer serviceId;
	private String serviceName;
	private String serviceFullname;
	private String orgName;
	private String serviceTypeName;
	private Integer isChecked;
	private String createTime;
	private String nickname;
	private String charger;
	private String telephone;
	private String mobile;
	
	public AppServiceModel(){
		
	}
	
	public AppServiceModel(Integer serviceId, String serviceName, String serviceFullName, String orgName, String serviceTypeName, Integer isChecked,
				Date createTime, String nickName, String charger, String telephone, String mobile){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceFullname = serviceFullName;
		this.orgName = orgName;
		this.serviceTypeName = serviceTypeName;
		this.isChecked = isChecked;
		this.createTime = format.format(createTime);
		this.nickname = nickName;
		this.charger  = charger;
		this.telephone = telephone;
		this.mobile = mobile;
	}

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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCharger() {
		return charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
