package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.Site;

/**
 * SiteRepository
 * 站点信息数据访问
 * 
 * @author Taven
 * @date 2016-05-13
 */
public interface SiteRepository extends JpaRepository<Site, Integer>,JpaSpecificationExecutor<Site>{
	
	@Query("select t from Site t where t.status=1 and t.domain = ?1")
	Site getSiteByDomain(String domain);
	
	@Query("select t from Site t where t.def=true order by t.def desc")
	List<Site> getDefaultSite();
	
	@Modifying
	@Query("update Site set def=false where id<>?1")
	void clearSiteDef(Integer id);
	
}
