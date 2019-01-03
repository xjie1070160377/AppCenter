package cn.mooc.app.module.push.service;

public interface MsgPushService {

	void pushMsgToAll(String title, String des, String type, String value, Integer siteId, String showType, Long userId, String userName, String ip, String reqUrl);
	/**
	 * JMS Topic 创建
	 * @param msgChannel
	 */
	void msgChannelCreate(String msgChannel);
	/**
	 * 测试推送
	 * @param info
	 * @param flag
	 */
	public void pushtest(String title, String type, String showType, String infotitle, String infoId, Integer siteId , Integer flag, Long userId, String userName, String ip, String reqUrl);
	
	/**
	 * 推送消息
	 * @param title
	 * @param des
	 * @param type
	 * @param value
	 * @param siteId
	 * @param showType
	 * @param userIds
	 * @param iosTitle
	 * @param userId
	 * @param userName
	 */
	public void pushMsg(final String title, final String des, String type, final String value, final Integer siteId, final String showType, final Long[] userIds, final String iosTitle,  Long userId, String userName, String ip, String reqUrl);
	
}
