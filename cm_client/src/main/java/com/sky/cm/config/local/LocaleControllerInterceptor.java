package com.sky.cm.config.local;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LocaleControllerInterceptor extends HandlerInterceptorAdapter{
	public static final String DEFAULT_PARAM_NAME = "locale";
	private String paramName = DEFAULT_PARAM_NAME;
	private LocaleResolver localeResolver;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		String newLocale = request.getParameter(getParamName());
		if (newLocale != null) {
			try {
				localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
			}catch (IllegalArgumentException ex) {
			}
		}else {
			localeResolver.setLocale(request, response, null);
		}
		return true;
	}

	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}
	
	protected Locale parseLocaleValue(String locale) {
		return  StringUtils.parseLocaleString(locale);
	}
}
