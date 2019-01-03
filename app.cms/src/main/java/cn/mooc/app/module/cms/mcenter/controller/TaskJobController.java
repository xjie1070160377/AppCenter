package cn.mooc.app.module.cms.mcenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.data.entity.TaskJob;
import cn.mooc.app.core.service.TaskJobService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

@Controller
@RequestMapping("/cms/task")
public class TaskJobController extends CmsModuleController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskJobService taskJobService;
	
	@RequestMapping("/videoJobList")
	public String videoJobList(Model model) {

		return ModuleView("/task/videoJobList");
	}
	
	
	@RequestMapping("/videoJobListJson")
	@ResponseBody
	public Map<String, Object> videoJobListJson(Model model,PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_jobType", 1);
		Page<TaskJob> pageData = taskJobService.findTaskJobList(searchParams, pageParam);
        		 
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/videoPreview")
	public String videoPreview(Model model, Long jobId, String mediaUrl) {
		logger.debug("jobId：{} mediaUrl：{}", jobId, mediaUrl);
		
		model.addAttribute("jobId", jobId);
		model.addAttribute("mediaUrl", mediaUrl);
		
		return ModuleView("/task/videoPreview");
		
	}
	
	
	
}
