package cn.mooc.app.module.cms.mcenter.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.InfoSpecialService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.PathResolver;

@Controller
@RequestMapping("/cms/selector")
public class SelectorController extends CmsModuleController {

	public static final String ACCEPTEDFILES_VIDEO = "video/*";
	public static final String ACCEPTEDFILES_AUDIO = "audio/*";

	@Autowired
	private NodeService nodeService;
	@Autowired
	private InfoSpecialService infoSpecialService;
	@Autowired
	protected PathResolver pathResolver;

	/**
	 * 栏目选择
	 * 
	 * @param id
	 * @param callback
	 * @param selectedId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/nodeTag")
	public String nodeTag(String id, String callback, Integer selectedId, Model modelMap) {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("callback", callback);
		modelMap.addAttribute("selectedId", selectedId);
		return ModuleView("/selector/nodeTag");
	}
	@RequestMapping("/nodeTag2")
	public String nodeTag2(String id, String callback, Integer selectedId, Model modelMap) {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("callback", callback);
		modelMap.addAttribute("selectedId", selectedId);
		return ModuleView("/selector/nodeTag");
	}
	
	@RequestMapping("/nodeSelector")
	public String nodeSelector(){
		return ModuleView("/selector/nodeSelector");
	}
	
	/**
	 * 模型选择
	 * 
	 * @param id
	 * @param callback
	 * @param selectedId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/templateTag")
	public String templateTag(String id, String callback, String selectedId, Model modelMap) {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("callback", callback);
		modelMap.addAttribute("selectedId", selectedId);
		return ModuleView("/selector/templateTag");
	}

	/**
	 * 单图片上传
	 * 
	 * @param id
	 * @param width
	 * @param height
	 * @param value
	 * @param scale
	 * @param stretch
	 * @param watermark
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/imgUploadTag")
	public String imgUploadTag(String id, String width, String height, String value, Boolean scale, Boolean stretch, Boolean watermark, Boolean original, Model modelMap) {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("width", "undefined".equals(width) ? "" : width);
		modelMap.addAttribute("height", "undefined".equals(height) ? "" : height);
		modelMap.addAttribute("value", value);
		modelMap.addAttribute("scale", scale);
		modelMap.addAttribute("stretch", stretch);
		modelMap.addAttribute("watermark", watermark);
		modelMap.addAttribute("original", original);
		return ModuleView("/selector/imgUploadTag");
	}

	/**
	 * 单文件上传
	 * 
	 * @param id
	 * @param fileNameId
	 * @param fileName
	 * @param fileUrlId
	 * @param fileUrl
	 * @param fileSizeId
	 * @param fileSize
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/fileUploadTag")
	public String fileUploadTag(String id, String fileNameId, String fileName, String fileUrlId, String fileUrl, String fileSizeId, String fileSize, Model modelMap) throws UnsupportedEncodingException {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("fileNameId", fileNameId);
		fileName = URLDecoder.decode(fileName,"UTF-8") ;
		modelMap.addAttribute("fileName", fileName);
		modelMap.addAttribute("fileUrlId", fileUrlId);
		modelMap.addAttribute("fileUrl", fileUrl);
		modelMap.addAttribute("fileSizeId", fileSizeId);
		modelMap.addAttribute("fileSize", fileSize);
		return ModuleView("/selector/fileUploadTag");
	}

	/**
	 * 单视频上传
	 * 
	 * @param id
	 * @param fileNameId
	 * @param fileName
	 * @param fileUrlId
	 * @param fileUrl
	 * @param fileSizeId
	 * @param fileSize
	 * @param fileTimeId
	 * @param fileTime
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/videoUploadTag")
	public String videoUploadTag(String id, String fileNameId, String fileName, String fileUrlId, String fileUrl, String fileSizeId, String fileSize, String fileTimeId, String fileTime, Model modelMap) throws UnsupportedEncodingException {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("fileNameId", fileNameId);
		fileName = URLDecoder.decode(fileName,"UTF-8") ;
		modelMap.addAttribute("fileName", fileName);
		modelMap.addAttribute("fileUrlId", fileUrlId);
		modelMap.addAttribute("fileUrl", fileUrl);
		modelMap.addAttribute("fileSizeId", fileSizeId);
		modelMap.addAttribute("fileSize", fileSize);
		modelMap.addAttribute("fileTimeId", fileTimeId);
		modelMap.addAttribute("fileTime", fileTime);
		modelMap.addAttribute("acceptedFiles", ACCEPTEDFILES_VIDEO);
		return ModuleView("/selector/fileUploadTag");
	}
	
	/**
	 * 音频上传
	 * @param id
	 * @param fileNameId
	 * @param fileName
	 * @param fileUrlId
	 * @param fileUrl
	 * @param fileSizeId
	 * @param fileSize
	 * @param fileTimeId
	 * @param fileTime
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/audioUploadTag")
	public String audioUploadTag(String id, String fileNameId, String fileName, String fileUrlId, String fileUrl, String fileSizeId, String fileSize, String fileTimeId, String fileTime, Model modelMap) throws UnsupportedEncodingException {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("fileNameId", fileNameId);
		fileName = URLDecoder.decode(fileName,"UTF-8") ;
		modelMap.addAttribute("fileName", fileName);
		modelMap.addAttribute("fileUrlId", fileUrlId);
		modelMap.addAttribute("fileUrl", fileUrl);
		modelMap.addAttribute("fileSizeId", fileSizeId);
		modelMap.addAttribute("fileSize", fileSize);
		modelMap.addAttribute("fileTimeId", fileTimeId);
		modelMap.addAttribute("fileTime", fileTime);
		modelMap.addAttribute("acceptedFiles", ACCEPTEDFILES_AUDIO);
		return ModuleView("/selector/fileUploadTag");
	}

	/**
	 * 图集上传
	 * 
	 * @param id
	 * @param width
	 * @param height
	 * @param imgListJson
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/imagesUploadTag")
	public String imagesUploadTag(String id, String width, String height, String imgListJson, String stretch, String watermark, String scale, String url, Model modelMap) throws Exception {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("width", "undefined".equals(width) ? "" : width);
		modelMap.addAttribute("height", "undefined".equals(height) ? "" : height);
		modelMap.addAttribute("stretch", "undefined".equals(stretch) ? "false" : stretch);
		modelMap.addAttribute("watermark", "undefined".equals(watermark) ? "false" : watermark);
		modelMap.addAttribute("scale", "undefined".equals(scale) ? "false" : scale);
		modelMap.addAttribute("url", "undefined".equals(url) ? "" : url);
		List imgList = new ArrayList();
		if(StringUtil.isNotEmpty(imgListJson)){
			imgListJson = imgListJson.replaceAll("'", "\"");
			imgList = JsonUtil.fromJson(imgListJson, List.class);
		}
		modelMap.addAttribute("imgList", imgList);
		return ModuleView("/selector/imagesUploadTag");
	}

	@RequestMapping("/specialSelector")
	public String specialSelector(String id, String callback, Integer infoId, Model modelMap) throws Exception {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("callback", callback);
		modelMap.addAttribute("infoId", infoId);
		return ModuleView("/selector/specialSelector");
	}

	/**
	 * 树型栏目选择器数据获取
	 * 
	 * @param queryParentId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/nodeTreeJson")
	@ResponseBody
	public List<Map<String, Object>> nodeTreeJson(Integer queryParentId, Model model) {
		Integer siteId = getCurrentSiteId();
		List<Map<String, Object>> tree = nodeService.getNodesForJstree(siteId, queryParentId, false, null);
		return tree;
	}

	/**
	 * 树型栏目选择器数据获取
	 * 
	 * @param queryParentId
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/templateTreeJson")
	@ResponseBody
	public List<Map<String, Object>> templateTreeJson(String template) throws IOException {
		Site site = getCurrentSite();
		File baseFile = new File(pathResolver.getPath(site.getTemplate(null)));
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("id", "root");
		rootMap.put("text", "root");
		Map<String, Object> rootMap1 = new HashMap<String, Object>();
		rootMap1.put("opened", true);
		rootMap.put("state", rootMap1);

		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
		if (baseFile.isDirectory()) {
			File[] files = baseFile.listFiles();
			for (File file : files) {
				if(file.isFile()){
					Map<String, Object> map = new HashMap<String, Object>();
					String fileName = file.getName();
					if (fileName.lastIndexOf(".") > 0) {
						fileName = fileName.substring(0, fileName.lastIndexOf("."));
					}
					map.put("id", fileName);
					map.put("text", file.getName());
					Map<String, Object> map1 = new HashMap<String, Object>();
					if (StringUtils.isNotEmpty(template) && template.equals("/" + file.getName())) {
						map1.put("selected", true);
					}
					map.put("state", map1);
					children.add(map);
				}
			}
		}
		rootMap.put("children", children);
		tree.add(rootMap);
		return tree;
	}

	/**
	 * 专题选择器数据获取
	 * 
	 * @param infoId
	 * @return
	 */
	@RequestMapping(value = "/findSpecialList")
	@ResponseBody
	public List<Map<String, Object>> findSpecialList(Integer infoId) {
		Integer siteId = getCurrentSiteId();
		List<Map<String, Object>> list = infoSpecialService.findSpecialList(infoId, siteId);
		return list;
	}
}
