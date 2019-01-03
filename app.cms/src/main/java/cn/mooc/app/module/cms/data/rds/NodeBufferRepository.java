package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.NodeBuffer;

/**
 * NodeBufferRepository
 * 栏目缓存数据访问
 * 
 * @author hwt
 * @date 2016-05-16
 */
public interface NodeBufferRepository extends JpaRepository<NodeBuffer, Integer>,JpaSpecificationExecutor<NodeBuffer>{
	
}
