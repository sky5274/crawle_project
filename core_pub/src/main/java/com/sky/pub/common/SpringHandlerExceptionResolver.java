package com.sky.pub.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.sky.pub.Result;
import com.sky.pub.common.exception.HttpExceptionEnum;
import com.sky.pub.common.exception.ResultException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@SuppressWarnings("deprecation")
@Configuration
public class SpringHandlerExceptionResolver implements HandlerExceptionResolver {

	private static Log logger = LogFactory.getLog(SpringHandlerExceptionResolver.class);
	private String errorUrl="/error";

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,Object handlerMethod, Exception ex) {
		boolean isRespondBody=false;
		if(handlerMethod instanceof HandlerMethod ){
			if (handlerMethod != null) {
				HandlerMethod handler = (HandlerMethod)handlerMethod;
				Method method = handler.getMethod();
				if (method != null) {
					//如果定义了ExceptionHandler则返回相应的Map中的数据
					ResponseBody annot = method.getAnnotation(ResponseBody.class);
					if(annot!=null){
						isRespondBody=true;
					}
				}
				Object bean = handler.getBean();
				if(bean!=null){
					if(bean.getClass().getAnnotation(RestController.class)!=null){
						isRespondBody=true;
					}
				}
			}
		}
		ModelAndView mv = specialExceptionResolve(ex, request);
		if (null == mv) {
			String message = "系统异常，请联系管理员";
			//BaseSystemException是我自定义的异常基类，继承自RuntimeException
			if (ex instanceof ResultException) {
				ResultException resex = (ResultException)ex;
				logger.warn("请求处理失败，请求url=["+ request.getRequestURI()+"], 失败原因 : "+resex.getMsg());
				resex.printStackTrace();
				mv = errorResult(resex.getCode(),resex.getMsg(),request.getPathInfo(),resex.getClass(), request,isRespondBody);
			}else{
				logger.error(ex.getMessage(),ex);
				mv = errorResult(500+"",message, request.getPathInfo(), ex.getClass(),request,isRespondBody);
			}
		}
		return mv;
	}

	/**
	 * 这个方法是拷贝 {@link org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver#doResolveException},
	 * 加入自定义处理，实现对400， 404， 405， 406， 415， 500(参数问题导致)， 503的处理
	 *
	 * @param ex      异常信息
	 * @param request 当前请求对象(用于判断当前请求是否为ajax请求)
	 * @return 视图模型对象
	 */
	private ModelAndView specialExceptionResolve(Exception ex, HttpServletRequest request) {
		try {
			if (ex instanceof NoSuchRequestHandlingMethodException 
					|| ex instanceof NoHandlerFoundException) {
				return result(HttpExceptionEnum.NOT_FOUND_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof HttpRequestMethodNotSupportedException) {
				return result(HttpExceptionEnum.NOT_SUPPORTED_METHOD_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof HttpMediaTypeNotSupportedException) {
				return result(HttpExceptionEnum.NOT_SUPPORTED_MEDIA_TYPE_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof HttpMediaTypeNotAcceptableException) {
				return result(HttpExceptionEnum.NOT_ACCEPTABLE_MEDIA_TYPE_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof MissingServletRequestParameterException) {
				return result(HttpExceptionEnum.MISSING_REQUEST_PARAMETER_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof ServletRequestBindingException) {
				return result(HttpExceptionEnum.REQUEST_BINDING_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof ConversionNotSupportedException) {
				return result(HttpExceptionEnum.NOT_SUPPORTED_CONVERSION_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof TypeMismatchException) {
				return result(HttpExceptionEnum.TYPE_MISMATCH_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof HttpMessageNotReadableException) {
				return result(HttpExceptionEnum.MESSAGE_NOT_READABLE_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof HttpMessageNotWritableException) {
				return result(HttpExceptionEnum.MESSAGE_NOT_WRITABLE_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof MethodArgumentNotValidException) {
				return result(HttpExceptionEnum.NOT_VALID_METHOD_ARGUMENT_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof MissingServletRequestPartException) {
				return result(HttpExceptionEnum.MISSING_REQUEST_PART_EXCEPTION, request,ex.getClass());
			}
			else if (ex instanceof BindException) {
				return result(HttpExceptionEnum.BIND_EXCEPTION, request,ex.getClass());
			}
		} catch (Exception handlerException) {
			logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
		}
		return null;
	}

	/**
	 * 判断是否ajax请求
	 *
	 * @param request 请求对象
	 * @return true:ajax请求  false:非ajax请求
	 */
	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}
	/**
	 * 是否是json格式请求
	 * @author  wangfan
	 * @time  2018年10月29日 下午4:14:30
	 * @param request
	 * @return
	 */
	private boolean isJson(HttpServletRequest request) {
		return "application/json".equalsIgnoreCase(request.getHeader("content-Type "));
	}

	/**
	 * 返回错误信息
	 *
	 * @param message 错误信息
	 * @param url     错误页url
	 * @param request 请求对象
	 * @param isRespondBody 
	 * @return 模型视图对象
	 */
	private ModelAndView errorResult(String code,String message, String url,Class<? extends Exception> exClass, HttpServletRequest request, boolean isRespondBody) {
		if (isRespondBody || isAjax(request) || isJson(request)) {
			return jsonResult(code, message);
		} else {
			return normalResult(code,message, url,exClass);
		}
	}

	/**
	 * 返回异常信息
	 *
	 * @param httpException 异常信息
	 * @param request 请求对象
	 * @return 模型视图对象
	 */
	private ModelAndView result(HttpExceptionEnum httpException, HttpServletRequest request,Class<? extends Exception> exClass) {
		logger.warn("请求处理失败，请求url=["+ request.getRequestURI()+"], 失败原因 : "+ httpException.getMessage());
		if (isAjax(request)) {
			return jsonResult(httpException.getCode(), httpException.getMessage());
		} else {
			return normalResult(httpException.getCode(),httpException.getMessage(), request.getPathInfo(),exClass);
		}
	}

	/**
	 * 返回错误页
	 *
	 * @param message 错误信息
	 * @param url     错误页url
	 * @param url2 
	 * @return 模型视图对象
	 */
	private ModelAndView normalResult(String code,String message, String url,Class<? extends Exception> exclazz) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("timestamp", new Date());
		model.put("status", 500);
		model.put("error", code);
		model.put("path", url);
		model.put("exception", exclazz.getName());
		model.put("message", message);
		return new ModelAndView(errorUrl, model);
	}

	/**
	 * 返回错误数据
	 *
	 * @param message 错误信息
	 * @return 模型视图对象
	 */
	private ModelAndView jsonResult(String code, String message) {
		ModelAndView mv = new ModelAndView();
		FastJsonJsonView view = new FastJsonJsonView();
		view.setFastJsonConfig(new FastJsonConfig());
		view.setAttributesMap((JSONObject) JSON.toJSON(new Result<>(code, message,false)));
		mv.setView(view);
		return mv;
	}
}
