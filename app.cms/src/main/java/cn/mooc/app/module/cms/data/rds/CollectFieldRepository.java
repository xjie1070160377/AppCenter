package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.CollectField;

/**
 * CollectFieldRepository
 * 采集字段数据访问
 * 
 * @author linwei
 * @date 2017-06-23
 */
public interface CollectFieldRepository extends JpaRepository<CollectField, Integer>,JpaSpecificationExecutor<CollectField>{
	
}
