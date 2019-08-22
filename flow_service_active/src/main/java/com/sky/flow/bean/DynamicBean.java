package com.sky.flow.bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

/**
 * 动态添加对象属性
 * @author 王帆
 * @date  2019年6月10日 下午4:50:31
 */
public class DynamicBean {
    private Object object = null; //动态生成的类
    private BeanMap beanMap = null; //存放属性名称以及属性的类型
 
    public DynamicBean() {
        super();
    }
 
    @SuppressWarnings("rawtypes")
	public DynamicBean(Map propertyMap) {
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
        initTarget(propertyMap);
    }
    

	/**
     * @param propertyMap
     * @return
     */
    @SuppressWarnings("rawtypes")
	private Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set keySet = propertyMap.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            generator.addProperty(key,  propertyMap.get(key).getClass());
            
        }
        return generator.create();
    }
    
    @SuppressWarnings("rawtypes")
    private void initTarget(Map propertyMap) {
    	 Set keySet = propertyMap.keySet();
         for (Iterator i = keySet.iterator(); i.hasNext(); ) {
             String key = (String) i.next();
             setValue(key, propertyMap.get(key));
         }
	}
 
    /**
     * 给bean属性赋值
     *
     * @param property 属性名
     * @param value    值
     */
    public void setValue(Object property, Object value) {
        beanMap.put(property, value);
    }
 
    /**
     * 通过属性名得到属性值
     *
     * @param property 属性名
     * @return 值
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }
 
    /**
     * 得到该实体bean对象
     *
     * @return
     */
    public Object getObject() {
        return this.object;

    }

}
