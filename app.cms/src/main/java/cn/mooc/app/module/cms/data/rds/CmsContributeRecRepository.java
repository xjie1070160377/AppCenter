package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.CmsContributeRec;

public interface CmsContributeRecRepository extends JpaRepository<CmsContributeRec, Integer>,JpaSpecificationExecutor<CmsContributeRec> {

	
	@Query("select bean from CmsContributeRec bean where bean.contribute.userId=?1")
	public List<CmsContributeRec> findByUser(Integer userId);
	
	@Query("select bean from CmsContributeRec bean where bean.fid=?1")
	public List<CmsContributeRec> findByFid(Integer fid);
}
