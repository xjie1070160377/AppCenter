package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.InfoDetail;

/**
 * InfoDetailRepository
 * 文章明细数据访问
 * 
 * @author hwt
 * @date 2016-05-19
 */
public interface InfoDetailRepository extends JpaRepository<InfoDetail, Integer>,JpaSpecificationExecutor<InfoDetail>{
	
}
