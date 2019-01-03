package cn.mooc.app.module.interact.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.data.nosql.InteractCommentRepository;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.model.InteractCommentFindPageListParams;

/**
 * InteractCommentService
 * 评论服务
 * 
 * @author oyhx
 * @date 2016-05-12
 */
@Service
public class InteractCommentService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String COMMENTALLOW = "interact.comment.allow";
	private static final String COMMENTNEEDCHECK = "interact.comment.needcheck";
	private static final String COMMENTLENGTH = "interact.comment.length";
	
	@Autowired
	private InteractCommentRepository interactCommentRepository;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	@Autowired
	private SysContext sysContext;
	/**
	 * 是否允许评论（0：不允许，1：允许）
	 * 默认 1：允许
	 * @return
	 */
	public int getConfig_allow(){
		return sysContext.getSysConfigInt(COMMENTALLOW, 1);
	}
	/**
	 * 评论是否需要审核（0：不需要，1：需要审核）
	 * 默认 0：不需要
	 * @return
	 */
	public int getConfig_needCheck(){
		return sysContext.getSysConfigInt(COMMENTNEEDCHECK, 0);
	}
	/**
	 * 评论允许长度
	 * 默认200
	 * @return
	 */
	public int getConfig_length(){
		return sysContext.getSysConfigInt(COMMENTLENGTH, 200);
	}
	
	public InteractComment getInteractCommentById(String id) {
		InteractComment entity = interactCommentRepository.findOne(id);
		return entity;
	}
	
	public List<InteractComment> getAllInteractComments(){
		return this.interactCommentRepository.findAll();
	}
	
	public Page<InteractComment> findInteractCommentList(Map<String, Object> searchParams, PagerParam pageParam){
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, InteractComment.class);
	}
	
	
	@Transactional
	public InteractComment saveInteractComment(InteractComment entity) throws Exception {
		if(StringUtils.isEmpty(entity.getContent())){
			throw new SaveFailureException("评论内容不能为空.");
		}
		if(entity.getCreateTime() == null){
			entity.setCreateTime(new Date());
		}
		entity.setCommentId(mongoDbOpr.getNextSeqIntFor(InteractComment.class));
		return this.interactCommentRepository.save(entity);
	}

	@Transactional
	public InteractComment updateInteractComment(InteractComment entity) throws Exception{
		if(StringUtils.isEmpty(entity.getContent())){
			throw new SaveFailureException("评论内容不能为空.");
		}
		return this.interactCommentRepository.save(entity);
	}
	
	@Transactional
	public boolean delInteractComment(String id){
		try{
			this.interactCommentRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delInteractComments(String[] ids){
		try{
			for(String id : ids){
				this.interactCommentRepository.delete(id);
			}
			return ids.length;
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	public int AuditInteractComments(String[] ids, long userId, String userName, int state) {
		// TODO Auto-generated method stub
		try{
			for(String id : ids){
				InteractComment entity = getInteractCommentById(id);
				entity.setState(state);
				entity.setAuditUserId(userId);
				entity.setAuditUserName(userName);
				entity.setAuditTime(new Date());
				updateInteractComment(entity);
			}
			return ids.length;
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	public Page<InteractComment> findPage(InteractCommentFindPageListParams params,
			PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(params.getFtype())) {
			searchParams.put(SpecOperator.EQ + "_ftype", params.getFtype());
		}
		if (params.getFid() != null) {
			searchParams.put(SpecOperator.EQ + "_fid", params.getFid());
		}
		if (params.getSourceId() != null) {
			searchParams.put(SpecOperator.EQ + "_sourceId", params.getSourceId());
		}
		if (ArrayUtils.isNotEmpty(params.getStatus())) {
			searchParams.put(SpecOperator.IN + "_state", params.getStatus());
		}
		if (ArrayUtils.isNotEmpty(params.getSiteId())) {
			searchParams.put(SpecOperator.IN + "_siteId", params.getSiteId());
		}
		if(StringUtils.isNotBlank(params.getContent())){
			searchParams.put(SpecOperator.LIKE + "_content", params.getContent());
		}
		if (ArrayUtils.isNotEmpty(params.getFtypes())) {
			searchParams.put(SpecOperator.IN + "_ftype", params.getFtypes());
		}
		if(params.getUserId() != null){
			searchParams.put(SpecOperator.EQ + "_userId", params.getUserId());
		}
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, InteractComment.class);
	}

	public List<InteractComment> findList(InteractCommentFindPageListParams params,
			PagerParam pageParam) {
		Page<InteractComment> page = findPage(params, pageParam);
		return page.getContent();
	}
	public List<InteractComment> findList(String ftype, Integer sourceId) {
		Integer[] status;
		if(getConfig_needCheck() == 1){
			status = new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND };
		}else{
			status = new Integer[] { InteractComment.SAVED, InteractComment.AUDITED, InteractComment.RECOMMEND };
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.IN + "_state", status);
		searchParams.put(SpecOperator.EQ + "_ftype", ftype);
		searchParams.put(SpecOperator.EQ + "_sourceId", sourceId);
		Criteria criteria = CriteriaExpression.parse(searchParams);
		Query query = new Query();
		query.addCriteria(criteria);
		return mongoDbOpr.find(query, InteractComment.class);
//		return interactCommentRepository.findList(ftype, sourceId);
	}
	public Page<InteractComment> findUserCommentPage(Long[] userId, Integer[] siteId, PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(userId)) {
//			List<Long> list = Arrays.asList(userId);
			searchParams.put(SpecOperator.IN + "_userId", userId);
		}
		if (ArrayUtils.isNotEmpty(siteId)) {
//			List<Integer> list = Arrays.asList(siteId);
			searchParams.put(SpecOperator.IN + "_siteId", siteId);
		}
//		searchParams.put(SpecOperator.IN + "_ftype", new String[]{InteractComment.FTYPE_INFO,InteractComment.FTYPE_COMMENT});
		searchParams.put(SpecOperator.IN + "_ftype", new String[]{CommentType.Info.toString(), CommentType.Comment.toString()});
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, InteractComment.class);
	}
	public Page<InteractComment> findEvalUserCommentPage(Long[] userId, Integer[] siteId, PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		if (ArrayUtils.isNotEmpty(siteId)) {
			searchParams.put(SpecOperator.IN + "_siteId", siteId);
		}
		Criteria criteria = new Criteria();
		if(ArrayUtils.isNotEmpty(userId)){
			Map<String, Object> searchParams1 = new HashMap<String, Object>();
			Map<String, Object> searchParams2 = new HashMap<String, Object>();
			searchParams1.put(SpecOperator.EQ + "_ftype", CommentType.Comment);
			searchParams1.put(SpecOperator.NI + "_userId", userId);
			searchParams1.put(SpecOperator.IN + "_sourceUserId", userId);
			
			searchParams2.put(SpecOperator.EQ + "_ftype", CommentType.Info);
			searchParams2.put(SpecOperator.NI + "_userId", userId);
			searchParams2.put(SpecOperator.IN + "_fuserId", userId);
			Criteria criteria1 = new Criteria();
			Criteria criteria2 = new Criteria();
			criteria = criteria2.andOperator(CriteriaExpression.parse(searchParams), criteria1.orOperator(CriteriaExpression.parse(searchParams1), CriteriaExpression.parse(searchParams2)));
		}else{
			searchParams.put(SpecOperator.IN + "_ftype", new String[]{CommentType.Info.toString(),CommentType.Comment.toString()});
			criteria = CriteriaExpression.parse(searchParams);
		}
		
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, InteractComment.class);
	}
	public InteractComment getInteractCommentByCommentId(Integer commentId) {
		// TODO Auto-generated method stub
		return interactCommentRepository.findByCommentId(commentId);
	}
	public List<InteractComment> findListBySourceId(Integer sourceId){
		return interactCommentRepository.findBySourceId(sourceId);
	}
	public List<InteractComment> findBySFid(Integer sourceId, int state){
		return interactCommentRepository.findBySFid(sourceId, state);
	}
	public boolean cancelCommentByCommentId(Integer commentId) {
		// TODO Auto-generated method stub
		InteractComment comment = getInteractCommentByCommentId(commentId);
		comment.setState(InteractComment.DELETED);
		try {
			updateInteractComment(comment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
