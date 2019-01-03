package cn.mooc.app.module.cms.data.rds;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import cn.mooc.app.module.cms.data.entity.WorkflowGroup;
import cn.mooc.app.module.cms.data.entity.WorkflowLog;

public interface WorkflowLogRepository extends JpaRepository<WorkflowLog, Integer>,JpaSpecificationExecutor<WorkflowLog>{
//	public Page<WorkflowLog> findAll(Specification<WorkflowLog> spec, Pageable pageable);
//
//	public List<WorkflowLog> findAll(Specification<WorkflowLog> spec, Limitable limitable);
//
//	public WorkflowLog findOne(Integer id);
//
//	public WorkflowLog save(WorkflowLog bean);
//
//	public void delete(WorkflowLog bean);

	// --------------------
	@Query("select bean from WorkflowLog bean where bean.process.rejection=1 and bean.process.dataId=?1 and bean.process.dataType=?2 order by bean.creationDate asc")
	public List<WorkflowLog> findListByTgInfo(Integer dataId, Integer type);
	
	@Query("select bean from WorkflowLog bean where bean.process.dataId=?1 and bean.process.dataType=?2 order by bean.creationDate asc")
	public List<WorkflowLog> findListByInfo(Integer dataId, Integer type);

	@Modifying
	@Query("delete from WorkflowLog bean where bean.process.id in (select process.id from WorkflowProcess process where process.step.id in ?1)")
	public int deleteByStepId(List<Integer> stepIds);

	@Modifying
	@Query("delete from WorkflowLog bean where bean.process.id in (select process.id from WorkflowProcess process where process.workflow.id in ?1)")
	public int deleteByWorkflowId(List<Integer> workflowIds);

	@Modifying
	@Query("delete from WorkflowLog bean where bean.process.id in (select process.id from WorkflowProcess process where process.userId in ?1)")
	public int deleteByProcessUserId(List<Integer> userIds);

	@Modifying
	@Query("delete from WorkflowLog bean where bean.userId in ?1")
	public int deleteByUserId(List<Integer> userIds);
	
	@Query(nativeQuery=true, value="select * from t_cms_workflow_log where workflowprocess_id=?1 order by creation_date desc limit 1")
	public WorkflowLog findLastLog(Integer pid);
}
