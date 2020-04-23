package com.sky.pub;

/**
 * 异常结果返回  携带异常信息
 * @author wangfan
 * @date 2020年3月7日 下午1:22:19
 */
public class ResultExceptionData<T> extends Result<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exception;
	public ResultExceptionData(String code, String messsage) {
		super(code,messsage);
	}
	public ResultExceptionData(String code, String messsage,String exception,T data,boolean success) {
		super(code,messsage,data,success);
		this.setException(exception);
	}
	public ResultExceptionData(ResultCode code) {
		super(code);
	}
	public ResultExceptionData() {
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}

}
