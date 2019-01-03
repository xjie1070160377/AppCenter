package cn.mooc.app.core.plugin.context;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class PluginServletContext {

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private byte[] reqInputBytes;
	
	public PluginServletContext(HttpServletRequest request){
		this.initReqContent(request);
		this.request = request;
		
	}
	
	public PluginServletContext(HttpServletRequest request, HttpServletResponse response){
		this.initReqContent(request);
		this.request = request;
		this.response = response;
	}
	
	private void initReqContent(HttpServletRequest request){
		//防止POST参数在request中取不到。
		//***** 勿删！！！ *****
		request.getParameterMap();
		//***** 勿删！！！ *****
		try {
			this.reqInputBytes = IOUtils.toByteArray(request.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public byte[] getReqInputBytes() {
		return reqInputBytes;
	}

	public void setReqInputBytes(byte[] reqInputBytes) {
		this.reqInputBytes = reqInputBytes;
	}


	
	
}
