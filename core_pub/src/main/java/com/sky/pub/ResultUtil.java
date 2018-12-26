package com.sky.pub;

public class ResultUtil {
	
	public static <T> Result<T> getOk(ResultCode code,T data){
		return new Result<>(code,data).ok();
	}
	public static <T> Result<T> get(ResultCode code,T data){
		return new Result<>(code,data);
	}
	public static <T> Result<T> getFailed(ResultCode code,T data){
		return new Result<>(code,data).fail();
	}
	public static <T> Result<T> copy(Result<Object> result){
		return new Result<>(result.getCode(),result.getMessage());
	}
	
}
