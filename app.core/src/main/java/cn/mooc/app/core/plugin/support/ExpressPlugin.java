package cn.mooc.app.core.plugin.support;

import cn.mooc.app.core.plugin.AbstractPlugin;


public abstract class ExpressPlugin extends AbstractPlugin {
	
	public abstract String NextExpressCode(String currentExpressCode);

	public abstract boolean CheckExpressCodeIsValid(String expressCode);
	
}