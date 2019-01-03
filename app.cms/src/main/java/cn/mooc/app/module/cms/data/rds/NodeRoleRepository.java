package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import cn.mooc.app.module.cms.data.entity.NodeRole;

public interface NodeRoleRepository extends JpaRepository<NodeRole, Integer>,JpaSpecificationExecutor<NodeRole>{

	public NodeRole findOne(Integer id);

	public List<NodeRole> findByNodeIdAndRoleId(Integer nodeId, long roleId);

	public List<NodeRole> findByNodeId(Integer nodeId);

	public List<NodeRole> findByRoleId(long roleId);

	@Modifying
	@Query("delete from NodeRole bean where bean.node.id=?1")
	public int deleteByNodeId(Integer nodeId);

	@Modifying
	@Query("delete from NodeRole bean where bean.roleId=?1")
	public int deleteByRoleId(long roleId);
}
