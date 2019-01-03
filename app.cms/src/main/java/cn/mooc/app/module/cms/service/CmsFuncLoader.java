package cn.mooc.app.module.cms.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.service.FuncLoader;
import cn.mooc.app.core.service.ViewFuncRegistry;
import cn.mooc.app.module.cms.web.view.func.AdList;
import cn.mooc.app.module.cms.web.view.func.CommentList;
import cn.mooc.app.module.cms.web.view.func.CommentPage;
import cn.mooc.app.module.cms.web.view.func.Info;
import cn.mooc.app.module.cms.web.view.func.InfoList;
import cn.mooc.app.module.cms.web.view.func.InfoNext;
import cn.mooc.app.module.cms.web.view.func.InfoNextXk;
import cn.mooc.app.module.cms.web.view.func.InfoPage;
import cn.mooc.app.module.cms.web.view.func.InfoPrev;
import cn.mooc.app.module.cms.web.view.func.InfoPrevXk;
import cn.mooc.app.module.cms.web.view.func.MarkList;
import cn.mooc.app.module.cms.web.view.func.MarkPage;
import cn.mooc.app.module.cms.web.view.func.Node;
import cn.mooc.app.module.cms.web.view.func.NodeList;
import cn.mooc.app.module.cms.web.view.func.NodePage;
import cn.mooc.app.module.cms.web.view.func.SiteFullNameOrName;
import cn.mooc.app.module.cms.web.view.func.Special;
import cn.mooc.app.module.cms.web.view.func.SpecialCategoryList;
import cn.mooc.app.module.cms.web.view.func.SpecialList;
import cn.mooc.app.module.cms.web.view.func.SpecialPage;
import cn.mooc.app.module.interact.web.view.func.CountInteractMark;
import cn.mooc.app.module.interact.web.view.func.HasInteractMark;

@Service
public class CmsFuncLoader implements FuncLoader {

	@Autowired
	private ViewFuncRegistry viewFuncRegistry;
	
	@PostConstruct
	public void startLoading(){
		
		viewFuncRegistry.addFuncLoader(this);		
		
	}

	@Override
	public void registerFunctions() {
		viewFuncRegistry.registerFunction("SiteFullNameOrName", new SiteFullNameOrName());
		viewFuncRegistry.registerFunction("NodeList", new NodeList());
		viewFuncRegistry.registerFunction("NodePage", new NodePage());
		viewFuncRegistry.registerFunction("InfoList", new InfoList());
		viewFuncRegistry.registerFunction("InfoPage", new InfoPage());
		viewFuncRegistry.registerFunction("Node", new Node());
		viewFuncRegistry.registerFunction("Info", new Info());
		viewFuncRegistry.registerFunction("InfoPrev", new InfoPrev());
		viewFuncRegistry.registerFunction("InfoPrevXk", new InfoPrevXk());
		viewFuncRegistry.registerFunction("InfoNext", new InfoNext());
		viewFuncRegistry.registerFunction("InfoNextXk", new InfoNextXk());
		viewFuncRegistry.registerFunction("SpecialCategoryList", new SpecialCategoryList());
		viewFuncRegistry.registerFunction("SpecialList", new SpecialList());
		viewFuncRegistry.registerFunction("SpecialPage", new SpecialPage());
		viewFuncRegistry.registerFunction("CommentList", new CommentList());
		viewFuncRegistry.registerFunction("CommentPage", new CommentPage());
		viewFuncRegistry.registerFunction("AdList", new AdList());
		viewFuncRegistry.registerFunction("Special", new Special());
		viewFuncRegistry.registerFunction("MarkList", new MarkList());
		viewFuncRegistry.registerFunction("MarkPage", new MarkPage());
		viewFuncRegistry.registerFunction("HasInteractMark", new HasInteractMark());
		viewFuncRegistry.registerFunction("CountInteractMark", new CountInteractMark());
	}
	
	
}
