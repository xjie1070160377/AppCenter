package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.points.data.entity.CurrencyRule;

/**
 * CurrencyRuleRepository
 * 获取R币规则数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface CurrencyRuleRepository extends JpaRepository<CurrencyRule, Integer>,JpaSpecificationExecutor<CurrencyRule>{
	
	@Query("select bean from CurrencyRule bean where bean.ruleCode=?1 and bean.status=1")
	public CurrencyRule findByCode(String code);
	
	@Query("select bean from CurrencyRule bean where bean.status=1 order by bean.id")
	public List<CurrencyRule> findValidRule();
}
