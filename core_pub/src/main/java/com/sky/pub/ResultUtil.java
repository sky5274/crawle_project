package com.sky.pub;

import java.util.List;

public class ResultUtil {
	
	public static <T> Result<T> getOk(ResultCode code,T data){
		return new Result<>(code,data).ok();
	}
	public static <T> Result<T> getOk(T data){
		return new Result<>(ResultCode.OK,data).ok();
	}
	public static <T> Result<T> get(ResultCode code,T data){
		return new Result<>(code,data);
	}
	public static <T> Result<T> getFailed(ResultCode code,T data){
		return new Result<>(code,data).fail();
	}
	public static <T> Result<T> getFailed(ResultCode code,String msg){
		return new Result<T>(code.getCode(),msg).fail();
	}
	public static <T> Result<T> copy(Result<Object> result){
		return new Result<>(result.getCode(),result.getMessage());
	}
	public static <T> Result<T> getFailed(ResultCode code,T data,List<MsgPrinter> msgdatas){
		return new ResultMData<T>(code,data, msgdatas).fail();
	}
	public static <T> Result<T> getFailed(ResultCode code,List<MsgPrinter> msgdatas){
		return new ResultMData<T>(code.getCode(), msgdatas).fail();
	}
	public static <T> Result<T> getFailed(ResultCode code,String msg,List<MsgPrinter> msgdatas){
		return new ResultMData<T>(code.getCode(), msg,msgdatas).fail();
	}
}
