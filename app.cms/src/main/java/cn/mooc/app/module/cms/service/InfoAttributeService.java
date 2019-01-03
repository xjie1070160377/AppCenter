package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoAttribute;
import cn.mooc.app.module.cms.data.rds.InfoAttributeRepository;
import cn.mooc.app.module.cms.model.InfoModel;

/**
 * InfoAttributeService 文章明细服务
 * 
 * @author hwt
 * @date 2016-05-19
 */
@Service
public class InfoAttributeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InfoAttributeRepository infoAttributeRepository;
	@Autowired
	private AttributeService attributeService;

	public InfoAttribute getInfoAttributeById(Integer id) {
		InfoAttribute entity = infoAttributeRepository.findOne(id);
		return entity;
	}

	public List<InfoAttribute> getAllInfoAttributes() {
		return this.infoAttributeRepository.findAll();
	}

	public Page<InfoAttribute> findInfoAttributePage(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<InfoAttribute> spec = SpecDynamic.buildSpec(filters.values());

		return infoAttributeRepository.findAll(spec, pageParam.getPageRequest());

	}
	
	public Page<InfoAttribute> pageAttrinfoBySort(final String title, PagerParam pageParam, final Integer attrId){
		Specification<InfoAttribute> spec = new Specification<InfoAttribute>() {
			public Predicate toPredicate(Root<InfoAttribute> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(StringUtil.isNotEmpty(title)){
					Path path = root.get("info").get("detail").get("title");
					Predicate pred = cb.like(path, "%"+title+"%");
					pred = cb.and(pred, cb.equal(root.get("attribute").get("id"), attrId), cb.equal(root.get("info").get("status"), Info.NORMAL));
					
					return pred;
				}else{
					Predicate pred = cb.and(cb.equal(root.get("attribute").get("id"), attrId), cb.equal(root.get("info").get("status"), Info.NORMAL));
					return pred;
				}
			}
		};
		return infoAttributeRepository.findAll(spec, pageParam.getPageRequest());
	}
	
	@Transactional
	public void updateSort(String str){
		if(StringUtil.isNotEmpty(str)){
			List<InfoAttribute> infoList = new ArrayList<InfoAttribute>();
			String[] args = str.split(",");
			for(String arg : args){
				String[] idcols = arg.split("#");
				Integer id = StringUtil.strnull2Int(idcols[0]);
				Integer seq = Integer.parseInt(idcols[1]);
				InfoAttribute info = this.getInfoAttributeById(id);
				info.setSeq(seq);
				infoList.add(info);
			}
			infoAttributeRepository.save(infoList);
		}
	}
	
	@Transactional
	public void setTop(Integer id){
		InfoAttribute attr = infoAttributeRepository.findOne(id);
		Integer seq = infoAttributeRepository.findMinSeqByAttr(attr.getAttribute().getId());
		attr.setSeq(seq-1);
		infoAttributeRepository.save(attr);
	}

	@Transactional
	public InfoAttribute saveInfoAttribute(InfoAttribute entity) throws Exception {
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.infoAttributeRepository.save(entity);
	}

	@Transactional
	public InfoAttribute updateInfoAttribute(InfoAttribute entity) throws Exception {
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.infoAttributeRepository.save(entity);
	}

	@Transactional
	public boolean delInfoAttribute(Integer id) {
		try {
			this.infoAttributeRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delInfoAttributes(Integer[] ids) {
		try {
			List<InfoAttribute> entities = this.infoAttributeRepository.findAll(Arrays.asList(ids));
			this.infoAttributeRepository.delete(entities);
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Transactional
	public List<InfoAttribute> save(Info info, Integer[] attrIds, Map<String, String> attrImages) {
		List<InfoAttribute> infoAttrs = info.getInfoAttrs();
		if (infoAttrs == null) {
			infoAttrs = new ArrayList<InfoAttribute>();
			info.setInfoAttrs(infoAttrs);
		}
		if (ArrayUtils.isEmpty(attrIds)) {
			return infoAttrs;
		}
		String image;
		for (Integer attrId : attrIds) {
			image = attrImages.get(attrId.toString());
			infoAttrs.add(save(info, attrId, image));
		}
		return infoAttrs;
	}

	@Transactional
	public InfoAttribute[] batchUpdate(Integer[] id) {
		InfoAttribute[] beans = new InfoAttribute[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			beans[i] = infoAttributeRepository.findOne(id[i]);
			beans[i].setSeq(i);
			beans[i] = infoAttributeRepository.save(beans[i]);
		}
		return beans;
	}

	

	
	@Transactional
	private InfoAttribute save(Info info, Integer attrId, String image) {
		Attribute attr = attributeService.getAttributeById(attrId);
		InfoAttribute bean = new InfoAttribute(info, attr, image);
		InfoAttribute b = infoAttributeRepository.findByInfoId(info.getId(), attrId);
		if (b == null) {
			bean.setSeq(0);
		}
		infoAttributeRepository.save(bean);
		this.updateSeqByInfo(info.getId());
		return bean;
	}

	@Transactional
	public List<InfoAttribute> update(Info info, Integer[] attrIds, Map<String, String> attrImages) {
		if (attrIds == null) {
			// 为null不更新。要设置为空，传空数组。
			return info.getInfoAttrs();
		}
		List<InfoAttribute> infoAttrs = info.getInfoAttrs();
		// 先删除
		Set<InfoAttribute> tobeDelete = new HashSet<InfoAttribute>();
		for (InfoAttribute infoAttr : infoAttrs) {
			if (!ArrayUtils.contains(attrIds, infoAttr.getAttribute().getId())) {
				tobeDelete.add(infoAttr);
			}
		}
		infoAttrs.removeAll(tobeDelete);
		infoAttributeRepository.delete(tobeDelete);
		// 再更新、新增
		for (Integer attrId : attrIds) {
			boolean contains = false;
			String image;
			for (InfoAttribute infoAttr : infoAttrs) {
				if (infoAttr.getAttribute().getId().equals(attrId)) {
					// 更新
					image = attrImages.get(attrId.toString());
					infoAttr.setImage(image);
					contains = true;
					break;
				}
			}
			if (!contains) {
				// 新增
				image = attrImages.get(attrId.toString());
				infoAttrs.add(save(info, attrId, image));
			}
		}
		return infoAttrs;
	}
	
	@Transactional
	public void updateSeqByInfo(Integer infoid){
		List<InfoAttribute> list = infoAttributeRepository.findByInfoId(infoid);
		List<InfoAttribute> nlist = new ArrayList<InfoAttribute>();
		if(list != null && list.size() > 0){
			for(InfoAttribute attr : list){
				if(attr.getSeq() == null || attr.getSeq().intValue()==0){
					Integer seq = infoAttributeRepository.findMinSeqByAttr(attr.getAttribute().getId());
					attr.setSeq(seq-1);
					nlist.add(attr);
				}
			}
			infoAttributeRepository.save(nlist);
		}
	}
	
	@Transactional
	public void updateSeqByInfo(Integer infoid,boolean flag){
		List<InfoAttribute> list = infoAttributeRepository.findByInfoId(infoid);
		List<InfoAttribute> nlist = new ArrayList<InfoAttribute>();
		if(list != null && list.size() > 0){
			for(InfoAttribute attr : list){
				if(flag ||attr.getSeq() == null || attr.getSeq().intValue()==0){
					Integer seq = infoAttributeRepository.findMinSeqByAttr(attr.getAttribute().getId());
					attr.setSeq(seq-1);
					nlist.add(attr);
				}
			}
			infoAttributeRepository.save(nlist);
		}
	}
	
}
