package cn.mooc.app.module.cms.mcenter.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.tools.internal.xjc.model.CElementPropertyInfo.CollectionMode;

import cn.mooc.app.module.cms.data.entity.Collect;
import cn.mooc.app.module.cms.data.entity.CollectField;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.CollectModel;
import cn.mooc.app.module.cms.service.CollectService;
import cn.mooc.app.module.cms.service.CollectorService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.service.SnsOpLogService;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CollectController
 * 采集控制器
 * 
 * @author linwei
 * @date 2017-06-22
 */
@Controller
@RequestMapping("/cms/collect")
public class CollectController extends CmsModuleController {

	@Autowired
	private CollectService collectService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private SnsOpLogService snsOpLogService;
	@Autowired
	private CollectorService collector;

	@RequestMapping("/collectList")
	public String collectList(Model model, PagerParam pageParam) {
		return ModuleView("/collect/collectList");
	}

	@RequestMapping("/collectListJson")
	@ResponseBody
	public Map<String, Object> collectListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		Page<Collect> pageData = collectService.findCollectPage(searchParams, pageParam);
		List<CollectModel> mList = pageData.getContent().stream().map(p -> new CollectModel(p)).collect(Collectors.toList());
		return HttpResponseUtil.successJson(mList, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}

	@RequestMapping("/collectAdd")
	public String collectAdd(Model model) {
		model.addAttribute("entity", new Collect());

		return ModuleView("/collect/collectForm");
	}

	@RequestMapping("/collectEdit")
	public String collectEdit(Model model, Integer id) {

		Collect entity = collectService.getCollectById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/collect/collectForm");
	}

	@RequestMapping(value = "/collectSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectSave(Model model, Collect entity, Integer nodeId) {
		try {
			if(nodeId == null){
				return HttpResponseUtil.failureJson("nodeId can not be null!");
			}
			Node node = nodeService.getNodeById(nodeId);
			entity.setUserId(webContext.getCurrentIntUserId());
			entity.setNode(node);
			entity.setSite(getCurrentSite());
			entity.applyDefaultValue();
			this.collectService.saveCollect(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/collectUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectUpdate(@ModelAttribute("entity") Collect entity, Integer nodeId, Model model) {
		try {
			if(nodeId != null){
				Node node = nodeService.getNodeById(nodeId);
				entity.setNode(node);
			}
			entity.applyDefaultValue();
			this.collectService.updateCollect(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/collectDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectDel(Integer id) {
		// 删除
		try {
			collectService.delCollect(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/collectDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectDels(Integer[] ids) {
		// 删除
		try {
			collectService.delCollects(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	
	@RequestMapping("/list_pattern_dialog")
	public String listPatternDialog(String listPattern, Integer pageBegin, Integer pageEnd, String charset,
			String userAgent, String areaId, String itemId, @RequestParam(defaultValue = "true") boolean desc,
			org.springframework.ui.Model modelMap) throws ClientProtocolException, IOException {
		List<String> urls = Collect.getListUrls(listPattern, pageBegin, pageEnd, desc);
		modelMap.addAttribute("urls", urls);
		modelMap.addAttribute("charset", charset);
		modelMap.addAttribute("userAgent", userAgent);
		modelMap.addAttribute("areaId", areaId);
		modelMap.addAttribute("itemId", itemId);
		return ModuleView("/collect/collect_pattern_dialog");
	}

	@RequestMapping("/item_pattern_dialog")
	public String itemPatternDialog(Integer collectId, String filterId, String areaId, String itemId,
			org.springframework.ui.Model modelMap) throws ClientProtocolException, IOException, URISyntaxException {
		Collect collect = collectService.getCollectById(collectId);
		List<URI> listUris = collect.getListUris();
		List<String> urls = new ArrayList<String>();
		if (listUris.size() > 0) {
			URI uri = listUris.get(0);
			String html = Collect.fetchHtml(uri, collect.getCharset(), collect.getUserAgent());
			List<URI> itemUris = collect.getItemUris(html, uri);
			for (URI itemUri : itemUris) {
				urls.add(itemUri.toString());
			}
		}
		modelMap.addAttribute("urls", urls);
		modelMap.addAttribute("charset", collect.getCharset());
		modelMap.addAttribute("userAgent", collect.getUserAgent());
		modelMap.addAttribute("filterId", filterId);
		modelMap.addAttribute("areaId", areaId);
		modelMap.addAttribute("itemId", itemId);
		return ModuleView("/collect/collect_pattern_dialog");
	}

	@RequestMapping("/id_pattern_dialog")
	public String idPatternDialog(Integer collectId, String idPattern, boolean idReg, String idUrl, boolean isUrlType,
			String filterId, String areaId, String itemId, org.springframework.ui.Model modelMap)
			throws ClientProtocolException, IOException, URISyntaxException {
		Collect collect = collectService.getCollectById(collectId);
		String charset = collect.getCharset();
		String userAgent = collect.getUserAgent();
		List<URI> listUris = collect.getListUris();
		List<String> urls = new ArrayList<String>();
		if (listUris.size() > 0) {
			URI uri = listUris.get(0);
			String html = Collect.fetchHtml(uri, charset, userAgent);
			List<URI> itemUris = collect.getItemUris(html, uri);
			for (URI itemUri : itemUris) {
				html = Collect.fetchHtml(itemUri, charset, userAgent);
				String id = Collect.findFirst(html, idPattern, idReg);
				urls.add(StringUtils.replace(idUrl, "{id}", id));
			}
		}
		modelMap.addAttribute("urls", urls);
		modelMap.addAttribute("charset", collect.getCharset());
		modelMap.addAttribute("userAgent", collect.getUserAgent());
		modelMap.addAttribute("filterId", filterId);
		modelMap.addAttribute("areaId", areaId);
		modelMap.addAttribute("itemId", itemId);
		return ModuleView("/collect/collect_pattern_dialog");
	}

	@RequestMapping("/find_text")
	public void findText(String source, String search, @RequestParam(defaultValue = "false") boolean isReg,
			@RequestParam(defaultValue = "true") boolean isFirst, HttpServletResponse response) {
		StringBuilder result = new StringBuilder();
		if (isFirst) {
			result.append(Collect.findFirst(source, search, isReg));
		} else {
			for (String s : Collect.find(source, search, isReg)) {
				result.append(s).append('\n');
			}
			if (result.length() > 1) {
				result.setLength(result.length() - 1);
			}
		}
		Servlets.writeHtml(response, result.toString());
	}

	@RequestMapping("/filter_text")
	public void filterText(String source, String filter, HttpServletResponse response) {
		List<Pattern> patterns = CollectField.getFilterPattern(filter);
		String result = CollectField.applyFilter(patterns, source);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping("/fetch_url")
	public void fetchUrl(String url, String charset, String userAgent, HttpServletResponse response) {
		String source;
		try {
			source = Collect.fetchHtml(URI.create(url), charset, userAgent);
		} catch (Exception e) {
			source = e.getMessage();
		}
		Servlets.writeHtml(response, source);
	}

	@RequestMapping("/start")
	@ResponseBody
	public Map<String, Object> start(Integer[] ids, HttpServletRequest request, RedirectAttributes ra) {
		Site site = getCurrentSite();
		Collect bean;
		for (Integer id : ids) {
			collector.start(id, getCurrentSiteId());
			bean = collectService.getCollectById(id);
			snsOpLogService.operation(site.getId(), webContext.getCurrentIntUserId(), 
					SnsOpLog.FTYPE_COLLECT_START, id, "开始采集:".concat(bean.getName()), "");
		}
		return HttpResponseUtil.successJson("操作成功!");
	}

	@RequestMapping("/stop")
	@ResponseBody
	public Map<String, Object> stop(Integer[] ids, HttpServletRequest request, RedirectAttributes ra) {
		Site site = getCurrentSite();
		Collect bean;
		for (Integer id : ids) {
			collectService.ready(id);
			bean = collectService.getCollectById(id);
			snsOpLogService.operation(site.getId(), webContext.getCurrentIntUserId(), 
					SnsOpLog.FTYPE_COLLECT_STOP, id, "停止采集:".concat(bean.getName()), "");
		}
		return HttpResponseUtil.successJson("操作成功!");
	}

	@RequestMapping("schedule_job.do")
	public String scheduleJob(HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = getCurrentSiteId();
		List<Collect> collectList = collectService.findList(siteId);
		modelMap.addAttribute("collectList", collectList);
		modelMap.addAttribute("includePage", "../../ext/collect/collect_job.jsp");
		return ModuleView("/schedule_job/schedule_job_form");
	}
	

	@ModelAttribute("entity")
	public Collect preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? collectService.getCollectById(oid) : null;
	}
}