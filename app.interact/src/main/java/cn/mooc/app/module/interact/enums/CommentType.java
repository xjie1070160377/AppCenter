package cn.mooc.app.module.interact.enums;


public enum CommentType {
	
	/**
	 * 文章评论
	 */
	Info("文章评论"),
	
	/**
	 * 评论回复
	 */
	Comment("评论回复"),
	/**
	 * 意见反馈
	 */
	Feedback("意见反馈"),
	/**
	 * 反馈回复
	 */
	FeedbackReply("反馈回复"),
	/**
	 * 建言献策
	 */
	Guestbook("建言献策");
	
	private final String value;
	
	CommentType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
