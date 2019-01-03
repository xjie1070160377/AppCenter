package cn.mooc.app.module.service.model;

/**
 * 服务号信息Model
 * @author Administrator
 *
 */
public class MobileAppServChannel {
	private Integer serviceId;
	private String msgChannel;
	
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getMsgChannel() {
		return msgChannel;
	}
	public void setMsgChannel(String msgChannel) {
		this.msgChannel = msgChannel;
	}
	
}
