package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.UserNode;
import cn.mooc.app.module.cms.data.rds.UserNodeRepository;

/**
 * UserNodeService
 * 用户订制栏目服务
 * 
 * @author zjj
 * @date 2016-07-06
 */
@Service
public class UserNodeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserNodeRepository userNodeRepository;
	
	public UserNode getUserNodeById(Integer id) {
		UserNode entity = userNodeRepository.findOne(id);
		return entity;
	}
	
	public List<UserNode> getAllUserNodes(){
		return this.userNodeRepository.findAll();
	}
	
	public Page<UserNode> findUserNodePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<UserNode> spec = SpecDynamic.buildSpec(filters.values());

		return userNodeRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public UserNode saveUserNode(UserNode entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.userNodeRepository.save(entity);
	}

	@Transactional
	public UserNode updateUserNode(UserNode entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.userNodeRepository.save(entity);
	}
	
	@Transactional
	public boolean delUserNode(Integer id){
		try{
			this.userNodeRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delUserNodes(Integer[] ids){
		try{
			List<UserNode> entities = this.userNodeRepository.findAll(Arrays.asList(ids));
			this.userNodeRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@Transactional
	public void updateUserNode(Long userId, Integer...nodeIds){
		userNodeRepository.deleteByUser(userId);
		List<UserNode> userNodes = new ArrayList<UserNode>();
		int i = 0;
		for(Integer nodeId : nodeIds){
			UserNode usernode = new UserNode();
			usernode.setNodeId(nodeId);
			usernode.setUserId(userId);
			usernode.setXh(i);
			i++;
			userNodes.add(usernode);
		}
		userNodeRepository.save(userNodes);
	}
}
