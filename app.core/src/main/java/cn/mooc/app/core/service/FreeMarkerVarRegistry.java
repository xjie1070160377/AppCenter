package cn.mooc.app.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FreeMarkerVarRegistry {

	private Map<String, Object> variables = new HashMap<String, Object>();
	
	public void addVar(String varName, Object obj){
		this.variables.put(varName, obj);
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	
	
	
}
