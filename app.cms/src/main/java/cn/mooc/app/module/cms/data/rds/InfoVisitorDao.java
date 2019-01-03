package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.InfoVisitor;


/**
 * InfoVisitorDao
 * 文档访问记录数据访问
 * 
 * @author hwt
 * @date 2015-11-17
 */
public interface InfoVisitorDao extends JpaRepository<InfoVisitor, Integer> {

	Page<InfoVisitor> findAll(Specification<InfoVisitor> spec, Pageable pageable);

	@Query("select bean from InfoVisitor bean where bean.userId = ?1 and bean.site.id = ?2 and bean.info.id = ?3")
	InfoVisitor find(long userId, Integer siteId, Integer infoId);

}
