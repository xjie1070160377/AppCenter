package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.CmsModelField;

/**
 * CmsModelFieldRepository
 * 模型字段管理数据访问
 * 
 * @author hwt
 * @date 2016-06-14
 */
public interface CmsModelFieldRepository extends JpaRepository<CmsModelField, Integer>,JpaSpecificationExecutor<CmsModelField>{

	@Modifying
	@Query("delete from CmsModelField bean where bean.model.id=?1")
	void clearByModelId(Integer modelId);

	@Query("select bean from CmsModelField bean where bean.model.id=?1 order by bean.seq asc")
	List<CmsModelField> findList(Integer modelId);
	
}
