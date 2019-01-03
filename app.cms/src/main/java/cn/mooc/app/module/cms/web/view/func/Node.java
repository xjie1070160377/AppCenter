package cn.mooc.app.module.cms.web.view.func;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;

public class Node implements Function {
	public static final String ID = "id";
	public static final String NUMBER = "number";
	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		NodeService nodeService = (NodeService) webApplicationContext.getBean(NodeService.class);
		SiteService siteService = (SiteService) webApplicationContext.getBean(SiteService.class);
		
		Map<String, Object> params = (Map<String, Object>)paras[0];
		Integer id = Beetls.getInteger(params, ID);
		String number = Beetls.getString(params, NUMBER);
		cn.mooc.app.module.cms.data.entity.Node node = null;
		if (id != null) {
			node = nodeService.getNodeById(id);
		} else if (StringUtils.isNotBlank(number)) {
			Integer siteId = siteService.getWebCurrentSiteId();
			node = nodeService.findByNumber(siteId, number);
		} else {
			return null;
		}
		return node;
	}

}
