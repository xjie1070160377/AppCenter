package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.points.data.entity.UserDayCur;

/**
 * UserDayCurRepository
 * 用户日获取R币信息数据访问
 * 
 * @author zjj
 * @date 2016-07-04
 */
public interface UserDayCurRepository extends JpaRepository<UserDayCur, Integer>,JpaSpecificationExecutor<UserDayCur>{
	
	@Query("select bean from UserDayCur bean where bean.userId=?1 and bean.currencyRule.id=?2 and DATE_FORMAT(bean.lastTime,'%Y-%m-%d')=?3")
	public UserDayCur findByUserTime(Long userId, Integer ruleId, String date);
	
	@Query("select bean from UserDayCur bean where bean.userId=?1 and bean.currencyRule.id=?2")
	UserDayCur findByUser(Long userId, Integer ruleId);
}
