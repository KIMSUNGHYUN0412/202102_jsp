package kr.or.ddit.commons.exception;

public class DataNotFoundException extends RuntimeException{

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNotFoundException(String message) { 
		super(String.format("%s PK에 해당하는 데이터가 없음", message));
	}

	public DataNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
