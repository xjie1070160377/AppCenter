package cn.mooc.app.core.data.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.mooc.app.core.data.entity.SysVCoinLog;

public interface SysVCoinLogRepository extends MongoRepository<SysVCoinLog, ObjectId> {
	
	
	
}
