package cn.mooc.app.core.data.rds;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.core.data.entity.SysOrg;

public interface SysOrgRepository extends JpaRepository<SysOrg, Integer>,
		JpaSpecificationExecutor<SysOrg> {

	public List<SysOrg> findByParentIdIsNull();
	
	public List<SysOrg> findByTreeNumberStartingWith(String treeNumber, Sort sort);

	@Query("select max(bean.treeNumber) from SysOrg bean where bean.parent.id is null")
	public String findMaxRootTreeNumber();
	
	@Query("select bean from SysOrg bean where bean.oid=?1")
	public SysOrg findByOid(Integer oid);

	@Query("select count(*) from SysOrg bean where bean.parent.id = ?1")
	public long countByParentId(Integer parentId);

	@Query("select count(*) from SysOrg bean where bean.parent.id is null")
	public long countRoot();

	@Query("select bean.treeNumber from SysOrg bean where bean.id = ?1")
	public String findTreeNumber(Integer id);

	@Modifying
	@Query("update SysOrg bean set bean.treeNumber=CONCAT('*',bean.treeNumber) where bean.treeNumber like ?1")
	public int appendModifiedFlag(String treeNumberStart);

	@Modifying
	@Query("update SysOrg bean set bean.treeLevel=(LENGTH(CONCAT(?2,SUBSTRING(bean.treeNumber,?3)))-4)/5, bean.treeNumber=CONCAT(?2,SUBSTRING(bean.treeNumber,?3)) where bean.treeNumber like ?1")
	public int updateTreeNumber(String treeNumber, String value, int len);

	@Modifying
	@Query("update SysOrg bean set bean.parent.id = ?2 where bean.id = ?1")
	public int updateParentId(Integer id, Integer parentId);

	@Modifying
	@Query("update SysOrg bean set bean.treeMax = ?2 where bean.id = ?1")
	public int updateTreeMax(Integer id, String treeMax);
}
