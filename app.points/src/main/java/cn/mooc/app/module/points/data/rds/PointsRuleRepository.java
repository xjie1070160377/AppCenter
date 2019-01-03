package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.points.data.entity.PointsRule;

public interface PointsRuleRepository extends JpaRepository<PointsRule, Integer>,
		JpaSpecificationExecutor<PointsRule> {

	@Query("select bean from PointsRule bean where bean.ruleCode=?1 and bean.status=1")
	public PointsRule findByCode(String code);
	
	@Query("select bean from PointsRule bean where bean.status=1 order by bean.id")
	public List<PointsRule> findValidRule();
}
