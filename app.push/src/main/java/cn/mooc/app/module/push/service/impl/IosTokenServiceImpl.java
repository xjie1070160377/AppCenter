package cn.mooc.app.module.push.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.module.push.data.entity.IosToken;
import cn.mooc.app.module.push.data.rds.IosTokenDao;
import cn.mooc.app.module.push.service.IosTokenService;

/**
 * IosTokenServiceImpl
 * IOS设备token实现类
 * 
 * @author zjj
 * @date 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class IosTokenServiceImpl implements IosTokenService {

	@Resource
	private IosTokenDao dao;
	@Autowired
	private CacheService cacheService;

	public List<IosToken> findList() {
		return dao.findAll();
	}
	
	public List<IosToken> findIosList(){
		return dao.findIosToken(true);
	}

	public IosToken get(Long id) {
		return dao.findOne(id);
	}
	
	@Transactional
	public IosToken save(IosToken bean) {
		IosToken b = dao.findOne(bean.getId());
		if(b == null){
			return dao.save(bean);
		}else{
			b.setToken(bean.getToken());
			b.setIsios(bean.getIsios());
			dao.save(b);
			return b;
		}
	}
	
	public List<IosToken> findByToken(String token){
		return dao.findByToken(token);
	}

	@Transactional
	public IosToken update(IosToken bean) {
		return dao.save(bean);
	}

	@Transactional
	public void delete(Long... ids) {
		if (ids != null) {
			for (Long id : ids) {
				dao.delete(id);
			}
		}
	}
	
	public IosToken getTokenByUserCode(String userCode){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IosToken> query = cb.createQuery(IosToken.class);
		Root<IosToken> root = query.from(IosToken.class);
		
		Subquery<SysUserEntity> subquery = query.subquery(SysUserEntity.class);
		Root<SysUserEntity> subr = subquery.from(SysUserEntity.class);
		subquery.where(cb.and(cb.equal(subr.get("userName"),userCode),cb.equal(root.get("id"), subr.get("id")))).select(subr);
		
		query.where(cb.exists(subquery)).select(root).distinct(false);
		TypedQuery<IosToken> typedQuery = em.createQuery(query); 
		return typedQuery.getSingleResult();
	}
	
	@PersistenceContext
	private EntityManager em;
	
}