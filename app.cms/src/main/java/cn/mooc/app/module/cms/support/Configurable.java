package cn.mooc.app.module.cms.support;

import java.util.Map;

public interface Configurable {
	public String getPrefix();

	public Map<String, String> getCustoms();
}
