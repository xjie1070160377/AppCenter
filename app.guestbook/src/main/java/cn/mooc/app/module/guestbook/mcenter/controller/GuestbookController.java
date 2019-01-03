package cn.mooc.app.module.guestbook.mcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.aop.UserAnnotationHandler;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.controller.ModuleController;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.data.nosql.SysUserExtRepository;
import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.guestbook.data.entity.Guestbook;
import cn.mooc.app.module.guestbook.data.entity.Guestbook.State;
import cn.mooc.app.module.guestbook.model.GuestbookModel;
import cn.mooc.app.module.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook/guestbook")
public class GuestbookController extends GuestbookModuleController{
	
	@Autowired
	private GuestbookService guestbookService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	@Autowired
	private WebContext webContext;
	
	/**
	 * 列表
	 * @param model
	 * @return String
	 */
	@RequestMapping("/list")
	public String list(Model model, PagerParam pagerParam){
		return ModuleView("/guestbook/list");
	}

	/**
	 * json列表
	 * @param model
	 * @return Map<String, Object>
	 */
	@RequestMapping(value="/listJson")
	@ResponseBody
	public Map<String, Object> listJson(Model model, PagerParam pagerParam, String columnField, String keyWord){
		Sort sort = new Sort(Sort.Direction.DESC, "ranking", "total", "replys");
		pagerParam.setSort(sort);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnField, keyWord);
		Page<Guestbook> page = guestbookService.findPage(searchParams, pagerParam);
		Page<GuestbookModel> mPage = ToModelPage(page, pagerParam);
		return HttpResponseUtil.successJson(mPage, pagerParam);
	}
	
	/**
	 * 新增
	 * @param model
	 * @return String
	 */
	@RequestMapping(value="/add")
	public String add(Model model){
		Map<State, Object> states = new HashMap<>();
		for(Guestbook.State state : Guestbook.State.values()){
			states.put(state, state.getValue());
		}
		model.addAttribute("entity", new Guestbook());
		model.addAttribute("oprt", "save");
		model.addAttribute("states", states);
		return ModuleView("/guestbook/form");
	}
	
	/**
	 * 修改
	 * @param model
	 * @return String
	 */
	@RequestMapping(value="/edit")
	public String edit(Model model, String id){
		Map<State, Object> states = new HashMap<>();
		for(Guestbook.State state : Guestbook.State.values()){
			states.put(state, state.getValue());
		}
		Guestbook entity = guestbookService.findOne(id);
		model.addAttribute("entity", entity);
		model.addAttribute("oprt", "update");
		model.addAttribute("states", states);
		model.addAttribute("userName", sysDataService.getUserInfoById(entity.getUserId()).getUserName());
		return ModuleView("/guestbook/form");
	}
	
	/**
	 * 保存
	 * @param model
	 * @return Map<String, Object>
	 */
	@RequestMapping(value="save",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Model model, Guestbook entity, Long userId){
		try {
			SysUserEntity user = webContext.getSysUser(userId);
			if(guestbookService.findOneByUserId(userId) != null){
				return HttpResponseUtil.failureJson("该用户已经开通过留言簿了");
			}
			if(user != null){
				entity.setUserId(user.getId());
				entity.setTotal(0);
				entity.setReplys(0);
				entity.setCreateTime(new Date());
				guestbookService.save(entity);
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
	 * @param model
	 * @return Map<String, Object>
	 */
	@RequestMapping(value="update",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Model model, @ModelAttribute("entity")Guestbook entity,
				String name, String summary, String icon, State state, Integer ranking){
		try {
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
			guestbookService.save(entity);
			return HttpResponseUtil.successJson();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}
	
	/**
	 * 删除
	 * @param model
	 * @return Map<String, Object>
	 */
	@RequestMapping(value="delete",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String id){
		try {
			if(StringUtils.isNoneEmpty(id)){
				guestbookService.delete(id);
				return HttpResponseUtil.successJson();
			}else{
				return HttpResponseUtil.failureJson("id不能为空");
			}
			
		} catch (Exception e) {
			return HttpResponseUtil.failureJson("无法删除该留言簿，请先清除所属的留言");
		}
	}
	
	/**
	 * 查询用户
	 * @param username
	 * @return Map<String, Object>
	 */
	@RequestMapping(value="queryUser",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUser(String userName){
		try{
			PagerParam pagerParam = new PagerParam(1, 10, "userName", "ASC");
			Map<String, Object> searchParams = new HashMap<>();
			searchParams.put("LIKE_userName", userName);
			/*Criteria criteria = CriteriaExpression.parse(searchParams);
			Page<SysUserExt> userPage = mongoDbOpr.findPage(pagerParam.getPageRequest(), criteria, SysUserExt.class);*/
			Page<SysUserEntity> userPage = sysDataService.findSysUserList(searchParams, pagerParam, "");
			List<SysUserExt> userList = new ArrayList<>();
			for(SysUserEntity user : userPage.getContent()){
				SysUserExt us = sysDataService.getSysUserExt(user.getId());
				us.setUserName(user.getUserName());
				userList.add(us);
			}
			Page<SysUserExt> userExtpage = new PageImpl<>(userList);
			return HttpResponseUtil.successJson(userExtpage, pagerParam);
		} catch (Exception e){
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}
	
	@ModelAttribute("entity")
	public Guestbook preloadBean(@RequestParam(required = false) String oid, org.springframework.ui.Model modelMap) {
		return StringUtils.isNotEmpty(oid) ? guestbookService.findOne(oid) : null;
	}
	
	public Page<GuestbookModel> ToModelPage(Page<Guestbook> page,PagerParam pagerParam ){
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
}
