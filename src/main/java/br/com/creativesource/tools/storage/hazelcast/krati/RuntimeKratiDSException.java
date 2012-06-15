package br.com.creativesource.tools.storage.hazelcast.krati;

public class RuntimeKratiDSException extends Exception {

	/**
	 * 
	 */
	public RuntimeKratiDSException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public RuntimeKratiDSException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RuntimeKratiDSException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public RuntimeKratiDSException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RuntimeKratiDSException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
