package cn.mooc.app.module.cms.service;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.cms.data.entity.Collect;
import cn.mooc.app.module.cms.data.entity.CollectField;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.CollectRepository;
import cn.mooc.app.module.cms.support.DeleteException;
import cn.mooc.app.module.cms.support.Uploader;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CollectService
 * 采集服务
 * 
 * @author linwei
 * @date 2017-06-22
 */
@Service
public class CollectService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private UploadHandlerService uploadHandler;
	@Autowired
	private InfoService infoService;
	@Autowired
	private SiteService siteService;
	
	public Collect getCollectById(Integer id) {
		Collect entity = collectRepository.findOne(id);
		return entity;
	}
	
	public List<Collect> getAllCollects(){
		return this.collectRepository.findAll();
	}
	
	public List<Collect> findList(Integer siteId) {
		return collectRepository.findList(siteId);
	}
	
	public Page<Collect> findCollectPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Collect> spec = SpecDynamic.buildSpec(filters.values());

		return collectRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public Collect saveCollect(Collect entity) throws Exception {
		return this.collectRepository.save(entity);
	}

	@Transactional
	public Collect updateCollect(Collect entity) throws Exception{
		return this.collectRepository.save(entity);
	}
	
	@Transactional
	public boolean delCollect(Integer id){
		try{
			this.collectRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCollects(Integer[] ids){
		try{
			List<Collect> entities = this.collectRepository.findAll(Arrays.asList(ids));
			this.collectRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@Transactional
	public void running(Integer id) {
		Collect bean = getCollectById(id);
		bean.setStatus(Collect.RUNNING);
	}

	@Transactional
	public void ready(Integer id) {
		Collect bean = getCollectById(id);
		bean.setStatus(Collect.READY);
	}

	public boolean isRunning(Integer id) {
		Collect bean = getCollectById(id);
		if (bean == null) {
			return false;
		}
		return bean.isRunning();
	}

	public boolean collcetItem(Integer siteId, CloseableHttpClient httpclient, URI uri,
			Integer collectId, String charset, Integer nodeId,
			Integer creatorId, Info info, InfoDetail detail,
			Map<String, String> customs, Map<String, String> clobs)
			throws ClientProtocolException, IOException {
		logger.debug("collcet item url: {}", uri);
		Collect collect = getCollectById(collectId);
		Integer userId = collect.getUserId();
		try {
			HttpGet httpget = new HttpGet(uri);
			CloseableHttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity, charset);
			
			String item = null;
			String id = collect.getFieldId(html, item, uri, httpclient, uploadHandler);
			String title = collect.getFieldTitle(html, item, id, uri, httpclient, uploadHandler);
			if (StringUtils.isBlank(title)) {
				return false;
			}
			if (!collect.getAllowDuplicate()
					&& infoService.containsTitle(title, siteId)) {
				return false;
			}
			StringBuilder textBuilder = new StringBuilder();
			String text = collect.getFieldText(html, item, id, uri, httpclient,
					uploadHandler);
			Site site = siteService.getSiteById(siteId);
			if (text != null) {
				// 采集图片
				text = collectImage(text, uri, site, userId,
						collect.getDownloadImage());
				textBuilder.append(text);
			}

			collect.infoField(info, html, item, id, uri, httpclient,
					uploadHandler);
			collect.detailField(detail, html, item, id, uri, httpclient,
					uploadHandler);
			detail.setTitle(title);
			collect.customsField(customs, html, item, id, uri, httpclient,
					uploadHandler);
			collect.clobsField(clobs, html, item, id, uri, httpclient,
					uploadHandler);
			String next = collect.getFieldNext(html, item, id, uri, httpclient,
					uploadHandler);
			response.close();
			while (StringUtils.isNotBlank(next)) {
				uri = uri.resolve(next);
				httpget = new HttpGet(uri);
				response = httpclient.execute(httpget);
				entity = response.getEntity();
				html = EntityUtils.toString(entity, charset);
				text = collect.getFieldText(html, item, id, uri, httpclient,
						uploadHandler);
				if (text != null) {
					textBuilder.append(Info.PAGEBREAK_OPEN);
					textBuilder.append(Info.PAGEBREAK_CLOSE);
					// 采集图片
					text = collectImage(text, uri, site, userId,
							collect.getDownloadImage());
					textBuilder.append(text);
				}
				next = collect.getFieldNext(html, item, id, uri, httpclient,
						uploadHandler);
				response.close();
			}
			if (StringUtils.isNotBlank(textBuilder)) {
				clobs.put(Info.INFO_TEXT, textBuilder.toString());
			}
			return true;
		} catch (IOException e) {
			logger.error(null, e);
			return false;
		}
	}

	private String collectImage(String html, URI uri, Site site,
			Integer userId, boolean isDownload) {
		if (StringUtils.isBlank(html)) {
			return html;
		}
		StringBuilder buff = new StringBuilder(html.length());
		try {
			Parser parser = new Parser(new Lexer(html));
			NodeFilter filter = new TagNameFilter("img");
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			SimpleNodeIterator it = nodes.elements();
			int begin = 0, end = 0;
			while (it.hasMoreNodes()) {
				ImageTag tag = (ImageTag) it.nextNode();
				String src = tag.getAttribute("src");
				src = StringUtils.trim(src);
				if (StringUtils.isBlank(src)) {
					continue;
				}
				String srcUrl = uri.resolve(src).toString();
				if (isDownload) {
					UploadResult result = new UploadResult();
					uploadHandler.upload(srcUrl, Uploader.IMAGE, site, userId,
							null, result);
					if (result.isStatus()) {
						srcUrl = result.getFileUrl();
					}
				}
				tag.setAttribute("src", srcUrl);
				end = tag.getStartPosition();
				buff.append(html.subSequence(begin, end));
				buff.append(tag.toHtml());
				begin = tag.getEndPosition();
			}
			buff.append(html.subSequence(begin, html.length()));
		} catch (Exception e) {
			logger.error(null, e);
		}
		return buff.toString();
	}

	public void preNodeDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (collectRepository.countByNodeId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("collect.management");
			}
		}
	}

	public void preUserDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (collectRepository.countByUserId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("collect.management");
			}
		}
	}

	public void preSiteDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (collectRepository.countBySiteId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("collect.management");
			}
		}
	}
	
	
}
