package cn.mooc.app.module.cms.mcenter.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.InfoSource;
import cn.mooc.app.module.cms.service.InfoSourceService;
import cn.mooc.app.module.cms.util.JqGridHandler;

/**
 * InfoSourceController 来源控制器
 * 
 * @author oyhx
 *
 */
@Controller
@RequestMapping("/cms/source")
public class InfoSourceController extends CmsModuleController {
	private static final Logger logger = LoggerFactory.getLogger(InfoSourceController.class);

	/**
	 * 文章来源列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(Model model) {
		return ModuleModelAndView("/source/list");
	}

	/**
	 * 文章来源列表数据获取
	 * 
	 * @param model
	 * @param pageParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> page(Model model, PagerParam pageParam, HttpServletRequest request) {
		Map<String, Object> searchParams = JqGridHandler.getQueryWebParameter(request);

		Page<InfoSource> pageData = service.findInfoSourcePage(searchParams, pageParam);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	/**
	 * 文章来源编辑页面
	 * 
	 * @param model
	 * @param infoSource
	 * @return
	 */
	@RequestMapping("/form")
	public String form(Model model, InfoSource infoSource, Integer id) {
		if (id != null) {
			infoSource = service.get(id);
		}
		model.addAttribute("entity", infoSource);

		return ModuleView("/source/form");
	}

	/**
	 * 来源保存
	 * 
	 * @param infoSource
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(InfoSource infoSource) {
		try {
			service.save(infoSource);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			return HttpResponseUtil.failureJson("保存失败.");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping("/del")
	@ResponseBody
	public boolean del(Integer id) {
		boolean result = service.delete(id);
		return result;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity uploadFile(MultipartFile file) {
		try {
			// Iterator<String> itr = request.getFileNames();
			// while (itr.hasNext()) {
			// String uploadedFile = itr.next();
			// MultipartFile file = request.getFile(uploadedFile);
			// String mimeType = file.getContentType();
			// String filename = file.getOriginalFilename();
			// byte[] bytes = file.getBytes();
			UploadResult uploadResult = webContext.uploadFile(file);
			// uploadResult.setStatus(false);
			// uploadResult.setMsg("上传错了，重试也是错的");
			if (uploadResult.isStatus()) {
				return new ResponseEntity<>("{\"fileUrl\": \"" + uploadResult.getFileUrl() + "\" }", HttpStatus.OK);
			} else {
				// 返回失败消息
				return new ResponseEntity<>("{\"error\": \"" + uploadResult.getMsg() + ".\" }",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("{\"error\": \"文件上传失败.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 暂时放了树形表格示例页面，不需要时请删除
	 * 
	 * @param model
	 * @param infoSource
	 * @return
	 */
	@RequestMapping("/form2")
	public String form2(Model model, InfoSource infoSource) {
		model.addAttribute("entity", infoSource);

		return ModuleView("/source/form2");
	}

	/**
	 * 暂时放了文件上传示例页面，不需要时请删除
	 * 
	 * @param model
	 * @param infoSource
	 * @return
	 */
	@RequestMapping("/uploadTest")
	public String uploadTest(Model model, InfoSource infoSource) {
		model.addAttribute("entity", infoSource);

		return ModuleView("/source/uploadTest");
	}

	/**
	 * 暂时放了文件上传示例页面，不需要时请删除
	 * 
	 * @param model
	 * @param infoSource
	 * @return
	 */
	@RequestMapping("/uploadTest2")
	public String uploadTest2(Model model, InfoSource infoSource) {
		model.addAttribute("entity", infoSource);

		return ModuleView("/source/uploadTest2");
	}

	@Autowired
	private InfoSourceService service;
}