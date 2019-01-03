package cn.mooc.app.core.plugin.support;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mooc.app.core.plugin.AbstractPlugin;
import cn.mooc.app.core.plugin.context.PluginServletContext;
import cn.mooc.app.core.plugin.enums.payment.ProcessType;
import cn.mooc.app.core.plugin.support.model.PayInfo;
import cn.mooc.app.core.plugin.support.model.PayReq;
import cn.mooc.app.core.plugin.support.model.PayResp;
import cn.mooc.app.core.plugin.support.model.RefundReq;


/**
 * 支付插件的抽象类
 * 
 * @author Taven
 *
 */
public abstract class PaymentPlugin extends AbstractPlugin {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public final int Page = 0;
	public final int QRCode = 1;
	public final int FormPost = 2;
	
	/**
	 * 生成向支付网关发起的请求
	 * 
	 * @param payReq
	 * @return
	 */
	public abstract String builderPayUrl(PayReq payReq);
	
	/**
	 * 生成向支付网关发起的退款请求
	 * 
	 * @param refundReq
	 * @return
	 */
	public abstract String builderRefundUrl(RefundReq refundReq);
	
	/**
	 * 接收网关返回的参数信息
	 * 
	 * @param request
	 * @return
	 */
	public abstract PayResp processResp(PluginServletContext pluginServletContext, ProcessType processType) throws Exception;
	
	/**
	 * Page = 0,QRCode = 1,FormPost = 2
	 * @return
	 */
	public abstract int getRequestUrlType();
	
	public void pay2Cust(String orderId, double amount, String desc, String openId){
		logger.debug("未实现pay2Cust方法");
	}
	
	public PayInfo getPayInfo(String outTradeNo, Date payDate){
		return null;
	}
	
}

