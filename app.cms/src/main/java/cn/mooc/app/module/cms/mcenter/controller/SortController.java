package cn.mooc.app.module.cms.mcenter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoAttribute;
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.model.InfoModel;
import cn.mooc.app.module.cms.service.AttributeService;
import cn.mooc.app.module.cms.service.InfoAttributeService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.interact.service.InteractCommentService;

@Controller
@RequestMapping("/cms/sort")
public class SortController extends CmsModuleController {
	
	@Autowired
	private InfoService infoService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private InfoAttributeService infaService;
	
	@RequestMapping("/column_list")
	public String infoList(Integer queryNodeId, Model modelMap, PagerParam pageParam) {
		
		Integer siteId = getCurrentSiteId();
		if (queryNodeId == null) {
			Node node = nodeService.findRoot(siteId);
			if (node != null) {
				queryNodeId = node.getId();
			}
		}
		modelMap.addAttribute("queryNodeId", queryNodeId);
		return ModuleView("/sort/column_list");
	}
	
	@RequestMapping(value = "/nodeTreeJson")
	@ResponseBody
	public List<Map<String, Object>> nodeTreeJson(Integer queryNodeId, Model model) {
		Integer siteId = getCurrentSiteId();
		List<Map<String, Object>> tree = nodeService.getNodesForJstree(siteId, queryNodeId, false, null);
		return tree;
	}

	@RequestMapping("/infoListJson")
	@ResponseBody
	public Map<String, Object> infoListJson(Integer queryNodeId,Model model, PagerParam pageParam, String title) throws Exception {
		int siteId = getCurrentSiteId();
		Node node;
		if (queryNodeId == null) {
			node = nodeService.findRoot(siteId);
			if (node == null) {
				throw new Exception("请先增加站点栏目再进行文章管理");
			}
		} else {
			node = nodeService.getNodeById(queryNodeId);
		}
		Page<Info> pageData = infoService.findColumnSortInfoPage(title, pageParam, queryNodeId, siteId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<InfoModel> modeLs = new ArrayList<InfoModel>();
		for (Info info : pageData.getContent()) {
			InfoModel infomodel = new InfoModel();
			infomodel.setId(info.getId());
			infomodel.setNodeName(info.getNode().getDisplayName());
			infomodel.setCreatorId(info.getCreatorId());
			
			infomodel.setTitle(info.getTitle());
			infomodel.setModeName(info.getModel().getName());
			infomodel.setPublishDate(sdf.format(info.getPublishDate()));
			infomodel.setPriority(info.getPriority());
			infomodel.setViews(info.getViews());
			infomodel.setStatus(info.getStatus());
			infomodel.setColumnSort(info.getColumnSort());
			modeLs.add(infomodel);
		}

		return HttpResponseUtil.successJson(modeLs, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	@RequestMapping("/infoView")
	public String infoView(Integer id, Integer queryNodeId, Boolean showDescendants, String queryStatus, Model modelMap) throws Exception {
		Info bean = infoService.getInfoById(id);
		SysUserEntity creator = sysDataService.getUserInfoById(bean.getCreatorId());
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		InteractCommentService interactCommentService = (InteractCommentService)webApplicationContext.getBean(InteractCommentService.class);
		Integer siteId = siteService.getCurrentSite().getId();
		if(siteId == null){
			siteId = 1;
		}
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("creator", creator);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute("queryStatus", queryStatus);
		modelMap.addAttribute("siteId", siteId);
		return ModuleView("/sort/info_view");
	}
	
	@RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchUpdate(String str) {
		try {
			if (StringUtil.isNotEmpty(str)) {
				infoService.updateSort(str);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	
	@RequestMapping(value = "/colTop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> colTop(Integer id) {
		try {
			if (StringUtil.isNotEmpty(id)) {
				infoService.colTop(id);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("attr_info_list")
	public String list(Model modelMap) {
		Integer siteId = getCurrentSiteId();
		List<Attribute> attrs = attributeService.findBySiteId(siteId);
		List<Map> typeList = new ArrayList<Map>();
		for(Attribute attr : attrs){
			Map params = new HashMap();
			params.put("id", attr.getId());
			params.put("name", attr.getName());
			typeList.add(params);
		}
		modelMap.addAttribute("typeList", typeList);
		return ModuleView("/sort/attrInfo_list");
	}
	
	@RequestMapping("/attrInfoListJson")
	@ResponseBody
	public Map<String, Object> attrInfoListJson(Integer attrId,Model model, PagerParam pageParam, String title) throws Exception {
		pageParam.setSort(new Sort("seq"));
		Page<InfoAttribute> pageData = infaService.pageAttrinfoBySort(title, pageParam, attrId);
		List<Map> alist = new ArrayList<Map>();
		for(InfoAttribute infoa : pageData.getContent()){
			Map params = new HashMap();
			params.put("id", infoa.getId());
			params.put("seq", infoa.getSeq());
			params.put("infoId", infoa.getInfo().getId());
			params.put("title", infoa.getInfo().getTitle());
			params.put("publishdate", infoa.getInfo().getPublishDate());
			params.put("views", infoa.getInfo().getViews());
			params.put("status", infoa.getInfo().getStatus());
			alist.add(params);
		}
		return HttpResponseUtil.successJson(alist, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	@RequestMapping(value = "/batchAttrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchAttrUpdate(String str) {
		try {
			if (StringUtil.isNotEmpty(str)) {
				infaService.updateSort(str);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/topInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> topInfo(Integer id) {
		try {
			if (StringUtil.isNotEmpty(id)) {
				infaService.setTop(id);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

}
