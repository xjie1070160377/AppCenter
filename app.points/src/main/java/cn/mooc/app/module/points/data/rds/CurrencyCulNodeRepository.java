package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.CurrencyCulNode;

/**
 * CurrencyCulNodeRepository
 * 剔除栏目参与R币数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface CurrencyCulNodeRepository extends JpaRepository<CurrencyCulNode, Integer>,JpaSpecificationExecutor<CurrencyCulNode>{

	void deleteByRuleId(Integer id);

	List<CurrencyCulNode> findByRuleId(Integer ruleId);
	
}
