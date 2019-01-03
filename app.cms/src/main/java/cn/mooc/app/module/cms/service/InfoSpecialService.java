package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoSpecial;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.data.rds.InfoSpecialRepository;

/**
 * InfoSpecialService 文档专题服务
 * 
 * @author hwt
 * @date 2016-05-16
 */
@Service
public class InfoSpecialService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InfoSpecialRepository infoSpecialRepository;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private SiteService siteService;

	public InfoSpecial getInfoSpecialById(Integer id) {
		InfoSpecial entity = infoSpecialRepository.findOne(id);
		return entity;
	}

	public Page<InfoSpecial> findInfoSpecialList(Map<String, Object> searchParams, PagerParam pageParam, Integer siteId) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<InfoSpecial> spec = SpecDynamic.buildSpec(filters.values());

		return infoSpecialRepository.findAll(spec, pageParam.getPageRequest());

	}
	
	public Page<InfoSpecial> pageSpecialInfoBySort(final String title, PagerParam pageParam, final Integer specialId){
		Specification<InfoSpecial> spec = new Specification<InfoSpecial>() {
			public Predicate toPredicate(Root<InfoSpecial> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(StringUtil.isNotEmpty(title)){
					Path path = root.get("info").get("detail").get("title");
					Predicate pred = cb.like(path, "%"+title+"%");
					pred = cb.and(pred, cb.equal(root.get("special").get("id"), specialId), cb.equal(root.get("info").get("status"), Info.NORMAL));
					return pred;
				}else{
					Predicate pred = cb.and(cb.equal(root.get("special").get("id"), specialId), cb.equal(root.get("info").get("status"), Info.NORMAL));
					return pred;
				}
			}
		};
		return infoSpecialRepository.findAll(spec, pageParam.getPageRequest());
	}

	@Transactional
	public InfoSpecial saveInfoSpecial(InfoSpecial entity) throws Exception {
		// entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.infoSpecialRepository.save(entity);
	}

	@Transactional
	public InfoSpecial updateInfoSpecial(InfoSpecial entity) throws Exception {
		// entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.infoSpecialRepository.save(entity);
	}

	@Transactional
	public boolean delInfoSpecial(Integer id) {
		try {
			this.infoSpecialRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delInfoSpecials(Integer[] ids) {
		try {
			List<InfoSpecial> entities = this.infoSpecialRepository.findAll(Arrays.asList(ids));
			this.infoSpecialRepository.delete(entities);
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	public List<InfoSpecial> findInfoSpecialByInfoId(Integer infoId) {
		return infoSpecialRepository.findInfoSpecialByInfoId(infoId);
	}

	public List<Map<String, Object>> findSpecialList(Integer infoId, Integer siteId) {
		List<Special> specials = specialService.findSpecialList(siteId);
		List<InfoSpecial> infoSpecials = findInfoSpecialByInfoId(infoId);
		List<Integer> selectedIdLs = new ArrayList<Integer>();
		List<Map<String, Object>> specialMaps = new ArrayList<Map<String, Object>>();
		for (InfoSpecial infoSpecial : infoSpecials) {
			selectedIdLs.add(infoSpecial.getSpecial().getId());
		}
		for (Special special : specials) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", special.getId());
			map.put("text", special.getTitle());
			if (selectedIdLs.contains(special.getId())) {
				map.put("selected", true);
			}
			specialMaps.add(map);
		}
		return specialMaps;
	}

	@Transactional
	public List<InfoSpecial> save(Info info, String specialIdsStr) {
		Integer[] specialIds = new Integer[0];
		if (StringUtils.isNotEmpty(specialIdsStr)) {
			String[] specialIdsArr = specialIdsStr.split(",");
			specialIds = new Integer[specialIdsArr.length];
			for (int i = 0; i < specialIdsArr.length; i++) {
				specialIds[i] = Integer.parseInt(specialIdsArr[i]);
			}
		}
		List<InfoSpecial> infoSpecials = info.getInfoSpecials();
		if (infoSpecials == null) {
			infoSpecials = new ArrayList<InfoSpecial>();
			info.setInfoSpecials(infoSpecials);
		}
		if (ArrayUtils.isEmpty(specialIds)) {
			return infoSpecials;
		}
		for (int i = 0, len = specialIds.length; i < len; i++) {
			infoSpecials.add(save(info, specialIds[i], info.getId()));
		}
		return infoSpecials;
	}

	private InfoSpecial save(Info info, Integer specialId, int index) {
		InfoSpecial bean = new InfoSpecial();
		Special special = specialService.refer(specialId);
		bean.setSpecial(special);
		bean.setInfo(info);
		bean.setSpecialIndex(index);
		infoSpecialRepository.save(bean);
		return bean;
	}

	@Transactional
	public List<InfoSpecial> update(Info info, String specialIdsStr) {
		Integer[] specialIds = new Integer[0];
		if (StringUtils.isNotEmpty(specialIdsStr)) {
			String[] specialIdsArr = specialIdsStr.split(",");
			specialIds = new Integer[specialIdsArr.length];
			for (int i = 0; i < specialIdsArr.length; i++) {
				specialIds[i] = Integer.parseInt(specialIdsArr[i]);
			}
		}
		List<InfoSpecial> infoSpecials = info.getInfoSpecials();
		if (specialIds == null || specialIds.length == 0) {
			// 为null不更新，要设置为空，传空数组。
			Set<InfoSpecial> tobeDelete = new HashSet<InfoSpecial>();
			for (InfoSpecial infoSpecial : infoSpecials) {
				Special special = infoSpecial.getSpecial();
				specialService.derefer(special);
				tobeDelete.add(infoSpecial);
			}
			infoSpecialRepository.delete(tobeDelete);
			return info.getInfoSpecials();
		}
		
		// 先删除
		Set<InfoSpecial> tobeDelete = new HashSet<InfoSpecial>();
		for (InfoSpecial infoSpecial : infoSpecials) {
			Special special = infoSpecial.getSpecial();
			if (!ArrayUtils.contains(specialIds, special.getId())) {
				specialService.derefer(special);
				tobeDelete.add(infoSpecial);
			}
		}
		infoSpecials.removeAll(tobeDelete);
		infoSpecialRepository.delete(tobeDelete);
		// 再新增
		for (int i = 0, len = specialIds.length; i < len; i++) {
			boolean contains = false;
			for (InfoSpecial infoSpecial : infoSpecials) {
				if (infoSpecial.getSpecial().getId().equals(specialIds[i])) {
					infoSpecial.setSpecialIndex(i);
					infoSpecials.remove(infoSpecial);
					infoSpecials.add(i, infoSpecial);
					contains = true;
					break;
				}
			}
			if (!contains) {
				// 新增
				infoSpecials.add(i, save(info, specialIds[i], info.getId()));
			}
		}
		return infoSpecials;
	}

	public void updateViewsByInfoId(Integer infoId) {
		List<InfoSpecial> infoSpecials = this.findInfoSpecialByInfoId(infoId);
		if (infoSpecials != null && !infoSpecials.isEmpty()) {
			for (InfoSpecial infoSpecial : infoSpecials) {
				specialService.updateViews(infoSpecial.getSpecial());
			}
		}
	}
	
	@Transactional
	public void updateSort(String str){
		if(StringUtil.isNotEmpty(str)){
			List<InfoSpecial> infoList = new ArrayList<InfoSpecial>();
			String[] args = str.split(",");
			for(String arg : args){
				String[] idcols = arg.split("#");
				Integer id = StringUtil.strnull2Int(idcols[0]);
				Integer index = Integer.parseInt(idcols[1]);
				InfoSpecial info = this.getInfoSpecialById(id);
				info.setSpecialIndex(index);
				infoList.add(info);
			}
			infoSpecialRepository.save(infoList);
		}
	}
	
	@Transactional
	public void setTop(Integer id){
		InfoSpecial infoSpecial = infoSpecialRepository.findOne(id);
		Integer index = infoSpecialRepository.findMaxSeqBySpecial(infoSpecial.getSpecial().getId());
		infoSpecial.setSpecialIndex(index + 1);
		infoSpecialRepository.save(infoSpecial);
	}
	
}
