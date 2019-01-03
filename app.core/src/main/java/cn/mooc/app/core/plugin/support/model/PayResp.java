package cn.mooc.app.core.plugin.support.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayResp {

	private List<String> orderIds = new ArrayList<String>();	
	private String tradNo;
	private Date tradeTime;
	private String resultCheckCode = "success";
	
	public void addOrderId(String orderId){
		this.orderIds.add(orderId);
	}
	public List<String> getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}
	public String getTradNo() {
		return tradNo;
	}
	public void setTradNo(String tradNo) {
		this.tradNo = tradNo;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getResultCheckCode() {
		return resultCheckCode;
	}
	public void setResultCheckCode(String resultCheckCode) {
		this.resultCheckCode = resultCheckCode;
	}
	
	
}
