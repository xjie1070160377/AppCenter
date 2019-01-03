package cn.mooc.app.module.service.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.service.data.entity.ServMsg;

/**
 * ServMsgRepository
 * 服务消息数据访问
 * 
 * @author zjj
 * @date 2016-09-13
 */
public interface ServMsgRepository extends JpaRepository<ServMsg, Integer>,JpaSpecificationExecutor<ServMsg>{
	
}
