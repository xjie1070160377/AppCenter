package cn.mooc.app.module.service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.service.data.entity.AppService;
import cn.mooc.app.module.service.data.entity.ServSelfmenu;
import cn.mooc.app.module.service.data.rds.ServSelfmenuRepository;
import cn.mooc.app.module.service.model.MobileAppServSelfMenu;

/**
 * ServSelfmenuService
 * 服务号自定义菜单实现类服务
 * 
 * @author zjj
 * @date 2016-09-14
 */
@Service
public class ServSelfmenuService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServSelfmenuRepository dao;
	@Autowired
	private AppServiceService appServiceService;
	
	public ServSelfmenu getServSelfmenuById(Integer id) {
		ServSelfmenu entity = dao.findOne(id);
		return entity;
	}
	
	public List<ServSelfmenu> getAllServSelfmenus(){
		return this.dao.findAll();
	}
	
	public Page<ServSelfmenu> findServSelfmenuPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ServSelfmenu> spec = SpecDynamic.buildSpec(filters.values());

		return dao.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public ServSelfmenu save(ServSelfmenu entity) throws Exception {
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.dao.save(entity);
	}

	@Transactional
	public ServSelfmenu update(ServSelfmenu entity) throws Exception{
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.dao.save(entity);
	}
	
	@Transactional
	public boolean delServSelfmenu(Integer id){
		try{
			this.dao.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delServSelfmenus(Integer[] ids){
		try{
			List<ServSelfmenu> entities = this.dao.findAll(Arrays.asList(ids));
			this.dao.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@Transactional
	public void update(Integer serviceId, String detail) {
		//先清空原菜单
		dao.deleteAllByServiceIdChild(serviceId);
		dao.deleteAllByServiceId(serviceId);
		AppService appService = appServiceService.getAppServiceById(serviceId);
		JSONArray arry = JSONArray.fromObject("["+detail+"]");
         for (int i = 0; i < arry.size(); i++) { 
             JSONObject jsonObject = arry.getJSONObject(i); 
             for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) { 
//            	 Map<String, String> map = new HashMap<String, String>(); 
                 String key = (String) iter.next(); 
                 String value = jsonObject.get(key).toString(); 
                 JSONObject obj = JSONObject.fromObject(value);
                 System.out.println("id:" + obj.get("id"));
      			System.out.println("menuname:" + obj.get("menuname"));
      			System.out.println("menutype:" + obj.get("menutype"));
      			System.out.println("menuindex:" + obj.get("index"));
    			ServSelfmenu menu = new ServSelfmenu();
    			menu.setAppService(appService);
    			menu.setMenuname(StringUtil.strnull(obj.get("menuname")));
    			menu.setMenutype(StringUtil.string2Int(obj.get("menutype")));
    			menu.setMenuvalue(StringUtil.strnull(obj.get("menuvalue")));
    			menu.setMenuorder(StringUtil.string2Int(obj.get("index")));
    			dao.save(menu);
      			JSONObject chilObj = obj.getJSONObject("childMenu");
      			for (Iterator<?> iter1 = chilObj.keys(); iter1.hasNext();){
                    String key1 = (String) iter1.next(); 
                    String value1 = chilObj.get(key1).toString();
                    JSONObject child = JSONObject.fromObject(value1);
                    System.out.println("child-id:" + child.get("id"));
					System.out.println("child-menuname:" + child.get("menuname"));
					System.out.println("child-menutype:" + child.get("menutype"));
					System.out.println("child-menuindex:" + child.get("index"));
					ServSelfmenu detailMenu = new ServSelfmenu();
					detailMenu.setAppService(appService);
					detailMenu.setParent(menu);
					detailMenu.setMenuname(StringUtil.strnull(child.get("menuname")));
					detailMenu.setMenutype(StringUtil.string2Int(child.get("menutype")));
					detailMenu.setMenuvalue(StringUtil.strnull(child.get("menuvalue")));
					detailMenu.setMenuorder(StringUtil.string2Int(child.get("index")));
					dao.save(detailMenu);
                }
             } 
         } 
	}

	@Transactional
	public void updateTest(Integer serviceId, String detail) {
		//先清空原菜单
		dao.deleteAllByServiceIdChild(serviceId);
		dao.deleteAllByServiceId(serviceId);
		AppService appService = appServiceService.getAppServiceById(serviceId);
		JSONArray arry = JSONArray.fromObject(detail );
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			System.out.println("name:" + jsonObject.get("name"));
			System.out.println("type:" + jsonObject.get("type"));
			System.out.println("value:" + jsonObject.get("value"));
			System.out.println("order:" + jsonObject.get("order"));
			ServSelfmenu menu = new ServSelfmenu();
			menu.setAppService(appService);
			menu.setMenuname(StringUtil.strnull(jsonObject.get("name")));
			menu.setMenutype(StringUtil.string2Int(jsonObject.get("type")));
			menu.setMenuvalue(StringUtil.strnull(jsonObject.get("value")));
			menu.setMenuorder(StringUtil.string2Int(jsonObject.get("order")));
			dao.save(menu);
			
			System.out.println("child:" + jsonObject.get("child"));
			String menuDetail = jsonObject.get("child").toString();
			if(!"".equals(menuDetail)){
				JSONArray detailArr = JSONArray.fromObject(menuDetail );
				for (int j = 0; j < detailArr.size(); j++) {
					JSONObject detailJsonObject = detailArr.getJSONObject(j);
					System.out.println("name:" + detailJsonObject.get("name"));
					System.out.println("type:" + detailJsonObject.get("type"));
					System.out.println("value:" + detailJsonObject.get("value"));
					System.out.println("order:" + detailJsonObject.get("order"));
					ServSelfmenu detailMenu = new ServSelfmenu();
					detailMenu.setAppService(appService);
					detailMenu.setParent(menu);
					detailMenu.setMenuname(StringUtil.strnull(detailJsonObject.get("name")));
					detailMenu.setMenutype(StringUtil.string2Int(detailJsonObject.get("type")));
					detailMenu.setMenuvalue(StringUtil.strnull(detailJsonObject.get("value")));
					detailMenu.setMenuorder(StringUtil.string2Int(detailJsonObject.get("order")));
					dao.save(detailMenu);
				}
			}
		}
	}

	public List<MobileAppServSelfMenu> findMobileAppServSelfMenu(Integer serviceId) {
		List<MobileAppServSelfMenu> result = new ArrayList<MobileAppServSelfMenu>();
		List<ServSelfmenu> parentMenus = dao.findParentMenusByServiceId(serviceId);
		for (ServSelfmenu servSelfmenu : parentMenus) {
			Integer id = servSelfmenu.getId();
			MobileAppServSelfMenu menu = new MobileAppServSelfMenu();
			menu.setName(servSelfmenu.getMenuname());
			menu.setType(servSelfmenu.getMenutype());
			menu.setValue(servSelfmenu.getMenuvalue());
			menu.setOrder(servSelfmenu.getMenuorder());
			List<MobileAppServSelfMenu> detail = new ArrayList<MobileAppServSelfMenu>();
			List<ServSelfmenu> child = dao.findChildMenusByParentId(id);
			for (ServSelfmenu childMenu : child) {
				MobileAppServSelfMenu childMMenu = new MobileAppServSelfMenu();
				childMMenu.setName(childMenu.getMenuname());
				childMMenu.setType(childMenu.getMenutype());
				childMMenu.setValue(childMenu.getMenuvalue());
				childMMenu.setOrder(childMenu.getMenuorder());
				detail.add(childMMenu);
			}
			menu.setChild(detail);
			if(detail != null && detail.size() > 0){
				menu.setValue("");
			}
			result.add(menu);
		}
		return result;
	}
}
