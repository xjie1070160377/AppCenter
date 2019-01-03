package cn.mooc.app.module.cms.data.rds;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.Info;

/**
 * InfoRepository
 * 文档数据访问
 * 
 * @author hwt
 * @date 2016-05-16
 */
public interface InfoRepository extends JpaRepository<Info, Integer>,JpaSpecificationExecutor<Info>{

	@Query("select bean from Info bean where bean.site.id = ?1")
	List<Info> findAll(Integer siteId);
	
	@Query("select min(bean.columnSort) from Info bean where bean.node.id=?1")
	Double getMinSortnum(Integer nodeId);
	
	@Modifying
	@Query("update Info bean set bean.views=?2+floor(rand()*(?3-?2)) where bean.views<?1 and bean.status='A'")
	public void updateHitByJs(Integer js, Integer zxdjs, Integer zddjs);
	
	@Modifying
	@Query("update Info bean set bean.views=?2+floor(rand()*(?3-?2)) where bean.status='A'")
	public void updateHitByAll(Integer zxdjs, Integer zddjs);
	
	@Modifying
	@Query("update Info bean set bean.views=bean.views*?2 where bean.views<?1 and bean.status='A'")
	public void updateHitByBs(Integer js, Integer bs);
	
	@Modifying
	@Query("update Info bean set bean.views=bean.views*?1 where bean.status='A'")
	public void updateHitByBsAll(Integer bs);
	
	@Query("select count(*) from Info bean where bean.node.id=?1 and bean.p1=?2 and bean.status='A'")
	public long countNodeInfo(Integer nodeId, Integer p1);
	
	@Query("select bean from Info bean where bean.node.id=?1 and bean.p1=?2 and bean.status='A'")
	public List<Info> findInfoByNode(Integer nodeId, Integer p1);
	
	@Query("from Info bean where bean.detail.title = ?1 and bean.site.id = ?2")
	public List<Info> findByTitle(String title, Integer siteId);
	
	@Modifying
	@Query("update Info bean set bean.status='A' where bean.site.id=?1 and (bean.status='F' or bean.status='G') and bean.publishDate<=?2 and (bean.offDate is null or bean.offDate>=?2)")
	public int publish(Integer siteId, Date now);

	@Modifying
	@Query("update Info bean set bean.status='F' where bean.site.id=?1 and bean.status='A' and bean.publishDate>?2")
	public int tobePublish(Integer siteId, Date now);
	
	@Modifying
	@Query("update Info bean set bean.status='G' where bean.site.id=?1 and bean.status='A' and bean.offDate<?2)")
	public int expired(Integer siteId, Date now);
	
	@Modifying
	@Query("update Info bean set bean.status=?2 where bean.id=?1")
	public int changeState(Integer infoId, String status, String typeUrl);
}
