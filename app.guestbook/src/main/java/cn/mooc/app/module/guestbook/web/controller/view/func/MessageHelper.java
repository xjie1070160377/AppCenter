package cn.mooc.app.module.guestbook.web.controller.view.func;

import java.util.List;
import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.module.guestbook.data.entity.Message;
import cn.mooc.app.module.guestbook.model.MessageModel;
import cn.mooc.app.module.guestbook.service.MessageService;

public class MessageHelper implements Function{

	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		MessageService messageService = (MessageService)webApplicationContext.getBean(MessageService.class);
		
		Map<String, Object> params = (Map<String, Object>) paras[0];
		String parentId = Beetls.getString(params, "parentId");
		
		List<Message> messages = messageService.findByParentId(parentId);
		/*for(Message msg : messages){
			MessageModel msgm = new MessageModel(msg, parentId, parentId, parentId);
		}*/
		return messages;
	}
}
