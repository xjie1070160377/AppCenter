package cn.mooc.app.module.service.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.service.data.entity.AppServiceUser;
import cn.mooc.app.module.service.data.entity.AppServiceUserPK;

/**
 * AppServiceUserRepository
 * 服务号关注用户数据访问
 * 
 * @author zjj
 * @date 2016-08-16
 */
public interface AppServiceUserRepository extends JpaRepository<AppServiceUser, AppServiceUserPK>,JpaSpecificationExecutor<AppServiceUser>{
	
}
