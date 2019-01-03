package cn.mooc.app.module.api.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.service.TokenService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.push.data.entity.IosToken;
import cn.mooc.app.module.push.service.IosTokenService;

/**
 * 
 * @author zjj
 *
 */
@Controller
public class MobileIosTokenController {

	@Autowired
	private IosTokenService service;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private WebContext webContext;
	
	@RequestMapping(value = { "/m-iostoken.htx" })
	public void getArticleList(Integer siteId, String token, String device_token, String iostoken, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = this.webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("发送成功")));
			return;
		}
		Long userId = user.getId();
		
		IosToken bean = new IosToken();
		bean.setId(userId);
		if(StringUtils.isNotEmpty(iostoken)){
			List<IosToken> ts = service.findByToken(iostoken);
			if(ts != null && ts.size() == 1){
				if(!ts.get(0).getId().equals(userId)){
					service.delete(ts.get(0).getId());
				}
			}else if(ts != null && ts.size() > 0){
				Long[] ids = new Long[ts.size()];
				for(int i = 0; i < ts.size(); i++){
					ids[i] = ts.get(i).getId();
				}
				service.delete(ids);
			}
			bean.setToken(iostoken);
			bean.setIsios(true);
			bean = service.save(bean);
		}else if(StringUtils.isNotEmpty(device_token)){
			List<IosToken> ts = service.findByToken(device_token);
			if(ts != null && ts.size() == 1){
				if(!ts.get(0).getId().equals(userId)){
					service.delete(ts.get(0).getId());
				}
			}else if(ts != null && ts.size() > 0){
				Long[] ids = new Long[ts.size()];
				for(int i = 0; i < ts.size(); i++){
					ids[i] = ts.get(i).getId();
				}
				service.delete(ids);
			}
			bean.setToken(device_token);
			bean.setIsios(false);
			bean = service.save(bean);
		}
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("发送成功")));
	}
}
