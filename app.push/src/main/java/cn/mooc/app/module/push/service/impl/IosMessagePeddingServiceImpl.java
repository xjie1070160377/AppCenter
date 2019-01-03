package cn.mooc.app.module.push.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.push.data.entity.IosMessagePedding;
import cn.mooc.app.module.push.data.rds.IosMessagePeddingDao;
import cn.mooc.app.module.push.service.IosMessagePeddingService;

/**
 * IosMessagePeddingServiceImpl
 * Ios正在处理的消息实现类
 * 
 * @author zjj
 * @date 2016-03-29
 */
@Service
@Transactional(readOnly = true)
public class IosMessagePeddingServiceImpl implements IosMessagePeddingService {

	@Resource
	private IosMessagePeddingDao dao;

	
	public List<IosMessagePedding> findByCount(int count){
		return dao.findByCount(count);
	}


	public List<IosMessagePedding> findList() {
		return dao.findAll();
	}

	public IosMessagePedding get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public IosMessagePedding save(IosMessagePedding bean) {
		return dao.save(bean);
	}

	@Transactional
	public IosMessagePedding update(IosMessagePedding bean) {
		return dao.save(bean);
	}

	@Transactional
	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.delete(id);
			}
		}
	}
}