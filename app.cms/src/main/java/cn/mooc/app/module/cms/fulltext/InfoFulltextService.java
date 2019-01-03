package cn.mooc.app.module.cms.fulltext;

import java.util.Date;
import java.util.List;

import org.apache.lucene.search.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.service.TaskService;


/**
 * InfoFulltextService
 * 
 * @author csmooc
 * 
 */
public interface InfoFulltextService {

	public int addDocument(Integer siteId, Node node, TaskService taskService,
			Integer taskId);
	
	public List<Info> list(Integer[] siteIds, Integer[] nodeIds,
			Integer[] attrIds, Date beginDate, Date endDate, String[] status,
			Integer[] excludeId, String q, String title, String[] keywords,
			String description, String text, String[] creators,
			String[] authors, Integer fragmentSize, PagerParam pageParam,
			Sort sort);
	
	public Page<Info> page(Integer[] siteIds, Integer[] nodeIds,
			Integer[] attrIds, Date beginDate, Date endDate, String[] status,
			Integer[] excludeId, String q, String title, String[] keywords,
			String description, String text, String[] creators,
			String[] authors, Integer fragmentSize, Pageable pageable, Sort sort);

	public void addDocument(Integer infoId);

	public void updateDocument(Integer infoId);
}
