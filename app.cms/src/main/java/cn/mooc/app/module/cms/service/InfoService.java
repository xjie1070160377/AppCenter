package cn.mooc.app.module.cms.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.CmsModelField;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoBuffer;
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.entity.InfoImage;
import cn.mooc.app.module.cms.data.entity.InfoProcess;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.NodeRole;
import cn.mooc.app.module.cms.data.entity.PicDic;
import cn.mooc.app.module.cms.data.entity.PublishPoint;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowLog;
import cn.mooc.app.module.cms.data.entity.WorkflowProcess;
import cn.mooc.app.module.cms.data.nosql.PicDicRepostory;
import cn.mooc.app.module.cms.data.rds.InfoRepository;
import cn.mooc.app.module.cms.fulltext.InfoFulltextGenerator;
import cn.mooc.app.module.cms.model.InfoFindListPageParams;
import cn.mooc.app.module.cms.model.InfoModel;
import cn.mooc.app.module.cms.support.FileHandler;
import cn.mooc.app.module.cms.util.UserTypeUtil;

/**
 * InfoService 文档服务
 * 
 * @author hwt
 * @date 2016-05-16
 */
@Service
public class InfoService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// @PersistenceContext
	// private EntityManager em;
	@Autowired
	private InfoRepository infoRepository;
	@Autowired
	protected InfoAttributeService infoAttributeService;
	@Autowired
	protected InfoBufferService infoBufferService;
	@Autowired
	protected InfoDetailService infoDetailService;
	@Autowired
	protected InfoNodeService infoNodeService;
	@Autowired
	protected InfoSpecialService infoSpecialService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private AttachmentRefService attachmentRefService;
	@Autowired
	protected PathResolver pathResolver;
	@Autowired
	private UploadHandlerService uploadHandler;
	@Autowired
	private InfoEventService infoEventService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private WebContext context;
	@Autowired
	private WorkflowStepService flowStepService;
	@Autowired
	private WorkflowLogService logService;
	@Autowired
	private WorkflowProcessService processService;
	@Autowired
	private InfoTagService infoTagService;
	@Autowired
	private InfoFulltextGenerator fulltextGenerator;
	@Autowired
	private PicDicRepostory picDicRepostory;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private WebContext webContext;
	
	public Info getInfoById(Integer id) {
		Info entity = infoRepository.findOne(id);
		return entity;
	}
	
	public Info findOne(Map<String, Object> searchParams){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
		return infoRepository.findOne(spec);
	}

	public List<Info> getAllInfos(Integer siteId) {
		return this.infoRepository.findAll(siteId);
	}

	public List<Info> findAll(Iterable<Integer> ids) {
		return infoRepository.findAll(ids);
	}
	
	public long countNodeInfo(Integer nodeId, Integer p1){
		return infoRepository.countNodeInfo(nodeId, p1);
	}
	
	public List<Info> findInfoByNode(Integer nodeId, Integer p1){
		return infoRepository.findInfoByNode(nodeId, p1);
	}
	
	public Page<InfoModel> findInfoModelPage(Map<String, Object> searchParams, PagerParam pageParam, Integer queryNodeId, String treeNumber, String queryStatus, Integer siteId, long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<InfoModel> modeLs = new ArrayList<InfoModel>();
		Page<Info> pageData = findInfoPage1(searchParams, pageParam, queryNodeId, treeNumber, queryStatus, siteId, userId);

		List<Info> pageList = pageData.getContent();

		for (Info info : pageList) {
			InfoModel model = new InfoModel();
			model.setId(info.getId());
			model.setNodeName(info.getNode().getDisplayName());
			model.setCreatorId(info.getCreatorId());
			
			SysUserEntity sysUserEntity = context.getSysUser(info.getCreatorId());
			if(sysUserEntity!=null){
				model.setCreator(sysUserEntity.getUserName());
			}else{
				model.setCreator("");
			}			
			
			model.setTitle(info.getTitle());
			model.setModeName(info.getModel().getName());
			model.setPublishDate(sdf.format(info.getPublishDate()));
			model.setPriority(info.getPriority());
			model.setViews(info.getViews());
			model.setInfoLevel((info.getP6()!=null && info.getP6().intValue()>1)?info.getP6():1);
			
			InfoDetail detail = infoDetailService.getInfoDetailById(info.getId());
			if(Info.AUDITING.equals(queryStatus)){
				model.setStatus(detail.getStepName());
			}else{
				model.setStatus(info.getStatus());
			}
			modeLs.add(model);
		}
		PageRequest pageRequest = new PageRequest(pageParam.getPageStart(), pageParam.getRows(), null);
		return new PageImpl<InfoModel>(modeLs, pageRequest, pageData.getTotalElements());
	}
	public Page<Info> findInfoPage(Map<String, Object> searchParams, PagerParam pageParam, Integer queryNodeId, String treeNumber, String queryStatus, Integer siteId, long userId) {
		if (StringUtils.isNotEmpty(treeNumber)) {
			searchParams.put(SpecOperator.BW + "_node.treeNumber", treeNumber);
		} else {
			searchParams.put(SpecOperator.EQ + "_node.id", queryNodeId);
		}
		searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
		spec = specAuditInfo(spec, queryStatus, userId);
		

		return infoRepository.findAll(spec, pageParam.getPageRequest());
	}

	
	public Page<Info> findInfoPage1(Map<String, Object> searchParams,PagerParam pageParam, Integer queryNodeId, String treeNumber, String queryStatus, Integer siteId, long userId) {
		Map<String, Object> searchOrParams = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(treeNumber)) {
			searchOrParams.put(SpecOperator.BW + "_node.treeNumber", treeNumber);
			searchOrParams.put(SpecOperator.EQ + "_p1", queryNodeId);
		} else {
			searchOrParams.put(SpecOperator.EQ + "_node.id", queryNodeId);
			searchOrParams.put(SpecOperator.EQ + "_p1", queryNodeId);
			logger.debug("infoService:"+queryNodeId);
		}
		searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Map<String, SpecExpression> orfilters = SpecExpression.parse(searchOrParams);
		
		Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
		if(searchOrParams.values().size()>0) {
			Specification<Info> orspec = SpecDynamic.buildSpec(orfilters.values(),false);
			spec = specOrInfo(orspec,spec);
		}
		spec = specAuditInfo(spec, queryStatus, userId);
		

		return infoRepository.findAll(spec, pageParam.getPageRequest());
	}
	private Specification<Info> specOrInfo(final Specification<Info> orfsp,final Specification<Info> fsp){
		Specification<Info> spec = new Specification<Info>() {
			public Predicate toPredicate(Root<Info> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				Predicate orpred = orfsp.toPredicate(root, query, cb);
				orpred = cb.and(orpred, pred);
				return orpred;
			}
		};
		return spec;
	}
	
	private Specification<Info> specAuditInfo(final Specification<Info> fsp, final String queryStatus, final long userId) {
		Specification<Info> spec = new Specification<Info>() {
			public Predicate toPredicate(Root<Info> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				SysUserEntity user = sysDataService.getUserInfoById(userId);
				if (StringUtils.isNotEmpty(queryStatus)) {
					if(Info.STATUS_ALL.equals(queryStatus)) {
						pred = cb.and(pred, cb.notEqual(root.get("status"), Info.DELETED));
					} else {
						if (queryStatus.length() == 1 && !queryStatus.equals(Info.REJECTION)) {
							pred = cb.and(pred, cb.equal(root.get("status"), queryStatus));
						} else if (queryStatus.equals("pending") || queryStatus.equals("notpassed")) {
							pred = cb.and(pred, cb.equal(root.get("status"), Info.AUDITING));
							
							boolean rejection = "notpassed".equals(queryStatus);
							Subquery<Integer> sq = query.subquery(Integer.class);
							Root<WorkflowProcess> root2 = sq.from(WorkflowProcess.class);
							sq.select(root2.<Integer> get("dataId"));
							
							java.util.Set<SysRoleEntity> roles = user.getRoles();
							Long[] ids = new Long[roles.size()];
							Iterator<SysRoleEntity> it = roles.iterator();
							int i = 0;
							while(it.hasNext()){
								ids[i] = it.next().getId();
								i++;
							}
							
							sq.where(cb.equal(root2.get("rejection"), rejection), cb.equal(root2.get("dataType"), 1), cb.equal(root2.get("end"), false),
									cb.and(root2.join("step").join("stepRoles").get("roleId").in(ids)));
							pred = cb.and(pred, cb.in(root.<Integer> get("id")).value(sq));
						}else if(queryStatus.equals(Info.REJECTION)){
//							pred = cb.or(pred, cb.and(cb.equal(root.get("status"), Info.AUDITING), cb.equal(root.get("status"), Info.AUDITING)));
							Subquery<Integer> sq = query.subquery(Integer.class);
							Root<WorkflowProcess> root2 = sq.from(WorkflowProcess.class);
							sq.select(root2.<Integer> get("dataId"));
							sq.where(cb.equal(root2.get("rejection"), true), cb.equal(root2.get("dataType"), 1));
							pred = cb.and(pred, cb.in(root.<Integer> get("id")).value(sq));
						}
					}
				}
				//加入条件，文章权限校验
				Boolean isSuper = false;
				isSuper = user.isSuperUser();
				if (user != null && !isSuper) {
					Root<SysRoleEntity> root1 = query.from(SysRoleEntity.class);
					Join<Node, NodeRole> nodeRole = root.join("node").join("nodeRoles");
					Join<SysRoleEntity, SysUserEntity> users = root1.join("users");
					pred = cb.and(pred, cb.equal(nodeRole.get("roleId"), root1.get("id")));
					pred = cb.and(pred, cb.equal(nodeRole.get("infoPerm"), true));
					pred = cb.and(pred, cb.equal(users.get("id"), userId));
					query.distinct(true);
				}
				return pred;
			}
		};
		return spec;
	}
	
	@Transactional
	public Info saveInfo(Info bean, InfoDetail detail, String specialIds, Map<String, String> customs, Map<String, String> clobs, List<InfoImage> images, Integer[] attrIds,
			Map<String, String> attrImages, String[] tagNames, Integer nodeId, Long creatorId, String status, Integer siteId) throws Exception {
		Site site = siteService.getSiteById(siteId);
		bean.applyDefaultValue();
		bean.setSite(site);
		bean.setCreatorId(creatorId);
		Node node = nodeService.getNodeById(nodeId);
		bean.setNode(node);
		
		bean.setClobs(clobs);
		if (images != null) {
			bean.setImages(images);
		}
		
		bean.setText(collectImage(bean.getText(), bean.getSite()));
//		try {
//			extractImage(bean.getSite(), creatorId, node, detail, images, clobs, attrIds, attrImages);
//		} catch (Exception e) {
//			logger.error("extract image error!", e);
//		}

		if (StringUtils.isNotBlank(detail.getSmallImage())) {
			bean.setWithImage(true);
		}
		if (StringUtils.isNotBlank(detail.getVideo())) {
			bean.setWithVideo(true);
		}
		if(StringUtils.isNotBlank(detail.getAudio())){
			bean.setWithAudio(true);
		}
		Workflow workflow = null;
		if (Info.DRAFT.equals(status) || Info.CONTRIBUTION.equals(status) || Info.COLLECTED.equals(status) || Info.NORMAL.equals(status)) {
			// 草稿、投稿、采集
			bean.setStatus(status);
		} else {
			workflow = node.getWorkflow();
			if (workflow != null) {
				bean.setStatus(Info.AUDITING);
			} else {
				bean.setStatus(Info.NORMAL);
			}
		}
		if (Info.NORMAL.equals(bean.getStatus())) {
			node = nodeService.refer(nodeId);
		}

		bean.applyDefaultValue();
		bean.adjustStatus();

		bean = infoRepository.save(bean);
		if (customs != null) {
			bean.setInfoCustoms(customs);
		}
		infoDetailService.save(detail, bean);
		// 将InfoBuffer对象一并保存，以免在网页浏览时再保存，导致并发保存报错
		infoBufferService.save(new InfoBuffer(), bean);
		infoAttributeService.save(bean, attrIds, attrImages);
		infoNodeService.save(bean, null, nodeId);
		infoTagService.save(bean, tagNames);
		infoSpecialService.save(bean, specialIds);
		attachmentRefService.update(bean.getAttachUrls(), Info.ATTACH_TYPE, bean.getId(), site.getGlobal());

		if (workflow != null && !Info.NORMAL.equals(bean.getStatus())) {
			String stepName = workflowService.pass(workflow, creatorId, creatorId, new InfoProcess(), Info.WORKFLOW_TYPE, bean.getId(), null, false);
			if (StringUtils.isNotBlank(stepName)) {
				// 审核中
				bean.setStatus(Info.AUDITING);
				detail.setStepName(stepName);
			} else if ("".equals(stepName)) {
				// 终审通过
				bean.setStatus(Info.NORMAL);
				detail.setStepName(null);
			}
		}
//		if (Info.NORMAL.equals(bean.getStatus())) {
//			bean.setColumnSort(dao.getSortNum(nodeId, 1, 0));
//		}
//		
		if(Info.NORMAL.equals(bean.getStatus())){
			updatePostInfo(bean);
			SysUserInfo userInfo = context.getSysUserFullInfo(creatorId);
			Map<String, String> customs1 = new HashMap();
			customs1.put("auditUserId", creatorId+"");
			customs1.put("auditUserName", userInfo.getNickName());
			bean.setCustoms(customs1);
		}
		//触发事件
		infoEventService.submitInfoSaveEvent(bean);
		fulltextGenerator.addDocument(new Info[] { bean });
		return bean;
	}
	
//	public void saveDangshi(Info bean, InfoDetail detail, String specialIds, Map<String, String> customs, Map<String, String> clobs, List<InfoImage> images, Integer[] attrIds,
//			Map<String, String> attrImages, String[] tagNames, Integer nodeId, Long creatorId, String status, Integer siteId) throws Exception {
//		Site site = siteService.getSiteById(siteId);
//		bean.applyDefaultValue();
//		bean.setSite(site);
//		bean.setCreatorId(creatorId);
//		Node node = nodeService.getNodeById(52);//信息港
//		bean.setNode(node);
//		
//		if (images != null) {
//			bean.setImages(images);//标题图
//		}
//		
//		bean.setText(collectImage(bean.getText(), bean.getSite()));//设置正文并处理图片
//
//
//		Workflow workflow = null;
//		if (Info.DRAFT.equals(status) || Info.CONTRIBUTION.equals(status) || Info.COLLECTED.equals(status) || Info.NORMAL.equals(status)) {
//			// 草稿、投稿、采集
//			bean.setStatus(status);
//		} else {
//			workflow = node.getWorkflow();
//			if (workflow != null) {
//				bean.setStatus(Info.AUDITING);
//			} else {
//				bean.setStatus(Info.NORMAL);
//			}
//		}
//		if (Info.NORMAL.equals(bean.getStatus())) {
//			node = nodeService.refer(nodeId);
//		}
//
//		bean.applyDefaultValue();
//		bean.adjustStatus();
//
//		bean = infoRepository.save(bean);
//		if (customs != null) {
//			bean.setInfoCustoms(customs);
//		}
//		infoDetailService.save(detail, bean);
//		// 将InfoBuffer对象一并保存，以免在网页浏览时再保存，导致并发保存报错
//		infoBufferService.save(new InfoBuffer(), bean);
//		infoAttributeService.save(bean, attrIds, attrImages);
//		infoNodeService.save(bean, null, nodeId);
//		infoTagService.save(bean, tagNames);
//		infoSpecialService.save(bean, specialIds);
//		attachmentRefService.update(bean.getAttachUrls(), Info.ATTACH_TYPE, bean.getId(), site.getGlobal());
//
//		if (workflow != null && !Info.NORMAL.equals(bean.getStatus())) {
//			String stepName = workflowService.pass(workflow, creatorId, creatorId, new InfoProcess(), Info.WORKFLOW_TYPE, bean.getId(), null, false);
//			if (StringUtils.isNotBlank(stepName)) {
//				// 审核中
//				bean.setStatus(Info.AUDITING);
//				detail.setStepName(stepName);
//			} else if ("".equals(stepName)) {
//				// 终审通过
//				bean.setStatus(Info.NORMAL);
//				detail.setStepName(null);
//			}
//		}
////		if (Info.NORMAL.equals(bean.getStatus())) {
////			bean.setColumnSort(dao.getSortNum(nodeId, 1, 0));
////		}
////		
//		if(Info.NORMAL.equals(bean.getStatus())){
//			updatePostInfo(bean);
//			SysUserInfo userInfo = context.getSysUserFullInfo(creatorId);
//			Map<String, String> customs1 = new HashMap();
//			customs1.put("auditUserId", creatorId+"");
//			customs1.put("auditUserName", userInfo.getNickName());
//			bean.setCustoms(customs1);
//		}
//		//触发事件
//		infoEventService.submitInfoSaveEvent(bean);
//		fulltextGenerator.addDocument(new Info[] { bean });
//		return bean;
//	}
	
	
	
	@Transactional
	public void moveNode(Integer[] infoIds, Integer nodeId){
		if(ArrayUtils.isNotEmpty(infoIds) && StringUtil.isNotEmpty(nodeId)){
			Node node = nodeService.getNodeById(nodeId);
			List<Info> infos = new ArrayList<Info>();
			for(Integer infoId : infoIds){
				Info bean = infoRepository.findOne(infoId);
				if(!nodeId.equals(bean.getNode().getId())){
					if(Info.NORMAL.equals(bean.getStatus())){
						node = nodeService.refer(nodeId);
						nodeService.derefer(bean.getNode());
						bean.setNode(node);
						infos.add(bean);
					}else{
						bean.setNode(node);
						infos.add(bean);
					}
				}
			}
			infoRepository.save(infos);
		}
	}

	@Transactional
	public Info updateInfo(Info bean, InfoDetail detail, String specialIds, Map<String, String> customs, Map<String, String> clobs, List<InfoImage> images, Integer[] attrIds,
			Map<String, String> attrImages, String[] tagNames, Integer nodeId, Long userId, boolean pass, boolean recall) throws Exception {
		if (detail == null) {
			// 允许更新时，不传入detail。
			detail = infoDetailService.getInfoDetailById(bean.getId());
		}
		Info old = this.getInfoById(bean.getId());
		Integer oldnodeid = old.getNode().getId();
		String oldstatus = old.getStatus();
		
		if (nodeId != null) {
			Node node = null;
			if(pass && !Info.NORMAL.equals(bean.getStatus())){
				node = nodeService.refer(nodeId);
				if(old.getNode().getId().intValue()!=node.getId().intValue() && Info.NORMAL.equals(old.getStatus())){
					nodeService.derefer(old.getNode());
				}
			} else {
				node = nodeService.getNodeById(nodeId);
			}
			bean.setNode(node);
		}
		
		bean.getClobs().clear();
		if (!CollectionUtils.isEmpty(clobs)) {
			bean.getClobs().putAll(clobs);
		}
		
		//自动下载外部图片并替换
		bean.setText(collectImage(bean.getText(), bean.getSite()));
		Site site = bean.getSite();
//		try {
//			extractImage(site, userId, bean.getNode(), detail, images, clobs, attrIds, attrImages);
//		} catch (Exception e) {
//			logger.error("extract image error!", e);
//		}
		if (StringUtils.isNotBlank(detail.getSmallImage())) {
			bean.setWithImage(true);
		}
		if (StringUtils.isNotBlank(detail.getVideo())) {
			bean.setWithVideo(true);
		}
		if(StringUtils.isNotBlank(detail.getAudio())){
			bean.setWithAudio(true);
		}
		// 更新并审核
		if (pass) {
			String status = bean.getStatus();
//			// 审核中、草稿、投稿、采集、退稿可审核。
			if (Info.AUDITING.equals(status) || Info.DRAFT.equals(status) || Info.CONTRIBUTION.equals(status) || Info.COLLECTED.equals(status)
					|| Info.REJECTION.equals(status)) {
				Workflow workflow = bean.getNode().getWorkflow();
				String stepName = workflowService.pass(workflow, bean.getCreatorId(), userId, new InfoProcess(), Info.WORKFLOW_TYPE, bean.getId(), null,
						!Info.AUDITING.equals(status));
				if (StringUtils.isNotBlank(stepName)) {
					// 审核中
					bean.setStatus(Info.AUDITING);
					detail.setStepName(stepName);
				} else if ("".equals(stepName)) {
					// 终审通过
					bean.setStatus(Info.NORMAL);
					detail.setStepName(null);
				}
			}else{
				bean.setStatus(Info.NORMAL);
				detail.setStepName(null);
			}
//			
		}
		if(Info.DELETED.equals(bean.getStatus()) && recall) {
			bean.setStatus(Info.NORMAL);
		}
		bean.applyDefaultValue();
		bean.adjustStatus();
		bean = infoRepository.save(bean);

		//bean.getInfoCustoms().clear();
		if (!CollectionUtils.isEmpty(customs)) {
			bean.updateInfoCustoms(customs);
		}
		
		//
		
		bean.getImages().clear();
		if (!CollectionUtils.isEmpty(images)) {
			bean.getImages().addAll(images);
		}

		infoDetailService.update(detail, bean);
		infoAttributeService.update(bean, attrIds, attrImages);
		infoNodeService.update(bean, null, nodeId);
		infoTagService.update(bean, tagNames);
		infoSpecialService.update(bean, specialIds);
		

		attachmentRefService.update(bean.getAttachUrls(), Info.ATTACH_TYPE, bean.getId(), site.getGlobal());
		
		//触发事件
		infoEventService.submitInfoUpdateEvent(bean);
		if((!Info.NORMAL.equals(oldstatus) && Info.NORMAL.equals(bean.getStatus())) || (Info.NORMAL.equals(bean.getStatus()) && !oldnodeid.equals(bean.getNode().getId()))){
			updatePostInfo(bean);
		}
		fulltextGenerator.updateDocument(new Info[] { bean });
		return bean;
	}

	private void extractImage(Site site, Integer userId, Node node, InfoDetail detail, List<InfoImage> images, Map<String, String> clobs, Integer[] attrIds, Map<String, String> attrImages)
			throws IOException {
		PublishPoint point = site.getUploadsPublishPoint();
		String urlPrefix = point.getUrlPrefix();
		String srcImage;
		// 内容图
		srcImage = detail.getLargeImage();
		if (!StringUtils.startsWith(srcImage, urlPrefix) && images != null) {
			// 图集
			for (InfoImage infoImage : images) {
				srcImage = infoImage.getImage();
				if (StringUtils.startsWith(srcImage, urlPrefix)) {
					break;
				} else {
					srcImage = null;
				}
			}
		}
		if (!StringUtils.startsWith(srcImage, urlPrefix) && clobs != null) {
			// 正文图
			for (String textImage : Info.getTextImages(clobs)) {
				if (StringUtils.startsWith(textImage, urlPrefix)) {
					srcImage = textImage;
					break;
				}
			}
		}
		if (!StringUtils.startsWith(srcImage, urlPrefix)) {
			return;
		}
		srcImage = srcImage.substring(urlPrefix.length());

		FileHandler fileHandler = point.getFileHandler(pathResolver);
		String formatName = fileHandler.getFormatName(srcImage);
		if (StringUtils.isBlank(formatName)) {
			return;
		}

		BufferedImage srcBuff = fileHandler.readImage(srcImage);
		String extension = FilenameUtils.getExtension(srcImage).toLowerCase();

		boolean scale, exact;
		String imageWidth, imageHeight;
		Integer width, height;

		String targetImage;
		CmsModel model = node.getInfoModel();
		if (StringUtils.isBlank(detail.getSmallImage())) {
			CmsModelField field = model.getField("smallImage");
			if (field != null) {
				Map<String, String> customs = field.getCustoms();
				scale = !"false".equals(customs.get("imageScale"));
				exact = "true".equals(customs.get("imageExact"));
				imageWidth = customs.get("imageWidth");
				imageHeight = customs.get("imageHeight");
				if (StringUtils.isNotBlank(imageWidth)) {
					width = Integer.parseInt(imageWidth);
				} else {
					width = null;
				}
				if (StringUtils.isNotBlank(imageHeight)) {
					height = Integer.parseInt(imageHeight);
				} else {
					height = null;
				}
				// 复制图片，压缩，得到新图片地址。
				targetImage = uploadHandler.copyImage(srcBuff, extension, formatName, site, scale, exact, width, height, null, null, null, null, null, userId, site.getId());
				// detail.setSmallImage(targetImage);
			}
		}
		if (ArrayUtils.isNotEmpty(attrIds)) {
			Attribute attr;
			Integer attrId;
			String attrImage;
			for (Integer i = 0, len = attrIds.length; i < len; i++) {
				attrId = attrIds[i];
				attrImage = attrImages.get(attrId.toString());
				attr = attributeService.getAttributeById(attrId);
				if (attr.getWithImage() && StringUtils.isBlank(attrImage)) {
					scale = true;
					width = attr.getImageWidth();
					height = attr.getImageHeight();
					exact = true;
					targetImage = uploadHandler.copyImage(srcBuff, extension, formatName, site, scale, exact, width, height, null, null, null, null, null, userId, site.getId());
					attrImages.put(attrId.toString(), targetImage);
				}
			}
		}
	}
	@Transactional
	public List<Info> pass(Integer[] ids, Long userId, String opinion) {
		Info info;
		InfoDetail detail;
		Workflow workflow;
//		User owner;
//		User operator = userService.get(userId);
		List<Info> infos = new ArrayList<Info>();
		List<Info> alist = new ArrayList<Info>();
		for (Integer id : ids) {
			info = getInfoById(id);
			detail = info.getDetail();
			String status = info.getStatus();

			if(!Info.NORMAL.equals(status)){
				nodeService.refer(info.getNode().getId());
			}
			// 审核中、草稿、投稿、采集、退稿可审核。
			if (Info.AUDITING.equals(status) || Info.DRAFT.equals(status) || Info.CONTRIBUTION.equals(status) 
					|| Info.COLLECTED.equals(status) || Info.REJECTION.equals(status)) {
				workflow = info.getNode().getWorkflow();
//				owner = info.getCreator();
				String stepName = workflowService.pass(workflow, info.getCreatorId(), userId, new InfoProcess(), Info.WORKFLOW_TYPE, info.getId(), null,
						!Info.AUDITING.equals(status));
				if (StringUtils.isNotBlank(stepName)) {
					// 审核中
					info.setStatus(Info.AUDITING);
					detail.setStepName(stepName);
				} else if ("".equals(stepName)) {
					// 终审通过
					info.setStatus(Info.NORMAL);
					detail.setStepName(null);
					info.adjustStatus();
				}
			}else if(Info.TOBE_PUBLISH.equals(status)){
				info.setStatus(Info.NORMAL);
			}
			
//			if (Info.NORMAL.equals(info.getStatus())) {
//				info.setColumnSort(dao.getSortNum(info.getNode().getId(), 1, 0));
//			}
			infoDetailService.update(detail, info);
			if(Info.NORMAL.equals(info.getStatus())){
				alist.add(info);
				SysUserInfo userInfo = context.getSysUserFullInfo(userId);
				Map<String, String> customs1 = new HashMap();
				customs1.put("auditUserId", userId+"");
				customs1.put("auditUserName", userInfo.getNickName());
				info.setCustoms(customs1);
			}
			infos.add(info);
		}
		if(alist != null && alist.size() > 0){
			updatePostInfo((Info[]) alist.toArray(new Info[alist.size()]));
		}
//		updateHtml(infos);
		
		
//		firePostPass(infos);
		fulltextGenerator.updateDocument(infos.toArray(new Info[infos.size()]));
		return infos;
	}
	
	@Transactional
	public Info toEnd(Integer infoId, Long userId) throws Exception{
		
		
		if(flowStepService.istoend(infoId, userId)){
			infoAttributeService.updateSeqByInfo(infoId,true);
			Info info = getInfoById(infoId);
			InfoDetail detail = info.getDetail();
			String status = info.getStatus();
			if(!Info.NORMAL.equals(status)){
				nodeService.refer(info.getNode().getId());
			}
			if (Info.AUDITING.equals(status) || Info.DRAFT.equals(status) || Info.CONTRIBUTION.equals(status) 
					|| Info.COLLECTED.equals(status) || Info.REJECTION.equals(status)) {
				Workflow workflow = info.getNode().getWorkflow();
				workflowService.toEnd(workflow,  info.getCreatorId(), userId, new InfoProcess(), Info.WORKFLOW_TYPE, info.getId());
				info.setStatus(Info.NORMAL);
				detail.setStepName("");
			}else{
				info.setStatus(Info.NORMAL);
			}
			SysUserInfo userInfo = context.getSysUserFullInfo(userId);
			Map<String, String> customs = new HashMap();
			customs.put("auditUserId", userId+"");
			customs.put("auditUserName", userInfo.getNickName());
			info.updateInfoCustoms(customs);
			
			infoDetailService.update(detail, info);
			updatePostInfo(info);
			fulltextGenerator.updateDocument(new Info[] { info });
			return info;
		}else{
			throw new Exception("当前用户没有终审的审核权限！");
		}
	}
	
	@Transactional
	public List<Info> reject(Integer[] ids, Long userId, String opinion, boolean rejectEnd, Long opUserId, String opUserName, String ip, String reqUrl) {
		Info info;
		InfoDetail detail;
		Workflow workflow;
//		User owner;
//		User operator = userService.get(userId);
		List<Info> infos = new ArrayList<Info>();
		for (Integer id : ids) {
			info = getInfoById(id);
			detail = info.getDetail();
			String status = info.getStatus();
			if(Info.NORMAL.equals(status)){
				nodeService.derefer(info.getNode());
			}
			if (Info.CONTRIBUTION.equals(status)) {
				// 投稿退回。不需要经过工作流。
				info.setStatus(Info.REJECTION);
			} else if (Info.AUDITING.equals(status) || Info.NORMAL.equals(status) || Info.TOBE_PUBLISH.equals(status) || Info.EXPIRED.equals(status)) {
				// 审核中、已发布、待发布、已过期可审核退回。
				workflow = info.getNode().getWorkflow();
				WorkflowProcess process = processService.findOne(Info.WORKFLOW_TYPE, info.getId());
				if(process != null){
					WorkflowLog log = logService.findLastLog(process.getId());
				}
				String stepName = workflowService.reject(workflow, info.getCreatorId(), userId, new InfoProcess(), Info.WORKFLOW_TYPE, info.getId(), opinion,
						rejectEnd);
					info.setStatus(Info.AUDITING);
					detail.setStepName(stepName);
//				}
				info.adjustStatus();
			}
			infoDetailService.update(detail, info);
			fulltextGenerator.updateDocument(new Info[] { info });
			WorkflowProcess process = processService.findOne(Info.WORKFLOW_TYPE, info.getId());
			if(process != null){
				infoEventService.submitInfoRejctEvent(info, opinion, userId, process.getStep(), opUserId, opUserName, ip, reqUrl);
			}
			infos.add(info);
		}
//		updateHtml(infos);
//		firePostReject(infos);
		return infos;
	}
	@Transactional
	public boolean delInfo(Integer id) {
		try {
			Info bean = infoRepository.findOne(id);
			this.infoRepository.delete(id);
			fulltextGenerator.deleteDocuments(new Info[] { bean });
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delInfos(Integer[] ids) {
		try {
			List<Info> entities = this.infoRepository.findAll(Arrays.asList(ids));
			this.infoRepository.delete(entities);
			fulltextGenerator.deleteDocuments(entities.toArray(new Info[entities.size()]));
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Transactional
	public boolean cancelInfo(Integer id) {
		try {
			Info bean = getInfoById(id);
			bean.setStatus(Info.DELETED);
			this.infoRepository.save(bean);
			fulltextGenerator.updateDocument(new Info[] { bean });
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int cancelInfos(Integer[] ids) {
		try {
			List<Info> entities = this.infoRepository.findAll(Arrays.asList(ids));
			for(Info bean: entities) {
				bean.setStatus(Info.DELETED);
				this.infoRepository.save(bean);
			}
			fulltextGenerator.updateDocument(entities.toArray(new Info[entities.size()]) );
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@Transactional
	public int passInfos(Integer[] ids, long userId) throws Exception {
		//直接发布，检查用户是否有操作权限
		int count = 0;
		for (Integer infoId : ids) {
			try {
				this.toEnd(infoId, userId);
				count++;
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw e;
			}
		}
		
		return count;
	}
	
	@Transactional
	public int passInfos(Integer[] ids) throws Exception {
		//批量发布文章，不检查用户权限
		try {
			List<Info> entities = this.infoRepository.findAll(Arrays.asList(ids));
			for(Info bean: entities) {
				bean.setStatus(Info.NORMAL);
				this.infoRepository.save(bean);
			}
			fulltextGenerator.updateDocument(entities.toArray(new Info[entities.size()]) );
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	public List<Info> findList(InfoFindListPageParams findParams, Sort sort) {
		Specification<Info> spec = buildSpec(findParams);
		return infoRepository.findAll(spec, sort);
	}
	
	public List<Info> findList(InfoFindListPageParams findParams, PagerParam pageParam) {
		if(pageParam.getRows() > 0){
			Page<Info> page = findPage(findParams, pageParam);
			return page.getContent();
		}else{
			return findList(findParams, pageParam.getSort());
		}
	}
	
	public List<Info> findList(InfoFindListPageParams findParams, PagerParam pageParam,  Integer[] arrtid, Integer[] witharrtid, Integer[] p1) {
		if(pageParam.getRows() > 0){
			Page<Info> page = findPage(findParams, pageParam, arrtid, witharrtid, p1);
			return page.getContent();
		}else{
			Specification<Info> spec = buildSpec(findParams);
			spec = spec(spec, arrtid, witharrtid, p1);
			return infoRepository.findAll(spec, pageParam.getSort());
		}
	}
	
	public Page<Info> findPage(InfoFindListPageParams findParams, PagerParam pageParam, Integer[] arrtid, Integer[] witharrtid, Integer[] p1) {
		Specification<Info> spec = buildSpec(findParams);
		spec = spec(spec, arrtid, witharrtid, p1);
		return infoRepository.findAll(spec, pageParam.getPageRequest());
	}
	
	private Specification<Info> spec(Specification<Info> spec, Integer[] arrtid, Integer[] witharrtid, Integer[] p1){
		return new Specification<Info>() {
			@Override
			public Predicate toPredicate(Root<Info> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = spec.toPredicate(root, query, cb);
				Path<List<Integer>> path = root.join("infoAttrs").get("attribute").get("id");
				List<Integer> attrIdList = Arrays.asList(arrtid);
				List<Integer> wattrIdList = Arrays.asList(witharrtid);
				pred = cb.and(pred, cb.or(cb.and(cb.in(path).value(attrIdList), root.get("p1").in(p1)), cb.in(path).value(wattrIdList)));
				return pred;
			}
		};
	}
	
	
	public Page<Info> findPage(InfoFindListPageParams findParams, PagerParam pageParam) {
		Specification<Info> spec = buildSpec(findParams);
		return infoRepository.findAll(spec, pageParam.getPageRequest());
	}
	
	public Page<Info> findPageByUserPermission (InfoFindListPageParams findParams, PagerParam pageParam) {
		Long userId = webContext.getCurrentUserId();
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		if(user!=null && UserTypeUtil.outUser(user.getuType())){//校外用户
			findParams.setP6(new Integer[]{2,3});
		}
		
		Specification<Info> spec = buildSpec(findParams);
		return infoRepository.findAll(spec, pageParam.getPageRequest());
	}
	
	public List<Info> findListByUserPermission(InfoFindListPageParams findParams, PagerParam pageParam) {
		if(pageParam.getRows() > 0){
			Page<Info> page = findPageByUserPermission(findParams, pageParam);
			return page.getContent();
		}else{
			Long userId = webContext.getCurrentUserId();
			SysUserEntity user = sysDataService.getUserInfoById(userId);
			if(user!=null && UserTypeUtil.outUser(user.getuType())){//校外用户
				findParams.setP6(new Integer[]{2,3});
			}
			Specification<Info> spec = buildSpec(findParams);
			return infoRepository.findAll(spec, pageParam.getSort());
		}
	}
	
	

	private Specification<Info> buildSpec(InfoFindListPageParams findParams){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, Object> searchOrParams = new HashMap<String, Object>();
		
		if (ArrayUtils.isNotEmpty(findParams.getModelId())) {
			List<Integer> list = Arrays.asList(findParams.getModelId());
			searchParams.put(SpecOperator.IN + "_node.infoModel.id", list);
		}
		if(findParams.isShowDescendants() && StringUtil.isNotEmpty(findParams.getNodeNumber())){
			searchOrParams.put(SpecOperator.LIKE + "_node.treeNumber", findParams.getNodeNumber());
			if (ArrayUtils.isNotEmpty(findParams.getNodeId())) {
				List<Integer> list = Arrays.asList(findParams.getNodeId());
				searchOrParams.put(SpecOperator.IN + "_p1", list);
			}
		}else{
			if (ArrayUtils.isNotEmpty(findParams.getNodeId())) {
				List<Integer> list = Arrays.asList(findParams.getNodeId());
				searchOrParams.put(SpecOperator.IN + "_node.id", list);
				searchOrParams.put(SpecOperator.IN + "_p1", list);
			}
		}
		if (ArrayUtils.isNotEmpty(findParams.getSiteId())) {
			List<Integer> list = Arrays.asList(findParams.getSiteId());
			searchParams.put(SpecOperator.IN + "_site.id", list);
		}
		
		if (ArrayUtils.isNotEmpty(findParams.getMainNodeId())) {
			List<Integer> list = Arrays.asList(findParams.getMainNodeId());
			searchParams.put(SpecOperator.IN + "_node.id", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getExcludeMainNodeId())) {
			List<Integer> list = Arrays.asList(findParams.getExcludeMainNodeId());
			searchParams.put(SpecOperator.NI + "_node.id", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getUserId())) {
			List<Long> list = Arrays.asList(findParams.getUserId());
			searchParams.put(SpecOperator.IN + "_creatorId", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getPriority())) {
			List<Integer> list = Arrays.asList(findParams.getPriority());
			searchParams.put(SpecOperator.IN + "_priority", list);
		}
		if (findParams.getBeginDate() != null) {
			searchParams.put(SpecOperator.GE + "_publishDate", findParams.getBeginDate());
		}
		if (findParams.getEndDate() != null) {
			searchParams.put(SpecOperator.NE + "_publishDate", findParams.getEndDate());
		}
		if (ArrayUtils.isNotEmpty(findParams.getIncludeId())) {
			List<Integer> list = Arrays.asList(findParams.getIncludeId());
			searchParams.put(SpecOperator.IN + "_id", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getExcludeId())) {
			List<Integer> list = Arrays.asList(findParams.getExcludeId());
			searchParams.put(SpecOperator.NI + "_id", list);
		}
		if (findParams.getIsWithImage() != null) {
			searchParams.put(SpecOperator.EQ + "_withImage", findParams.getIsWithImage());
		}
		if (findParams.getIsWithVideo() != null) {
			searchParams.put(SpecOperator.EQ + "_withVideo", findParams.getIsWithVideo());
		}
		if(findParams.getIsAudio() != null){
			searchParams.put(SpecOperator.EQ + "_withAudio", findParams.getIsAudio());
		}
		if (ArrayUtils.isNotEmpty(findParams.getStatus())) {
			List<String> list = Arrays.asList(findParams.getStatus());
			searchParams.put(SpecOperator.IN + "_status", list);
		}
//		if (ArrayUtils.isNotEmpty(findParams.getP1())) {//字段已经被征用
//			List<Integer> list = Arrays.asList(findParams.getP1());
//			searchParams.put(SpecOperator.IN + "_p1", list);
//		}
		if (ArrayUtils.isNotEmpty(findParams.getP2())) {
			List<Integer> list = Arrays.asList(findParams.getP2());
			searchParams.put(SpecOperator.IN + "_p2", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP3())) {
			List<Integer> list = Arrays.asList(findParams.getP3());
			searchParams.put(SpecOperator.IN + "_p3", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP4())) {
			List<Integer> list = Arrays.asList(findParams.getP4());
			searchParams.put(SpecOperator.IN + "_p4", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP5())) {
			List<Integer> list = Arrays.asList(findParams.getP5());
			searchParams.put(SpecOperator.IN + "_p5", list);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP6())) {
			List<Integer> list = Arrays.asList(findParams.getP6());
			searchParams.put(SpecOperator.IN + "_p6", list);
		}
//		if(findParams.getCustomer() != null){
//				searchParams.put(SpecOperator.EQ + "_customs", findParams.getCustomer());
//		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
		Map<String, SpecExpression> orfilters = SpecExpression.parse(searchOrParams);
	
		if(searchOrParams.values().size()>0) {
			Specification<Info> orspec = SpecDynamic.buildSpec(orfilters.values(),false);
			spec = specOrInfo(orspec,spec);
		}
		
		spec = spec(spec, findParams.getAttrId(), findParams.getSpecialId(), findParams.getSpecialTitle(), findParams.getTitle(), findParams.getTreeNumber(), findParams.getExcludeTreeNumber(), findParams.getCustomer());
		return spec;
	}
	
	private Specification<Info> spec(final Specification<Info> fsp, final Integer[] attrId, final Integer[] specialId, final String[] specialTitle, final String[] title, final String[] treeNumber,
			final String[] excludeTreeNumber, final Map<String, String> customer) {
		Specification<Info> spec = new Specification<Info>() {
			public Predicate toPredicate(Root<Info> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);

				boolean isDistinct = false;
//				
				if(customer != null){
					Set<String> keyset = customer.keySet();
					Iterator<String> it = keyset.iterator();
					while(it.hasNext()){
						String key = it.next();
						Path p = root.join("infoCustoms");
						pred = cb.and(pred, cb.equal(p.get("key"), key), cb.equal(p.get("value"), customer.get(key)));
					}
					
				}
				
				if (ArrayUtils.isNotEmpty(attrId)) {
					Path<List<Integer>> path = root.join("infoAttrs").get("attribute").get("id");
					List<Integer> attrIdList = Arrays.asList(attrId);
					pred = cb.and(pred, cb.in(path).value(attrIdList));
					isDistinct = true;
				}
				boolean isSpecialId = ArrayUtils.isNotEmpty(specialId);
				boolean isSpecialTitle = ArrayUtils.isNotEmpty(specialTitle);
				if (isSpecialId || isSpecialTitle) {
					if (isSpecialId) {
						Path<List<Integer>> path = root.join("infoSpecials").get("special").get("id");
						List<Integer> specialIdList = Arrays.asList(specialId);
						pred = cb.and(pred, cb.in(path).value(specialIdList));
						isDistinct = true;
					}
					if (isSpecialTitle) {
						Path path = root.join("infoSpecials").get("special").get("title");
						for (int i = 0, len = specialTitle.length; i < len; i++) {
							pred = cb.or(pred, cb.like(path, "%" + specialTitle[i] + "%"));
						}
						isDistinct = true;
					}
				}
				if (ArrayUtils.isNotEmpty(title)) {
					// 实现 and (title like '' or title like '')
					Predicate predDetail = null;
					Path path = root.get("detail").get("title");
					for (int i = 0, len = title.length; i < len; i++) {
						if (predDetail == null) {
							predDetail = cb.like(path, "%" + title[i] + "%");
						} else {
							predDetail = cb.or(predDetail, cb.like(path, "%" + title[i] + "%"));
						}
					}
					pred = cb.and(pred, predDetail);
				}
				boolean isTreeNumber = ArrayUtils.isNotEmpty(treeNumber);
				boolean isExcludeTreeNumber = ArrayUtils.isNotEmpty(excludeTreeNumber);
				if (isTreeNumber || isExcludeTreeNumber) {
					Path path = root.get("node").get("treeNumber");
					if (isTreeNumber) {
						Predicate predNode = null;
						for (int i = 0, len = treeNumber.length; i < len; i++) {
							if (predNode == null) {
								predNode = cb.like(path, treeNumber[i] + "%");
							} else {
								predNode = cb.or(predNode, cb.like(path, "%" + treeNumber[i] + "%"));
							}
						}
						pred = cb.and(pred, predNode);
					}
					if (isExcludeTreeNumber) {
						Predicate predNode = null;
						for (int i = 0, len = excludeTreeNumber.length; i < len; i++) {
							if (predNode == null) {
								predNode = cb.like(path, excludeTreeNumber[i] + "%").not();
							} else {
								predNode = cb.or(predNode, cb.like(path, "%" + excludeTreeNumber[i] + "%")).not();
							}
						}
						pred = cb.and(pred, predNode);
					}
				}
				if (isDistinct) {
					query.distinct(true);
				}
				return pred;
			}
		};
		return spec;
	}
	
	public Info findPrevForXk(Integer id, boolean bySite) {
		Info info = getInfoById(id);
		if (info != null) {
			Integer siteId = bySite ? info.getSite().getId() : null;
			Integer nodeId = bySite ? null : info.getNode().getId();
			
			Map<String, Object> searchParams = new HashMap<String, Object>();
			if (nodeId != null) {
				searchParams.put(SpecOperator.EQ + "_node.id", nodeId);
			} else if (siteId != null) {
				searchParams.put(SpecOperator.EQ + "_site.id", siteId);
			}
			searchParams.put(SpecOperator.EQ + "_p1", info.getP1());
			
			searchParams.put(SpecOperator.LTE + "_publishDate", info.getPublishDate());
			searchParams.put(SpecOperator.EQ + "_status", Info.NORMAL);
			
			PagerParam pageParam = new PagerParam(1, 1, new String[]{"publishDate", "id"}, Direction.DESC.toString());
			
			Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
			Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
			spec = specPrev(spec, id);
			Page<Info> page = infoRepository.findAll(spec, pageParam.getPageRequest());
			List<Info> list = page.getContent();
			if(list != null && !list.isEmpty()){
				Info prevInfo = list.get(0);
				return prevInfo;
			}
			return null;
		} else {
			return null;
		}
	}

	public Info findPrev(Integer id, boolean bySite) {
		Info info = getInfoById(id);
		if (info != null) {
			Integer siteId = bySite ? info.getSite().getId() : null;
			Integer nodeId = bySite ? null : info.getNode().getId();
			
			Map<String, Object> searchParams = new HashMap<String, Object>();
			if (nodeId != null) {
				searchParams.put(SpecOperator.EQ + "_node.id", nodeId);
			} else if (siteId != null) {
				searchParams.put(SpecOperator.EQ + "_site.id", siteId);
			}
			searchParams.put(SpecOperator.LTE + "_publishDate", info.getPublishDate());
			searchParams.put(SpecOperator.EQ + "_status", Info.NORMAL);
			
			PagerParam pageParam = new PagerParam(1, 1, new String[]{"publishDate", "id"}, Direction.DESC.toString());
			
			Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
			Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
			spec = specPrev(spec, id);
			Page<Info> page = infoRepository.findAll(spec, pageParam.getPageRequest());
			List<Info> list = page.getContent();
			if(list != null && !list.isEmpty()){
				Info prevInfo = list.get(0);
				return prevInfo;
			}
			return null;
		} else {
			return null;
		}
	}
	private Specification<Info> specPrev(final Specification<Info> fsp, final Integer id) {
		Specification<Info> spec = new Specification<Info>() {
			public Predicate toPredicate(Root<Info> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				if (id != null) {
					// 实现 and (title like '' or title like '')
					Predicate predDetail = null;
					Path path = root.get("id");
					predDetail = cb.lt(path, id);
					predDetail = cb.or(predDetail, cb.gt(path, id));
					
					pred = cb.and(pred, predDetail);
				}
				
				return pred;
			}
		};
		return spec;
	}

	public Info findNextForXk(Integer id, boolean bySite) {
		Info info = getInfoById(id);
		if (info != null) {
			Integer siteId = bySite ? info.getSite().getId() : null;
			Integer nodeId = bySite ? null : info.getNode().getId();
			
			Map<String, Object> searchParams = new HashMap<String, Object>();
			if (nodeId != null) {
				searchParams.put(SpecOperator.EQ + "_node.id", nodeId);
			} else if (siteId != null) {
				searchParams.put(SpecOperator.EQ + "_site.id", siteId);
			}
			searchParams.put(SpecOperator.EQ + "_p1", info.getP1());
			
			searchParams.put(SpecOperator.GTE + "_publishDate", info.getPublishDate());
			searchParams.put(SpecOperator.EQ + "_status", Info.NORMAL);
			
			PagerParam pageParam = new PagerParam(1, 1, new String[]{"publishDate", "id"}, Direction.ASC.toString());
			
			Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
			Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
			spec = specPrev(spec, id);
			Page<Info> page = infoRepository.findAll(spec, pageParam.getPageRequest());
			List<Info> list = page.getContent();
			if(list != null && !list.isEmpty()){
				Info prevInfo = list.get(0);
				return prevInfo;
			}
			return null;
		} else {
			return null;
		}
	}
	
	public Info findNext(Integer id, boolean bySite) {
		Info info = getInfoById(id);
		if (info != null) {
			Integer siteId = bySite ? info.getSite().getId() : null;
			Integer nodeId = bySite ? null : info.getNode().getId();
			
			Map<String, Object> searchParams = new HashMap<String, Object>();
			if (nodeId != null) {
				searchParams.put(SpecOperator.EQ + "_node.id", nodeId);
			} else if (siteId != null) {
				searchParams.put(SpecOperator.EQ + "_site.id", siteId);
			}
			
			searchParams.put(SpecOperator.GTE + "_publishDate", info.getPublishDate());
			searchParams.put(SpecOperator.EQ + "_status", Info.NORMAL);
			
			PagerParam pageParam = new PagerParam(1, 1, new String[]{"publishDate", "id"}, Direction.ASC.toString());
			
			Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
			Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
			spec = specPrev(spec, id);
			Page<Info> page = infoRepository.findAll(spec, pageParam.getPageRequest());
			List<Info> list = page.getContent();
			if(list != null && !list.isEmpty()){
				Info prevInfo = list.get(0);
				return prevInfo;
			}
			return null;
		} else {
			return null;
		}
	}

	public void addComments(Integer id) {
		Info info = getInfoById(id);
		if (info != null) {
			info.addComments(1);
			infoRepository.save(info);
		}
	}
	
	public void delComments(Integer id){
		Info info = getInfoById(id);
		if (info != null) {
			info.delComments(1);
			infoRepository.save(info);
		}
	}

	public void addDiggs(Integer id, int digg) {
		Info info = getInfoById(id);
		if (info != null) {
			int diggs = info.getDiggs();
			info.setDiggs(diggs + digg);
			infoRepository.save(info);
		}
	}
	

	public Page<Info> findPageAuditInfo(Long userId, Integer infoIds, PagerParam pagerParam){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
		spec = specAuditInfo(spec, userId, infoIds);
		//判断用户是否拥有角色,没有角色就不需要再执行
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		java.util.Set<SysRoleEntity> roles = user.getRoles();
		if(roles.size() == 0){
			return new PageImpl<Info>(null);
		}
		
		Page<Info> page = infoRepository.findAll(spec, pagerParam.getPageRequest());
		return page;
	}
	private Specification<Info> specAuditInfo(final Specification<Info> fsp, final Long userId, final Integer infoIds) {
		Specification<Info> spec = new Specification<Info>() {
			public Predicate toPredicate(Root<Info> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);
				Predicate pred = fsp.toPredicate(root, query, cb);

				if(infoIds != null)
					pred = cb.and(pred, cb.equal(root.get("id"), infoIds));
				
				pred = cb.and(pred, cb.equal(root.get("status"), Info.AUDITING));
				Join stepsjoin = root.join("node").join("workflow").join("steps");
				Join processjoin = stepsjoin.join("processes");
				pred = cb.and(pred, cb.equal(processjoin.get("dataType"), Info.WORKFLOW_TYPE), cb.equal(processjoin.get("dataId"), root.get("id")));
				
				if(infoIds != null)
				pred = cb.and(pred, cb.equal(processjoin.get("dataId"), infoIds));
				
				Join steprolejoin = stepsjoin.join("stepRoles");
				
				SysUserEntity user = sysDataService.getUserInfoById(userId);
				java.util.Set<SysRoleEntity> roles = user.getRoles();
				Long[] ids = new Long[roles.size()];
				
				Iterator<SysRoleEntity> it = roles.iterator();
				int i = 0;
				while(it.hasNext()){
					ids[i] = it.next().getId();
					i++;
				}
				pred = cb.and(pred, steprolejoin.get("roleId").in(ids));

				return pred;
			}
		};
		return spec;
	}



	public long countAuditInfoByUser(Long userId) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
		//判断用户是否拥有角色,没有角色就不需要再执行
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		java.util.Set<SysRoleEntity> roles = user.getRoles();
		if(roles.size() == 0){
			return 0;
		}
		
		spec = specAuditInfo(spec, userId, null);
		return infoRepository.count(spec);
	}
	
	@Transactional
	public void updateSort(String str){
		if(StringUtil.isNotEmpty(str)){
			List<Info> infoList = new ArrayList<Info>();
			String[] args = str.split(",");
			for(String arg : args){
				String[] idcols = arg.split("#");
				Integer id = StringUtil.strnull2Int(idcols[0]);
				Double colsort = Double.parseDouble(idcols[1]);
				Info info = this.getInfoById(id);
				info.setColumnSort(colsort);
				infoList.add(info);
			}
			infoRepository.save(infoList);
		}
	}
	
	@Transactional
	public void colTop(Integer id){
		Info info = infoRepository.findOne(id);
		Double seq = infoRepository.getMinSortnum(info.getNode().getId());
		if(seq == null){
			seq = 1D;
		}
		info.setColumnSort(seq-1);
		infoRepository.save(info);
	}
	
	private Specification<Info> specPrev(final Specification<Info> fsp, final Integer nodeId, final Integer siteId) {
		Specification<Info> spec = new Specification<Info>() {
			public Predicate toPredicate(Root<Info> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				pred = cb.and(pred, cb.equal(root.get("site").get("id"), siteId), cb.equal(root.get("node").get("id"), nodeId), cb.equal(root.get("status"), Info.NORMAL));
				return pred;
			}
		};
		return spec;
	}
	
	public Page<Info> findColumnSortInfoPage(String title, PagerParam pagerParam, Integer nodeId, int siteId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("LIKE_detail.title", title);
		Map<String, SpecExpression> filters = SpecExpression.parse(params);
		Specification<Info> spec = SpecDynamic.buildSpec(filters.values());
		spec = specPrev(spec, nodeId, siteId);
		pagerParam.setSort(new Sort(Direction.ASC, "columnSort"));
		return  infoRepository.findAll(spec, pagerParam.getPageRequest());
//		String s = "";
//		if(!StringUtils.isEmpty(title)){
//			s += " where x.title like '%"+title+"%'";
//		}
//		String sql = "select CONCAT(rownum,'') as rownum,info_id as id,title,publishdate,priority,status,views,column_sort "
//				+ "from (select @rownum/*'*/:=/*'*/@rownum+1 as rownum,info_id,title,publishdate,priority,status,views,column_sort "
//				+ "from (SELECT a.info_id,b.title,DATE_FORMAT(a.publish_date, '%Y-%m-%d %H:%i') as publishdate,priority,status,views,column_sort "
//				+ "from t_cms_info a,t_cms_info_detail b where a.site_id="+siteId+" and a.info_id=b.info_id and a.status='"+Info.NORMAL+"' and a.node_id="+nodeId
//				+ " order by a.column_sort asc) t,"
//				+ "(select @rownum/*'*/:=/*'*/0) c) x "+s;
//		
//		Query query = entityManager.createNativeQuery(sql);
//		if (pagerParam != null) {
//			int startPosition = pagerParam.getPageStart() * pagerParam.getRows();
//			int maxResult = pagerParam.getPage() * pagerParam.getRows();
//			query.setFirstResult(startPosition);
//			query.setMaxResults(maxResult);
//		}
//		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//		List list = query.getResultList();
//		Page page = new PageImpl<>(list);
//		return page;
	}
	
	public Page<Info> findAuditPage(Long userId, int start, int size){
		String sql = "select a.* from t_cms_info a,t_cms_node b,t_cms_workflow_process c,t_cms_workflow_step f,t_cms_workflowstep_role d,t_sys_user_role e "+
				"where a.`status`='1' "+
				"and a.node_id=b.node_id and b.workflow_id=c.workflow_id and c.data_type=1 and a.info_id=c.data_id and c.workflowstep_id=f.workflowstep_id "
				+ "and f.workflowstep_id=d.workflowstep_id and d.role_id=e.roleId and e.userId="+userId+" "+
				"and not exists (select * from t_cms_workflow_log a1,t_cms_workflow_step a2,t_cms_workflow_step a3 where a1.workflowprocess_id=c.workflowprocess_id "
				+ "and a1.from_=a2.name and a1.to_=a3.name and a2.workflow_id=a3.workflow_id and a3.seq<a2.seq "+
				"and a1.creation_date=(select max(b1.creation_date) from t_cms_workflow_log b1 where b1.workflowprocess_id=c.workflowprocess_id)) order by c.end_date desc,c.begin_date desc";
		Query query = entityManager.createNativeQuery(sql, Info.class);
		
		query.setFirstResult(start*size);
		//query.setMaxResults(start*size+size-1);
		query.setMaxResults(size);
		List<Info> infos = query.getResultList();
		
		String csql = "select count(*) num from t_cms_info a "+
				"where a.`status`='1' and exists (select * from t_cms_node b,t_cms_workflow_process c,t_cms_workflow_step f,t_cms_workflowstep_role d,t_sys_user_role e "+
				"where a.node_id=b.node_id and b.workflow_id=c.workflow_id and c.data_type=1 and a.info_id=c.data_id and c.workflowstep_id=f.workflowstep_id and f.workflowstep_id=d.workflowstep_id and d.role_id=e.roleId and e.userId="+userId+" "+
				"and not exists (select * from t_cms_workflow_log a1,t_cms_workflow_step a2,t_cms_workflow_step a3 where a1.workflowprocess_id=c.workflowprocess_id and a1.from_=a2.name and a1.to_=a3.name and a2.workflow_id=a3.workflow_id and a3.seq<a2.seq "+
				"and a1.creation_date=(select max(b1.creation_date) from t_cms_workflow_log b1 where b1.workflowprocess_id=c.workflowprocess_id)))";
		Query query1 = entityManager.createNativeQuery(csql);
		java.math.BigInteger count = (BigInteger) query1.getSingleResult();
		
		return new PageImpl<Info>(infos, null,  count.longValue());
	}
	
	public Page<Info> findAuditBackPage(Long userId, int start, int size){
		String sql = "select a.* from t_cms_info a,t_cms_node b,t_cms_workflow_process c,t_cms_workflow_step f,t_cms_workflowstep_role d,t_sys_user_role e "+
				"where a.`status`='1' "+
				"and a.node_id=b.node_id and b.workflow_id=c.workflow_id and c.data_type=1 and a.info_id=c.data_id and c.workflowstep_id=f.workflowstep_id and f.workflowstep_id=d.workflowstep_id and d.role_id=e.roleId and e.userId="+userId+" "+
				"and exists (select * from t_cms_workflow_log a1,t_cms_workflow_step a2,t_cms_workflow_step a3 where a1.workflowprocess_id=c.workflowprocess_id and a1.from_=a2.name and a1.to_=a3.name and a2.workflow_id=a3.workflow_id and a3.seq<a2.seq "+
				"and a1.creation_date=(select max(b1.creation_date) from t_cms_workflow_log b1 where b1.workflowprocess_id=c.workflowprocess_id)) order by c.end_date desc,c.begin_date desc";
		Query query = entityManager.createNativeQuery(sql, Info.class);
		
		query.setFirstResult(start*size);
		//query.setMaxResults(start*size+size-1);
		query.setMaxResults(size);
		List<Info> infos = query.getResultList();
		
		String csql = "select count(*) num from t_cms_info a "+
				"where a.`status`='1' and exists (select * from t_cms_node b,t_cms_workflow_process c,t_cms_workflow_step f,t_cms_workflowstep_role d,t_sys_user_role e "+
				"where a.node_id=b.node_id and b.workflow_id=c.workflow_id and c.data_type=1 and a.info_id=c.data_id and c.workflowstep_id=f.workflowstep_id and f.workflowstep_id=d.workflowstep_id and d.role_id=e.roleId and e.userId="+userId+" "+
				"and exists (select * from t_cms_workflow_log a1,t_cms_workflow_step a2,t_cms_workflow_step a3 where a1.workflowprocess_id=c.workflowprocess_id and a1.from_=a2.name and a1.to_=a3.name and a2.workflow_id=a3.workflow_id and a3.seq<a2.seq "+
				"and a1.creation_date=(select max(b1.creation_date) from t_cms_workflow_log b1 where b1.workflowprocess_id=c.workflowprocess_id)))";
		Query query1 = entityManager.createNativeQuery(csql);
		java.math.BigInteger count = (BigInteger) query1.getSingleResult();
		
		return new PageImpl<Info>(infos, null,  count.longValue());
	}
	
	public Page<Info> findHasAuditPage(Long userId, int start, int size){
		String sql = "select a.* from t_cms_info a,t_cms_node b,t_cms_workflow_process c "+
				"where a.node_id=b.node_id and a.`status` in ('1','A') and b.workflow_id=c.workflow_id and a.info_id=c.data_id and c.data_type=1 "+
				"and exists (select * from t_sys_user_role c1,t_cms_workflowstep_role c2,t_cms_workflow_step c3  "+
				"where c1.userId="+userId+" and c1.roleId=c2.role_id and c2.workflowstep_id=c3.workflowstep_id and c3.workflow_id=b.workflow_id) "+
				"and (c.workflowstep_id is NULL or exists (select * from t_cms_workflow_step a1 where a1.workflowstep_id=c.workflowstep_id "+
				"and a1.seq>(select max(b3.seq) from t_sys_user_role b1,t_cms_workflowstep_role b2,t_cms_workflow_step b3 "+
				"where b1.userId="+userId+" and b1.roleId=b2.role_id and b2.workflowstep_id=b3.workflowstep_id and b3.workflow_id=b.workflow_id))) "+
				"and date_sub(now(), INTERVAL 3 DAY) <= a.publish_date order by c.end_date desc,c.begin_date desc";
		Query query = entityManager.createNativeQuery(sql, Info.class);
		
		query.setFirstResult(start*size);
		
		//query.setMaxResults(start*size+size-1);
		query.setMaxResults(size);
		
		List<Info> infos = query.getResultList();
		
		String csql = "select count(*) num from t_cms_info a,t_cms_node b,t_cms_workflow_process c "+
				"where a.node_id=b.node_id and a.`status` in ('1','A') and b.workflow_id=c.workflow_id and a.info_id=c.data_id and c.data_type=1 "+
				"and exists (select * from t_sys_user_role c1,t_cms_workflowstep_role c2,t_cms_workflow_step c3  "+
				"where c1.userId="+userId+" and c1.roleId=c2.role_id and c2.workflowstep_id=c3.workflowstep_id and c3.workflow_id=b.workflow_id) "+
				"and (c.workflowstep_id is NULL or exists (select * from t_cms_workflow_step a1 where a1.workflowstep_id=c.workflowstep_id "+
				"and a1.seq>(select max(b3.seq) from t_sys_user_role b1,t_cms_workflowstep_role b2,t_cms_workflow_step b3 "+
				"where b1.userId="+userId+" and b1.roleId=b2.role_id and b2.workflowstep_id=b3.workflowstep_id and b3.workflow_id=b.workflow_id))) "+
				"and date_sub(now(), INTERVAL 3 DAY) <= a.publish_date";
		Query query1 = entityManager.createNativeQuery(csql);
		java.math.BigInteger count = (BigInteger) query1.getSingleResult();
		
		return new PageImpl<Info>(infos, null,  count.longValue());
	}
	
	private String collectImage(String html, Site site) {
		if (StringUtils.isBlank(html)) {
			return html;
		}
		StringBuilder buff = new StringBuilder(html.length());
		try {
			Parser parser = new Parser(new Lexer(html));
			NodeFilter filter = new TagNameFilter("img");
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			SimpleNodeIterator it = nodes.elements();
			int begin = 0, end = 0;
			while (it.hasMoreNodes()) {
				ImageTag tag = (ImageTag) it.nextNode();
				String src = tag.getAttribute("src");
				String dataSrc = tag.getAttribute("data-src");
				src = StringUtils.isEmpty(src) ? dataSrc : src;
				if (StringUtils.isBlank(src)) {
					continue;
				}
				try{
					String domain = site.getDomain();
					URI uri = new URI(src);
					if(StringUtil.isNull(uri.getHost())){
						continue;
					}
					if(domain.equals(uri.getHost())){
						continue;
					}else{
						String srcUrl = uri.toString();
						UploadResult result = context.uploadFile(uri.toURL());
						
						//uploadHandler.upload(srcUrl, Uploader.IMAGE, site, userId, null, result);
						if (result.isStatus()) {
							String url = result.getFileUrl();
							tag.setAttribute("src", url);
							// html = StringUtils.replace(html, src, url);
						}
						end = tag.getStartPosition();
						buff.append(html.subSequence(begin, end));
						buff.append(tag.toHtml());
						begin = tag.getEndPosition();
					}
				}catch(Exception e){
					logger.error("collectImage",e);
					continue;
				}
			}
			buff.append(html.subSequence(begin, html.length()));
		} catch (ParserException e) {
			logger.error("collectImage", e);
		}
		return buff.toString();
	}
	
	private void updatePostInfo(Info ... infos){
		if(infos != null && infos.length > 0){
			List<Info> list = new ArrayList<Info>();
			for(Info info : infos){
//				infoAttributeService.updateSeqByInfo(info.getId());
				Double seq = infoRepository.getMinSortnum(info.getNode().getId());
				if(seq == null){
					seq = 1D;
				}
				info.setColumnSort(seq-1);
				list.add(info);
			}
			infoRepository.save(list);
		}
	}
	
	@Transactional
	public Info updateHitcount(Info bean, Integer hitcount){
		bean.setViews(hitcount);
		infoRepository.save(bean);
		return bean;
	}
	
	public Info updateInfo(Info bean){
		infoRepository.save(bean);
		return bean;
	}
	
	@Transactional
	public void updateHitByJs(Integer js, Integer zxdjs, Integer zddjs){
		if(js == 0){
			infoRepository.updateHitByAll(zxdjs, zddjs);
		}else{
			infoRepository.updateHitByJs(js, zxdjs, zddjs);
		}
	}
	
	@Transactional
	public void updateHitByBs(Integer js, Integer bs){
		if(js == 0){
			infoRepository.updateHitByBsAll(bs);
		}else{
			infoRepository.updateHitByBs(js, bs);
		}
	}
	
	
	public List<PicDic> getPicDicList(){
		Sort sort = new Sort(Direction.ASC, "sortIndex");
		return picDicRepostory.findAll(sort);
		//return picDicRepostory.findAll();
	}
	
	public boolean containsTitle(String title, Integer siteId) {
		return !infoRepository.findByTitle(title, siteId).isEmpty();
	}
	
	
	public int publish(Integer siteId) {
		return infoRepository.publish(siteId, new Date());
	}

	public int tobePublish(Integer siteId) {
		return infoRepository.tobePublish(siteId, new Date());
	}

	public int expired(Integer siteId) {
		return infoRepository.expired(siteId, new Date());
	}

}
