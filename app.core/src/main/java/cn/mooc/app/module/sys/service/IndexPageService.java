package cn.mooc.app.module.sys.service;

import org.springframework.stereotype.Service;

@Service
public class IndexPageService {

	private IndexAction indexAction = new DefaultIndexAction();
	private LoginAction loginAction = new DefaultLoginAction();
	private MIndexAction mindexAction = new DefaultMIndexAction();
	
	public void setIndexAction(IndexAction indexAction){
		this.indexAction = indexAction;
	}
	
	public IndexAction getIndexAction(){
		return indexAction;
	}
	
	public void setLoginAction(LoginAction loginAction){
		this.loginAction = loginAction;
	}
	
	public LoginAction getLoginAction(){
		return loginAction;
	}

	public MIndexAction getMindexAction() {
		return mindexAction;
	}

	public void setMindexAction(MIndexAction mindexAction) {
		this.mindexAction = mindexAction;
	}


	
	
	
}
