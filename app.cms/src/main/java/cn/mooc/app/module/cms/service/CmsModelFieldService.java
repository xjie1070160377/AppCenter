package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.CmsModelField;
import cn.mooc.app.module.cms.data.rds.CmsModelFieldRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CmsModelFieldService 模型字段管理服务
 * 
 * @author hwt
 * @date 2016-06-14
 */
@Service
public class CmsModelFieldService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CmsModelFieldRepository modelFieldRepository;
	@Autowired
	private CmsModelService modelService;

	public CmsModelField getCmsModelFieldById(Integer id) {
		CmsModelField entity = modelFieldRepository.findOne(id);
		return entity;
	}

	public List<CmsModelField> getAllCmsModelFields() {
		return this.modelFieldRepository.findAll();
	}

	public List<CmsModelField> findList(Integer modelId) {
		return this.modelFieldRepository.findList(modelId);
	}

	public Page<CmsModelField> findCmsModelFieldPage(Map<String, Object> searchParams, PagerParam pageParam) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsModelField> spec = SpecDynamic.buildSpec(filters.values());

		return modelFieldRepository.findAll(spec, pageParam.getPageRequest());
	}

	@Transactional
	public CmsModelField saveCmsModelField(CmsModelField entity) throws Exception {
		entity.applyDefaultValue();
		return this.modelFieldRepository.save(entity);
	}

	@Transactional
	public CmsModelField updateCmsModelField(CmsModelField entity) throws Exception {
		entity.applyDefaultValue();
		return this.modelFieldRepository.save(entity);
	}

	@Transactional
	public CmsModelField[] disable(Integer[] ids) {
		CmsModelField[] beans = new CmsModelField[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = modelFieldRepository.findOne(ids[i]);
			beans[i].setDisabled(true);
		}
		return beans;
	}

	@Transactional
	public CmsModelField[] enable(Integer[] ids) {
		CmsModelField[] beans = new CmsModelField[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = modelFieldRepository.findOne(ids[i]);
			beans[i].setDisabled(false);
		}
		return beans;
	}

	@Transactional
	public CmsModelField[] delCmsModelField(Integer[] ids) {
		CmsModelField[] beans = new CmsModelField[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = modelFieldRepository.findOne(ids[i]);
			beans[i].getModel().getFields().remove(beans[i]);
			modelFieldRepository.delete(beans[i]);
		}
		return beans;
	}

	@Transactional
	public CmsModelField copy(CmsModelField bean, CmsModel model) {
		CmsModelField target = new CmsModelField();
		BeanUtils.copyProperties(bean, target, new String[] { "id", "customs", "model" });
		target.setCustoms(new HashMap<String, String>(bean.getCustoms()));
		target.setModel(model);
		modelFieldRepository.save(target);
		return target;
	}

	@Transactional
	public void clearByModelId(Integer id) {
		List<CmsModelField> modelFields = findList(id);
		for(CmsModelField modelField : modelFields) {
			modelFieldRepository.delete(modelField);
		}
	}

	@Transactional
	public CmsModelField save(CmsModelField bean, Integer modelId, Map<String, String> customs, Boolean clob) throws Exception {
		CmsModel model = modelService.getCmsModelById(modelId);
		bean.setModel(model);
		bean.setCustoms(customs);
		if (clob != null && clob) {
			bean.setInnerType(CmsModelField.FIELD_CUSTOM_CLOB);
		}
		bean.applyDefaultValue();
		bean = modelFieldRepository.save(bean);
		model.addField(bean);
		return bean;
	}

	@Transactional
	public CmsModelField update(CmsModelField bean, Map<String, String> customs, Boolean clob) throws Exception {
		if (customs != null) {
			bean.getCustoms().clear();
			bean.getCustoms().putAll(customs);
		}
		// 只有自定义字段才可以设置是否为大字段
		if (bean.isCustom() && clob != null) {
			bean.setInnerType(clob ? CmsModelField.FIELD_CUSTOM_CLOB : CmsModelField.FIELD_CUSTOM);
		}
		bean.applyDefaultValue();
		bean = modelFieldRepository.save(bean);
		return bean;
	}

	@Transactional
	public CmsModelField[] batchUpdate(Integer[] id, String[] name, String[] label, Boolean[] dblColumn) throws Exception {
		CmsModelField[] beans = new CmsModelField[id.length];
		CmsModel model = null;
		for (int i = 0, len = id.length; i < len; i++) {
			if(StringUtils.isEmpty(name[i])){
				throw new SaveFailureException("字段代码不能为空！");
			}
			if(StringUtils.isEmpty(label[i])){
				throw new SaveFailureException("字段名称不能为空！");
			}
			beans[i] = getCmsModelFieldById(id[i]);
			if (model == null) {
				model = beans[i].getModel();
				// 清空模型字段，防止应二级缓存导致脏数据。
				model.getFields().clear();
			}
			model.addField(beans[i]);
			// 只有自定义字段才允许改名称，系统字段不允许修改名称。
			if (beans[i].getInnerType() == CmsModelField.FIELD_CUSTOM) {
				beans[i].setName(name[i]);
			}
			beans[i].setLabel(label[i]);
			beans[i].setSeq(i);
			beans[i].setDblColumn(dblColumn[i]);
			update(beans[i], null, null);
		}
		return beans;
	}

	@Transactional
	public CmsModelField[] batchSave(Integer modelId, String[] name, String[] label, Boolean[] dblColumn, String[] property, String[] custom) throws Exception {
		CmsModelField[] beans = new CmsModelField[name.length];
		Map<String, String> customs;
		for (int i = 0, len = name.length; i < len; i++) {
			beans[i] = new CmsModelField();
			JsonUtil.update(property[i], beans[i]);
			beans[i].setName(name[i]);
			beans[i].setLabel(label[i]);
			beans[i].setDblColumn(dblColumn[i]);
			customs = new HashMap<String, String>();
			JsonUtil.update(custom[i], customs);
			save(beans[i], modelId, customs, null);
		}
		return beans;
	}
}
