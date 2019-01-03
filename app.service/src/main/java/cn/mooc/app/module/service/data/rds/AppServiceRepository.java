package cn.mooc.app.module.service.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.service.data.entity.AppService;

/**
 * AppServiceRepository
 * 服务号数据访问
 * 
 * @author zjj
 * @date 2016-08-16
 */
public interface AppServiceRepository extends JpaRepository<AppService, Integer>,JpaSpecificationExecutor<AppService>{
	
	@Query("select bean from AppService bean where bean.createrUserid = ?1 order by bean.createTime desc")
	List<AppService> findByUserId(Long userId);
}
