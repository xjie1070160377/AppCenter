package cn.mooc.app.module.cms.support;


/**
 * Commentable
 * 
 * @author csmooc
 * 
 */
public interface Commentable {
	/**
	 * 增加评论数量
	 * 
	 * @param comments
	 */
	public void addComments(int comments);

	/**
	 * 获取评论状态
	 * 
	 * @param groups
	 * @return
	 */
	public int getCommentStatus(Long userId);
}
