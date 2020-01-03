package com.sky.cm.config.handle;

import java.lang.annotation.Annotation;
import org.springframework.beans.TypeConverter;

/**
 * 根据 注解进行spring 属性配置
 * @author 王帆
 * @date  2020年1月1日 下午8:39:53
 */
public interface AnnotationPropertyConvertHandle {
	
	/**
	 * 获取value annotation 
	 * @return
	 * @author 王帆
	 * @date 2020年1月1日 下午8:39:22
	 */
	public Class<? extends Annotation> getAnnotation();
	
	/**
	 * 属性配置注解 类型转换
	 * @return
	 * @author 王帆
	 * @date 2020年1月1日 下午8:40:21
	 */
	public TypeConverter getTypeConverter();
}
