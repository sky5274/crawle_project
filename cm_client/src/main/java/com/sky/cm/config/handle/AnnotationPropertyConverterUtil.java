package com.sky.cm.config.handle;

import org.springframework.beans.TypeConverter;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author wangfan
 * @Date 2020/1/2 14:12
 */
public class AnnotationPropertyConverterUtil {
	private static List<AnnotationPropertyConvertHandle> propertyConverterHandles=new LinkedList<AnnotationPropertyConvertHandle>();

	/**
	 * 获取 propertyConverter 配置集合
	 * @Author wangfan
	 * @return java.util.List<com.sky.cm.config.handle.AnnotationPropertyConvertHandle>
	 * @Date 2020/1/2 14:14
	 */
	public static List<AnnotationPropertyConvertHandle> getPropertyConverterHandles(){
		return propertyConverterHandles;
	}

	public static void addPropertyConverter(AnnotationPropertyConvertHandle propertyConverterHandle){
		propertyConverterHandles.add(propertyConverterHandle);
	}

	public static void addPropertyConverter(Class<? extends Annotation> anno, TypeConverter typeConverter){
		propertyConverterHandles.add(new AnnotaitionTypeConverter(anno,typeConverter));
	}

	/**
	 *  注解与注解转换方式配置接口
	 * @Author wangfan
	 * @Date 2020/1/2 15:26
	 */
	public static class AnnotaitionTypeConverter implements  AnnotationPropertyConvertHandle{
		private TypeConverter typeConverter;
		private Class<? extends Annotation> annotation;
		public	AnnotaitionTypeConverter(Class<? extends Annotation> annotation,TypeConverter typeConverter){
			this.annotation=annotation;
			this.typeConverter=typeConverter;
		}
		@Override
		public Class<? extends Annotation> getAnnotation() {
			return this.annotation;
		}
		@Override
		public TypeConverter getTypeConverter() {
			return this.typeConverter;
		}
	}

}
