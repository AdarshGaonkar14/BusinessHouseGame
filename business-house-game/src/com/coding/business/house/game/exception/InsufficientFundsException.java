package com.coding.business.house.game.exception;

public class InsufficientFundsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 78275950010659998L;

	public InsufficientFundsException() {
		super();
	}

	public InsufficientFundsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InsufficientFundsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientFundsException(String message) {
		super(message);
	}

	public InsufficientFundsException(Throwable cause) {
		super(cause);
	}

}
