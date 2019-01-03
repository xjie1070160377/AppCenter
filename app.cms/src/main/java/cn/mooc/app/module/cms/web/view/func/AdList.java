package cn.mooc.app.module.cms.web.view.func;

import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.ad.service.AdService;

public class AdList implements Function {
	/**
	 * 广告板块编码。字符串。
	 */
	public static final String SLOT = "slot";
	/**
	 * 广告板块ID。整型。
	 */
	public static final String SLOT_ID = "slotId";
	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		AdService adService = (AdService) webApplicationContext.getBean(AdService.class);
		
		Map<String, Object> params = (Map<String, Object>)paras[0];

		Integer[] slotId = Beetls.getIntegers(params, SLOT_ID);
		String[] slot = Beetls.getStrings(params, SLOT);


		PagerParam pageParam = Beetls.getLimitable(params, ctx, new String[]{"seq", "id"}, Direction.ASC);
		try {
			return adService.findList(slot, slotId, pageParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
