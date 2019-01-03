package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import cn.mooc.app.module.cms.data.entity.Node;

/**
 * NodeRepository
 * 栏目数据访问
 * 
 * @author hwt
 * @date 2016-05-11
 */
public interface NodeRepository extends JpaRepository<Node, Integer>,JpaSpecificationExecutor<Node>{

	@QueryHints(value = { @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
	List<Node> findBySiteIdAndParentIdIsNull(Integer siteId);
	
	@Query("select bean from Node bean where bean.site.id = ?1")
	List<Node> findAll(Integer siteId);

	@QueryHints(value = { @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
	List<Node> findBySiteIdAndNumber(Integer siteId, String number);

	@Query("select count(*) from Node bean where bean.parent.id = ?1")
	public long countByParentId(Integer parentId);

	@Query("select bean.treeNumber from Node bean where bean.id = ?1")
	public String findTreeNumber(Integer id);

	@Modifying
	@Query("update Node bean set bean.treeNumber=CONCAT('*',bean.treeNumber) where bean.treeNumber like ?1 and bean.site.id = ?2")
	public int appendModifiedFlag(String treeNumberStart, Integer siteId);

	@Modifying
	@Query("update Node bean set bean.treeLevel=(LENGTH(CONCAT(?2,SUBSTRING(bean.treeNumber,?3)))-4)/5, bean.treeNumber=CONCAT(?2,SUBSTRING(bean.treeNumber,?3)) where bean.treeNumber like ?1 and bean.site.id = ?4")
	public int updateTreeNumber(String treeNumber, String value, int len,
			Integer siteId);

	@Modifying
	@Query("update Node bean set bean.treeMax = ?2 where bean.id = ?1")
	public int updateTreeMax(Integer id, String treeMax);
	
	/**
	 * 获取固定栏目
	 * @param siteId
	 * @return
	 */
	@Query("select bean from Node bean where bean.site.id=?1 and bean.p1=0 and bean.p5=1 order by bean.treeNumber")
	public List<Node> findFixedNode(Integer siteId);
	
	/**
	 * 获取匿名用户栏目
	 * @param siteId
	 * @return
	 */
	@Query("select bean from Node bean where bean.site.id=?1 and bean.p1=0 and bean.p5 in (1,2) and bean.p6 in (2,3) order by bean.treeNumber")
	public List<Node> findNonUserNode(Integer siteId);
	
	/**
	 * 获取校外用户定制栏目
	 * @param siteId
	 * @param userId
	 * @return
	 */
	@Query("select bean from UserNode bean1,Node bean where bean1.nodeId=bean.id and bean.site.id=?1 and bean1.userId=?2 and bean.p1=0 and bean.p5 in (1,2) and bean.p6 in (2,3) order by bean1.xh")
	public List<Node> findOutUserNode(Integer siteId, Long userId);
	
	/**
	 * 获取校外用户未定制的栏目
	 * @param siteId
	 * @param userId
	 * @return
	 */
	@Query("select bean from Node bean where bean.site.id=?1 and not exists (select bean1 from UserNode bean1 where bean1.nodeId=bean.id and bean1.userId=?2) and bean.p1=0 and bean.p5 in (1,2) and bean.p6 in (2,3) order by bean.treeNumber")
	public List<Node> findNotOutUserCustomNode(Integer siteId, Long userId);
	
	/**
	 * 获取用户定制栏目
	 * @param siteId
	 * @param userId
	 * @return
	 */
	@Query("select bean from UserNode bean1,Node bean where bean1.nodeId=bean.id and bean.site.id=?1 and bean1.userId=?2 and bean.p1=0 and bean.p5=2 order by bean1.xh")
	public List<Node> findUserCustomNode(Integer siteId, Long userId);
	/**
	 * 获取用户未定制的栏目
	 * @param siteId
	 * @param userId
	 * @return
	 */
	@Query("select bean from Node bean where bean.site.id=?1 and not exists (select bean1 from UserNode bean1 where bean1.nodeId=bean.id and bean1.userId=?2) and bean.p1=0 and bean.p5=2 order by bean.treeNumber")
	public List<Node> findNotUserCustomNode(Integer siteId, Long userId);
	
	@Modifying
	@Query("update Node bean set bean.parent.id = ?2 where bean.id = ?1")
	public int updateParentId(Integer id, Integer parentId);
}
