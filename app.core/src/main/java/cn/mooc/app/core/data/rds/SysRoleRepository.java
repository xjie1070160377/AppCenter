package cn.mooc.app.core.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.core.data.entity.SysRoleEntity;

public interface SysRoleRepository  extends JpaRepository<SysRoleEntity, Long>,JpaSpecificationExecutor<SysRoleEntity>{

	@Query("select t from SysRoleEntity t where t.id = ?1")
	SysRoleEntity getRole(long roleId);
	
	@Query("select t from SysRoleEntity t where t.roleName = ?1")
	SysRoleEntity getRoleByName(String roleName);
	
/*	@Query("select t from SysRoleEntity t where t.id in(?1)")
	List<SysRoleEntity> getRoles(int[] roleIds);*/
	
	@Query("select t from SysRoleEntity t where t.status = ?1")
	List<SysRoleEntity> getRoles(int status);
	
	@Modifying
	@Query("update SysRoleEntity set status=?2  where id=?1")
	int updateRoleStatus(long roleId,int status);
	
	
}
