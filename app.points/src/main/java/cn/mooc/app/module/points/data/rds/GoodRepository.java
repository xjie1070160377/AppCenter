package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.Good;

/**
 * GoodRepository
 * 商品信息数据访问
 * 
 * @author zjj
 * @date 2016-06-23
 */
public interface GoodRepository extends JpaRepository<Good, Integer>,JpaSpecificationExecutor<Good>{
	
}
