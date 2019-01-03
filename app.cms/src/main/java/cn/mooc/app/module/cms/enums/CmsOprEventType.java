package cn.mooc.app.module.cms.enums;

/**
 * 操作事件类型
 * 
 * @author Taven
 *
 */
public enum CmsOprEventType {
	/**
	 * 分享
	 */
	ShareArticle,
	/**
	 * 点赞
	 */
	Praised,
	/**
	 * 评论
	 */
	Comment,
	
	/**
	 * 发布新文章
	 */
	InfoSave,
	
	/**
	 * 更新文章
	 */
	InfoUpdate,
	
	/**
	 * 删除文章
	 */
	InfoDel,
	
	/**
	 * 审核通过
	 */
	InfoPass,
	/**
	 * 文章退回
	 */
	InfoReject
	
}
