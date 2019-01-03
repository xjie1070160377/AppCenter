package cn.mooc.app.core.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtils {
 
	/**
	 * 字符串转Map
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static Map xml2Map(String xml) throws DocumentException{
		Document document;
		Map params = new HashMap();
		try {
			document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			
			List<Element> node = nodeElement.elements();
			params = xml2Map(node);
		} catch (DocumentException e) {
			throw e;
		}
		
		return params;
	}
	
	private static Map xml2Map(List<Element> elm){
		Map params = new HashMap();
		List list = new ArrayList();
		for(Element node : elm){
			List<Element> n = node.elements();
			if(n == null || n.size() == 0){
				String text = node.getText();
				params.put(node.getName().toLowerCase(), text);
			}else{
				list.add(xml2Map(n));
			}
		}
		if(list != null && list.size() > 0){
			params.put("data", list);
		}
		return params;
	}
	
	public static String mapToXml(Map map, String rootName) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement(rootName);
		addXml(map, nodeElement);
		return doc2String(document);
	}
	
	public static void addXml(Map map, Element nodeElement){
		for(Object obj : map.keySet()){
			Object o = map.get(obj);
			if(o instanceof Map){
				Element keyElement = nodeElement.addElement(StringUtil.strnull(obj).toUpperCase());
				addXml((Map)o, keyElement);
			}else if(o instanceof List){
				List<Map> list = (List<Map>)o;
				for(Map p : list){
					Element keyElement = nodeElement.addElement(StringUtil.strnull(obj).toUpperCase());
					addXml((Map)p, keyElement);
				}
			}else{
				Element keyElement = nodeElement.addElement(StringUtil.strnull(obj).toUpperCase());
				keyElement.setText(StringUtil.strnull(map.get(obj)));
			}
		}
	}
	public static String mapToXml2(Map map, String rootName) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement(rootName);
		addXml2(map, nodeElement);
		return doc2String(document);
	}
	
	public static void addXml2(Map map, Element nodeElement){
		for(Object obj : map.keySet()){
			Object o = map.get(obj);
			if(o instanceof Map){
				Element keyElement = nodeElement.addElement(StringUtil.strnull(obj).toUpperCase());
				addXml2((Map)o, keyElement);
			}else if(o instanceof List){
				List<Map> list = (List<Map>)o;
				Element keyElement = nodeElement.addElement(StringUtil.strnull(obj).toUpperCase());
				for(Map p : list){
					addXml2((Map)p, keyElement);
				}
			}else{
				Element keyElement = nodeElement.addElement(StringUtil.strnull(obj).toUpperCase());
				keyElement.setText(StringUtil.strnull(map.get(obj)));
			}
		}
	}
	
	public static String doc2String(Document document) throws Exception {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream(); // 浣跨敤UTF-8缂栫爜
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			throw ex;
		}
		return s;
	}
}
