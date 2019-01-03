package cn.mooc.app.module.cms.data.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.mooc.app.module.cms.data.entity.PicDic;

public interface PicDicRepostory extends MongoRepository<PicDic, String> {

	
}
