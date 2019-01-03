package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.ExchangeGood;

/**
 * ExchangeGoodRepository
 * R币兑换商品明细数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface ExchangeGoodRepository extends JpaRepository<ExchangeGood, Integer>,JpaSpecificationExecutor<ExchangeGood>{

	void deleteByExchangeRuleId(Integer exchangeRuleId);

	List<ExchangeGood> findByExchangeRuleId(Integer exchangeRuleId);

	ExchangeGood findByExchangeRuleIdAndGoodId(Integer exchangeRuleId, Integer goodId);

	void deleteByExchangeRuleIdAndGoodId(Integer exchangeRuleId, Integer goodId);
	
}
