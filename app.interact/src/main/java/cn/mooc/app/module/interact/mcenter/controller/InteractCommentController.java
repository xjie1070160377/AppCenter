package cn.mooc.app.module.interact.mcenter.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.model.InteractCommentFindPageListParams;
import cn.mooc.app.module.interact.model.InteractCommentModel;
import cn.mooc.app.module.interact.service.InteractCommentService;

/**
 * InteractCommentController
 * 评论控制器
 * 
 * @author oyhx
 * @date 2016-05-12
 */
@Controller
@RequestMapping("/interact/interactComment")
public class InteractCommentController extends InteractModuleController {

	@Autowired
	private InteractCommentService interactCommentService;

	@RequestMapping("/interactCommentList")
	public String interactCommentList(Model model, PagerParam pageParam) {
		return ModuleView("/interactComment/interactCommentList");
	}

	@RequestMapping("/interactCommentListJson")
	@ResponseBody
	public Map<String, Object> interactCommentListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		
		Page<InteractComment> pageData = interactCommentService.findInteractCommentList(searchParams, pageParam);
		List<InteractCommentModel> commentModels = new ArrayList<InteractCommentModel>();
		for (InteractComment comment : pageData.getContent()) {
			InteractCommentModel commentModel = new InteractCommentModel(comment);
			commentModels.add(commentModel);
		}
		return HttpResponseUtil.successJson(commentModels, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	@RequestMapping("/interactCommentPageTag")
	public String interactCommentPageTag(Model model, Integer id) {
		model.addAttribute("infoId", id);
		return "/htmltag/InteractCommentPageTag";
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@RequestMapping("/interactCommentAdd")
	public String interactCommentAdd(Model model) {
		model.addAttribute("entity", new InteractComment());

		model.addAttribute("oprt", "Save");
		return ModuleView("/interactComment/interactCommentForm");
	}
	/**
	 * 修改
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/interactCommentEdit")
	public String interactCommentEdit(Model model, String id) {

		InteractComment entity = interactCommentService.getInteractCommentById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("oprt", "Update");

		return ModuleView("/interactComment/interactCommentForm");
	}
	
	/**
	 * 意见反馈回复
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/interactCommentReplyForm")
	public String interactCommentReplyForm(Model model, String id) {

		InteractComment entity = interactCommentService.getInteractCommentById(id);
		
		InteractCommentFindPageListParams params = new InteractCommentFindPageListParams();
		params.setFtype(CommentType.FeedbackReply.name());
		params.setSourceId(entity.getCommentId());
		
		PagerParam paper = new PagerParam(0, 100, new Sort(Direction.ASC, "createTime"));
		List<InteractComment> datalist = interactCommentService.findList(params, paper);
		model.addAttribute("entity", entity);
		model.addAttribute("datalist", datalist);

		return ModuleView("/interactComment/interactCommentReplyForm");
	}
	
	@RequestMapping(value = "/interactCommentReplySave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentReplySave(Model model, InteractComment entity) {
		LoginUserInfo user = this.webContext.getCurrentUserInfo();
		entity.setUserId(user.getUserId());
		entity.setUserName(user.getUserName());
		entity.setCreateTime(new Date());
		entity.setAuditTime(new Date());
		entity.setAuditUserId(user.getUserId());
		entity.setAuditUserName(user.getUserName());
		entity.setState(InteractComment.AUDITED);
		try {
			this.interactCommentService.saveInteractComment(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 评论审核
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/interactCommentAuditForm")
	public String interactCommentAuditForm(Model model, String id) {

		InteractComment entity = interactCommentService.getInteractCommentById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("oprt", "Audit");
		return ModuleView("/interactComment/interactCommentForm");
	}

	/**
	 * 评论反审核
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/interactCommentAntiAuditForm")
	public String interactCommentAntiAuditForm(Model model, String id) {

		InteractComment entity = interactCommentService.getInteractCommentById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("oprt", "AntiAudit");
		return ModuleView("/interactComment/interactCommentForm");
	}
	
	@RequestMapping(value = "/interactCommentSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentSave(Model model, InteractComment entity) {

		try {
			this.interactCommentService.saveInteractComment(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/interactCommentUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentUpdate(@ModelAttribute("entity") InteractComment entity,  Model model) {
		try {
			this.interactCommentService.updateInteractComment(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}
	

	@RequestMapping(value = "/interactCommentAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentAudit(@RequestParam(required = false) String oid,  Model model) {
		try {
			InteractComment entity = interactCommentService.getInteractCommentById(oid);
			LoginUserInfo loginUserInfo = webContext.getCurrentUserInfo();
			entity.setAuditTime(new Date());
			entity.setAuditUserId(loginUserInfo.getUserId());
			entity.setAuditUserName(loginUserInfo.getUserName());
			entity.setState(1);
			this.interactCommentService.updateInteractComment(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}
	

	@RequestMapping(value = "/interactCommentAntiAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentAntiAudit(@RequestParam(required = false) String oid,  Model model) {
		try {
			InteractComment entity = interactCommentService.getInteractCommentById(oid);
			LoginUserInfo loginUserInfo = webContext.getCurrentUserInfo();
			entity.setAuditTime(new Date());
			entity.setAuditUserId(loginUserInfo.getUserId());
			entity.setAuditUserName(loginUserInfo.getUserName());
			entity.setState(0);
			this.interactCommentService.updateInteractComment(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/interactCommentDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentDel(String id) {
		// 删除
		try {
			interactCommentService.delInteractComment(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	/**
	 * 批量审核
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/interactCommentAudits", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentAudits(String[] ids) {
		try {
			LoginUserInfo loginUserInfo = webContext.getCurrentUserInfo();
			interactCommentService.AuditInteractComments(ids, loginUserInfo.getUserId(), loginUserInfo.getUserName(), 1);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	/**
	 * 批量反审核
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/interactCommentAntiAudits", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentAntiAudits(String[] ids) {
		try {
			LoginUserInfo loginUserInfo = webContext.getCurrentUserInfo();
			interactCommentService.AuditInteractComments(ids, loginUserInfo.getUserId(), loginUserInfo.getUserName(), 0);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/interactCommentDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactCommentDels(String[] ids) {
		// 删除
		try {
			interactCommentService.delInteractComments(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public InteractComment preloadBean(@RequestParam(required = false) String oid, org.springframework.ui.Model modelMap) {
		return StringUtil.isNotEmpty(oid) ? interactCommentService.getInteractCommentById(oid) : null;
	}
}