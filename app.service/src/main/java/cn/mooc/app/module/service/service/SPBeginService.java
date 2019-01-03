package cn.mooc.app.module.service.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.service.FreeMarkerVarRegistry;
import cn.mooc.app.module.service.web.directive.PagingMethod;
import cn.mooc.app.module.service.web.directive.ServMsgsPageDirective;
import cn.mooc.app.module.service.web.directive.ServUsersPageDirective;

@Service
public class SPBeginService {
	
	@Autowired
	private PagingMethod paging;
	
	@Autowired
	private ServMsgsPageDirective ServMsgsPage;
	@Autowired
	private ServUsersPageDirective ServUsersPage;
	
	@Autowired
	private FreeMarkerVarRegistry freeMarkerVarRegistry;
	
	@PostConstruct
	public void startUp(){
		
		freeMarkerVarRegistry.addVar("paging", paging);
		freeMarkerVarRegistry.addVar("ServMsgsPage", ServMsgsPage);
		freeMarkerVarRegistry.addVar("ServUsersPage", ServUsersPage);
	}
	
}
