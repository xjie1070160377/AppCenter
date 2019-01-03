package cn.mooc.app.module.service.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.service.data.entity.ServMsgInfo;
import cn.mooc.app.module.service.data.entity.ServMsgInfoPK;

/**
 * ServMsgInfoRepository
 * 服务文章消息数据访问
 * 
 * @author zjj
 * @date 2016-09-13
 */
public interface ServMsgInfoRepository extends JpaRepository<ServMsgInfo, ServMsgInfoPK>,JpaSpecificationExecutor<ServMsgInfo>{
	
}
