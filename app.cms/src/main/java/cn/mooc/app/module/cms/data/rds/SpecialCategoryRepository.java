package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.SpecialCategory;

/**
 * SpecialCategoryRepository
 * 专题分类数据访问
 * 
 * @author hwt
 * @date 2016-05-06
 */
public interface SpecialCategoryRepository extends JpaRepository<SpecialCategory, Integer>,JpaSpecificationExecutor<SpecialCategory>{

	@Query("select bean from SpecialCategory bean where bean.site.id = ?1")
	List<SpecialCategory> findAll(Integer siteId);
	
}
