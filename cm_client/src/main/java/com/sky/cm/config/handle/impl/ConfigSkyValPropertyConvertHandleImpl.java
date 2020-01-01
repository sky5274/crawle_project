package com.sky.cm.config.handle.impl;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.TypeConverter;
import org.springframework.stereotype.Component;
import com.sky.cm.annotation.Val;
import com.sky.cm.config.handle.AnnotationPropertyConvertHandle;
import com.sky.cm.core.ConfigValueTypeConverter;

/**
 * val 注解以及其属性注入转换方法
 * @author 王帆
 * @date  2020年1月1日 下午8:43:59
 */
@Component
public class ConfigSkyValPropertyConvertHandleImpl implements AnnotationPropertyConvertHandle{

	@Override
	public Set<Class<? extends Annotation>> getAnnotations() {
		return new LinkedHashSet<>(Arrays.asList(Val.class));
	}

	@Override
	public TypeConverter getTypeConverter() {
		return new ConfigValueTypeConverter();
	}

}
