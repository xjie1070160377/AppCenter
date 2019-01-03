package cn.mooc.app.module.api.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.mooc.app.module.cms.mcenter.controller.DiscoverController;
import cn.mooc.app.module.cms.model.DiscoverServer;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.DiscoverUtil;

@Controller
public class MobileDiscoverController {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private DiscoverUtil discoverUtil;
	
	/**
	 * 获取发现栏目
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-discover.htx" })
	public void updateUserNode(String token, Integer type, HttpServletRequest request, HttpServletResponse response){
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(type == null){
			type = 1;
		}
		List<Map<String, Object>> menu_list  = DiscoverController.getMenu_list();
		String key = "", filePath = "";
		for(Map params : menu_list){
			Integer rtype = StringUtil.strnull2Int(params.get("type"));
			if(rtype.equals(type)){
				key = StringUtil.strnull(params.get("key"));
				filePath = StringUtil.strnull(params.get("filePath"));
				break;
			}
		}
		List<DiscoverServer> list = discoverUtil.getDiscoverJson(key, filePath);
//		if(UserTypeUtil.outUser(user.getuType()) && list != null){
//			List<DiscoverServer> olist = new ArrayList<DiscoverServer>();
//			for(DiscoverServer server : list){
//				DiscoverServer os = new DiscoverServer();
//				os.setServiceName(server.getServiceName());
//				List<DiscoverServerItem> items = server.getItems();
//				List<DiscoverServerItem> oitems = new ArrayList<DiscoverServerItem>();
//				if(items != null && items.size() > 0){
//					for(DiscoverServerItem it : items){
//						if(StringUtil.string2Int(it.getRange()) >1){
//							oitems.add(it);
//						}
//					}
//				}
//				if(oitems.size() > 0){
//					os.setItems(oitems);
//					olist.add(os);
//				}
//			}
//			String json = mapper.toJson(olist);
//			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(json)));
//		}else{
			String json = mapper.toJson(list);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(json)));
//		}
		
	}
	
}
