package cn.mooc.app.module.guestbook.web.controller.view.func;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.guestbook.data.entity.Guestbook;
import cn.mooc.app.module.guestbook.data.entity.Message;
import cn.mooc.app.module.guestbook.enums.DocumentType;
import cn.mooc.app.module.guestbook.service.GuestbookService;
import cn.mooc.app.module.guestbook.service.MessageService;

public class MessagePage implements Function{

	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		MessageService messageService = (MessageService)webApplicationContext.getBean(MessageService.class);
		GuestbookService guestbookService = (GuestbookService)webApplicationContext.getBean(GuestbookService.class);
		Map<String, Object> params = (Map<String, Object>) paras[0];
		Map<String, Object> searchParam = new HashMap<>();
		long userId = Beetls.getLongs(params, "userId")[0];
		if(userId == 0){
			return null;
		}
		Guestbook gb = guestbookService.findOneByUserId(userId);
		if(gb != null){
			searchParam.put("EQ_guestbookId", gb.get_id());
		}else{
			return null;
		}
		searchParam.put("EQ_type", DocumentType.Message);
		searchParam.put("NE_state", Message.DISABLED);
		PagerParam pageParam = Beetls.getPageable(params, ctx, new String[]{"priority", "createTime", "id"}, Direction.DESC);
		Page<Message> page = messageService.findPage(searchParam, pageParam);
		return page;
	}
}
