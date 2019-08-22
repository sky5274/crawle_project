package com.sky.flow;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.sky.flow.util.SpringExpressionUtil;

public class test {
	public static void main(String[] agrs) {
		Map<String, Object> map=new HashMap<>();
		map.put("flag", "N");
		Object VALUE = SpringExpressionUtil.parse("#{flag}=='N'",map);
		System.err.println(VALUE);
		
		Class<test> clazz = test.class;
		for(Method m:clazz.getDeclaredMethods()) {
			System.err.println(m.getName());
			System.err.println(Modifier.isStatic(m.getModifiers()));
			System.err.println(JSON.toJSONString(m));
		}
		
	}
}
