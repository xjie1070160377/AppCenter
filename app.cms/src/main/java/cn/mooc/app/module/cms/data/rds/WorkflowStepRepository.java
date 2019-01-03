package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;

public interface WorkflowStepRepository extends JpaRepository<WorkflowStep, Integer>,JpaSpecificationExecutor<WorkflowStep>{
//	public List<WorkflowStep> findAll(Specification<WorkflowStep> spec, Sort sort);
//
//	public List<WorkflowStep> findAll(Specification<WorkflowStep> spec, Limitable limit);
//
//	public WorkflowStep findOne(Integer id);
//
//	public WorkflowStep save(WorkflowStep bean);
//
//	public void delete(WorkflowStep bean);

	// --------------------
	@Query(nativeQuery=true, value="SELECT a.* from t_cms_workflow_step a join t_cms_node b on a.workflow_id=b.workflow_id join t_cms_info c on b.node_id=c.node_id join t_cms_workflowstep_role d on a.workflowstep_id=d.workflowstep_id join t_sys_user_role e on d.role_id=e.roleId where c.info_id=?1 and a.toend=1 and e.userId=?2")
	List<WorkflowStep> findHasToEnd(Integer infoId, Long userId);
	
	@Query(nativeQuery=true, value="SELECT a.* from t_cms_workflow_step a join t_cms_node b on a.workflow_id=b.workflow_id join t_cms_workflowstep_role d on a.workflowstep_id=d.workflowstep_id join t_sys_user_role e on d.role_id=e.roleId where b.node_id=?1 and a.toend=1 and e.userId=?2")
	List<WorkflowStep> findNodeHasToEnd(Integer nodeId, Long userId);
}
