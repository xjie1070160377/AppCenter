package cn.mooc.app.module.cms.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.module.cms.data.entity.PicDic;
import cn.mooc.app.module.cms.data.nosql.PicDicRepostory;
import cn.mooc.app.module.interact.data.entity.InteractComment;

@Service
public class PicDicService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PicDicRepostory picDicRepostory;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	
	public List<PicDic> getPicDicList(){
		Sort sort = new Sort(Direction.ASC, "sortIndex");
		return picDicRepostory.findAll(sort);
		//return picDicRepostory.findAll();
	}
	
	public PicDic save(PicDic entity) throws Exception {
		if(StringUtils.isEmpty(entity.getPic())){
			throw new SaveFailureException("图片地址不能为空.");
		}
		if(entity.getpId() == null){
			entity.setpId(mongoDbOpr.getNextSeqFor(PicDic.class));
		}		
		return this.picDicRepostory.save(entity);
		
	}
	
	
}
