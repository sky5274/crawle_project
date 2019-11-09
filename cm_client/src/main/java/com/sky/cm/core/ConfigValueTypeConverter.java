package com.sky.cm.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.TypeConverter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;

import com.alibaba.fastjson.JSON;
import com.sky.cm.annotation.Val;
import com.sky.pub.util.SpringUtil;

/**
 * config-value  filed remote converter
 * @author 王帆
 * @date  2019年11月9日 下午9:16:16
 */
public class ConfigValueTypeConverter implements TypeConverter{

	@Override
	public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
		return null;
	}

	@Override
	public <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam)throws TypeMismatchException {
		return null;
	}

	/**
	 * 根据属性返回config-value远程值
	 */
	@Override
	public <T> T convertIfNecessary(Object value, Class<T> requiredType, Field field) throws TypeMismatchException {
		Val anno = field.getAnnotation(Val.class);
		if(anno!=null) {
			return getValue(anno.value(),requiredType);
		}
		return null;
	}

	private <T> T getValue(String val, Class<T> requiredType) {
		if(val !=null) {
			if(val.startsWith("${") && val.endsWith("}")) {
				val=val.substring(2, val.length()-1);
				String[] vals = val.split(":");
				SkyConfig config = SpringUtil.getBean(SkyConfig.class);
				if(config!=null) {
					if(vals.length==1) {
						return config.getProperty(val, requiredType);
					}else {
						return config.getProperty(vals[0], requiredType,getObjValue(vals[1],requiredType));
					}
				}
			}else {
				return getObjValue(val,requiredType);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> T getObjValue(String val,Class<T> requiredType) {
		Method[] ms = requiredType.getMethods();
		for(Method m:ms) {
			if(m.getName().equals("valueOf")) {
				try {
					return (T)m.invoke(null, val);
				} catch (Exception e) {
				}
			}
		}
		return JSON.parseObject(val,requiredType);
	}
}
