package cn.mooc.app.module.ad.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.ad.data.entity.AdSlot;

/**
 * AdSlotRepository
 * 广告版位数据访问
 * 
 * @author oyhx
 * @date 2016-05-06
 */
public interface AdSlotRepository extends JpaRepository<AdSlot, Integer>,JpaSpecificationExecutor<AdSlot>{

	@Query("select bean from AdSlot bean where bean.number = ?1")
	AdSlot findByNumber(String number);
	
}
