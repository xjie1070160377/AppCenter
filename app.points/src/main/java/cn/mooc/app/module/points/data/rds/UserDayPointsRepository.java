package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.points.data.entity.UserDayPoints;

public interface UserDayPointsRepository extends JpaRepository<UserDayPoints, Integer>,
		JpaSpecificationExecutor<UserDayPoints> {
	
	@Query("select bean from UserDayPoints bean where bean.userPoints.id=?1 and bean.rule.id=?2 and DATE_FORMAT(bean.lastTime,'%Y-%m-%d')=?3")
	UserDayPoints findByUserTime(Long userId, Integer ruleId, String day);
	
	@Query("select bean from UserDayPoints bean where bean.userPoints.id=?1 and bean.rule.id=?2")
	UserDayPoints findByUser(Long userId, Integer ruleId);
}
