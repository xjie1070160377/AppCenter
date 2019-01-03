package cn.mooc.app.core.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * https://github.com/FasterXML/jackson-databind
 * 
 * https://github.com/FasterXML/jackson-annotations
 * 
 * 
 * @author Taven
 *
 */
public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String toJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public static void exportToFile(File outFile, Object obj) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outFile, obj);
	}

	public static <T> T fromJson(String json, Class<T> objClass) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, objClass);
	}
	
	public static <T> T fromJson(String json, Class<?> collectionClass, Class<T> objClass) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, objClass);
		return mapper.readValue(json, javaType);
	}

	/**
	 * @param jsonFile
	 *            eg：new File("data.json")
	 * @param objClass
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromFile(File jsonFile, Class<T> objClass) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonFile, objClass);
	}

	/**
	 * @param url
	 *            http://some.com/api/entry.json
	 * @param objClass
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromUrl(String url, Class<T> objClass) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(new URL(url), objClass);
	}

	/**
	 * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T update(String jsonString, T object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return (T) mapper.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}

}
