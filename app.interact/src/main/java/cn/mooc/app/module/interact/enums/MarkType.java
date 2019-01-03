package cn.mooc.app.module.interact.enums;


public enum MarkType {
	
	/**
	 * 文章点赞
	 */
	InfoDigg("文章点赞"),
	
	/**
	 * 文章收藏
	 */
	InfoSave("文章收藏"),
	/**
	 * 关注个人
	 */
	AttentionUser("关注个人"),
	/**
	 * 关注栏目
	 */
	AttentionNode("关注栏目"),
	/**
	 * 关注专题
	 */
	AttentionSpecial("关注专题");
	
	private final String value;
	
	MarkType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
