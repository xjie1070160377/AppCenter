package cn.mooc.app.module.sys.data.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.mooc.app.module.sys.data.entity.ChickenSoup;


public interface ChickenSoupRepository extends MongoRepository<ChickenSoup, String>{

}
