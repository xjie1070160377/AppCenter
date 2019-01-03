package cn.mooc.app.core.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;

import cn.mooc.app.core.utils.StringUtil;

public class Substring implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		// TODO Auto-generated method stub
		int length = paras.length;
		String value = StringUtil.strnull(paras[0]);
		if(value == null){
			return "";
		}
		int len = StringUtil.string2Int(paras[1]);
		String result = value;
		if(value.length() > len){
			result = value.substring(0, len);
			if(length > 2){
				String def = StringUtil.strnull(paras[2]);
				result += def;
			}
		}
		
		return result;
	}

}
