package cn.mooc.app.module.service.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.service.data.entity.AppServiceInfo;

/**
 * AppServiceInfoRepository
 * 服务号文章资讯实数据访问
 * 
 * @author zjj
 * @date 2016-08-16
 */
public interface AppServiceInfoRepository extends JpaRepository<AppServiceInfo, Integer>,JpaSpecificationExecutor<AppServiceInfo>{
	
}
