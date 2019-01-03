package cn.mooc.app.module.cms.event;

import cn.mooc.app.core.context.SysContext;

/**
 * 应用操作事件
 * @author Taven
 *
 */
public interface CmsOprEventListener {

	void receivedCmsOprEvent(OprEvent oprEvent, SysContext sysContext);
	
}
