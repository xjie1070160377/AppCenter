package cn.mooc.app.module.cms.service;


/**
 * 路径获取接口
 * 
 * @author csmooc
 * 
 */
public interface PathResolver {
	public String getPath(String uri);

	public String getPath(String uri, String prefix);
}
