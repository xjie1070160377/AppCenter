package cn.mooc.app.module.sys.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.StringUtil;

@Controller
public class SysUserWebController {
	@Autowired
	private WebContext webContext;
	@Autowired
	private SysDataService sysDataService;
	
	/**
	 * 用户资料修改
	 * @param userExt
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/my/changeExt")
	public String saveUserExt(String nickName, String email, String successUrl, String errorUrl, HttpServletRequest request, Model modelMap){
		modelMap.addAttribute("siteId", request.getParameter("siteId"));
		modelMap.addAttribute("nextUrl", request.getParameter("nextUrl"));
		try {
			Long userId = webContext.getCurrentUserId();
			SysUserExt sysUserExt = sysDataService.getSysUserExt(userId);
			sysUserExt.setNickName(nickName);
			sysUserExt.setEmail(email);
			sysDataService.updateUserExt(sysUserExt);
			modelMap.addAttribute("messages", "用户资料修改成功.");
			return successUrl;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("messages", "用户资料修改失败.");
		}
		modelMap.addAttribute("messages", "用户资料修改失败.");
		return errorUrl;
	}
	@RequestMapping(value = "/my/changePhoto", method = RequestMethod.POST)
	public String saveUserPhone(String successUrl, String errorUrl, HttpServletRequest request, Model modelMap){
		modelMap.addAttribute("siteId", request.getParameter("siteId"));
		modelMap.addAttribute("nextUrl", request.getParameter("nextUrl"));
		try {
			MultipartFile file = getMultipartFile(request);
			UploadResult uploadResult = webContext.uploadFile(file);
			if (uploadResult.isStatus()) {
				Long userId = webContext.getCurrentUserId();
				SysUserExt sysUserExt = sysDataService.getSysUserExt(userId);
				sysUserExt.setAvatarUrl(uploadResult.getFileUrl());
				sysDataService.updateUserExt(sysUserExt);
				modelMap.addAttribute("messages", "头像上传成功.");
				return successUrl;
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("messages", "头像上传失败.");
		}
		modelMap.addAttribute("messages", "头像上传失败.");
		return errorUrl;
	}
	@RequestMapping(value = "/my/changePwd")
	public String changePwd(String oldPwd, String newPwd, String reNewPwd, String successUrl, String errorUrl, HttpServletRequest request, HttpServletResponse response, Model modelMap){
		modelMap.addAttribute("siteId", request.getParameter("siteId"));
		modelMap.addAttribute("nextUrl", request.getParameter("nextUrl"));
		if(StringUtil.isNull(oldPwd)){
			//原密码不能为空
			modelMap.addAttribute("messages", "原密码不能为空.");
			return errorUrl;
		}
		if(StringUtil.isNull(newPwd)){
			//新密码不能为空
			modelMap.addAttribute("messages", "新密码不能为空.");
			return errorUrl;
		}
		if(StringUtil.isNull(reNewPwd)){
			//确认密码不能为空
			modelMap.addAttribute("messages", "确认密码不能为空.");
			return errorUrl;
		}
		if(!newPwd.equals(reNewPwd)){
			//两次密码不一致
			modelMap.addAttribute("messages", "两次密码不一致.");
			return errorUrl;
		}
		if(oldPwd.equals(newPwd)){
			modelMap.addAttribute("messages", "新密码不能与旧密码相同.");
			return errorUrl;
		}
		Long userId = webContext.getCurrentUserId();
		if(sysDataService.validateSysUserPwd(userId, oldPwd)){
			if(sysDataService.changeSysUserPwd(userId, newPwd)){
				//密码修改成功
				modelMap.addAttribute("messages", "密码修改成功.");
				return successUrl;
			}else{
				//密码修改失败
				modelMap.addAttribute("messages", "密码修改失败.");
				return errorUrl;
			}
		}else{
			//原密码不正确
			modelMap.addAttribute("messages", "原密码不正确.");
			return errorUrl;
		}
	}
	
	private MultipartFile getMultipartFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		if (CollectionUtils.isEmpty(fileMap)) {
			throw new IllegalStateException("No upload file found!");
		}
		return fileMap.entrySet().iterator().next().getValue();
	}
}
