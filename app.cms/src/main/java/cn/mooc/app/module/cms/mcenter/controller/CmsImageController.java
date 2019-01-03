package cn.mooc.app.module.cms.mcenter.controller;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;

import cn.mooc.app.module.cms.data.entity.CmsImage;
import cn.mooc.app.module.cms.data.entity.CmsImageType;
import cn.mooc.app.module.cms.model.CmsImageModel;
import cn.mooc.app.module.cms.service.CmsImageService;
import cn.mooc.app.module.cms.service.CmsImageTypeService;
import cn.mooc.app.core.annotation.SameUrlData;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CmsImageController
 * 图片详情控制器
 * 
 * @author linwei
 * @date 2017-05-27
 */
@Controller
@RequestMapping("/cms/cmsImage")
public class CmsImageController extends CmsModuleController {

	@Autowired
	private CmsImageService cmsImageService;
	@Autowired
	private CmsImageTypeService cmsImageTypeService;

	/**
	 * @param model
	 * @param pageParam
	 * @param layout 0:缩略图， 1:列表
	 * @return
	 */
	@RequestMapping("/cmsImageList")
	public String cmsImageList(Model model, PagerParam pageParam, Integer layout) {
		if(layout == null){
			layout = 0;
		}
		List<CmsImageType> typeList = cmsImageTypeService.getAllCmsImageTypes();
		model.addAttribute("typeList", typeList);
		model.addAttribute("layout", layout);
		if(layout == 0){
			return ModuleView("/cmsImage/cmsImageList_grid");
		}else{
			return ModuleView("/cmsImage/cmsImageList_list");
		}
	}

	@RequestMapping("/cmsImageListJson")
	@ResponseBody
	public Map<String, Object> cmsImageListJson(Model model, PagerParam pageParam, 
			Integer typeId,String columnFiled, String keyWord, String startTime, String endTime) {
		try {
			SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			if(StringUtils.isNoneBlank(keyWord)){
				keyWord=URLDecoder.decode(keyWord,"UTF-8");
				searchParams.put(columnFiled, keyWord);
			}
			if (StringUtils.isNotEmpty(startTime)) {
				searchParams.put(SpecOperator.GE + "_createTime", dateFmt.parse(startTime));
			}
			if (StringUtils.isNotEmpty(endTime)) {
				searchParams.put(SpecOperator.LT + "_createTime", dateFmt.parse(endTime));
			}
			if(typeId != null && typeId != 0){
				searchParams.put(SpecOperator.EQ + "_cmsImageType.id", typeId);
			}
			Page<CmsImage> pageData = cmsImageService.findCmsImagePage(searchParams, pageParam);
			List<CmsImageModel> mlist = pageData.getContent().stream().map(p -> new CmsImageModel(p)).collect(Collectors.toList());
			return HttpResponseUtil.successJson(mlist, pageData, pageParam);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}

	@RequestMapping("/cmsImageAdd")
	public String cmsImageAdd(Model model) {
		List<CmsImageType> typeList = cmsImageTypeService.getAllCmsImageTypes();
		model.addAttribute("entity", new CmsImage());
		model.addAttribute("typeList", typeList);
		return ModuleView("/cmsImage/cmsImageForm");
	}
	
	@RequestMapping("/batchAdd")
	public String batchAdd(Model model) {
		List<CmsImageType> typeList = cmsImageTypeService.getAllCmsImageTypes();
		model.addAttribute("typeList", typeList);
		return ModuleView("/cmsImage/batchAddForm");
	}

	@RequestMapping("/cmsImageEdit")
	public String cmsImageEdit(Model model, Integer id) {
		List<CmsImageType> typeList = cmsImageTypeService.getAllCmsImageTypes();
		CmsImage entity = cmsImageService.getCmsImageById(id);
		model.addAttribute("typeList", typeList);
		model.addAttribute("entity", entity);
		return ModuleView("/cmsImage/cmsImageForm");
	}

	@RequestMapping(value = "/cmsImageSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageSave(HttpServletRequest request, Model model, CmsImage entity, Integer typeId, String fileSize) {
		try {
			if(StringUtils.isBlank(entity.getUrl())){
				return HttpResponseUtil.failureJson("图片地址不能为空！");
			}
			if(typeId == null){
				return HttpResponseUtil.failureJson("图片分类不能为空！");
			}
			CmsImageType type = cmsImageTypeService.getCmsImageTypeById(typeId);
			
			if(StringUtils.isNoneBlank(fileSize)){
				entity.setFileSize(Integer.parseInt(fileSize));
			}
			entity.setSite(getCurrentSite());
			entity.setIp(ValidatorUtil.getIpAddr(request));
			entity.setCmsImageType(type);
			entity.setCreateTime(new Date());
			entity.setCreatorId(webContext.getCurrentIntUserId());
			this.cmsImageService.saveCmsImage(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("新增失败");
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
	@SameUrlData
	@ResponseBody
	public Map<String, Object> batchSave(HttpServletRequest request, Model model, String imgStr, Integer typeId) {
		try {
			if(StringUtils.isEmpty(imgStr)){
				return HttpResponseUtil.failureJson("图片不能为空");
			}
			List<Map<String, Object>> imgList = (List<Map<String, Object>>) JSONUtils.parse(imgStr);
			for(Map<String, Object> map : imgList){
				CmsImage entity = new CmsImage();
				String url = map.get("url").toString();
				String fileName = map.get("fileName").toString();
				Integer fileSize = Integer.parseInt(map.get("fileSize").toString());
				if(StringUtils.isBlank(url)){
					return HttpResponseUtil.failureJson("图片地址不能为空！");
				}
				if(typeId == null){
					return HttpResponseUtil.failureJson("图片分类不能为空！");
				}
				CmsImageType type = cmsImageTypeService.getCmsImageTypeById(typeId);
				entity.setSite(getCurrentSite());
				entity.setIp(ValidatorUtil.getIpAddr(request));
				entity.setCmsImageType(type);
				entity.setCreateTime(new Date());
				entity.setCreatorId(webContext.getCurrentIntUserId());
				entity.setFileSize(fileSize);
				entity.setUrl(url);
				entity.setTitle(fileName);
				this.cmsImageService.saveCmsImage(entity);
			}
			
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("新增失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/cmsImageUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageUpdate(@ModelAttribute("entity") CmsImage entity,  Model model) {
		try {
			this.cmsImageService.updateCmsImage(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/cmsImageDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageDel(Integer id) {
		// 删除
		try {
			cmsImageService.delCmsImage(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/cmsImageDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageDels(Integer[] ids) {
		// 删除
		try {
			cmsImageService.delCmsImages(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public CmsImage preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? cmsImageService.getCmsImageById(oid) : null;
	}
}