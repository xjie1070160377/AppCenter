package cn.mooc.app.module.cms.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.entity.WorkflowStepRole;
import cn.mooc.app.module.cms.data.rds.WorkflowStepRoleRepository;


@Service
@Transactional(readOnly = true)
public class WorkflowStepRoleService {
	@Transactional
	public void save(WorkflowStep step, Integer[] roleIds) {
		if (roleIds != null) {
			WorkflowStepRole stepRole;
			SysRoleEntity role;
			for (int i = 0, len = roleIds.length; i < len; i++) {
				role = sysDataService.getSysRole(roleIds[i]);
//				role = roleService.get(roleIds[i]);
				stepRole = new WorkflowStepRole(step, role.getId(), i);
				dao.save(stepRole);
			}
		}
	}
	
	public List<WorkflowStepRole> findList(Integer stepId){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_step.id", stepId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<WorkflowStepRole> spec = SpecDynamic.buildSpec(filters.values());
		Sort sort = new Sort(Sort.Direction.ASC, "roleIndex");
		return dao.findAll(spec, sort);
	}

	@Transactional
	public void update(WorkflowStep step, Integer[] roleIds) {
//		dao.delete(step.getStepRoles());
		dao.deleteByWorkFlowStepId(step.getId());
		save(step, roleIds);
	}
	
	public boolean hasInfoWorkflow(Long userId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<WorkflowStepRole> query = cb.createQuery(WorkflowStepRole.class);
		Root<WorkflowStepRole> root = query.from(WorkflowStepRole.class);
		
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		java.util.Set<SysRoleEntity> roles = user.getRoles();
		Long[] ids = new Long[roles.size()];
		Iterator<SysRoleEntity> it = roles.iterator();
		int i = 0;
		while(it.hasNext()){
			ids[i] = it.next().getId();
			i++;
		}
		if(ids.length == 0){
			return false;
		}
//		pred = cb.and(pred, steprolejoin.get("roleId").in(ids));
//		
//		Subquery<UserRole> sub1 = query.subquery(UserRole.class);
		Subquery<SysRoleEntity> sub1 = query.subquery(SysRoleEntity.class);
		Root<SysRoleEntity> root1 = sub1.from(SysRoleEntity.class);
//		Predicate where1 = cb.and(root.get("role").get("id").in(ids));
//		Predicate where1 = cb.and(cb.equal(root.get("role").get("id"), root1.get("role").get("id")), cb.equal(root1.get("user").get("id"), userId));
		Predicate where1 = cb.and(cb.equal(root.get("roleId"), root1.get("id")), root1.get("id").in(ids));
		sub1.where(where1).select(root1);
//		
		Subquery<WorkflowStep> sub2 = query.subquery(WorkflowStep.class);
		Root<WorkflowStep> root2 = sub2.from(WorkflowStep.class);
		Predicate where2 = cb.equal(root2.get("id"), root.get("step").get("id"));
//		
		Subquery<Node> sub21 = query.subquery(Node.class);
		Root<Node> root21 = sub21.from(Node.class);
		Predicate where21 = cb.equal(root21.get("workflow").get("id"), root2.get("workflow").get("id"));
		sub21.where(where21).select(root21);
//		
		where2 = cb.and(where2, cb.exists(sub21));
		sub2.where(where2).select(root2);
//		
		query.where(cb.and(cb.exists(sub1), cb.exists(sub2))).select(root);
//		
		TypedQuery<WorkflowStepRole> typedQuery = em.createQuery(query); 
		List<WorkflowStepRole> list = typedQuery.getResultList();
		if(list==null || list.size() == 0){
			return false;
		}else{
			return true;
		}
	}


	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WorkflowStepRoleRepository dao;

	private EntityManager em;

	@javax.persistence.PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
