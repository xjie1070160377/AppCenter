package cn.mooc.app.module.cms.data.entity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mooc.app.module.cms.service.UploadHandlerService;
import cn.mooc.app.module.cms.support.Siteable;
import cn.mooc.app.module.cms.util.Strings;

@Entity
@Table(name="t_cms_collect")
public class Collect implements Siteable,java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Collect.class);
	
	/**
	 * 就绪
	 */
	public static final int READY = 0;
	/**
	 * 运行中
	 */
	public static final int RUNNING = 1;
	/**
	 * 暂停
	 */
	public static final int PAUSE = 2;
	
	public static final String PLACEHOLDER = "(*)";
	
	
	private Integer id;
	private List<CollectField> fields = new ArrayList<CollectField>();
	private Site site;
	private Node node;
	
	private Integer userId;
	private String name;
	private String charset;
	private String userAgent;
	private Integer pageBegin;
	private Integer pageEnd;
	private Integer intervalMin;
	private Integer intervalMax;
	private Boolean desc;
	private String listPattern;
	private String listNextPattern;
	private String itemAreaPattern;
	private String itemPattern;
	private String blockAreaPattern;
	private String blockPattern;
	private Boolean listNextReg;
	private Boolean itemAreaReg;
	private Boolean itemReg;
	private Boolean blockAreaReg;
	private Boolean blockReg;
	private Boolean submit;
	private Boolean downloadImage;
	private Boolean allowDuplicate;
	private Integer status;
	
	@Id
	@Column(name="collect_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_collect", pkColumnValue = "t_cms_collect", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_collect")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE }, mappedBy = "collect")
	@OrderBy(value = "seq asc, id asc")
	public List<CollectField> getFields() {
		return this.fields;
	}

	public void setFields(List<CollectField> fields) {
		this.fields = fields;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id", nullable = false)
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "charset", nullable = false, length = 100)
	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Column(name = "user_agent")
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(name = "list_pattern", nullable = false, length = 2000)
	public String getListPattern() {
		return this.listPattern;
	}

	public void setListPattern(String listPattern) {
		this.listPattern = listPattern;
	}

	@Column(name = "page_begin", nullable = false)
	public Integer getPageBegin() {
		return this.pageBegin;
	}

	public void setPageBegin(Integer pageBegin) {
		this.pageBegin = pageBegin;
	}

	@Column(name = "page_end", nullable = false)
	public Integer getPageEnd() {
		return this.pageEnd;
	}

	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}

	@Column(name = "is_desc", nullable = false, length = 1)
	public Boolean getDesc() {
		return this.desc;
	}

	public void setDesc(Boolean desc) {
		this.desc = desc;
	}

	@Column(name = "item_area_pattern")
	public String getItemAreaPattern() {
		return this.itemAreaPattern;
	}

	public void setItemAreaPattern(String itemAreaPattern) {
		this.itemAreaPattern = itemAreaPattern;
	}

	@Column(name = "item_pattern", nullable = false)
	public String getItemPattern() {
		return this.itemPattern;
	}

	public void setItemPattern(String itemPattern) {
		this.itemPattern = itemPattern;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "interval_min", nullable = false)
	public Integer getIntervalMin() {
		return this.intervalMin;
	}

	public void setIntervalMin(Integer intervalMin) {
		this.intervalMin = intervalMin;
	}

	@Column(name = "interval_max", nullable = false)
	public Integer getIntervalMax() {
		return this.intervalMax;
	}

	public void setIntervalMax(Integer intervalMax) {
		this.intervalMax = intervalMax;
	}

	@Column(name = "list_next_pattern")
	public String getListNextPattern() {
		return this.listNextPattern;
	}

	public void setListNextPattern(String listNextPattern) {
		this.listNextPattern = listNextPattern;
	}

	@Column(name = "is_list_next_reg", nullable = false, length = 1)
	public Boolean getListNextReg() {
		return this.listNextReg;
	}

	public void setListNextReg(Boolean listNextReg) {
		this.listNextReg = listNextReg;
	}

	@Column(name = "is_item_area_reg", nullable = false, length = 1)
	public Boolean getItemAreaReg() {
		return this.itemAreaReg;
	}

	public void setItemAreaReg(Boolean itemAreaReg) {
		this.itemAreaReg = itemAreaReg;
	}

	@Column(name = "is_item_reg", nullable = false, length = 1)
	public Boolean getItemReg() {
		return this.itemReg;
	}

	public void setItemReg(Boolean itemReg) {
		this.itemReg = itemReg;
	}

	@Column(name = "block_area_pattern")
	public String getBlockAreaPattern() {
		return blockAreaPattern;
	}

	public void setBlockAreaPattern(String blockAreaPattern) {
		this.blockAreaPattern = blockAreaPattern;
	}

	@Column(name = "is_block_area_reg", nullable = false, length = 1)
	public Boolean getBlockAreaReg() {
		return blockAreaReg;
	}

	public void setBlockAreaReg(Boolean blockAreaReg) {
		this.blockAreaReg = blockAreaReg;
	}

	@Column(name = "block_pattern")
	public String getBlockPattern() {
		return blockPattern;
	}

	public void setBlockPattern(String blockPattern) {
		this.blockPattern = blockPattern;
	}

	@Column(name = "is_block_reg", nullable = false, length = 1)
	public Boolean getBlockReg() {
		return blockReg;
	}

	public void setBlockReg(Boolean blockReg) {
		this.blockReg = blockReg;
	}

	@Column(name = "is_submit", nullable = false, length = 1)
	public Boolean getSubmit() {
		return submit;
	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}

	@Column(name = "is_download_image", nullable = false, length = 1)
	public Boolean getDownloadImage() {
		return downloadImage;
	}

	public void setDownloadImage(Boolean downloadImage) {
		this.downloadImage = downloadImage;
	}

	@Column(name = "is_allow_duplicate", nullable = false, length = 1)
	public Boolean getAllowDuplicate() {
		return allowDuplicate;
	}

	public void setAllowDuplicate(Boolean allowDuplicate) {
		this.allowDuplicate = allowDuplicate;
	}
	
	
	@Transient
	public boolean isRunning(){
		return getStatus() == RUNNING;
	}
	
	@Transient
	public boolean isReady(){
		return getStatus() == READY;
	}
	
	@Transient
	public static String fetchHtml(URI uri, String charset, String userAgent) 
			throws ClientProtocolException,IOException{
		CloseableHttpClient httpClient = HttpClients.custom().setUserAgent(userAgent).build();
		return fetchHtml(httpClient, uri, charset);
	}
	
	@Transient
	public static String fetchHtml(CloseableHttpClient httpClient, URI uri,
			String charset) throws ClientProtocolException,IOException{
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String html = null;
		try {
			if(response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK){
				HttpEntity entity = response.getEntity();
				html = EntityUtils.toString(entity, charset);
			}
		} finally{
			response.close();
		}
		return html;
	}
	
	@Transient
	public static String findFirst(String input, String pattern, boolean isReg){
		List<String> results = find(input, pattern, isReg, 1);
		if(results.size() > 0){
			return results.get(0);
		}else{
			return null;
		}
	}
	
	@Transient
	public static List<String> find(String input, String pattern, boolean isReg){
		return find(input, pattern, isReg, Integer.MAX_VALUE);
	}
	
	@Transient
	public static List<String> find(String input, String pattern, boolean isReg, int max){
		List<String> results;
		if(isReg){
			results = findByReg(input, pattern, max);
		}else{
			results = findByPlaceholder(input, pattern, max);
		}
		return results;
	}
	
	@Transient
	public static List<String> findByReg(String input, String pattern, int max){
		List<String> results = new ArrayList<String>();
		if(input == null){
			return results;
		}
		if(StringUtils.isBlank(pattern)){
			results.add(input);
			return results;
		}
		input = Strings.replaceNewline(input);
		pattern = Strings.replaceNewline(pattern);
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(input);
		int i = 0;
		while (i++ < max && m.find()) {
			results.add(m.group(1).trim());
			
		}
		return results;
	}
	
	@Transient
	public static List<String> findByPlaceholder(String input, String pattern, int max){
		List<String> results = new ArrayList<String>();
		if(input == null){
			return results;
		}
		if(StringUtils.isBlank(pattern)){
			results.add(input);
			return results;
		}
		input = Strings.replaceNewline(input);
		pattern = Strings.replaceNewline(pattern);
		int index = pattern.indexOf(PLACEHOLDER);
		if(index == -1){
			return results;
		}
		String open = pattern.substring(0, index);
		String close = pattern.substring(index + PLACEHOLDER.length());
		int begin = 0, end;
		int i = 0;
		while (i++ < max && (begin = input.indexOf(open, begin)) != -1) {
			begin += open.length();
			end = input.length();
			if (close.length() > 0) {
				end = input.indexOf(close, begin);
				if (end == -1) {
					return results;
				}
			}
			results.add(input.substring(begin, end).trim());
			begin = end + close.length();
		}
		return results;
	}
	
	@Transient
	public static List<String> getListUrls(String listPattern, int pageBegin, int pageEnd, boolean desc){
		List<String> urls = new ArrayList<String>();
		String[] lines = Strings.splitLines(listPattern);
		String s;
		for(String line : lines){
			if(line.indexOf(PLACEHOLDER) != -1){
				for(int i = pageBegin, len = pageEnd; i < len; i++){
					s = StringUtils.replace(line, PLACEHOLDER, String.valueOf(i));
					urls.add(s);
				}
			}else{
				urls.add(line);
			}
		}
		if(desc){
			Collections.reverse(urls);
		}
		return urls;
	}
	
	@Transient
	public static long getRandomBetween(long min, long max) {
		if (max >= min && min > 0) {
			double d = Math.random();
			return (long) (d * max - d * min + min);
		} else {
			return 0;
		}
	}
	
	@Transient
	public long getInterval() {
		long min = getIntervalMin() * 1000;
		long max = getIntervalMax() * 1000;
		return getRandomBetween(min, max);
	}
	
	@Transient
	public List<String> getListUrls() {
		return getListUrls(getListPattern(), getPageBegin(), getPageEnd(), getDesc());
	}
	
	@Transient
	public List<URI> getListUris() throws URISyntaxException {
		List<URI> uris = new ArrayList<URI>();
		for (String url : getListUrls(getListPattern(), getPageBegin(), getPageEnd(), getDesc())) {
			uris.add(new URI(url));
		}
		return uris;
	}
	
	@Transient
	public List<URI> getItemUris(String html, URI source) {
		String itemArea = getItemArea(html);
		List<String> items = find(itemArea, getItemPattern(), getItemReg());
		List<URI> uris = new ArrayList<URI>(items.size());
		for (String item : items) {
			item = StringUtils.trim(item);
			uris.add(source.resolve(item));
		}
		if (getDesc()) {
			Collections.reverse(uris);
		}
		return uris;
	}
	
	@Transient
	public String getItemArea(String html) {
		return findFirst(html, getItemAreaPattern(), getItemAreaReg());
	}
	
	@Transient
	public CollectField getField(String code) {
		List<CollectField> fields = getFields();
		for (CollectField field : fields) {
			if (code.equals(field.getCode())) {
				return field;
			}
		}
		return null;
	}
	
	@Transient
	public String getFieldValue(String code, String html, String item,
			String id, URI uri, CloseableHttpClient httpclient,
			UploadHandlerService uploadHandler) throws ClientProtocolException,
			IOException {
		CollectField field = getField(code);
		return getFieldValue(field, html, item, id, uri, httpclient, uploadHandler);
	}
	
	@Transient
	public String getFieldValue(CollectField field, String html, String item, String id,
			URI uri, CloseableHttpClient httpclient, UploadHandlerService uploadHandler)
				throws ClientProtocolException, IOException {
		if (field == null) {
			return null;
		}
		String result = field.getText(html, item, id, uri, httpclient, getCharset(), uploadHandler);
		return result;
	}
	
	@Transient
	public String getFieldId(String html, String item, URI uri,
			CloseableHttpClient httpclient, UploadHandlerService uploadHandler)
			throws ClientProtocolException, IOException {
		String str = getFieldValue("id", html, item, null, uri, httpclient, uploadHandler);
		return str;
	}
	
	@Transient
	public String getFieldNext(String html, String item, String id, URI uri,
			CloseableHttpClient httpclient, UploadHandlerService uploadHandler)
			throws ClientProtocolException, IOException {
		String str = getFieldValue("next", html, item, id, uri, httpclient, uploadHandler);
		return str;
	}
	
	@Transient
	public String getFieldTitle(String html, String item, String id, URI uri,
			CloseableHttpClient httpclient, UploadHandlerService uploadHandler)
			throws ClientProtocolException, IOException {
		String str = getFieldValue("title", html, item, id, uri, httpclient, uploadHandler);
		str = StringEscapeUtils.unescapeHtml4(str);
		return str;
	}

	@Transient
	public String getFieldText(String html, String item, String id, URI uri,
			CloseableHttpClient httpclient, UploadHandlerService uploadHandler)
			throws ClientProtocolException, IOException {
		String str = getFieldValue("text", html, item, id, uri, httpclient, uploadHandler);
		return str;
	}
	
	@Transient
	public String getFieldTagKeywords(String html, String item, String id,
			URI uri, CloseableHttpClient httpclient, UploadHandlerService uploadHandler)
			throws ClientProtocolException, IOException {
		String str = getFieldValue("tagKeywords", html, item, id, uri,
				httpclient, uploadHandler);
		return str;
	}
	
	@Transient
	public void infoField(Info info, String html, String item, String id,
			URI uri, CloseableHttpClient httpclient, UploadHandlerService uploadHandler)
			throws ClientProtocolException, IOException {
		String value;
		// publishDate, views, downloads
		CollectField field = getField("publishDate");
		if (field != null) {
			String dateFormat = field.getDateFormat();
			value = getFieldValue(field, html, item, id, uri, httpclient, uploadHandler);
			if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(dateFormat)) {
				SimpleDateFormat df = new SimpleDateFormat(dateFormat);
				try {
					Date d = df.parse(value);
					info.setPublishDate(d);
				} catch (ParseException e) {
					logger.error("Collect publishDate error!", e);
				}
			}
		}

		value = getFieldValue("views", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			info.setViews(NumberUtils.toInt(value, 0));
		}
		value = getFieldValue("downloads", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			info.setDownloads(NumberUtils.toInt(value, 0));
		}
	}
	
	@Transient
	public void detailField(InfoDetail detail, String html, String item,
			String id, URI uri, CloseableHttpClient httpclient,
			UploadHandlerService uploadHandler) throws ClientProtocolException,
			IOException {
		// subtitle;fullTitle;metaDescription;source;author;smallImage;largeImage;video;file;
		String value = getFieldValue("subtitle", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setSubtitle(value);
		}
		value = getFieldValue("fullTitle", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setFullTitle(value);
		}
		value = getFieldValue("metaDescription", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setMetaDescription(value);
		}
		value = getFieldValue("source", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setSource(value);
		}
		value = getFieldValue("author", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setAuthor(value);
		}
		value = getFieldValue("smallImage", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setSmallImage(value);
		}
		value = getFieldValue("largeImage", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setLargeImage(value);
		}
		value = getFieldValue("video", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setVideo(value);
		}
		value = getFieldValue("file", html, item, id, uri, httpclient, uploadHandler);
		if (StringUtils.isNotBlank(value)) {
			detail.setFile(value);
		}
	}
	
	@Transient
	public void customsField(Map<String, String> customs, String html,
			String item, String id, URI uri, CloseableHttpClient httpclient,
			UploadHandlerService uploadHandler) throws ClientProtocolException,
			IOException {
		String value;
		for (CollectField f : getFields()) {
			if (f.getType() != CollectField.TYPE_CUSTOM) {
				continue;
			}
			value = getFieldValue(f.getCode(), html, item, id, uri, httpclient, uploadHandler);
			if (StringUtils.isNotBlank(value)) {
				customs.put(f.getCode(), value);
			}
		}
	}
	
	@Transient
	public void clobsField(Map<String, String> clobs, String html, String item,
			String id, URI uri, CloseableHttpClient httpclient,
			UploadHandlerService uploadHandler) throws ClientProtocolException,
			IOException {
		String value;
		for (CollectField f : getFields()) {
			if (f.getType() != CollectField.TYPE_CLOB) {
				continue;
			}
			value = getFieldValue(f.getCode(), html, item, id, uri, httpclient, uploadHandler);
			if (StringUtils.isNotBlank(value)) {
				clobs.put(f.getCode(), value);
			}
		}
	}
	
	@Transient
	public void applyDefaultValue() {
		if (getPageBegin() == null) {
			setPageBegin(2);
		}
		if (getPageEnd() == null) {
			setPageEnd(10);
		}
		if (getIntervalMin() == null) {
			setIntervalMin(0);
		}
		if (getIntervalMax() == null) {
			setIntervalMax(0);
		}
		if (getDesc() == null) {
			setDesc(true);
		}
		if (getListNextReg() == null) {
			setListNextReg(false);
		}
		if (getItemAreaReg() == null) {
			setItemAreaReg(false);
		}
		if (getItemReg() == null) {
			setItemReg(false);
		}
		if (getBlockAreaReg() == null) {
			setBlockAreaReg(false);
		}
		if (getBlockReg() == null) {
			setBlockReg(false);
		}
		if (getSubmit() == null) {
			setSubmit(false);
		}
		if (getDownloadImage() == null) {
			setDownloadImage(true);
		}
		if (getAllowDuplicate() == null) {
			setAllowDuplicate(false);
		}
		if (getStatus() == null) {
			setStatus(READY);
		}
	}

}
