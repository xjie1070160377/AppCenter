package cn.mooc.app.module.guestbook.enums;


public enum DocumentType {
	
	/**
	 * 留言
	 */
	Message("留言"),
	
	/**
	 * 留言回复
	 */
	Reply("留言回复");
	
	
	private final String value;
	
	DocumentType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
