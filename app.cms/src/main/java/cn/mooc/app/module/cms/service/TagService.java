package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Tag;
import cn.mooc.app.module.cms.data.rds.TagRepository;
import cn.mooc.app.module.cms.orm.RowSide;


/**
 * TagServiceImpl
 * 
 * @author csmooc
 * 
 */
@Service
@Transactional(readOnly = true)
public class TagService{
	public Page<Tag> findAll(Integer siteId, Map<String, Object> searchParams,PagerParam pageParam) {
		if(siteId != null){
			searchParams.put(SpecOperator.EQ+"_site.id", siteId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Tag> spec = SpecDynamic.buildSpec(filters.values());
		
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public RowSide<Tag> findSide(Integer siteId, Map<String, Object> searchParams,
			Tag bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Tag>();
		}
		PagerParam limit = RowSide.limitable(position, sort);
		
		if(siteId != null){
			searchParams.put(SpecOperator.EQ+"_site.id", siteId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Tag> spec = SpecDynamic.buildSpec(filters.values());
		
		Page<Tag> list = dao.findAll(spec, limit.getPageRequest());
		return RowSide.create(list.getContent(), bean);
	}
	public List<Tag> findList(Integer[] siteId, String[] node,
			Integer[] nodeId, Integer refers, Sort sort) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(node)) {
			List<String> list = Arrays.asList(node);
			searchParams.put(SpecOperator.IN + "_infoTags.info.node.number", list);
		}
		if (ArrayUtils.isNotEmpty(siteId)) {
			List<Integer> list = Arrays.asList(siteId);
			searchParams.put(SpecOperator.IN + "_site.id", list);
		}
		if (ArrayUtils.isNotEmpty(nodeId)) {
			List<Integer> list = Arrays.asList(nodeId);
			searchParams.put(SpecOperator.IN + "_infoTags.info.node.id", list);
		}
		if(refers != null){
			searchParams.put(SpecOperator.EQ + "_refers", refers);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Tag> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, sort);
	}
	
	public List<Tag> findList(Integer[] siteId, String[] node,
			Integer[] nodeId, Integer refers, PagerParam pageParam) {
		if(pageParam.getRows() > 0){
			Page<Tag> page = findPage(siteId, node, nodeId, refers, pageParam);
			return page.getContent();
		}else{
			return findList(siteId, node, nodeId, refers, pageParam.getSort());
		}
	}

	public Page<Tag> findPage(Integer[] siteId, String[] node,
			Integer[] nodeId, Integer refers, PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(node)) {
			List<String> list = Arrays.asList(node);
			searchParams.put(SpecOperator.IN + "_infoTags.info.node.number", list);
		}
		if (ArrayUtils.isNotEmpty(siteId)) {
			List<Integer> list = Arrays.asList(siteId);
			searchParams.put(SpecOperator.IN + "_site.id", list);
		}
		if (ArrayUtils.isNotEmpty(nodeId)) {
			List<Integer> list = Arrays.asList(nodeId);
			searchParams.put(SpecOperator.IN + "_infoTags.info.node.id", list);
		}
		if(refers != null){
			searchParams.put(SpecOperator.EQ + "_refers", refers);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Tag> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public List<Tag> findByName(String[] names, Integer[] siteIds) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(names)) {
			List<String> list = Arrays.asList(names);
			searchParams.put(SpecOperator.IN + "_name", list);
		}
		if (ArrayUtils.isNotEmpty(siteIds)) {
			List<Integer> list = Arrays.asList(siteIds);
			searchParams.put(SpecOperator.IN + "_site.id", list);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Tag> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec);
	}

	public Tag findByName(Integer siteId, String name) {
		List<Tag> list = dao.findBySiteIdAndName(siteId, name);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Tag get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public Tag save(Tag bean, Integer siteId) {
		Site site = siteService.getSiteById(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Tag refer(String name, Integer siteId) {
		List<Tag> tags = dao.findBySiteIdAndName(siteId, name);
		Tag tag;
		if (tags.size() > 0) {
			tag = tags.get(0);
			tag.setRefers(tag.getRefers() + 1);
		} else {
			tag = new Tag();
			tag.setName(name);
			tag.setRefers(1);
			save(tag, siteId);
		}
		return tag;
	}

	@Transactional
	public List<Tag> refer(String[] names, Integer siteId) {
		if (ArrayUtils.isEmpty(names)) {
			return Collections.emptyList();
		}
		Set<String> tagSet = new HashSet<String>();
		List<Tag> tags = new ArrayList<Tag>(names.length);
		for (String name : names) {
			if (!tagSet.contains(name)) {
				tags.add(refer(name, siteId));
				tagSet.add(name);
			}
		}
		return tags;
	}

	@Transactional
	public void derefer(Tag tag) {
		tag.setRefers(tag.getRefers() - 1);
	}

	@Transactional
	public void derefer(Collection<Tag> tags) {
		for (Tag tag : tags) {
			derefer(tag);
		}
	}

	@Transactional
	public Tag update(Tag bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Tag delete(Integer id) {
		Tag entity = dao.findOne(id);
		infoTagService.deleteByTagId(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public Tag[] delete(Integer[] ids) {
		Tag[] beans = new Tag[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	private InfoTagService infoTagService;
	private SiteService siteService;

	@Autowired
	public void setInfoTagService(InfoTagService infoTagService) {
		this.infoTagService = infoTagService;
	}

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	private TagRepository dao;

	@Autowired
	public void setDao(TagRepository dao) {
		this.dao = dao;
	}
}
