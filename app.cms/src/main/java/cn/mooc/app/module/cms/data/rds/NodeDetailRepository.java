package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.NodeDetail;

/**
 * NodeDetailRepository
 * 栏目明细数据访问
 * 
 * @author hwt
 * @date 2016-05-16
 */
public interface NodeDetailRepository extends JpaRepository<NodeDetail, Integer>,JpaSpecificationExecutor<NodeDetail>{
	
}
