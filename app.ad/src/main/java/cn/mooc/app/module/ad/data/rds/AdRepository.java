package cn.mooc.app.module.ad.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.ad.data.entity.Ad;

/**
 * AdRepository
 * 广告数据访问
 * 
 * @author oyhx
 * @date 2016-05-09
 */
public interface AdRepository extends JpaRepository<Ad, Integer>,JpaSpecificationExecutor<Ad>{
	
}
