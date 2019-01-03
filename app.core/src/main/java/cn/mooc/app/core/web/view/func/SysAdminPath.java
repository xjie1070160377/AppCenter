package cn.mooc.app.core.web.view.func;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;


/**
 * 假设当前地址：http://localhost:8080/app.center/mcenter/login
 * 取得结果：/app.center/mcenter
 * @author Taven
 *
 */
public class SysAdminPath implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		
		String sysAdminPath = "";
		Object ctxPath = ctx.getGlobal("ctxPath");
		if(ctxPath!=null && StringUtils.isNotEmpty(ctxPath.toString())){
			sysAdminPath = ctx.getGlobal("ctxPath").toString();
		}
		sysAdminPath += "/" + ctx.getGlobal("SYS_ADMIN_PATH");

		return sysAdminPath;
		
		
	}

}