package cn.mooc.app.module.cms.support;

/**
 * html a 接口
 * 
 * @author csmooc
 * 
 */
public interface Anchor {
	public String getTitle();

	public String getUrl();
	
	public Boolean getNewWindow();

	public String getColor();

	public Boolean getStrong();

	public Boolean getEm();

	public String getDescription();
}
