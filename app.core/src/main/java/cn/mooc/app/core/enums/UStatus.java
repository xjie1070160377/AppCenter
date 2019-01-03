package cn.mooc.app.core.enums;

public enum UStatus {
	
	/**
	 * 禁用
	 */
	Disable(0),
	/**
	 * 启用
	 */
	Enable(1);
	
	private final int value;

	UStatus(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
}
