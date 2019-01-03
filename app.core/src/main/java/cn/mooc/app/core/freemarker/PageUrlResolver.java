package cn.mooc.app.core.freemarker;

/**
 * 分页url获取接口
 * 
 * @author csmooc
 * 
 */
public interface PageUrlResolver {
	public String getPageUrl(Integer page);
}
