package cn.mooc.app.core.plugin.support;

import java.util.Map;

import cn.mooc.app.core.plugin.AbstractPlugin;
import cn.mooc.app.core.plugin.support.model.MsgContent;

public abstract class MessagePlugin extends AbstractPlugin {

	/**
	 * 统计信息，用于前端展示 网关余额等
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract Map<String, Object> getStatistics() throws Exception;
	
	/**
	 * @param destination 短信类消息，这里是手机号；邮件类消息，这里是邮箱地址
	 * @param content 消息内容
	 * @return
	 * @throws Exception
	 */
	public abstract boolean sendMessages(MsgContent content) throws Exception;

	
	
}
