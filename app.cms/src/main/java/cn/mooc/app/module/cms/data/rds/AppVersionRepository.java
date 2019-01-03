package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.AppVersion;

/**
 * AppVersionRepository
 * App版本管理数据访问
 * 
 * @author Taven
 * @date 2016-05-16
 */
public interface AppVersionRepository extends JpaRepository<AppVersion, Integer>,JpaSpecificationExecutor<AppVersion>{
	
	@Query("select t from AppVersion t where t.site.id = ?1 order by t.publishDate desc, t.id desc")
	List<AppVersion> findBySiteId(Integer siteId);
	
}
