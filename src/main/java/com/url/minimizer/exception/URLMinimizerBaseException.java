package com.url.minimizer.exception;

public class URLMinimizerBaseException extends RuntimeException {

	private final int statusCode;

	public URLMinimizerBaseException(int statusCode,String message){
		super(message);
		this.statusCode = statusCode;
	}

	public URLMinimizerBaseException(int statusCode,String message,Throwable cause){
		super(message, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
