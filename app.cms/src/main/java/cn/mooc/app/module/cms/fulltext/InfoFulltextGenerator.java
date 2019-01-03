package cn.mooc.app.module.cms.fulltext;

import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;

public interface InfoFulltextGenerator {
	
	public void addDocument(Info[] beans);

	public void updateDocument(Info[] beans);

	public void deleteDocuments(Info[] beans);

	public void addDocument(Integer siteId, final Node node, Long userId);
}
