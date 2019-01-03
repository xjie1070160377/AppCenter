package cn.mooc.app.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	
	public static Map<String, String> getReqParam(HttpServletRequest request) {
		Map<String, String[]> sMap = request.getParameterMap();
		Map<String, String> rMap = new HashMap<String, String>();
		for (String sKey : sMap.keySet()) {			
			rMap.put(sKey, sMap.get(sKey)[0]);
		}
		
		return rMap;
	}
	
	public static String getReqParam(HttpServletRequest request, String paramName, String defaultVal){
		if(request.getParameter(paramName)!=null){
			return request.getParameter(paramName);
		}else{
			return defaultVal;
		}
		
	}
	
	public static Integer getReqParamInt(HttpServletRequest request, String paramName, Integer defaultVal){
		try{
			Integer val = Integer.parseInt(request.getParameter(paramName));
			return val;
		}catch (Exception e) {
			return defaultVal;
		}
		
	}
	
	public static Boolean getReqParamBoolean(HttpServletRequest request, String paramName, Boolean defaultVal){
		try{
			Boolean val = Boolean.parseBoolean(request.getParameter(paramName));
			return val;
		}catch (Exception e) {
			return defaultVal;
		}
		
	}
	
	public static long getSessionAttrLong(HttpServletRequest request, String name, long def) {
		Object obj = request.getSession().getAttribute(name);
		if(obj==null){
			return def;
		}
		return Long.parseLong(obj.toString());

	}
	
	public static <T> T getSessionAttr(HttpServletRequest request, String name) {
		Object obj = request.getSession().getAttribute(name);
		return (T) obj;

	}
	
	public static <T> T getSessionAttr(HttpServletRequest request, String name, T def) {
		Object obj = request.getSession().getAttribute(name);
		if(obj==null){
			return def;
		}
		return (T) obj;

	}

	public static <T> void setSessionAttr(HttpServletRequest request, String name, T obj) {
		request.getSession().setAttribute(name, obj);

	}
	

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "";
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return "";

	}

	public static void setCookie(HttpServletResponse response, String name, String obj) {
		Cookie cookie = new Cookie(name, obj);
		//在默认情况下，出于安全方面的考虑，只有与创建 cookie 的页面处于同一个目录或在创建cookie页面的子目录下的网页才可以访问。
		//那么此时如果希望其父级或者整个网页都能够使用cookie，就需要进行路径的设置。
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void setCookie(HttpServletResponse response, String name, String obj, int seconds) {
		Cookie cookie = new Cookie(name, obj);
		cookie.setMaxAge(seconds);
		//在默认情况下，出于安全方面的考虑，只有与创建 cookie 的页面处于同一个目录或在创建cookie页面的子目录下的网页才可以访问。
		//那么此时如果希望其父级或者整个网页都能够使用cookie，就需要进行路径的设置。
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static boolean containCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return false;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}

	}
	
	
	public static String simpleGet(String url) throws Exception{
		
		return Request.Get(url).execute().returnContent().asString();

		
	}
	
	public static String simpleGet(String url, Charset charset) throws Exception{
		
		Response response = Request.Get(url).execute();
		HttpEntity entity = response.returnResponse().getEntity();	   
        return EntityUtils.toString(entity, charset);
		
	}
	
	public static String simplePost(String url) throws Exception {
		
		return Request.Post(url).execute().returnContent().asString();
		
	}

	public static String simplePost(String url, Charset charset) throws Exception {
		
		Response response =  Request.Post(url).execute();//.returnContent().asString();
		HttpEntity entity = response.returnResponse().getEntity();	   
        return EntityUtils.toString(entity, charset);
	}
	
	public static String simplePost(String url, List<NameValuePair> formParams) throws Exception {
		return Request.Post(url).bodyForm(formParams).execute().returnContent().asString();
	}
	
	public static String simplePost(String url, Map<String, String> formParams) throws Exception {
		return Request.Post(url).bodyForm(convert(formParams)).execute().returnContent().asString();
	}
	
	public static String simplePost(String url, byte[] bytes) throws Exception {
		
		return Request.Post(url).bodyByteArray(bytes).execute().returnContent().asString();
	}
	
	public static String simplePost(String url, byte[] bytes, Charset charset) throws Exception {
		
		Response response =  Request.Post(url).bodyByteArray(bytes).execute();//.returnContent().asString();
		HttpEntity entity = response.returnResponse().getEntity();	   
        return EntityUtils.toString(entity, charset);
	}
	
	public static String certPost(String url, byte[] bytes, String certFile, String certPwd) throws Exception {
		
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certFile));//加载本地的证书进行https加密传输
        try {
            keyStore.load(instream, certPwd.toCharArray());//设置证书密码
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }
        
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPwd.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new ByteArrayEntity(bytes));
		
		CloseableHttpResponse response = httpclient.execute(httpPost);
		
		try {
			logger.debug("Url：{}  StatusLine：{}  ", url, response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    // do something useful with the response body
		    ContentType contentType = ContentType.getOrDefault(entity);
		    Charset contentCharset = contentType.getCharset();
	        if (contentCharset == null) {
	        	contentCharset = Consts.UTF_8;
	        }
	        return EntityUtils.toString(entity, contentCharset);
		} finally {
		    response.close();
		}
	}
	
	public static String authPost(String url, byte[] bytes, String userName, String passWord) throws Exception {
		URI uri = new URI(url);
		HttpHost httpHost = new HttpHost(uri.getHost(), uri.getPort());
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()), new UsernamePasswordCredentials(userName, passWord));
		//DigestAuthentication
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new ByteArrayEntity(bytes));
		
		// Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate DIGEST scheme object, initialize it and add it to the local
        // auth cache
        DigestScheme digestAuth = new DigestScheme();
        // Suppose we already know the realm name
        digestAuth.overrideParamter("realm", "some realm");
        // Suppose we already know the expected nonce value
        digestAuth.overrideParamter("nonce", "whatever");
        authCache.put(httpHost, digestAuth);

        // Add AuthCache to the execution context
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAuthCache(authCache);
		
        CloseableHttpResponse response =  httpclient.execute(httpHost, httpPost, localContext);
		
		try {
			logger.debug("Url：{}  StatusLine：{}  ", url, response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    // do something useful with the response body
		    ContentType contentType = ContentType.getOrDefault(entity);
		    Charset contentCharset = contentType.getCharset();
	        if (contentCharset == null) {
	        	contentCharset = Consts.UTF_8;
	        }
	        return EntityUtils.toString(entity, contentCharset);
		} finally {
		    response.close();
		}
		
	}
	
	public static String simplePost(String url, List<NameValuePair> formParams, int timeout) throws Exception{
		
		//Request.Post("http://targethost/login").bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build()).execute().returnContent();
		
		return Request.Post(url).socketTimeout(timeout).connectTimeout(timeout).bodyForm(formParams).execute().returnContent().asString();
		
	}

	public static String getData(String url) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		
		try {
		    
		    logger.debug("Url：{}  StatusLine：{}  ", url, response.getStatusLine());
		    HttpEntity entity = response.getEntity();		  
		    ContentType contentType = ContentType.getOrDefault(entity);
		    Charset contentCharset = contentType.getCharset();
	        if (contentCharset == null) {
	        	contentCharset = Consts.UTF_8;
	        }
	        //return EntityUtils.toString(entity);
	        return EntityUtils.toString(entity, contentCharset);
		} finally {
		    response.close();
		}
		
	}
	
	public static List<NameValuePair> convert(Map<String, String> parameters){
		
		List<NameValuePair> formParams = new ArrayList <NameValuePair>();
		for (String key : parameters.keySet()) {
			formParams.add(new BasicNameValuePair(key, parameters.get(key)));		
		}
		
		return formParams;
	}
	
	public static String postData(String url) throws Exception{
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		return postData(url, nvps, Consts.UTF_8);
	}
	
	public static String postData(String url, List <NameValuePair> nvps, Charset charset) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);
		
		//nvps.add(new BasicNameValuePair("rnd", RandomUtils.nextInt(0,32)+""));		
		//httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
		
		CloseableHttpResponse response = httpclient.execute(httpPost);
		
		try {
			logger.debug("Url：{}  StatusLine：{}  ", url, response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    // do something useful with the response body
//		    ContentType contentType = ContentType.getOrDefault(entity);
//		    Charset contentCharset = contentType.getCharset();
//	        if (contentCharset == null) {
//	        	contentCharset = Consts.UTF_8;
//	        }
//	        logger.debug("返回内容编码方式：{}", contentCharset);
	        return EntityUtils.toString(entity, charset);
		} finally {
		    response.close();
		}
		
	}
	/**
	 * 判断是否AJAX请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
	    return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
	}
	
	public static void downloadFile(String url, String fileName) throws ClientProtocolException, IOException{
		Request.Get(url).execute().saveContent(new File(fileName));
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//FileUtils.copyURLToFile(new URL("https://res.wx.qq.com/mpres/zh_CN/htmledition/comm_htmledition/style/page/page_login_z318e8e.png"), new File("d:\\test1.png"));
		FileUtils.copyFile(new File("d:\\test1.png"), new File("d:\\test1.jpg"));
	}
	
}
