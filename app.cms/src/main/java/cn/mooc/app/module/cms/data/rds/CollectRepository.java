package cn.mooc.app.module.cms.data.rds;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.Collect;

/**
 * CollectRepository
 * 采集数据访问
 * 
 * @author linwei
 * @date 2017-06-22
 */
public interface CollectRepository extends JpaRepository<Collect, Integer>,JpaSpecificationExecutor<Collect>{
	
	@Query("from Collect bean where bean.site.id=?1 and bean.status=0 order by bean.id asc")
	public List<Collect> findList(Integer siteId);

	@Query("select count(*) from Collect bean where bean.node.id in (?1)")
	public long countByNodeId(Collection<Integer> nodeIds);

	@Query("select count(*) from Collect bean where bean.userId in (?1)")
	public long countByUserId(Collection<Integer> userIds);

	@Query("select count(*) from Collect bean where bean.site.id in (?1)")
	public long countBySiteId(Collection<Integer> siteIds);

}
