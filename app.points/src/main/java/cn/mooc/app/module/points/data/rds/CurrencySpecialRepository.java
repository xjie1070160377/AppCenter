package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.CurrencySpecial;

/**
 * CurrencySpecialRepository
 * 指定专题文章获取R币规则数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface CurrencySpecialRepository extends JpaRepository<CurrencySpecial, Integer>,JpaSpecificationExecutor<CurrencySpecial>{

	void deleteByCurrencyRuleId(Integer id);

	List<CurrencySpecial> findByCurrencyRuleId(Integer ruleId);
	
}
