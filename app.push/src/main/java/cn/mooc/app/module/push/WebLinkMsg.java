package cn.mooc.app.module.push;

import java.io.Serializable;

public class WebLinkMsg implements Serializable {

	private static final long serialVersionUID = -404439653845001533L;
	
	private String webUrl;
	

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	
}
