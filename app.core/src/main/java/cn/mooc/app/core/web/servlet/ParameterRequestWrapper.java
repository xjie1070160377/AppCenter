package cn.mooc.app.core.web.servlet;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 该类实现对 HttpServletRequest 的伪造和改写servlet请求
 * @author Taven
 *
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {
	
	private Map params;  
	  
    public ParameterRequestWrapper(HttpServletRequest request, Map params) {  
        super(request);  
        this.params = params;  
    }  
  
    public Map getParameterMap() {  
        return params;  
    }  
  
    public Enumeration getParameterNames() {  
        Vector l = new Vector(params.keySet());  
        return l.elements();  
    }  
  
    public String[] getParameterValues(String name) {  
        Object v = params.get(name);  
        if (v == null) {  
            return null;  
        } else if (v instanceof String[]) {  
            return (String[]) v;  
        } else if (v instanceof String) {  
            return new String[] { (String) v };  
        } else {  
            return new String[] { v.toString() };  
        }  
    }  
  
    public String getParameter(String name) {  
        Object v = params.get(name);  
        if (v == null) {  
            return null;  
        } else if (v instanceof String[]) {  
            String[] strArr = (String[]) v;  
            if (strArr.length > 0) {  
                return strArr[0];  
            } else {  
                return null;  
            }  
        } else if (v instanceof String) {  
            return (String) v;  
        } else {  
            return v.toString();  
        }  
    }  
}  
	

