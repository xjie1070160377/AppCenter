package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.ExchangeRule;

/**
 * ExchangeRuleRepository
 * R币兑换规则数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface ExchangeRuleRepository extends JpaRepository<ExchangeRule, Integer>,JpaSpecificationExecutor<ExchangeRule>{
	
}
