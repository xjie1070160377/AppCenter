package cn.mooc.app.module.widget.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.MobileArticleInfo;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.service.InfoBufferService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.InfoSpecialService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.event.selector.DiggEventSelector;
import cn.mooc.app.module.interact.event.selector.UnDiggEventSelector;

@RequestMapping("/widget")
@Controller
public class InfoShareController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InfoSpecialService infoSpecialService;
	@Autowired
	private InfoBufferService infoBufferService;
	@Autowired
	private InfoService infoQueryService;
	
	@RequestMapping("/share/detail/{articleId}")
	public String shareDetail(Model model,HttpServletRequest request,HttpServletResponse response, @PathVariable int articleId) {
		
		String tplArticleDetailNotice = "/widget/cms/articleDetailNotice.html";
		
		if (articleId == 0) {
			model.addAttribute("msg", "请求参数不正确");
			return tplArticleDetailNotice;
		}
		
		logger.debug("请求articleId：{}", articleId);
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_id", articleId);
		//searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
		//searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{3}));
		Info info = infoQueryService.findOne(searchParams);
		
		if (info == null) {
			//显示禁止查看信息
			model.addAttribute("msg", "请求的信息内容不存在");
			return tplArticleDetailNotice;
		}
		
		if(!info.getStatus().equals(Info.NORMAL)){
			model.addAttribute("msg", "请求的内容暂未通过");
			return tplArticleDetailNotice;
		}
		
		MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, true, false);
		if(articleInfo.getInfoLevel()!=null && articleInfo.getInfoLevel().equals(3)){
			//只有设置了允许校外分享，才可以被分享
			infoBufferService.updateViews(articleId);
			infoSpecialService.updateViewsByInfoId(articleId);
					
			model.addAttribute("articleInfo", articleInfo);
			return "/widget/cms/articleShare.html";
			//return "/widget/cms/articleShareDetail.html";
		}else{
			//显示禁止查看信息
			model.addAttribute("msg", "该信息目前禁止校外访问");
			return tplArticleDetailNotice;
		}		
	}
	
	@RequestMapping("/link/detail/{articleId}")
	public String linkDetail(Model model,HttpServletRequest request,HttpServletResponse response, @PathVariable int articleId) {
		
		String tplArticleDetailNotice = "/widget/cms/articleDetailNotice.html";
		
		if (articleId == 0) {
			model.addAttribute("msg", "请求参数不正确");
			return tplArticleDetailNotice;
		}
		
		logger.debug("请求articleId：{}", articleId);
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_id", articleId);
		//searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
		//searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{3}));
		Info info = infoQueryService.findOne(searchParams);
		
		if (info == null) {
			//显示禁止查看信息
			model.addAttribute("msg", "请求的信息内容不存在");
			return tplArticleDetailNotice;
		}
		
		if(!info.getStatus().equals(Info.NORMAL)){
			model.addAttribute("msg", "请求的内容暂未通过");
			return tplArticleDetailNotice;
		}
		
		MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, true, false);
		if(articleInfo.getInfoLevel()!=null && articleInfo.getInfoLevel().equals(3)){
			//只有设置了允许校外分享，才可以被分享
			infoBufferService.updateViews(articleId);
			infoSpecialService.updateViewsByInfoId(articleId);
					
			model.addAttribute("articleInfo", articleInfo);
			
			return "/widget/cms/articlePreviewDetail.html";
		}else{
			//显示禁止查看信息
			model.addAttribute("msg", "超链接显示的文章必须为校外可见");
			return tplArticleDetailNotice;
		}

		
	}
	
	//@RequestMapping("/preview/detail/{articleId}")
	public String previewDetail(Model model,HttpServletRequest request,HttpServletResponse response, @PathVariable int articleId) {
		//测试时开启该页面访问
		
		String tplArticleDetailNotice = "/widget/cms/articleDetailNotice.html";
		
		if (articleId == 0) {
			model.addAttribute("msg", "请求参数不正确");
			return tplArticleDetailNotice;
		}
		
		logger.debug("请求articleId：{}", articleId);
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_id", articleId);
		Info info = infoQueryService.findOne(searchParams);
		
		if (info == null) {
			//显示禁止查看信息
			model.addAttribute("msg", "请求的信息内容不存在");
			return tplArticleDetailNotice;
		}
		
		
		MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, true, false);
		infoBufferService.updateViews(articleId);
		infoSpecialService.updateViewsByInfoId(articleId);
				
		model.addAttribute("articleInfo", articleInfo);
		
		return "/widget/cms/articlePreviewDetail.html";

		
	}
	
	/**
	 * 文章点赞
	 * 
	 * @param token
	 *            令牌
	 * @param articleId
	 *            文章主键
	 */
	@RequestMapping(value = { "/m-article-diggs.htx" })
	public void diggArticle(boolean isReDiggs, Integer articleId, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		
		if (articleId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		Info info = infoQueryService.getInfoById(articleId);
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		try {	
			if(isReDiggs) {
				infoQueryService.addDiggs(info.getId() , 1);
			}else {
				infoQueryService.addDiggs(info.getId() , -1);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章点赞失败!")));
			return;
		}
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getDiggs())));
	}
	
}
