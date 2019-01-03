package cn.mooc.app.module.interact.data.nosql;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.module.interact.data.entity.SnsOpLog;

public interface SnsOpLogRepostory extends MongoRepository<SnsOpLog, ObjectId> {

	@Query("{userId:?0 , ftype : ?1, fid:?2}")
	List<SnsOpLog> findList(Integer userId, Integer ftype, Integer fid);


}
