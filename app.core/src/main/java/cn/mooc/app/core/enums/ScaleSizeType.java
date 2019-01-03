package cn.mooc.app.core.enums;

/**
 * 压缩大小的方式
 * 
 * @author Taven
 *
 */
public enum ScaleSizeType {

	
	/**
	 * 自动按最小的值压缩
	 */
	ByMin(0),
	/**
	 * 按最大的值压缩
	 */
	ByMax(1);
	
	private final int value;

	ScaleSizeType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
	
}



