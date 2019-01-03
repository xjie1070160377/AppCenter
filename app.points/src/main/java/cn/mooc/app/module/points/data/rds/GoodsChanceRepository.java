package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.GoodsChance;

/**
 * GoodsChanceRepository
 * 商品抽奖概率数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface GoodsChanceRepository extends JpaRepository<GoodsChance, Integer>,JpaSpecificationExecutor<GoodsChance>{

	List<GoodsChance> findByDrawRuleId(Integer ruleId);

	void deleteByDrawRuleId(Integer id);

	GoodsChance findByDrawRuleIdAndGoodId(Integer id, Integer goodId);

	void deleteByDrawRuleIdAndGoodId(Integer id, Integer id2);
	
}
