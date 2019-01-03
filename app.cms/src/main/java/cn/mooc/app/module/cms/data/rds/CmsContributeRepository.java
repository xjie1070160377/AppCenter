package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.CmsContribute;

public interface CmsContributeRepository extends JpaRepository<CmsContribute, Integer>,JpaSpecificationExecutor<CmsContribute>{

	@Query("select bean from CmsContribute bean where bean.userId=?1")
	public CmsContribute findByUser(Long userId);
}
