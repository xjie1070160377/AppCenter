package cn.mooc.app.core.utils;

import java.util.regex.Pattern;

public class RegexUtil {
	
	public static boolean isMatch(String regex, String input){
		boolean matche = Pattern.compile(regex).matcher(input).matches();
		return matche;
	}
	
	
}
