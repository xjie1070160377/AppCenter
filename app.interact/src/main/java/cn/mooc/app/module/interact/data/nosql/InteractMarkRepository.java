package cn.mooc.app.module.interact.data.nosql;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.enums.MarkType;

public interface InteractMarkRepository extends MongoRepository<InteractMark, ObjectId> {
	@Query("{ ?0 : ?1 }")
	Page<InteractMark> findList(String columnFiled,Object keyWord, Pageable pageable);
	
	@Query("{ ?0 : ?1 , userId:?2 }")
	Page<InteractMark> findList(String columnFiled,Object keyWord,long userId, Pageable pageable);
	
	@Query("{userId:?0}")
	Page<InteractMark> findList(long userId, Pageable pageable);
	
	@Query("{_id:?0}")
	InteractMark findOne(ObjectId id);
	
	@Query("{fid:?0 , ftype : ?1, userId:?2}")
	List<InteractMark> findList(long fid, MarkType ftype, long userId);
	
}
