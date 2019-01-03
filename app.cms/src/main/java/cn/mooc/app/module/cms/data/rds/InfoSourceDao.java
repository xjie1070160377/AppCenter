package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.module.cms.data.entity.InfoSource;

/**
 * InfoSourceDao
 * 来源数据访问
 * 
 * @author zjj
 * @date 2016-02-26
 */
public interface InfoSourceDao extends JpaRepository<InfoSource, Integer>,JpaSpecificationExecutor<InfoSource> {

	//Page<InfoSource> findAll(Specification<InfoSource> spec, Pageable pageable);

}
