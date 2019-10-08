package com.sky.transaction.bean;

import java.io.Serializable;

public class TransactionResult implements Serializable{

	/***/
	private static final long serialVersionUID = -8990302888803480519L;
	
	private boolean isSuccess;
	private Throwable exception;
	public TransactionResult(boolean success) {
		this.isSuccess=success;
	}
	public TransactionResult(Throwable exception) {
		this.isSuccess=false;
		this.exception=exception;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
