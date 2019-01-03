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
import cn.mooc.app.module.cms.data.entity.NodeBuffer;
import cn.mooc.app.module.cms.data.rds.NodeBufferRepository;

/**
 * NodeBufferService 栏目缓存服务
 * 
 * @author hwt
 * @date 2016-05-16
 */
@Service
public class NodeBufferService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NodeBufferRepository nodeBufferRepository;

	public NodeBuffer getNodeBufferById(Integer id) {
		NodeBuffer entity = nodeBufferRepository.findOne(id);
		return entity;
	}

	public List<NodeBuffer> getAllNodeBuffers() {
		return this.nodeBufferRepository.findAll();
	}

	public Page<NodeBuffer> findNodeBufferList(Map<String, Object> searchParams, PagerParam pageParam) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<NodeBuffer> spec = SpecDynamic.buildSpec(filters.values());

		return nodeBufferRepository.findAll(spec, pageParam.getPageRequest());

	}

	@Transactional
	public NodeBuffer saveNodeBuffer(NodeBuffer entity) throws Exception {
		entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.nodeBufferRepository.save(entity);
	}

	@Transactional
	public NodeBuffer updateNodeBuffer(NodeBuffer entity) throws Exception {
		entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.nodeBufferRepository.save(entity);
	}

	@Transactional
	public boolean delNodeBuffer(Integer id) {
		try {
			this.nodeBufferRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delNodeBuffers(Integer[] ids) {
		try {
			List<NodeBuffer> entities = this.nodeBufferRepository.findAll(Arrays.asList(ids));
			this.nodeBufferRepository.delete(entities);
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Transactional
	public NodeBuffer save(NodeBuffer bean, Node node) {
		bean.setNode(node);
		bean.applyDefaultValue();
		bean = nodeBufferRepository.save(bean);
		node.setBuffer(bean);
		return bean;
	}
}
