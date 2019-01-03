package cn.mooc.app.core.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.core.data.entity.SysMenuEntity;


public interface SysMenuRepository  extends JpaRepository<SysMenuEntity, Long>,JpaSpecificationExecutor<SysMenuEntity>{

	@Query("select t from SysMenuEntity t where t.menuArea = ?1")
	List<SysMenuEntity> getMenusByType(int menuType);
	
	@Query("select t from SysMenuEntity t where t.menuArea = 1 and t.navMenu=1")
	List<SysMenuEntity> getMNavMenus();
	
	@Query("select t from SysMenuEntity t where t.menuArea = 0 and t.navMenu=1")
	List<SysMenuEntity> getUNavMenus();
	
	@Query("select t from SysMenuEntity t where t.menuArea = 0")
	List<SysMenuEntity> getUMenus();
	
	@Query("select t from SysMenuEntity t where t.parentId = ?1 and t.navMenu=1")
	List<SysMenuEntity> getChildNavMenus(long parentId);
	
	@Query("select t from SysMenuEntity t where t.parentId = ?1")
	List<SysMenuEntity> getChildMenus(long parentId);
	
}