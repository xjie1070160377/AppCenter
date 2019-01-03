package cn.mooc.app.module.guestbook.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.beetl.ext.fn.ParseInt;
import org.hibernate.exception.DataException;
import org.hibernate.loader.custom.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.api.utils.RedisTokenUtil;
import cn.mooc.app.module.guestbook.data.entity.Guestbook;
import cn.mooc.app.module.guestbook.data.entity.Guestbook.State;
import cn.mooc.app.module.guestbook.data.entity.GuestbookType;
import cn.mooc.app.module.guestbook.data.entity.Message;
import cn.mooc.app.module.guestbook.enums.DocumentType;
import cn.mooc.app.module.guestbook.mcenter.controller.GuestbookModuleController;
import cn.mooc.app.module.guestbook.model.GuestbookModel;
import cn.mooc.app.module.guestbook.model.MessageModel;
import cn.mooc.app.module.guestbook.service.GuestbookService;
import cn.mooc.app.module.guestbook.service.GuestbookTypeService;
import cn.mooc.app.module.guestbook.service.MessageService;

@Controller
@RequestMapping("/widget")
public class WebGuestbookController extends GuestbookModuleController{
	
	private static final int PAGE_NUM = 1;
	private static final int PAGE_SIZE = 10;
	private static final Logger logger = LoggerFactory.getLogger(WebGuestbookController.class);
	private static final String TEMPLATE_GUESTBOOK_LIST = "/guestbook_list";
	private static final String TEMPLATE_GUESTBOOK_MY = "/guestbook_my";
	private static final String TEMPLATE_GUESTBOOK_ADD = "/guestbook_add";
	private static final String TEMPLATE_MSG_LIST = "message_list";
	private static final String TEMPLATE_MSG_ADD = "message_add";
	private static final String TEMPLATE_MESSAGE_MANAGEMENT = "message_management";
	private static final String TEMPLATE_MESSAGE_FORM = "message_form";
	
	
	@Autowired
	private GuestbookService guestbookservice;
	@Autowired
	private MessageService messageservice;
	@Autowired
	private GuestbookTypeService guestbookTypeService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	
	/**
	 * 留言簿列表
	 */
	@RequestMapping("/guestbook.htx")
	public String guestbookList(HttpServletRequest request, org.springframework.ui.Model modelMap){
		String token = request.getParameter("token");
		if(StringUtils.isNotEmpty(token)){
			request.getSession().setAttribute("token", token);
		}else{
			request.getSession().setAttribute("token", "");
		}
		long userId = getUserId(request);
		if(userId != 0){
			Guestbook guestbook = guestbookservice.findOneByUserId(userId);
			if(guestbook != null){
				modelMap.addAttribute("guestbookId", guestbook.get_id());
			}
		}
		return getWebView(TEMPLATE_GUESTBOOK_LIST);
	}
	
	
	/**
	 * 保存
	 */
	@RequestMapping(value="/guestbook/save",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest req, Model model, Guestbook entity){
		try {
			long userId = getUserId(req);
			SysUserEntity user = webContext.getSysUser(userId);
			if(user != null){
				if(guestbookservice.findOneByUserId(userId) != null){
					return HttpResponseUtil.failureJson("该用户已经开通过留言簿了");
				}
				entity.setUserId(user.getId());
				entity.setTotal(0);
				entity.setReplys(0);
				entity.setCreateTime(new Date());
				guestbookservice.save(entity);
				return HttpResponseUtil.successJson();
			}else{
				return HttpResponseUtil.failureJson("该用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/guestbook/update",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest req, Model model, @ModelAttribute("entity")Guestbook entity,
				String name, String summary, String icon, State state, Integer ranking){
		try {
			long userId = getUserId(req);
			if(userId == 0){
				return HttpResponseUtil.failureJson("请登录");
			}
			if((long)entity.getUserId() != userId){
				return HttpResponseUtil.failureJson("非法请求");
			}
			if(StringUtils.isNoneEmpty(name))
				entity.setName(name);
			if(StringUtils.isNoneEmpty(summary))
				entity.setSummary(summary);
			if(StringUtils.isNoneEmpty(icon))
				entity.setIcon(icon);
			if(state != null)
				entity.setState(state);
			if(ranking != null)
				entity.setRanking(ranking);
			guestbookservice.save(entity);
			return HttpResponseUtil.successJson();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}
	
	
	/**
	 * 留言簿json Page
	 */
	@RequestMapping("/guestbook/listJson")
	@ResponseBody
	public Map<String, Object> guestbookListJson(HttpServletRequest request, org.springframework.ui.Model modelMap,
			PagerParam pagerParam, String columnField, String keyWord){
		Page<GuestbookModel> mPage = getModelPage(columnField, keyWord, pagerParam);
		return HttpResponseUtil.successJson(mPage, pagerParam);
	}
	
	/**
	 * 留言簿Page<Guestbook> to Page<GuestbookModel>
	 */
	public Page<GuestbookModel> getModelPage(String columnField, String keyWord,PagerParam pagerParam ){
		Sort sort = new Sort(Sort.Direction.DESC, "ranking", "total", "replys");
		pagerParam.setSort(sort);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnField, keyWord);
		Page<Guestbook> page = guestbookservice.findPage(searchParams, pagerParam);
		Pageable pageable = pagerParam.getPageRequest();
		List<GuestbookModel> list = new ArrayList<>();
		int i = 1;
		for(Guestbook gb : page.getContent()){
			GuestbookModel gbm = new GuestbookModel(gb);
			gbm.setRanking(pageable.getPageNumber() * pageable.getPageSize() + i);
			list.add(gbm);
			i++;
		}
		return new PageImpl<>(list, pagerParam.getPageRequest(), page.getTotalElements());
	}
	
	/**
	 * 查询留言簿
	 */
	@RequestMapping("/guestbook/queryGuestbook")
	@ResponseBody
	public Map<String, Object> queryGuestbook(Model modelMap,String name){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtils.isNoneEmpty(name)){
			searchParams.put("LIKE_name", name);
		}
		Sort sort = new Sort(Direction.ASC, "name");
		PagerParam pagerParam = new PagerParam(0, 10, sort);
		Page<Guestbook> page = guestbookservice.findPage(searchParams, pagerParam);
		return HttpResponseUtil.successJson(page, pagerParam);
	}
	
	/**
	 * 开通留言簿
	 */
	@RequestMapping("/guestbook/add")
	public String addGuestbook(HttpServletRequest req,HttpServletResponse resp, Model modelMap){
		long userId = getUserId(req);
		Guestbook gb = guestbookservice.findOneByUserId(userId);
		modelMap.addAttribute("gb",gb);
		if(userId == 0){
			try {
				WebUtils.issueRedirect(req, resp, "/ulogin");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Map<State, Object> states = new HashMap<>();
		for(Guestbook.State state : Guestbook.State.values()){
			states.put(state, state.getValue());
		}
		modelMap.addAttribute("entity", new Guestbook());
		modelMap.addAttribute("states", states);
		return getWebView(TEMPLATE_GUESTBOOK_ADD); 
	}
	
	/**
	 * 留言列表
	 */
	@RequestMapping("/guestbook/msgList/{guestbookId:[a-z0-9]+}")
	public String Msglist(@PathVariable String guestbookId, HttpServletRequest request, org.springframework.ui.Model modelMap){
		long userId = getUserId(request);
		Guestbook gb = guestbookservice.findOne(guestbookId);
		modelMap.addAttribute("gb", gb);
		modelMap.addAttribute("ranking", guestbookservice.getRanking(gb));
		modelMap.addAttribute("state", gb.getState().getValue());
		modelMap.addAttribute("userId", userId);
		return getWebView(TEMPLATE_MSG_LIST);
	}
	
	/**
	 * 留言json列表
	 */
	@RequestMapping("guestbook/msgListJson")
	@ResponseBody
	public Map<String, Object> msgListJson(HttpServletRequest request, org.springframework.ui.Model modelMap,
			PagerParam pagerParam, String columnField, String keyWord, String guestbookId, Long userId){
		Sort sort = new Sort(Sort.Direction.DESC, "isTop", "createTime");
		pagerParam.setSort(sort);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		/*筛选：已审核，是否显示,原始留言*/
		searchParams.put("EQ_state", Message.AUDITED);
		searchParams.put("EQ_isVisibile", 1);
		searchParams.put("ISNOTNULL_parentId", false);
		if(StringUtils.isNotEmpty(guestbookId)){
			searchParams.put("EQ_guestbookId", guestbookId);
		}
		if(userId != null){
			searchParams.put("EQ_userId", userId);
		}
		if(StringUtils.isNoneEmpty(columnField) && StringUtils.isNotEmpty(keyWord)){
			if(columnField.indexOf("isReply") > -1){
				searchParams.put(columnField, Integer.parseInt(keyWord));
			}else{
				searchParams.put(columnField, keyWord);
			}
		}
		Page<Message> page = messageservice.findPage(searchParams, pagerParam);
		List<MessageModel> list = new ArrayList<>();
		for(Message msg : page.getContent()){
			MessageModel msgm = toModel(msg);
			list.add(msgm);
		}
		Page<MessageModel> msgPage = new PageImpl<MessageModel>(list, pagerParam.getPageRequest(), page.getTotalElements());
		return HttpResponseUtil.successJson(msgPage, pagerParam);
	}
	
	/**
	 * 查询信箱名称
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/guestbook/searchMailBox")
	@ResponseBody
	public Map<String, Object> searchMailBox(String name ,org.springframework.ui.Model modelMap){
		Criteria criteria = new Criteria().and("name").regex(name);
		Pageable pageable = new PageRequest(PAGE_NUM - 1, PAGE_SIZE);
		Page<Guestbook> gbList = mongoDbOpr.findPage(pageable, criteria, Guestbook.class);
		Map<String, Object> result = new HashMap<>();
		result.put("resultStatus", true);
		result.put("rows", gbList.getContent());
		return result;
	}
	
	/**
	 * 新增留言页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/guestbook/addMsg")
	public String addMSg(HttpServletRequest req,String guestbookId ,org.springframework.ui.Model modelMap){
		long userId = getUserId(req);
		Guestbook gb = guestbookservice.findOne(guestbookId);
		Guestbook gb1 = guestbookservice.findOneByUserId(userId);
		List<GuestbookType> types = guestbookTypeService.getGuestbookTypes();
		modelMap.addAttribute("gb", gb1);
		modelMap.addAttribute("types", types);
		if(gb != null){
			modelMap.addAttribute("guestbookId", gb.get_id());
			modelMap.addAttribute("state", gb.getState());
		}
		return getWebView(TEMPLATE_MSG_ADD);
	}
	
	/**
	 * 保存留言
	 */
	@RequestMapping("/guestbook/saveMsg")
	@ResponseBody
	public Map<String, Object> saveMsg(HttpServletRequest request, org.springframework.ui.Model modelMap,
			String sourceId, Integer isAnonymous, String title, String text, String guestbookTypeId, 
			String guestbookId, String email,String parentId){
		Message msg = new Message();
		if(StringUtils.isEmpty(title))
			return HttpResponseUtil.failureJson("标题不能为空");
		if(StringUtils.isEmpty(text))
			return HttpResponseUtil.failureJson("内容不能为空");
		if(StringUtils.isEmpty(guestbookId))
			return HttpResponseUtil.failureJson("guestbookId can not be null");
		if(StringUtils.isEmpty(guestbookTypeId))
			return HttpResponseUtil.failureJson("留言类型不能为空");
		if(isAnonymous == null){
			isAnonymous = 0;
		}
		GuestbookType gbt = guestbookTypeService.getGuestbookTypeById(guestbookTypeId);
		Guestbook gb = guestbookservice.findOne(guestbookId);
		long userId = getUserId(request);
		SysUserInfo user = webContext.getSysUserFullInfo(userId);
		if(gb==null)
			return HttpResponseUtil.failureJson("guestbook can not be null");
		if(gbt==null)
			return HttpResponseUtil.failureJson("留言类型不能为空");
		if(user==null && !(gb.getState() == Guestbook.State.allowAnonymous))
			return HttpResponseUtil.failureJson("请先登录");
		try {
			msg.setTitle(title);
			msg.setText(text);
			msg.setIsReply(0);
			msg.setGuestbookId(guestbookId);
			if(isAnonymous == 1 && gb.getState() == Guestbook.State.allowAnonymous){
				msg.setUsername("匿名用户");
			}else{
				msg.setUserId(user.getUserId());
				msg.setUsername(user.getUserName());
			}
			msg.setCreateTime(new Date());
			msg.setEmail(email);
			msg.setIp(ValidatorUtil.getIpAddr(request));
			if(StringUtils.isNoneEmpty(parentId))
				msg.setParentId(parentId);
			if(StringUtils.isNoneEmpty(sourceId)){
				Message source = messageservice.findOne(sourceId);
				msg.setSourceId(sourceId);
				msg.setSourceUserName(source.getUsername());
			}
			msg.setType(DocumentType.Message);
			messageservice.save(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败!");
		}
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 追加留言
	 */
	@RequestMapping("/guestbook/appendMsg")
	@ResponseBody
	public Map<String, Object> appendMsg(Model modelMap, HttpServletRequest request,HttpServletResponse response,
			String parentId, String text) throws Exception{
		long userId = getUserId(request);
		if(userId == 0){
			WebUtils.issueRedirect(request, response, "/ulogin");
		}
		if(StringUtils.isEmpty(parentId))
			return HttpResponseUtil.failureJson("parentId不能为空");
		if(StringUtils.isEmpty(text))
			return HttpResponseUtil.failureJson("内容不能为空");
		Message parent = messageservice.findOne(parentId);
		if(userId != (long)parent.getUserId()){
			return HttpResponseUtil.failureJson("非法请求");
		}
		Message msg = new Message();
		msg.setUserId(userId);
		msg.setUsername(parent.getUsername());
		msg.setCreateTime(new Date());
		msg.setText(text);
		msg.setIsReply(0);
		msg.setState(Message.SAVED);
		msg.setParentId(parentId);
		msg.setType(DocumentType.Message);
		msg.setIp(ValidatorUtil.getIpAddr(request));
		msg.setEmail(parent.getEmail());
		msg.setGuestbookId(parent.getGuestbookId());;
		msg.setTitle(parent.getTitle().concat("（续）"));
		messageservice.save(msg);
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 转发
	 */
	@RequestMapping("/guestbook/forward")
	@ResponseBody
	public Map<String, Object> forward(Model modelMap,HttpServletRequest request,HttpServletResponse response,
			String sourceId,String guestbookId, String text) throws IOException{
		long userId = getUserId(request);
		if(userId == 0){
			WebUtils.issueRedirect(request, response, "/ulogin");
		}
		if(StringUtils.isEmpty(guestbookId))
			return HttpResponseUtil.failureJson("留言簿不能为空");
		if(StringUtils.isEmpty(text))
			return HttpResponseUtil.failureJson("内容不能为空");
		if(StringUtils.isEmpty(sourceId)){
			return HttpResponseUtil.failureJson("sourceId不能为空");
		}
		Guestbook gb = guestbookservice.findOne(guestbookId);
		SysUserInfo user = webContext.getSysUserFullInfo(userId);
		Message source = messageservice.findOne(sourceId);
		Message msg = new Message();
		msg.setUserId(userId);
		msg.setUsername(user.getUserName());
		msg.setCreateTime(new Date());
		msg.setText(text);
		msg.setIsReply(0);
		msg.setState(Message.SAVED);
		msg.setSourceId(sourceId);
		msg.setSourceUserName(source.getUsername());
		msg.setType(DocumentType.Message);
		msg.setIp(ValidatorUtil.getIpAddr(request));
		msg.setGuestbookId(gb.get_id());
		msg.setTitle(source.getTitle().concat("（转发）"));
		try {
			messageservice.save(msg);
			gb.setTotal(gb.getTotal()+1);
			guestbookservice.save(gb);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 我的留言
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/guestbook/my")
	public String myMessage(Model modelMap, HttpServletRequest request,HttpServletResponse response) throws IOException{
		long userId = getUserId(request);
		Guestbook gb = guestbookservice.findOneByUserId(userId);
		modelMap.addAttribute("gb",gb);
		if(userId == 0){
			WebUtils.issueRedirect(request, response, "/ulogin");
		}
		modelMap.addAttribute("userId", userId);
		return getWebView(TEMPLATE_GUESTBOOK_MY);
		
	}
	
	/**
	 * 管理我的留言簿
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/guestbook/manage")
	public String manage(Model modelMap, HttpServletRequest request,HttpServletResponse response) throws IOException{
		long userId = getUserId(request);
		if(userId == 0){
			WebUtils.issueRedirect(request, response, "/ulogin");
		}
		Guestbook gb = guestbookservice.findOneByUserId(userId);
		modelMap.addAttribute("gb",gb);
		modelMap.addAttribute("userId",userId);
		return getWebView(TEMPLATE_MESSAGE_MANAGEMENT);
		
	}
	
	
	/**
	 * 留言审核页面
	 */
	@RequestMapping("/guestbook/message/form")
	public String messageForm(Model modelMap, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required = true)String _id) throws IOException{
		Message	msg = messageservice.findOne(_id);
		msg.setIsRead(1);
		try {
			messageservice.save(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long userId =getUserId(request);
		if(userId == 0){
			WebUtils.issueRedirect(request, response, "/ulogin");
		}
		//如果用户不是超级管理员,只取自己留言簿的留言
		if(!isSuper(userId)){
			Guestbook gb = guestbookservice.findOneByUserId(userId);
			if(gb == null){
				WebUtils.issueRedirect(request, response, "/guestbook.htx");
			}else{
				if(!msg.getGuestbookId().equals(gb.get_id())){
					WebUtils.issueRedirect(request, response, "/guestbook.htx");
				}
			}
		}
		List<Message> children = messageservice.findByParentId(_id);
		for(Message child : children){
			if(child.getType() == DocumentType.Reply){
				modelMap.addAttribute("reply", child);
			}
		}
		MessageModel m = toModel(msg);
		modelMap.addAttribute("oprt", "audit");
		modelMap.addAttribute("entity", m);
		return getWebView(TEMPLATE_MESSAGE_FORM);
	}
	
	
	/**
	 * 前台管理json列表
	 */
	@RequestMapping("/guestbook/message/listJson")
	@ResponseBody
	public Map<String, Object> listJson(HttpServletRequest req, Model model, PagerParam pagerParam, String columnFiled, String keyWord){
		Map<String, Object> searchParam = new HashMap<String, Object>();
		long userId = getUserId(req);
		if(userId == 0){
			return HttpResponseUtil.failureJson("请先登录!");
		}
		//如果用户不是超级管理员
		if(!isSuper(userId)){
			Guestbook gb = guestbookservice.findOneByUserId(userId);
			if(gb != null){
				searchParam.put("EQ_guestbookId", gb.get_id());
			}else{
				return HttpResponseUtil.failureJson("没有权限!");
			}
		}
		searchParam.put("EQ_type", DocumentType.Message);
		searchParam.put("NE_state", Message.DISABLED);
		searchParam.put(columnFiled, keyWord);
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		pagerParam.setSort(sort);
		Page<Message> page = messageservice.findPage(searchParam, pagerParam);
		List<MessageModel> list = new ArrayList<>();
		for(Message msg : page.getContent()){
			MessageModel msgm = toModel(msg);
			list.add(msgm);
		}
		Page<MessageModel> msgPage = new PageImpl<MessageModel>(list, pagerParam.getPageRequest(), page.getTotalElements());
		return HttpResponseUtil.successJson(msgPage, pagerParam);
	}
	
	/**
	 * 审核
	 * @throws Exception 
	 */
	@RequestMapping("/guestbook/message/audit")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest req, Model model, 
			String _id, String text, Integer isTop, Integer isVisibile,String replyId) throws Exception{
		if(StringUtils.isEmpty(_id)){
			return HttpResponseUtil.failureJson("id can not be null");
		}
		long userId = getUserId(req);
		if(userId == 0){
			HttpResponseUtil.failureJson("请先登录");
		}
		Message	msg = messageservice.findOne(_id);
		//如果用户不是超级管理员
		if(!isSuper(userId)){
			Guestbook gb = guestbookservice.findOneByUserId(userId);
			if(gb == null){
				HttpResponseUtil.failureJson("没有权限");
			}else{
				if(!msg.getGuestbookId().equals(gb.get_id())){
					HttpResponseUtil.failureJson("没有权限");
				}
			}
		}
		SysUserInfo user = webContext.getSysUserFullInfo(userId);
		if(msg.getState() == Message.SAVED){
			msg.setAuditTime(new Date());
			msg.setAuditUserId(userId);
			msg.setState(Message.AUDITED);
		}
		if(isTop != null)
			msg.setIsTop(isTop);
		if(isVisibile != null)
			msg.setIsVisibile(isVisibile);
		
		if(StringUtils.isNoneEmpty(text)){
			if(StringUtils.isNotEmpty(replyId)){
				Message reply = messageservice.findOne(replyId);
				reply.setText(text);
				messageservice.save(reply);
			}else{
				msg.setIsReply(1);
				Message reply = new Message();
				reply.setType(DocumentType.Reply);
				reply.setCreateTime(new Date());
				reply.setUserId(userId);
				reply.setParentId(_id);
				reply.setUsername(StringUtils.isNoneEmpty(user.getRealName()) ? user.getRealName() : user.getUserName());
				reply.setText(text);
				reply.setGuestbookId(msg.getGuestbookId());
				messageservice.save(reply);
			}
		}
		messageservice.save(msg);
		Guestbook guestbook = guestbookservice.findOne(msg.getGuestbookId());
		guestbook.setTotal(messageservice.getTotals(guestbook));
		guestbook.setReplys(messageservice.getReplys(guestbook));
		guestbookservice.save(guestbook);
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 移除
	 * @throws Exception 
	 */
	@RequestMapping("/guestbook/message/remove")
	@ResponseBody
	public Map<String, Object> removeMsg(Model model,HttpServletRequest request,
			HttpServletResponse response,@RequestParam(required = true) String _id){
		
		/*	Message msg = messageService.findOne(_id);
		if(msg != null){
			msg.setState(Message.DISABLED);*/
		try {
			messageservice.delete(_id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		/*}*/
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 转化Message为MessageModel
	 * @param msg
	 * @return
	 */
	private MessageModel toModel(Message msg ){
		String sourceId= msg.getSourceId();
		String sourceText = null;
		if(StringUtils.isNoneEmpty(sourceId)){
			Message source = messageservice.findOne(sourceId);
			sourceText = source.getText();
		}
		Guestbook gb = guestbookservice.findOne(msg.getGuestbookId());
		String guestbook = gb == null ? "" : gb.getName();
		String auditUserName = "";
		if(msg.getAuditUserId() != null){
			SysUserExt user = sysDataService.getSysUserExt(msg.getAuditUserId());
			auditUserName = StringUtils.isNoneEmpty(user.getRealName()) ? user.getRealName() : user.getUserName();
		}
		List<MessageModel> mlist = new ArrayList<>();
		List<Message> list = messageservice.findByParentId(msg.get_id());
		if(CollectionUtils.isNotEmpty(list)){
			for(Message child : list){
				if((child.getType() == DocumentType.Message && child.getState() == Message.AUDITED) || child.getType() == DocumentType.Reply ){
					MessageModel m = toModel(child);
					mlist.add(m);
				}
			}
		}
		MessageModel msgm = new MessageModel(msg, guestbook,auditUserName, sourceText, mlist);
		return msgm;
	}
	
}
