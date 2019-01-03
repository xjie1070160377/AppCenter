package cn.mooc.app.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * excel相关操作
 * 
 * @author KH
 * @version 2012-7-4
 */

public class ExcelUtil {

	/**
	 * 导出Excel的头部分定义输出
	 * 
	 * @param workbook
	 * @param response
	 * @param filename
	 * @throws IOException
	 */
	public static void printExcel(HttpServletResponse response, HSSFWorkbook workbook, String filename) throws IOException {
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + filename);
		response.setContentType("application/msexcel");
		response.setCharacterEncoding("utf-8");
		workbook.write(out);
		out.flush();
		out.close();
	}

	public static void expExcel(HttpServletRequest request, HttpServletResponse response, InputStream is, Map<String, Object> map, String fileName)
			throws InvalidFormatException, IOException, UnsupportedEncodingException {
		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, map);

		// 获取浏览器信息
		String agent = request.getHeader("USER-AGENT");
		// 处理不同浏览器中文文件名显示乱码的问题
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			ExcelUtil.printExcel(response, workbook, URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
		} else if (null != agent && -1 != agent.indexOf("Firefox")) {
			ExcelUtil.printExcel(response, workbook, new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
		} else {
			ExcelUtil.printExcel(response, workbook, URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
		}
	}

}
