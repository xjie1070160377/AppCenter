package cn.mooc.app.module.service.web.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import cn.mooc.app.core.freemarker.Freemarkers;
import cn.mooc.app.module.service.data.entity.ServMsg;
import cn.mooc.app.module.service.model.MobileAppServConvert;
import cn.mooc.app.module.service.model.MobileServMsg;
import cn.mooc.app.module.service.service.ServMsgService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class ServMsgsPageDirective implements TemplateDirectiveModel {

	public static final String ID = "id"; 
	public static final String STATE = "state";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if (loopVars.length < 1) {
			throw new TemplateModelException("Loop variable is required.");
		}
		if (body == null) {
			throw new RuntimeException("missing body");
		}
		Integer id = Freemarkers.getInteger(params, ID);
		Integer state = Freemarkers.getInteger(params, STATE);
		
		
		Sort defSort = new Sort(Direction.DESC, "msgid");
		String[] sort = Freemarkers.getStrings(params, "sort");
		String direction = Freemarkers.getString(params, "direction");
		if (sort != null) {
			Direction directionType = Direction.ASC;
			if (StringUtils.isNotEmpty(direction)) {
				if ("desc".equals(direction)) {
					directionType = Direction.DESC;
				}
			}
			defSort = new Sort(directionType, sort);
		}
		Pageable pageable = Freemarkers.getPageable(params, env, defSort);
		Page<ServMsg> pagedList = query.findPageByServiceId(id, state, pageable);
		List<ServMsg> list = pagedList.getContent();
		List<MobileServMsg> listMsgs = new ArrayList<MobileServMsg>();
		for (ServMsg msg : list) {
			MobileServMsg mobileServMsg = MobileAppServConvert.convertMsg("", msg);
			if (mobileServMsg != null) {
				listMsgs.add(mobileServMsg);
			}
		}
		Page<MobileServMsg> page = new PageImpl<MobileServMsg>(listMsgs, pageable, pagedList.getTotalElements());
		
		//ForeContext.setTotalPages(pagedList.getTotalPages());
		loopVars[0] = env.getObjectWrapper().wrap(page);
		
		body.render(env.getOut());
	} 

	@Autowired
	private ServMsgService query;

}
