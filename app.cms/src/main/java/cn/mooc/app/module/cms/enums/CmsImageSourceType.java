package cn.mooc.app.module.cms.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片来源类型
 * linwei
 */
public enum CmsImageSourceType {
	/**
	 * 文章图片
	 */
	InfoImage(1),
	
	/**
	 * 栏目标题图
	 */
	NodeSmallImage(2),
	/**
	 * 广告图
	 */
	AdImage(3);
	
	private final int value;

	CmsImageSourceType(int value) {
        this.value = value;
    }
    
    
    public static Map<Integer, String> cNameMap = new HashMap<Integer, String>();
	public static Map<Integer, CmsImageSourceType> vMap = new HashMap<Integer, CmsImageSourceType>();
	
	static {
		cNameMap.put(InfoImage.getValue(), "文章图片");
		cNameMap.put(NodeSmallImage.getValue(), "栏目图片");
		cNameMap.put(AdImage.getValue(), "广告图片");
		for (CmsImageSourceType item : CmsImageSourceType.values()) {
			vMap.put(item.getValue(), item);
		}
	}
	
	public int getValue() {
		return value;
	}
	
	public String getCName() {
		return cNameMap.get(this.value);
	}

	public static String getCName(int value) {
		return cNameMap.get(value);
	}
	
	public static CmsImageSourceType getEnum(int value) {
		return vMap.get(value);
	}
}
