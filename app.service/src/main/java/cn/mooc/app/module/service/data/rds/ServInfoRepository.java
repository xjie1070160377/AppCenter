package cn.mooc.app.module.service.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.service.data.entity.ServInfo;

/**
 * ServInfoRepository
 * 服务号文章数据访问
 * 
 * @author zjj
 * @date 2016-08-16
 */
public interface ServInfoRepository extends JpaRepository<ServInfo, Integer>,JpaSpecificationExecutor<ServInfo>{
	
}
