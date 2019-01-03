package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.CmsContributeFile;

public interface CmsContributeFileRepository extends JpaRepository<CmsContributeFile, Integer>,JpaSpecificationExecutor<CmsContributeFile> {

	
	@Query("select bean from CmsContributeFile bean where bean.contributeRec.id=?1 and bean.fileType=?2")
	public List<CmsContributeFile> findByRec(Integer recId, String type);
}
