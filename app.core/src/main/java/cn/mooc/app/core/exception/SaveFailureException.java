package cn.mooc.app.core.exception;

public class SaveFailureException extends RuntimeException {

	public SaveFailureException() {
        super("保存失败");
    }
	
	public SaveFailureException(Throwable cause) {
		super("保存失败", cause);
    }
	
	public SaveFailureException(String message) {
        super(message);
    }
	
    public SaveFailureException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
