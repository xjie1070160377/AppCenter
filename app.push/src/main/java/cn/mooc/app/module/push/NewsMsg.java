package cn.mooc.app.module.push;

import java.io.Serializable;

public class NewsMsg implements Serializable {

	private static final long serialVersionUID = 3373589677748055452L;

	private int articleId;
	

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	
	
}
