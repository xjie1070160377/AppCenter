package cn.mooc.app.module.cms.data.rds;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.data.entity.WorkflowGroup;

/**
 * WorkflowGroupDao
 * 
 * @author csmooc
 * 
 */
public interface WorkflowGroupRepository extends JpaRepository<WorkflowGroup, Integer>,JpaSpecificationExecutor<WorkflowGroup>{
	@Query("select bean from WorkflowGroup bean where bean.site.id = ?1")
	List<WorkflowGroup> findBySiteId(Integer siteId);
//	public List<WorkflowGroup> findAll(Specification<WorkflowGroup> spec, Sort sort);
//
//	public List<WorkflowGroup> findAll(Specification<WorkflowGroup> spec, Limitable limit);
//
//	public WorkflowGroup findOne(Integer id);
//
//	public WorkflowGroup save(WorkflowGroup bean);
//
//	public void delete(WorkflowGroup bean);

//	public List<WorkflowGroup> findBySiteId(Integer siteId);
//
//	@Query("select count(*) from WorkflowGroup bean where bean.site.id in ?1")
//	public long countBySiteId(Collection<Integer> siteIds);
}
