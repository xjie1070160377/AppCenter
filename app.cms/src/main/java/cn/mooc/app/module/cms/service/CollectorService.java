package cn.mooc.app.module.cms.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.module.cms.data.entity.Collect;
import cn.mooc.app.module.cms.data.entity.CollectField;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.entity.Site;

@Service
public class CollectorService{
	
	@Autowired
	private SiteService siteService;
	
	private Logger logger = LoggerFactory.getLogger(CollectorService.class);
	
	public void startAll() {
		List<Collect> collects = service.getAllCollects();
		for(Collect c : collects){
			new CollectThread(c.getId(), c.getSite().getId()).start();
		}
	}

	public void start(Integer collectId, Integer siteId) {
		new CollectThread(collectId, siteId).start();
	}

	public class CollectThread extends Thread {
		private Integer collectId;
		private Integer siteId;

		public CollectThread(Integer collectId, Integer siteId) {
			this.collectId = collectId;
			this.siteId = siteId;
		}

		@Override
		public void run() {
			cn.mooc.app.module.cms.data.entity.Collect collect = service.getCollectById(collectId);
			if (collect.isRunning()) {
				return;
			}
			System.out.println("****开始采集*****:"+collect.getName());
			
			service.running(collectId);
			CloseableHttpClient httpclient = HttpClients.custom()
					.setUserAgent(collect.getUserAgent()).build();
			try {
				doCollect(httpclient, collect, siteId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("close HttpClient error!", e);
			}
			service.ready(collectId);
		}

		private void doCollect(CloseableHttpClient httpclient, Collect collect, Integer siteId)
				throws Exception {
			List<URI> listUris = collect.getListUris();
			Integer nodeId = collect.getNode().getId();
			Integer creatorId = collect.getUserId();
			String charset = collect.getCharset();
			String html;
			List<URI> itemUris;
			for (URI listUri : listUris) {
				if (!service.isRunning(collectId)) {
					return;
				}
				html = Collect.fetchHtml(httpclient, listUri, charset);
				if (html == null) {
					continue;
				}
				itemUris = collect.getItemUris(html, listUri);
				for (URI itemUri : itemUris) {
					if (!service.isRunning(collectId)) {
						return;
					}
					long millis = collect.getInterval();
					if (millis > 0) {
						try {
							Thread.sleep(millis);
						} catch (InterruptedException e) {
							logger.error(null, e);
						}
					}
					Info info = new Info();
					InfoDetail detail = new InfoDetail();
					Map<String, String> customs = new HashMap<String, String>();
					Map<String, String> clobs = new HashMap<String, String>();
					boolean success = service.collcetItem(siteId, httpclient, itemUri,
							collectId, charset, nodeId, creatorId, info,
							detail, customs, clobs);
					String status = Info.COLLECTED;
					if (collect.getSubmit()) {
						status = Info.NORMAL;
					}
					if (success) {
						infoService.saveInfo(info,detail,null,customs,clobs,null,null,null,null,nodeId, Long.parseLong(creatorId.toString()), status, siteId);
					}
				}
			}
		}
	}

	private InfoService infoService;
	private CollectService service;

	@Autowired
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}

	@Autowired
	public void setService(CollectService service) {
		this.service = service;
	}


}
