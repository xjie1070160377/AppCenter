package cn.mooc.app.module.guestbook.mcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JToggleButton.ToggleButtonModel;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.druid.stat.TableStat.Mode;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.guestbook.data.entity.Guestbook;
import cn.mooc.app.module.guestbook.data.entity.GuestbookType;
import cn.mooc.app.module.guestbook.data.entity.Message;
import cn.mooc.app.module.guestbook.enums.DocumentType;
import cn.mooc.app.module.guestbook.model.MessageModel;
import cn.mooc.app.module.guestbook.service.GuestbookService;
import cn.mooc.app.module.guestbook.service.GuestbookTypeService;
import cn.mooc.app.module.guestbook.service.MessageService;
import javassist.expr.NewArray;

/**
 * @author linwei
 * @date 2016年9月1日
 * @description 留言控制类
 */
@Controller
@RequestMapping("/guestbook/message")
public class MessageController extends GuestbookModuleController{
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private GuestbookService gbService;
	@Autowired
	private GuestbookTypeService gbtService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WebContext webContext;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public String list(Model model){
		return ModuleView("/message/list");
	}
	
	/**
	 * json列表
	 */
	@RequestMapping("/listJson")
	@ResponseBody
	public Map<String, Object> listJson(HttpServletRequest req, Model model, PagerParam pagerParam, String columnFiled, String keyWord){
		Map<String, Object> searchParam = new HashMap<String, Object>();
		long userId = getUserId(req);
		if(userId == 0){
			return HttpResponseUtil.failureJson("请先登录!");
		}
		//如果用户不是超级管理员
		if(!isSuper(userId)){
			Guestbook gb = gbService.findOneByUserId(userId);
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
		Page<Message> page = messageService.findPage(searchParam, pagerParam);
		List<MessageModel> list = new ArrayList<>();
		for(Message msg : page.getContent()){
			MessageModel msgm = toModel(msg);
			list.add(msgm);
		}
		Page<MessageModel> msgPage = new PageImpl<MessageModel>(list, pagerParam.getPageRequest(), page.getTotalElements());
		return HttpResponseUtil.successJson(msgPage, pagerParam);
	}
	
	
	/**
	 * form页
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest req, Model model,@RequestParam(required=true)String _id){
		long userId = getUserId(req);
		Message	msg = messageService.findOne(_id);
		Guestbook gb = gbService.findOneByUserId(userId);
		if(gb == null){
			return ModuleView("/message/form");
		}
		//是超级管理员或者留言簿所有者
		if(isSuper(userId) || msg.getGuestbookId().equals(gb.get_id())){
			List<Message> children = messageService.findByParentId(_id);
			for(Message child : children){
				if(child.getType() == DocumentType.Reply){
					model.addAttribute("reply", child);
				}
			}
			MessageModel m = toModel(msg);
			model.addAttribute("oprt", "audit");
			model.addAttribute("entity", m);
		}
		return ModuleView("/message/form");
	}
	
	/**
	 * 审核
	 * @throws Exception 
	 */
	@RequestMapping("/audit")
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
		Message	msg = messageService.findOne(_id);
		//如果用户不是超级管理员
		if(!isSuper(userId)){
			Guestbook gb = gbService.findOneByUserId(userId);
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
				Message reply = messageService.findOne(replyId);
				reply.setText(text);
				messageService.save(reply);
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
				messageService.save(reply);
			}
		}
		
		messageService.save(msg);
		Guestbook guestbook = gbService.findOne(msg.getGuestbookId());
		guestbook.setTotal(messageService.getTotals(guestbook));
		guestbook.setReplys(messageService.getReplys(guestbook));
		gbService.save(guestbook);
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 移除
	 * @throws Exception 
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Map<String, Object> removeMsg(Model model,HttpServletRequest request,
			HttpServletResponse response,@RequestParam(required = true) String _id){
		
		/*	Message msg = messageService.findOne(_id);
		if(msg != null){
			msg.setState(Message.DISABLED);*/
		try {
			messageService.delete(_id);
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
			Message source = messageService.findOne(sourceId);
			sourceText = source.getText();
		}
		Guestbook gb = gbService.findOne(msg.getGuestbookId());
		String guestbook = gb == null ? "" : gb.getName();
		String auditUserName = "";
		if(msg.getAuditUserId() != null){
			SysUserExt user = sysDataService.getSysUserExt(msg.getAuditUserId());
			auditUserName = StringUtils.isNoneEmpty(user.getRealName()) ? user.getRealName() : user.getUserName();
		}
		List<MessageModel> mlist = new ArrayList<>();
		List<Message> list = messageService.findByParentId(msg.get_id());
		if(CollectionUtils.isNotEmpty(list)){
			for(Message child : list){
				MessageModel m = toModel(child);
				mlist.add(m);
			}
		}
		MessageModel msgm = new MessageModel(msg, guestbook,auditUserName, sourceText, mlist);
		return msgm;
	}
	

}
