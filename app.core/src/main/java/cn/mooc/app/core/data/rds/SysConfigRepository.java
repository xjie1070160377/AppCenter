package cn.mooc.app.core.data.rds;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.mooc.app.core.data.entity.SysConfigEntity;

public interface SysConfigRepository  extends JpaRepository<SysConfigEntity, String>,JpaSpecificationExecutor<SysConfigEntity>{
	
	@Query("select t from SysConfigEntity t where t.keyName = ?1")
	SysConfigEntity getSysConfig(String keyName);	
	
	@Query("select t from SysConfigEntity t where t.keyName = ?1")
	List<SysConfigEntity> getSysConfigs(String keyName, Pageable pageable);
	
	@Query("select t from SysConfigEntity t where t.keyName like :keyName")
	List<SysConfigEntity> getSysConfigs(@Param("keyName")String keyName);
	
}
