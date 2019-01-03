package cn.mooc.app.core.data.rds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.core.data.entity.SysUserEntity;

public interface SysUserRepository extends JpaRepository<SysUserEntity, Long>,JpaSpecificationExecutor<SysUserEntity>{
	
	@Query("select t from SysUserEntity t where t.userName = ?1")
	SysUserEntity getUserInfo(String userName);
	
	@Query("select t from SysUserEntity t where t.status = ?1")
	List<SysUserEntity> getUserInfo(int status);
	
	@Query("select t from SysUserEntity t")
	Page<SysUserEntity> findUserList(Pageable pageable);
	
	@Modifying
    @Query("update SysUserEntity set status=?2 where id=?1")
    int changeStatus(long userId,int status);
	
	
	@Modifying
    @Query("delete from SysUserEntity where id in ?1")
    int delUsers(List<Long> idList);
	
}
