package cn.mooc.app.module.push;

import java.io.Serializable;

public class NudtPushMessage implements Serializable {

	private static final long serialVersionUID = -4355757325618247014L;

	/**
	 * 消息类型，0：Web地址跳转，1：新闻资讯类，11：类似服务号的推送消息
	 * (该属性值用于未来消息类型的扩展)
	 */
	private int msgType = 0;
	
	/**
	 * 消息编码方式，用来标示消息是明文还是加密的，0：不加密，1：RSA证书加密
	 */
	private int encodeType = 0;

	/**
	 * 消息标题
	 */
	private String msgTitle;
	
	/**
	 * 消息描述
	 */
	private String msgDesc;
	
	/**
	 * 消息展示形式
	 */
	private int showtype;
	
	/**
	 * JSON消息对象
	 */
	private String msgBody;

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(int encodeType) {
		this.encodeType = encodeType;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public int getShowtype() {
		return showtype;
	}

	public void setShowtype(int showtype) {
		this.showtype = showtype;
	}
}
