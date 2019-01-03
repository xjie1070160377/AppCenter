package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.Attribute;

/**
 * AttributeRepository 文档属性数据访问
 * 
 * @author hwt
 * @date 2016-05-06
 */
public interface AttributeRepository extends JpaRepository<Attribute, Integer>, JpaSpecificationExecutor<Attribute> {

	@Query("select bean from Attribute bean where bean.site.id = ?1")
	List<Attribute> findBySiteId(Integer siteId);

}
