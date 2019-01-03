package cn.mooc.app.core.data.nosql;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.core.data.entity.SysLogEntity;
import cn.mooc.app.core.enums.LogType;

public interface SysLogRepository extends MongoRepository<SysLogEntity, ObjectId> {

	@Query("{ ?0 : ?1 }")
	Page<SysLogEntity> findLogList(String columnFiled,Object keyWord, Pageable pageable);
	
	@Query("{ ?0 : ?1 , userId:?2 }")
	Page<SysLogEntity> findLogList(String columnFiled,Object keyWord,long userId, Pageable pageable);
	
	@Query("{userId:?0}")
	Page<SysLogEntity> findLogList(long userId, Pageable pageable);
	
	@Query("{id:?0}")
	SysLogEntity findLog(long id);
	
	@Query("{userId:?0 , logType:?1}")
	SysLogEntity getUserLog(long userId, LogType logType, Sort sort);
	
	
}
