package cn.mooc.app.core.exception;

public class ExistUserException extends RuntimeException {

	public ExistUserException(String message) {
		super(message);
	}

	public ExistUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
