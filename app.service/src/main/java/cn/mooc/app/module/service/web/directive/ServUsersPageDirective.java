package cn.mooc.app.module.service.web.directive;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import cn.mooc.app.core.freemarker.Freemarkers;
import cn.mooc.app.module.service.service.AppServiceUserService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class ServUsersPageDirective implements TemplateDirectiveModel {
	public static final String ID = "id"; 

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
		
		
		Sort defSort = new Sort(Direction.DESC, "auditTime");
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
		Page<Map> pagedList = query.findPageByServiceId(id, pageable);
		loopVars[0] = env.getObjectWrapper().wrap(pagedList);
		
		body.render(env.getOut());
	} 

	@Autowired
	private AppServiceUserService query;
	

}
