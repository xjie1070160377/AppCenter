package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.Global;

public interface GlobalRepository  extends JpaRepository<Global, Integer>,JpaSpecificationExecutor<Global>{

	@Query("select t from Global t order by t.id desc")
	Global getDefaultGlobal();
	
}
