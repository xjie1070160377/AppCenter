package cn.mooc.app.core.exception;


public class WebShowException extends RuntimeException {

	public WebShowException(String message) {
        super(message);
    }
	
    public WebShowException(String message, Throwable cause) {
        super(message, cause);
    }
    
}