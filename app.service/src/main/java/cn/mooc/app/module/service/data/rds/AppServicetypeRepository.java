package cn.mooc.app.module.service.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.service.data.entity.AppServicetype;

/**
 * AppServicetypeRepository
 * 服务号类型数据访问
 * 
 * @author zjj
 * @date 2016-08-16
 */
public interface AppServicetypeRepository extends JpaRepository<AppServicetype, Integer>,JpaSpecificationExecutor<AppServicetype>{
	
}
