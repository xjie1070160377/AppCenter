package cn.mooc.app.module.cms.web.view.func;

import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;

import cn.mooc.app.module.cms.web.view.func.directive.AbstractMarkListPageDirective;

public class MarkPage extends AbstractMarkListPageDirective implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		Map<String, Object> params = (Map<String, Object>)paras[0];
		return doExecute(params,ctx,true);
	}

}
