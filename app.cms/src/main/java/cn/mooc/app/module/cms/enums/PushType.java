package cn.mooc.app.module.cms.enums;

public enum PushType {
	/**
	 * 文章推送
	 */
	Info(1),
	/**
	 * 审核推送
	 */
	Audit(2);
	
	private final int value;

	PushType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
