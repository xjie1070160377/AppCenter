package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
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
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoNode;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.rds.InfoNodeRepository;

/**
 * InfoNodeService
 * 文章栏目服务
 * 
 * @author hwt
 * @date 2016-05-16
 */
@Service
public class InfoNodeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InfoNodeRepository infoNodeRepository;
	@Autowired
	private NodeService nodeService;
	
	public InfoNode getInfoNodeById(Integer id) {
		InfoNode entity = infoNodeRepository.findOne(id);
		return entity;
	}
	
	public List<InfoNode> getAllInfoNodes(){
		return this.infoNodeRepository.findAll();
	}
	
	public Page<InfoNode> findInfoNodeList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<InfoNode> spec = SpecDynamic.buildSpec(filters.values());
		
		return infoNodeRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public InfoNode saveInfoNode(InfoNode entity) throws Exception {
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.infoNodeRepository.save(entity);
	}

	@Transactional
	public InfoNode updateInfoNode(InfoNode entity) throws Exception{
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.infoNodeRepository.save(entity);
	}
	
	@Transactional
	public boolean delInfoNode(Integer id){
		try{
			this.infoNodeRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delInfoNodes(Integer[] ids){
		try{
			List<InfoNode> entities = this.infoNodeRepository.findAll(Arrays.asList(ids));
			this.infoNodeRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	public void deleteByNodeId(Integer nodeId) {
		infoNodeRepository.deleteByNodeId(nodeId);
	}
	@Transactional
	public List<InfoNode> save(Info info, Integer[] nodeIds, Integer nodeId) {
		if (nodeIds == null) {
			nodeIds = new Integer[0];
		} else {
			nodeIds = ArrayUtils.removeElement(nodeIds, nodeId);
		}
		int size = ArrayUtils.getLength(nodeIds) + 1;
		List<InfoNode> infoNodes = info.getInfoNodes();
		if (infoNodes == null) {
			infoNodes = new ArrayList<InfoNode>(size);
			info.setInfoNodes(infoNodes);
		}
		infoNodes.add(save(info, nodeId, 0));
		if (size > 1) {
			for (int i = 0, len = nodeIds.length; i < len; i++) {
				infoNodes.add(save(info, nodeIds[i], i + 1));
			}
		}
		return infoNodes;
	}

	@Transactional
	private InfoNode save(Info info, Integer nodeId, int index) {
		InfoNode infoNode = new InfoNode();
		Node node = nodeService.getNodeById(nodeId);
		infoNode.setNode(node);
		infoNode.setInfo(info);
		infoNode.setNodeIndex(index);
		infoNodeRepository.save(infoNode);
		return infoNode;
	}

	@Transactional
	public List<InfoNode> update(Info info, Integer[] nodeIds, Integer nodeId) {
		// 主栏目为null，不更新。
		if (nodeId == null) {
			return info.getInfoNodes();
		}
		if (nodeIds == null) {
			nodeIds = new Integer[0];
		} else {
			nodeIds = ArrayUtils.removeElement(nodeIds, nodeId);
		}
		List<InfoNode> infoNodes = info.getInfoNodes();
		// 处理主栏目
		if (!infoNodes.isEmpty()) {
			InfoNode infoNode = infoNodes.get(0);
			if (!infoNode.getNode().getId().equals(nodeId)) {
				infoNodes.set(0, save(info, nodeId, 0));
				infoNodeRepository.delete(infoNode);
			}
		} else {
			infoNodes.add(0, save(info, nodeId, 0));
		}
		// 先删除
		Set<InfoNode> tobeDelete = new HashSet<InfoNode>();
		// 从第二个栏目开始，第一个是主栏目。
		for (int i = 1, len = infoNodes.size(); i < len; i++) {
			InfoNode infoNode = infoNodes.get(i);
			Integer nid = infoNode.getNode().getId();
			if (!ArrayUtils.contains(nodeIds, nid) || nodeId.equals(nid)) {
				tobeDelete.add(infoNode);
			}
		}
		infoNodes.removeAll(tobeDelete);
		infoNodeRepository.delete(tobeDelete);
		// 再新增
		for (int i = 0, len = nodeIds.length; i < len; i++) {
			boolean contains = false;
			for (InfoNode infoNode : infoNodes) {
				if (infoNode.getNode().getId().equals(nodeIds[i])) {
					infoNode.setNodeIndex(i + 1);
					infoNodes.remove(infoNode);
					infoNodes.add(i + 1, infoNode);
					contains = true;
					break;
				}
			}
			if (!contains) {
				infoNodes.add(save(info, nodeIds[i], i + 1));
			}
		}
		return infoNodes;
	}
}
