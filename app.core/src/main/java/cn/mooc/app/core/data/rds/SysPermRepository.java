package cn.mooc.app.core.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.core.data.entity.SysPermEntity;

public interface SysPermRepository extends JpaRepository<SysPermEntity, Long>,JpaSpecificationExecutor<SysPermEntity>{

}
