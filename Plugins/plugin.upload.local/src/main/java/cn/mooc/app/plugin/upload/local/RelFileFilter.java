package cn.mooc.app.plugin.upload.local;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class RelFileFilter implements FileFilter {

	private String regex = "";
	
	public RelFileFilter(String regex){
		this.regex = regex;
	}
	
	@Override
	public boolean accept(File pathname) {
		String fileName = pathname.getName();
		//Pattern.compile("fileName_(\\d)+x(\\d)+\\..*").matcher(fileName).matches()
		//Pattern.compile("fileName_(\\d)+\\..*"").matcher(fileName).matches()
		boolean matche = Pattern.compile(this.regex).matcher(fileName).matches();
		return matche;
	}

}
