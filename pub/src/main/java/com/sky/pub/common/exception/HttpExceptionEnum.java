package com.sky.pub.common.exception;

/**
 * http  异常枚举
 * @author wangfan
 * @time  2018年9月17日 下午4:49:12
 */
public enum HttpExceptionEnum {
	MESSAGE_NOT_READABLE_EXCEPTION("HTTP_01","消息读取异常"),
	NOT_FOUND_EXCEPTION("HTTP_02","未发现路径"),
	NOT_SUPPORTED_METHOD_EXCEPTION("HTTP_03","不支持该请求method"),
	NOT_SUPPORTED_MEDIA_TYPE_EXCEPTION("HTTP_04","不支持该媒体类型"),
	NOT_ACCEPTABLE_MEDIA_TYPE_EXCEPTION("HTTP_05","未找到请求的媒体类型"),
	MISSING_REQUEST_PARAMETER_EXCEPTION("HTTP_06","失去请求状态"),
	REQUEST_BINDING_EXCEPTION("HTTP_07","请求绑定异常"),
	NOT_SUPPORTED_CONVERSION_EXCEPTION("HTTP_08","不支持转译"),
	TYPE_MISMATCH_EXCEPTION("HTTP_09","类型匹配异常"), 
	MESSAGE_NOT_WRITABLE_EXCEPTION("HTTP_10","消息不在等待异常"),
	MISSING_REQUEST_PART_EXCEPTION("HTTP_11","http请求缺失异常"),
	NOT_VALID_METHOD_ARGUMENT_EXCEPTION("HTTP_12","未验证请求方法异常"),
	BIND_EXCEPTION("HTTP_13","绑定异常"),
	ASYNC_REQUEST_TIMEOUT_EXCEPTION("HTTP-14","异步请求异常");

	private String code;
	private String message;
	HttpExceptionEnum(String code,String message){
		this.code=code;
		this.message=message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
