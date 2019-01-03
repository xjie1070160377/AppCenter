package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.CmsModel;

/**
 * CmsModelRepository 模型管理数据访问
 * 
 * @author hwt
 * @date 2016-05-09
 */
public interface CmsModelRepository extends JpaRepository<CmsModel, Integer>, JpaSpecificationExecutor<CmsModel> {

	@Query("select bean from CmsModel bean where bean.site.id = ?1 and bean.type = ?2 order by bean.seq asc")
	List<CmsModel> findList(Integer siteId, String type);

	@Query("select bean from CmsModel bean where bean.site.id = ?1 order by bean.seq asc")
	List<CmsModel> findAll(Integer siteId);
}
