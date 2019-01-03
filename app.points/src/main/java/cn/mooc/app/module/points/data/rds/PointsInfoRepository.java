package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.points.data.entity.PointsInfo;

public interface PointsInfoRepository extends JpaRepository<PointsInfo, Integer>,
		JpaSpecificationExecutor<PointsInfo> {
	
	@Query("select bean from PointsInfo bean where bean.rule.id=?1")
	List<PointsInfo> findListByRule(Integer ruleId);
	
	@Modifying
	@Query("delete from PointsInfo bean where bean.rule.id=?1")
	void deleteByRule(Integer ruleId);
	
	@Query("select bean from PointsInfo bean where bean.rule.id=?1 and bean.info.id=?2")
	PointsInfo findByRuleInfo(Integer ruleId, Integer infoId);
	
	@Modifying
	@Query("delete from PointsInfo bean where bean.rule.id=?1 and bean.info.id=?2")
	void deleteByRuleInfo(Integer ruleId, Integer infoId);
}
