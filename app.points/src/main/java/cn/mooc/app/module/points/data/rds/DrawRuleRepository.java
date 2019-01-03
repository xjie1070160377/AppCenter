package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.DrawRule;

/**
 * DrawRuleRepository
 * 抽奖规则数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface DrawRuleRepository extends JpaRepository<DrawRule, Integer>,JpaSpecificationExecutor<DrawRule>{
	
}
