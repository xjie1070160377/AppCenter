package cn.mooc.app.core.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.core.data.entity.SysUserVCoin;

public interface SysUserVCoinRepository extends JpaRepository<SysUserVCoin, Long>,JpaSpecificationExecutor<SysUserVCoin>{

	
	@Modifying
	@Query("update SysUserVCoin set historyTotal=historyTotal+?2, availableTotal= availableTotal+?2  where userId=?1")
	int obtainPoints(long userId,double points);
	
	@Modifying
	@Query("update SysUserVCoin set availableTotal= availableTotal+?2  where userId=?1")
	int spendPoints(long userId, double points);
	
	@Query("select t from SysUserVCoin t where t.userId = ?1")
	SysUserVCoin getPoints(long userId);
	
	
}
