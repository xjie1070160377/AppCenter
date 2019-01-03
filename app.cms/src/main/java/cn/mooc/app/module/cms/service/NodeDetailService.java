package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.NodeDetail;
import cn.mooc.app.module.cms.data.rds.NodeDetailRepository;

/**
 * NodeDetailService 栏目明细服务
 * 
 * @author hwt
 * @date 2016-05-16
 */
@Service
public class NodeDetailService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NodeDetailRepository nodeDetailRepository;

	public NodeDetail getNodeDetailById(Integer id) {
		NodeDetail entity = nodeDetailRepository.findOne(id);
		return entity;
	}

	public List<NodeDetail> getAllNodeDetails() {
		return this.nodeDetailRepository.findAll();
	}

	public Page<NodeDetail> findNodeDetailList(Map<String, Object> searchParams, PagerParam pageParam) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<NodeDetail> spec = SpecDynamic.buildSpec(filters.values());

		return nodeDetailRepository.findAll(spec, pageParam.getPageRequest());

	}

	@Transactional
	public NodeDetail saveNodeDetail(NodeDetail entity) throws Exception {
		entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.nodeDetailRepository.save(entity);
	}

	@Transactional
	public NodeDetail updateNodeDetail(NodeDetail entity) throws Exception {
		entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.nodeDetailRepository.save(entity);
	}

	@Transactional
	public boolean delNodeDetail(Integer id) {
		try {
			this.nodeDetailRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delNodeDetails(Integer[] ids) {
		try {
			List<NodeDetail> entities = this.nodeDetailRepository.findAll(Arrays.asList(ids));
			this.nodeDetailRepository.delete(entities);
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Transactional
	public void save(NodeDetail detail, Node node) {
		node.setDetail(detail);
		detail.setNode(node);
		detail.applyDefaultValue();
		nodeDetailRepository.save(detail);
	}

	@Transactional
	public NodeDetail update(NodeDetail detail, Node node) {
		detail.setNode(node);
		detail.applyDefaultValue();
		NodeDetail entity = nodeDetailRepository.save(detail);
		node.setDetail(detail);
		return entity;
	}
}
