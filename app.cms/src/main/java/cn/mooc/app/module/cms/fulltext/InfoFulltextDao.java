package cn.mooc.app.module.cms.fulltext;

import cn.mooc.app.module.cms.service.TaskService;


/**
 * InfoFulltextDao
 * 
 * @author csmooc
 * 
 */
public interface InfoFulltextDao {
	public int addDocument(Integer siteId, String treeNumber,
			TaskService taskService, Integer taskId);
}
