package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.InfoNode;

/**
 * InfoNodeRepository 文章栏目数据访问
 * 
 * @author hwt
 * @date 2016-05-16
 */
public interface InfoNodeRepository extends JpaRepository<InfoNode, Integer>, JpaSpecificationExecutor<InfoNode> {

	@Modifying
	@Query("delete from InfoNode bean where bean.node.id=?1")
	void deleteByNodeId(Integer nodeId);

}
