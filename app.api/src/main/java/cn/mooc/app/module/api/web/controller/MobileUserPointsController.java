package cn.mooc.app.module.api.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.api.model.MobileCurrencyRule;
import cn.mooc.app.module.api.model.MobileListData;
import cn.mooc.app.module.api.model.MobilePointsConvert;
import cn.mooc.app.module.api.model.MobilePointsRule;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.api.service.TokenService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.event.selector.ReadEventSelector;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.points.data.entity.CurrencyRule;
import cn.mooc.app.module.points.data.entity.CurrencySpecial;
import cn.mooc.app.module.points.data.entity.PointsLevel;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.data.entity.UserPoints;
import cn.mooc.app.module.points.service.CurrencyRuleService;
import cn.mooc.app.module.points.service.CurrencySpecialService;
import cn.mooc.app.module.points.service.PointsInfoService;
import cn.mooc.app.module.points.service.PointsLevelService;
import cn.mooc.app.module.points.service.PointsRuleService;
import cn.mooc.app.module.points.service.UserPointsService;


@Controller
public class MobileUserPointsController {

	private static final Logger logger = LoggerFactory.getLogger(MobileUserPointsController.class);
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private InfoService infoQueryService;
	@Autowired
	private PointsRuleService ruleService;
	@Autowired
	private CurrencyRuleService cruleService;
	@Autowired
	private PointsInfoService infoService;
	@Autowired
	private CurrencySpecialService specialService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private MobileCommonService mobileCommonService;
	@Autowired
	private UserPointsService userPointsService;
	@Autowired
	private PointsLevelService levelService;
	
	/**
	 * 阅读积分
	 * @param articleId
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-readPoints.htx" })
	public void readPoints(Integer articleId, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (articleId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		Info info = infoQueryService.getInfoById(articleId);
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			return;
		}
//		User user = userService.get(StringUtil.string2Int("" + userMap.get("UID")));
		
		LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
		OprEvent oprEvent = new OprEvent(request);
		oprEvent.setLoginUser(shiroUser);
		oprEvent.addParam("infoId", articleId);
		oprEvent.addSelector(new ReadEventSelector());
		//发送事件
		eventDispatch.postEvent(oprEvent);
		
//		pointsService.updateUserPoints(user.getId(), PointsRule.RULE_READ, articleId);
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("")));
	}
	
	/**
	 * 积分规则
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-pointsRules.htx" })
	public void pointsRules(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		List<PointsRule> rule_list = ruleService.findValidRule();
		List<MobilePointsRule> mobileRules = new ArrayList<MobilePointsRule>();
		for(PointsRule rule : rule_list){
			mobileRules.add(MobilePointsConvert.convert(rule, infoService));
		}
		
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(mobileRules, 1, mobileRules.size(), mobileRules.size())));
	}
	

	@RequestMapping(value = { "/m-points.htx" })
	public void getPoints(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Map params = new HashMap();
			params.put("points", "0");
			params.put("levelName", "");
			params.put("pimage", "");
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(params)));
			return;
		}
		UserPoints userPoints = userPointsService.get(user.getId());
		Map params = new HashMap();
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		if(userPoints != null){
			params.put("points", userPoints.getPoints());
			if(userPoints.getLevel() != null){
				params.put("levelName", userPoints.getLevel().getLevelName());
				params.put("pimage", userPoints.getLevel().getImageUrl() == null ? "" : MobileModelConvert.fullLinkPrefix(userPoints.getLevel().getImageUrl(), siteUrl));
			}
		}else{
			params.put("points", "0");
			params.put("levelName", "");
			params.put("pimage", "");
		}
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(params)));
	}
	

	/**
	 * R币规则
	 */
	@RequestMapping(value = { "/m-currencyRules.htx" })
	public void currencyRules(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		List<CurrencyRule> rule_list = cruleService.findValidRule();
		List<MobileCurrencyRule> mobileRules = new ArrayList<MobileCurrencyRule>();
		for(CurrencyRule rule : rule_list){
			mobileRules.add(MobilePointsConvert.convert(rule, specialService));
		}
		
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(mobileRules, 1, mobileRules.size(), mobileRules.size())));
	}
	
	/**
	 * 获取积分等级信息
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-levelInfo.htx" })
	public void levelInfo(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		List<PointsLevel> rule_list = levelService.findList();
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		for(PointsLevel level : rule_list){
			level.setImageUrl(level.getImageUrl()==null?"":MobileModelConvert.fullLinkPrefix(level.getImageUrl(), siteUrl));
		}
		
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(rule_list, 1, rule_list.size(), rule_list.size())));
	}
	//兑换列表
	
	//用户兑换
	
	//抽奖列表
	
	//用户抽奖
}
