package cn.mooc.app.core.plugin.support.model;

import java.util.HashMap;
import java.util.Map;

public class PayReq {

	private String returnUrl;
	private String notifyUrl;
	private String orderId;
	private double totalPay;
	private String subject;
	private String desc;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public double getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public <T> T getParame(String key){
		return (T) this.params.get(key);
	}
	
	public <T> void setParame(String key, T val){
		this.params.put(key, val);
	}
	
	public boolean hasParame(String key){
		return this.params.containsKey(key);
	}
	
}
