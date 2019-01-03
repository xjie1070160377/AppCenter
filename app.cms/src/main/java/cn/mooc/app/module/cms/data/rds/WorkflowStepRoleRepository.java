package cn.mooc.app.module.cms.data.rds;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.entity.WorkflowStepRole;

public interface WorkflowStepRoleRepository extends JpaRepository<WorkflowStepRole, Integer>,JpaSpecificationExecutor<WorkflowStepRole>{
	@Modifying
	@Query("delete from WorkflowStepRole bean where bean.step.id = ?1")
	void deleteByWorkFlowStepId(Integer id);

//	public WorkflowStepRole findOne(Integer id);
//
//	public WorkflowStepRole save(WorkflowStepRole bean);
//
//	void delete(Iterable<WorkflowStepRole> entities);

	// --------------------

//	@Query("select count(*) from WorkflowStepRole bean where bean.role.id in (?1)")
//	public long countByRoleId(Collection<Integer> roleIds);
}
