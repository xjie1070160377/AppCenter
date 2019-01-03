package cn.mooc.app.module.cms.data.entity;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.module.cms.service.UploadHandlerService;
import cn.mooc.app.module.cms.support.Siteable;
import cn.mooc.app.module.cms.util.Strings;

@Entity
@Table(name="t_cms_collect_field")
public class CollectField implements Siteable, java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 系统字段
	 */
	public static final int TYPE_SYSTEM = 1;
	
	/**
	 * custom字段
	 */
	public static final int TYPE_CUSTOM = 2;
	
	/**
	 * clob字段
	 */
	public static final int TYPE_CLOB = 3;

	/**
	 * 详情页
	 */
	public static final int SOURCE_DETAIL = 1;
	
	/**
	 * 列表页
	 */
	public static final int SOURCE_ITEM = 2;
	
	/**
	 * 固定值
	 */
	public static final int SOURCE_TEXT = 3;
	
	/**
	 * URL
	 */
	public static final int SOURCE_URL = 4;
	
	private Integer id;
	private Collect collect;
	private Site site;
	
	private String name;
	private String code;
	private Integer type;
	private Integer sourceType;
	private String sourceUrl;
	private String sourceText;
	private String dataAreaPattern;
	private Boolean dataAreaReg;
	private String dataPattern;
	private Boolean dataReg;
	private String dateFormat;
	private String downloadType;
	private String imageParam;
	private String filter;
	private Integer seq;
	
	@Id
	@Column(name="collectfield_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_collect_field", pkColumnValue = "t_cms_collect_field", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_collect_field")
	public Integer getId(){
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "collect_id", nullable = false)
	public Collect getCollect() {
		return this.collect;
	}

	public void setCollect(Collect collect) {
		this.collect = collect;
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", nullable = false, length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "source_type", nullable = false)
	public Integer getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "source_url")
	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Column(name = "source_text")
	public String getSourceText() {
		return this.sourceText;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}

	@Column(name = "data_area_pattern")
	public String getDataAreaPattern() {
		return this.dataAreaPattern;
	}

	public void setDataAreaPattern(String dataAreaPattern) {
		this.dataAreaPattern = dataAreaPattern;
	}

	@Column(name = "is_data_area_reg", nullable = false, length = 1)
	public Boolean getDataAreaReg() {
		return this.dataAreaReg;
	}

	public void setDataAreaReg(Boolean dataAreaReg) {
		this.dataAreaReg = dataAreaReg;
	}

	@Column(name = "data_pattern")
	public String getDataPattern() {
		return this.dataPattern;
	}

	public void setDataPattern(String dataPattern) {
		this.dataPattern = dataPattern;
	}

	@Column(name = "is_data_reg", nullable = false, length = 1)
	public Boolean getDataReg() {
		return this.dataReg;
	}

	public void setDataReg(Boolean dataReg) {
		this.dataReg = dataReg;
	}

	@Column(name = "date_format")
	public String getDateFormat() {
		return this.dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Column(name = "download_type", length = 20)
	public String getDownloadType() {
		return this.downloadType;
	}

	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}

	@Column(name = "image_param")
	public String getImageParam() {
		return imageParam;
	}

	public void setImageParam(String imageParam) {
		this.imageParam = imageParam;
	}

	@Column(name = "filter", length = 2000)
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Column(name = "seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Transient
	public String getText(String html, String item, String itemId, URI source, 
			CloseableHttpClient httpClient, String charset, UploadHandlerService uploadhandler) 
				throws ClientProtocolException,IOException{
		List<String> texts = getTexts(html, item, itemId, source, httpClient,
				charset, uploadhandler);
		return texts.size() > 0 ? texts.get(0) : null;
	}
	
	@Transient
	public List<String> getTexts(String html, String item, String itemId,
			URI source, CloseableHttpClient httpclient, String charset,
			UploadHandlerService uploadhandler) throws ClientProtocolException,IOException {
		List<String> texts;
		int type = getSourceType();
		if(type == SOURCE_DETAIL){
			texts = Collect.find(html, getDataPattern(), getDataReg());
			applyFilter(texts);
			downloads(texts, uploadhandler);
		} else if(type == SOURCE_ITEM){
			texts = Collect.find(item, getDataPattern(), getDataReg());
			applyFilter(texts);
			downloads(texts, uploadhandler);
		} else if (type == SOURCE_TEXT) {
			texts = new ArrayList<String>();
			texts.add(getSourceText());
		} else if (type == SOURCE_URL) {
			html = getHtmlByUrl(itemId, source, httpclient, charset);
			texts = Collect.find(html, getDataPattern(), getDataReg());
			applyFilter(texts);
			downloads(texts, uploadhandler);
		} else {
			throw new IllegalStateException("SourceType not found:" + type);
		}
		return texts;
	}
	
	private String download(String url, UploadHandlerService uploadhandler){
		String type = getDownloadType();
		if (StringUtils.isBlank(type)) {
			return url;
		}
		UploadResult result = new UploadResult();
		uploadhandler.upload(url, type, getSite(), getCollect().getUserId(), null, result);
		if (result.isStatus()) {
			return result.getFileUrl();
		} else {
			return url;
		}
	}
	
	private void downloads(List<String> urls, UploadHandlerService uploadHandler) {
		for (int i = 0, len = urls.size(); i < len; i++) {
			urls.set(i, download(urls.get(i), uploadHandler));
		}
	}
	
	@Transient
	public String getHtmlByUrl(String itemId, URI source,
			CloseableHttpClient httpclient, String charset)
			throws ClientProtocolException, IOException {
		String url = getSourceUrl();
		url = StringUtils.replace(url, "{id}", itemId);
		url = StringUtils.trim(url);
		URI uri = source.resolve(url);
		HttpGet httpget = new HttpGet(uri);
		CloseableHttpResponse response = httpclient.execute(httpget);
		String html = null;
		try {
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity, charset);
		} finally {
			response.close();
		}
		return html;
	}
	
	@Transient
	public static String applyFilter(List<Pattern> patterns, String text) {
		for (Pattern p : patterns) {
			text = Strings.filter(text, p);
		}
		return text;
	}

	@Transient
	private String applyFilter(String text) {
		for (Pattern p : getFilterPattern()) {
			text = Strings.filter(text, p);
		}
		return text;
	}

	@Transient
	private void applyFilter(List<String> texts) {
		for (int i = 0, len = texts.size(); i < len; i++) {
			texts.set(i, applyFilter(texts.get(i)));
		}
	}
	
	@Transient
	public static List<Pattern> getFilterPattern(String filter) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		if (StringUtils.isEmpty(filter)) {
			return patterns;
		}
		String[] filters = Strings.splitLines(filter);
		if (ArrayUtils.isNotEmpty(filters)) {
			for (String f : filters) {
				patterns.add(Pattern.compile(f));
			}
		}
		return patterns;
	}

	@Transient
	public List<Pattern> getFilterPattern() {
		return getFilterPattern(getFilter());
	}
	
	@Transient
	public void applyDefaultValue() {
		if (getSourceType() == null) {
			setSourceType(SOURCE_DETAIL);
		}
		if (getDataAreaReg() == null) {
			setDataAreaReg(false);
		}
		if (getDataReg() == null) {
			setDataReg(false);
		}
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}
	
}


















