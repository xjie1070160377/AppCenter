package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.CurrencyLog;

/**
 * CurrencyLogRepository
 * R币获取兑换日志控制器数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface CurrencyLogRepository extends JpaRepository<CurrencyLog, Integer>,JpaSpecificationExecutor<CurrencyLog>{
	
}
