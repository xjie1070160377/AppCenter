package cn.mooc.app.core.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cn.mooc.app.core.web.model.PagerParam;

public class HttpResponseUtil {

	public static <T> ResponseEntity<T> getAjaxFormJson(T data){
		HttpHeaders headers = new HttpHeaders();  
	    headers.setContentType(MediaType.TEXT_PLAIN);  
	    return new ResponseEntity<T>(data, headers, HttpStatus.OK);  
	}
	
	public static Map<String, Object> toPageListJson(Collection<?> dataList, long page, int pageSize, long totalCount){
		Map<String, Object> resMap = new HashMap<String, Object>();
        
		resMap.put("rows", dataList);//当前页的数据集合	
		resMap.put("page", page);//当前页码
        resMap.put("records", totalCount);//总记录条数
        resMap.put("total", getTotalPage(totalCount, pageSize));//总页数

        return resMap;
        
	}

	public static Map<String, Object> toPageListJson(Map<String, Object> resMap, List<?> listData, long totalElements, int totalPages, PagerParam pageParam){
		
		resMap.put("rows", listData);//当前页的数据集合	
		resMap.put("page", pageParam.getPage());//当前页码
        resMap.put("records", totalElements);//总记录条数
        resMap.put("total", totalPages);//总页数

        
        return resMap;
	}
	
	public static Map<String, Object> toPageListJson(Map<String, Object> resMap, Page<?> pageData, PagerParam pageParam){
		
		resMap.put("rows", pageData.getContent());//当前页的数据集合	
		resMap.put("page", pageParam.getPage());//当前页码
        resMap.put("records", pageData.getTotalElements());//总记录条数
        resMap.put("total", pageData.getTotalPages());//总页数

        
        return resMap;
	}
	
	public static Map<String, Object> successJson(){

        return resMapJson(true, "");
        
	}
	public static Map<String, Object> successJson(List<?> list, Page<?> pageData, PagerParam pageParam){
		return successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	public static Map<String, Object> successJson(List<?> listData, long totalElements, int totalPages, PagerParam pageParam){
		Map<String, Object> resMap = new HashMap<String, Object>();
		toPageListJson(resMap, listData, totalElements, totalPages, pageParam);
		return resMapJson(resMap, true, "");
        
	}
	public static Map<String, Object> successJson(Page<?> pageData, PagerParam pageParam){
		Map<String, Object> resMap = new HashMap<String, Object>();
		toPageListJson(resMap, pageData, pageParam);
		return resMapJson(resMap, true, "");
        
	}
	
	public static Map<String, Object> successJson(Map<String, Object> resMap, Page<?> pageData, PagerParam pageParam){
		toPageListJson(resMap, pageData, pageParam);
		return resMapJson(resMap, true, "");
        
	}
	
	public static Map<String, Object> successJson(String msg){

        return resMapJson(true, msg);
        
	}
	
	public static Map<String, Object> failureJson(String msg){

        return resMapJson(false, msg);
        
	}
	
	public static Map<String, Object> successJson(Map<String, Object> resMap, String msg){

        return resMapJson(resMap, true, msg);
        
	}
	
	public static Map<String, Object> failureJson(Map<String, Object> resMap, String msg){

        return resMapJson(resMap, false, msg);
        
	}
	
	public static Map<String, Object> resMapJson( boolean status, String msg) {        
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("resultStatus", status);
		resMap.put("resultMsg", msg);

        return resMap;
        
	}
	
	public static Map<String, Object> resMapJson( boolean status, Object object) {        
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("resultStatus", status);
		resMap.put("resultObject", object);

        return resMap;
        
	}
	
	public static Map<String, Object> resMapJson(Map<String, Object> resMap, boolean status, String msg) {        
		resMap.put("resultStatus", status);
		resMap.put("resultMsg", msg);

        return resMap;
        
	}
	
	
	public static int getTotalPage(long totalCount, int pageSize) {
		int totalPage = (int) Math.ceil(totalCount/(double)pageSize);
		return totalPage;
	}
	
	public static void writeHtml(HttpServletResponse response, String s) {
		response.setContentType("text/html;charset=utf-8");
		setNoCacheHeader(response);
		try {
			response.getWriter().write(s);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeHtmlGBK(HttpServletResponse response, String s) {
		response.setContentType("text/html;charset=GBK");
		setNoCacheHeader(response);
		try {
			response.getWriter().write(s);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}
	
}
