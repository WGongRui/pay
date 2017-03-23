package pay.weixn.exception;

public class WxPayException extends Exception {

	private String message;
	
	public WxPayException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
