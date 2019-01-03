package cn.mooc.app.module.interact.data.nosql;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.module.interact.data.entity.InteractComment;

public interface InteractCommentRepository extends MongoRepository<InteractComment, String> {

	@Query("{ ?0 : ?1 }")
	Page<InteractComment> findList(String columnFiled,Object keyWord, Pageable pageable);
	
	@Query("{ ?0 : ?1 , userId:?2 }")
	Page<InteractComment> findList(String columnFiled,Object keyWord,long userId, Pageable pageable);
	
	@Query("{userId:?0}")
	Page<InteractComment> findList(long userId, Pageable pageable);
	
	@Query("{_id:?0}")
	InteractComment findOne(String id);

	@Query("{ftype:?0,sourceId:?1}")
	List<InteractComment> findList(String ftype, Integer sourceId);

	@Query("{commentId:?0}")
	InteractComment findByCommentId(Integer commentId);
	
	@Query("{sourceId:?0}")
	List<InteractComment> findBySourceId(Integer sourceId);

	@Query("{fid:?0,state:?1}")
	List<InteractComment> findBySFid(Integer fid, int state);
}
