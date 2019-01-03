package cn.mooc.app.core.service;

import java.util.HashSet;
import java.util.Set;

import org.beetl.core.Function;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.web.view.ViewShareVars;

@Service
public class ViewFuncRegistry {
	
	private Set<FuncLoader> funcLoaders = new HashSet<FuncLoader>();
	
	private ViewShareVars viewShareVars;
	
	public void addFuncLoader(FuncLoader funcLoader){

		funcLoaders.add(funcLoader);
		
	}
	
	
	public void registerFunction(String name, Function fn){

		viewShareVars.registerFunction(name, fn);
		
	}


	public ViewShareVars getViewShareVars() {
		return viewShareVars;
	}


	public void setViewShareVars(ViewShareVars viewShareVars) {
		this.viewShareVars = viewShareVars;
	}


	public Set<FuncLoader> getFuncLoaders() {
		return funcLoaders;
	}


	
}
