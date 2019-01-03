package cn.mooc.app.module.cms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.filefilter.DirectoryFileFilter;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Global;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.GlobalRepository;
import cn.mooc.app.module.cms.data.rds.SiteRepository;
import cn.mooc.app.module.cms.model.SiteModel;

/**
 * SiteService 站点信息服务
 * 
 * @author Taven
 * @date 2016-05-13
 */
@Service
public class SiteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String SESSION_SITE_NAME = "mSite";

	@Value("${cms.user.login.url}")
	private String loginUrl;

	@Value("${SYS_ADMIN_PATH}")
	private String SYS_ADMIN_PATH;

	/**
	 * CMS前端页面站点信息
	 */
	private static final String WEB_SESSION_SITE_NAME = "webSite";

	@Autowired
	private SiteRepository siteRepository;
	@Autowired
	private GlobalRepository globalRepository;
	@Autowired
	private WebContext webContext;
	@Autowired
	private CmsCacheService cmsCacheService;

	private static ThreadLocal<Site> siteHolder = new ThreadLocal<Site>();

	public String getULoginUrl() {
		return loginUrl;
	}

	public void setWebCurrentSite(Site site) {
		siteHolder.set(site);
		this.webContext.getHttpRequest().getSession().setAttribute(WEB_SESSION_SITE_NAME, site);
	}
	
	public static Site getWebCurrentSite(HttpServletRequest request) {
		Site site = (Site) request.getSession().getAttribute(WEB_SESSION_SITE_NAME);
		return site;

	}

	public int getWebCurrentSiteId() {
		return siteHolder.get().getId();
	}

	public Site getWebCurrentSite() {
		return siteHolder.get();
	}

	public void resetWebCurrentSite() {
		siteHolder.remove();
	}

	public String getWebThemeRoot() {
		String ctx = this.webContext.getHttpRequest().getContextPath();
		return ctx + this.getThemeRoot();
	}

	public String getThemeRoot() {
		Site site = this.getWebCurrentSite();
		String siteCtx = site.getContextPath();
		siteCtx = StringUtils.isEmpty(siteCtx) ? "" : siteCtx;
		String themeRoot = siteCtx + "/theme/" + site.getId() + "/" + site.getTemplateTheme();
		return themeRoot;
	}
	
	public String getThemePath(){
		Site site = this.getWebCurrentSite();
		String siteCtx = site.getContextPath();
		siteCtx = StringUtils.isEmpty(siteCtx) ? "" : siteCtx;
		String themeRoot = siteCtx + "/theme/";
		return themeRoot;
	}

	public Site getCurrentSite() {
		Site site = (Site) this.webContext.getHttpRequest().getSession().getAttribute(SESSION_SITE_NAME);
		if (site == null) {
			String domain = this.webContext.getHttpRequest().getServerName();
			site = cmsCacheService.getSiteByDomainCache(domain);			
			if (site == null) {
				site = cmsCacheService.getDefaultSiteCache();
			}
			this.changeCurrentSite(site);
		}
		return site;

	}

	public Site changeCurrentSite(Site site) {
		this.webContext.getHttpRequest().getSession().setAttribute(SESSION_SITE_NAME, site);
		return site;
	}

	public Site getSiteByDomain(String domain) {
		return this.siteRepository.getSiteByDomain(domain);
	}

	public Site getDefaultSite() {
		List<Site> list = this.siteRepository.getDefaultSite();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Global getDefaultGlobal() {
		return this.globalRepository.getDefaultGlobal();
	}

	public Site getSiteById(Integer id) {
		Site entity = siteRepository.findOne(id);
		return entity;
	}

	public List<Site> getAllSites() {
		return this.siteRepository.findAll();
	}

	public Page<SiteModel> findSiteList(Map<String, Object> searchParams, PagerParam pageParam) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Site> spec = SpecDynamic.buildSpec(filters.values());

		Page<Site> pageData = siteRepository.findAll(spec, pageParam.getPageRequest());

		List<SiteModel> siteModels = new ArrayList<SiteModel>();
		for (Site site : pageData.getContent()) {
			SiteModel siteModel = new SiteModel();
			BeanUtils.copyProperties(site, siteModel);
			siteModels.add(siteModel);
		}
		Page<SiteModel> pageDataList = new PageImpl<SiteModel>(siteModels, pageParam.getPageRequest(), pageData.getTotalElements());

		return pageDataList;

	}
	
	@Transactional
	public Global updateGlobal(Global entity) throws Exception {
		
		Global update = this.globalRepository.findOne(entity.getId());
		
		//BeanUtils.copyProperties(entity, update,"id");
		update.setProtocol(entity.getProtocol());
		update.setPort(entity.getPort());
		update.setContextPath(entity.getContextPath());
		update.setWithDomain(entity.getWithDomain());
		
		cmsCacheService.clearDefaultSiteCache();
		return this.globalRepository.save(update);
	}

	@Transactional
	public Site saveSite(Site entity) throws Exception {

		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		cmsCacheService.clearSiteByDomainCache(entity.getDomain());
		cmsCacheService.clearDefaultSiteCache();
		return this.siteRepository.save(entity);
	}

	@Transactional
	public Site updateSite(Site entity) throws Exception {

		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		if(entity.getDef()){
			siteRepository.clearSiteDef(entity.getId());
		}
		cmsCacheService.clearSiteByDomainCache(entity.getDomain());
		cmsCacheService.clearDefaultSiteCache();
		return this.siteRepository.save(entity);
	}

	@Transactional
	public boolean delSite(Integer id) {
		try {
			this.siteRepository.delete(id);
			cmsCacheService.clearDefaultSiteCache();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delSites(Integer[] ids) {
		try {
			List<Site> entities = this.siteRepository.findAll(Arrays.asList(ids));
			this.siteRepository.delete(entities);
			cmsCacheService.clearDefaultSiteCache();
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	public List<String> getSiteTemplateList(int siteId) {
		//
		if (siteId <= 0) {
			// 默认为站点1
			siteId = 1;
		}
		String siteBaseDir = "/theme/" + siteId + "/";

		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String themePath = servletContext.getRealPath("/") + siteBaseDir;

		File templateBaseFile = new File(themePath);
		// 取站点模板
		File[] themeFiles = templateBaseFile.listFiles(new DirectoryFileFilter());
		List<String> themeList = new ArrayList<String>();
		if (themeFiles != null) {
			for (File themeFile : themeFiles) {
				themeList.add(themeFile.getName());
			}
		}
		return themeList;

	}

	public String getSYS_ADMIN_PATH(){
		return SYS_ADMIN_PATH;
	}

	public String getCtxPath() {
		return webContext.getServletContext().getContextPath();
	}
}
