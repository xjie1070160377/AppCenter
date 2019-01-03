package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.CmsMessage;
import cn.mooc.app.module.cms.data.entity.Info;

/**
 * CmsMessageRepository
 * 消息中心数据访问
 * 
 * @author zjj
 * @date 2016-08-10
 */
public interface CmsMessageRepository extends JpaRepository<CmsMessage, Integer>,JpaSpecificationExecutor<CmsMessage>{
	
	@Query(nativeQuery=true, value="select * from t_cms_message order by create_date desc limit 1")
	CmsMessage findLastMessage();
}
