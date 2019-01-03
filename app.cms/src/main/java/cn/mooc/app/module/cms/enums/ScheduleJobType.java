package cn.mooc.app.module.cms.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务类型
 */
public enum ScheduleJobType {
	
	/**
	 * 信息发布
	 */
	InfoPublishJob(1),
	
	/**
	 * 首页静态化
	 */
	HtmlHomeJob(2),
	/**
	 * 采集
	 */
	CollectJob(3);
	
	private final int value;

	ScheduleJobType(int value) {
        this.value = value;
    }
    
    
    public static Map<Integer, String> cNameMap = new HashMap<Integer, String>();
	public static Map<Integer, ScheduleJobType> vMap = new HashMap<Integer, ScheduleJobType>();
	
	static {
		cNameMap.put(InfoPublishJob.getValue(), "信息发布");
		cNameMap.put(HtmlHomeJob.getValue(), "首页静态化");
		cNameMap.put(CollectJob.getValue(), "采集");
		for (ScheduleJobType item : ScheduleJobType.values()) {
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
	
	public static ScheduleJobType getEnum(int value) {
		return vMap.get(value);
	}
}
