package com.sky.cm.config.handle.impl;

import java.lang.annotation.Annotation;
import org.springframework.beans.TypeConverter;
import com.sky.cm.annotation.Val;
import com.sky.cm.config.handle.AnnotationPropertyConvertHandle;
import com.sky.cm.core.ConfigValueTypeConverter;

/**
 * val 注解以及其属性注入转换方法
 * @author 王帆
 * @date  2020年1月1日 下午8:43:59
 */
public class ConfigSkyValPropertyConvertHandleImpl implements AnnotationPropertyConvertHandle{

	@Override
	public Class<? extends Annotation> getAnnotation() {
		return Val.class;
	}

	@Override
	public TypeConverter getTypeConverter() {
		return new ConfigValueTypeConverter();
	}

}
