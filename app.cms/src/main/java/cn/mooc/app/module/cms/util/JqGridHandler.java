package cn.mooc.app.module.cms.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

//import org.codehaus.jackson.map.ObjectMapper;



/**
 * jqGrid表格工具类
 * @author oyhx
 *
 */
public class JqGridHandler {
    /**
     * 获取查询条件QueryParameter
     * @param request
     * @return
     */
//    public static QueryParameter generateQueryWebParameter(HttpServletRequest request){
//    	if(request == null){
//    		throw new IllegalStateException("Can not be obtained from the Request object in the thread context.");
//    	}
//    	String sidx = request.getParameter("sidx");
//    	String sord = request.getParameter("sord");
//    	String page = request.getParameter("page");//当前页数
//    	String rows = request.getParameter("rows");//每页显示行数
//    	
//        int pageNo = StringUtil.toInt(page);
//        int pageSize = StringUtil.toInt(rows);
//        int start = (pageNo-1)*pageSize;
//        
//        QueryParameter queryParameter = QueryParameter.createQueryParameter();
//		queryParameter.setStart(start);
//		queryParameter.setSize(pageSize);
//		
//		if(sidx!=null && !"".equals(sidx)){
//			List<OrderParameter> orderParameters = new ArrayList<OrderParameter>();
//			orderParameters.add(OrderParameter.createOrderParameter(sidx, sord));
//			queryParameter.setOrderParameters(orderParameters);
//		}
//		
//		Map paramMap = getQueryWebParameter(request);
//		Iterator iter = paramMap.keySet().iterator();
//		while(iter.hasNext()){
//			String key = (String)iter.next();
//			String value = (String)paramMap.get(key);
//			queryParameter.addWhereParameter(AbstractWhereParameter.createWhereParameterBase(key, value));
//		}
//		
//    	return queryParameter;
//    }
    /**
     * 获取查询条件Map
     * @param request
     * @return
     */
    public static Map<String, Object> getQueryWebParameter(HttpServletRequest request){
    	if(request == null){
    		throw new IllegalStateException("Can not be obtained from the Request object in the thread context.");
    	}
    	String _search = request.getParameter("_search");
    	String searchOper = request.getParameter("searchOper");
    	String searchString = request.getParameter("searchString");
    	String searchField = request.getParameter("searchField");
    	String filters = request.getParameter("filters");
    	
    	// 分拆，全部写入filersearch
    	// 存储总体的search
        FilterSearch filterSearch = null;
        if (null != _search && "true".equalsIgnoreCase(_search)) {
            // 先写多选择的，一般有多选择就不会有单选择。
            if (null != filters && filters.length() > 0) {
                    ObjectMapper objectMapper=new ObjectMapper();
                    try {
						filterSearch = objectMapper.readValue(filters, FilterSearch.class);
					} catch (Exception e) {
						throw new IllegalStateException("Can not be parse search filters.");
					}
            } else {
                if (null != searchOper && null != searchString && null != searchField) {
                    SearchRule rule = new SearchRule();
                    rule.setData(searchString);
                    rule.setOp(searchOper);
                    rule.setField(searchField);
                    filterSearch = new FilterSearch();
                    filterSearch.setGroupOp(null);
                    List<SearchRule> rules = new ArrayList<SearchRule>();
                    rules.add(rule);
                    filterSearch.setRules(rules);
                }
            }
        }
       if(filterSearch == null){
    	   return new HashMap();
       }
       return filterSearch.getParamsMap();
    }
    /**
     * 将Page转换成Json字符串
     * @param page
     * @param columns
     * @param idColumn
     * @return
     */
//    public static String convertToJson(Page page,String[] columns,String idColumn){
//		
//		StringBuffer sbf = new StringBuffer("");
//		
//		sbf.append("{");
//		
//		sbf.append("\"page\":").append(page.getCurrentPageNo());
//		sbf.append(",\"total\":").append(page.getTotalPageCount());
//		sbf.append(",\"records\":").append(page.getTotalCount());
//		Collection list = page.getResults();
//		if(list!=null&&list.size()>0){
//		
//			sbf.append(",\"rows\":").append("[");
//			int i=0;
//			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//				if(i>0){
//					sbf.append(",");
//				}
//				//sbf.append("{");
//				//sbf.append("rowData:");
//				sbf.append("{");
//				Map element = (Map)iterator.next();
//				
//				String idValue = StringUtil.STRING_EMPTY + getJsonValue(element.get(idColumn));
//				sbf.append("\"id\"").append(":").append(idValue);
//				
//				for(int j=0;j<columns.length;j++){
//					Object valueObject = getJsonValue(element.get(columns[j]));
//					if(valueObject!=null){
//						sbf.append(",");
//						
//						String value = StringUtil.STRING_EMPTY + getJsonValue(element.get(columns[j]));
//						sbf.append("\"").append(columns[j]).append("\"").append(":").append(value);
//					}
//					
//				}
//				sbf.append("}");
//				i++;
//			}
//			sbf.append("]");
//		}
//
//		sbf.append("}");
//		return sbf.toString();
//	}

    /**
     * 把Page转换成树型的Json字符串
     * @param page
     * @param columns
     * @param idColumn
     * @param level
     * @param parent
     * @param terminated
     * @param expanded
     * @return
     */
//    public static String convertToTreeJson(Page page,String[] columns,String idColumn, String level, String parent, String terminated, boolean expanded){
//		
//		StringBuffer sbf = new StringBuffer("");
//		
//		sbf.append("{");
//		
//		sbf.append("\"page\":").append(page.getCurrentPageNo());
//		sbf.append(",\"total\":").append(page.getTotalPageCount());
//		sbf.append(",\"records\":").append(page.getTotalCount());
//		Collection list = page.getResults();
//		if(list!=null&&list.size()>0){
//		
//			sbf.append(",\"rows\":").append("[");
//			int i=0;
//			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//				if(i>0){
//					sbf.append(",");
//				}
//				sbf.append("{");
//				Map element = (Map)iterator.next();
//				
//				String idValue = StringUtil.STRING_EMPTY + getJsonValue(element.get(idColumn));
//				sbf.append("\"id\"").append(":").append(idValue);
//				
//				for(int j=0;j<columns.length;j++){
//					Object valueObject = getJsonValue(element.get(columns[j]));
//					if(valueObject!=null){
//						sbf.append(",");
//						
//						String value = StringUtil.STRING_EMPTY + getJsonValue(element.get(columns[j]));
//						sbf.append("\"").append(columns[j]).append("\"").append(":").append(value);
//					}
//				}
//				//节点级别
//				String levelValue = StringUtil.STRING_EMPTY + getJsonValue(element.get(level));
//				sbf.append(",");
//				sbf.append("\"level\":").append(levelValue);
//				//父节点ID
//				String pValue = StringUtil.STRING_EMPTY + getJsonValue(element.get(parent));
//				sbf.append(",");
//				sbf.append("\"parent\":").append(pValue);
//				//是否有下级节点
//				String leftValue = StringUtil.STRING_EMPTY + getJsonValue(element.get(terminated));
//				if(leftValue.equals("0")){
//					sbf.append(",");
//					sbf.append("\"isLeaf\":").append(false);
//				}else{
//					sbf.append(",");
//					sbf.append("\"isLeaf\":").append(true);
//				}
//				//是否展开
//				sbf.append(",");
//				sbf.append("\"expanded\":").append(expanded);
//				//是否有加载子节点
//				sbf.append(",");
//				sbf.append("\"loaded\":").append(false);
//				
//				sbf.append("}");
//				i++;
//			}
//			sbf.append("]");
//		}
//
//		sbf.append("}");
//		return sbf.toString();
//	}
    
//	private static String getJsonValue(Object value) {
//		if (value == null) {
//			return null;
//		}
//
//		String strValue = "";
//
//		/* 日期类型 */
//		if (value instanceof Date) {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			return "\"" + sdf.format(value) + "\"";
//		}
//
//		if (value instanceof Integer || value instanceof Long) {
//			return StringUtil.STRING_EMPTY + value;
//		}
//
//		if (value instanceof java.math.BigDecimal) {
//			return StringUtil.STRING_EMPTY + ((java.math.BigDecimal) value).toString();
//		}
//
//		/* 字符串类型 */
//		if (value instanceof String) {
//
//			strValue = quote(StringUtil.STRING_EMPTY + (value));
//			return strValue;
//		}
//
//		strValue = quote(StringUtil.STRING_EMPTY + value);
//
//		return strValue;
//	}

//	private static String quote(String string) {
//		StringWriter sw = new StringWriter();
//		synchronized (sw.getBuffer()) {
//			try {
//				return quote(string, sw).toString();
//			} catch (IOException ignored) {
//				return "";
//			}
//		}
//	}

//	private static Writer quote(String string, Writer w) throws IOException {
//		if (string == null || string.length() == 0) {
//			w.write("\"\"");
//			return w;
//		}
//
//		char b;
//		char c = 0;
//		String hhhh;
//		int i;
//		int len = string.length();
//
//		w.write('"');
//		for (i = 0; i < len; i += 1) {
//			b = c;
//			c = string.charAt(i);
//			switch (c) {
//			case '\\':
//			case '"':
//				w.write('\\');
//				w.write(c);
//				break;
//			case '/':
//				if (b == '<') {
//					w.write('\\');
//				}
//				w.write(c);
//				break;
//			case '\b':
//				w.write("\\b");
//				break;
//			case '\t':
//				w.write("\\t");
//				break;
//			case '\n':
//				w.write("\\n");
//				break;
//			case '\f':
//				w.write("\\f");
//				break;
//			case '\r':
//				w.write("\\r");
//				break;
//			default:
//				if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
//					w.write("\\u");
//					hhhh = Integer.toHexString(c);
//					w.write("0000", 0, 4 - hhhh.length());
//					w.write(hhhh);
//				} else {
//					w.write(c);
//				}
//			}
//		}
//		w.write('"');
//		return w;
//	}
}
