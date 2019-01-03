package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.Special;

/**
 * SpecialRepository
 * 专题管理数据访问
 * 
 * @author hwt
 * @date 2016-05-09
 */
public interface SpecialRepository extends JpaRepository<Special, Integer>,JpaSpecificationExecutor<Special>{

	@Query("select bean from Special bean where bean.site.id = ?1")
	List<Special> findAll(Integer siteId);
}
