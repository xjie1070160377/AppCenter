package cn.mooc.app.core.data.nosql;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.core.data.entity.SysUserExt;

public interface SysUserExtRepository extends MongoRepository<SysUserExt, ObjectId> {

	@Query("{userId:?0}")
	SysUserExt findUserExt(long userId);
	
	@Query("{nickName:?0}")
	SysUserExt findUserExtByNickName(String nickName);
	
//	@Query("{ $or: [ { 'nickName': '/.*?0.*/i' }, {'realName': '/.*?0.*/i'} ,{ 'userName': '/.*?0.*/i'}]}")
//	List<SysUserExt> findUserExtByName(String name);
	
	@Query("{ $or: [ { nickName: {$regex:?0,$options:'i'}}, {realName: {$regex:?0,$options:'i'}} ,{ userName: {$regex:?0,$options:'i'}}],userId:{$ne:?1}}")
	List<SysUserExt> findUserExtByName(String name, Long userId);
}
