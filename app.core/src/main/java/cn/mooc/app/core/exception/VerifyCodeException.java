package cn.mooc.app.core.exception;

import org.apache.shiro.authc.AuthenticationException;

public class VerifyCodeException extends AuthenticationException {

	private static final long serialVersionUID = 6094309079928232279L;

	public VerifyCodeException(String message) {
        super(message);
    }
    
}
