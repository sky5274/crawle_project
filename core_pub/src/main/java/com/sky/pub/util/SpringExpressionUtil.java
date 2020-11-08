package com.sky.pub.util;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * spring expression util
 * @author wangfan
 * @date 2020年10月28日 下午7:09:42
 */
public class SpringExpressionUtil {
    static ExpressionParser ep= new SpelExpressionParser();
    static ParserContext parseContext=new ParserContext() {

        @Override
        public boolean isTemplate() {
            return true;
        }

        @Override
        public String getExpressionSuffix() {
            return "}";
        }

        @Override
        public String getExpressionPrefix() {
            return "#{";
        }
    };
    static EvaluationContext ctx =null;

    private static String initExpression(String expression) {
        return expression.replaceAll("\\#\\{|\\}", "").trim();
    }
    
    /**
     * 获取初始模板
     * @param expression
     * @return
     * @author wangfan
     * @date 2020年6月7日 下午9:04:15
     */
    private static Expression getExpression(String expression) {
        boolean isTemp=expression.contains(parseContext.getExpressionPrefix());
        if(!isTemp) {
            expression=initExpression(expression);
        }
        return ep.parseExpression(expression,isTemp?parseContext:null);
    }
    
    /**
     * 将value 数据通过模板输入模板中，在获取表达式
     * @param expression
     * @param value
     * @return
     * @author wangfan
     * @date 2020年6月7日 下午9:05:18
     */
    private static Expression getExpression(String expression,Object value) {
        boolean isTemp=expression.contains(parseContext.getExpressionPrefix());
        if(isTemp) {
            expression=parse(expression, value).toString();
        }
        expression=initExpression(expression);
        return ep.parseExpression(expression);
    }

    /**
     * 将value 赋值到表达式中形成模板
     * @param expression
     * @param obj
     * @return
     * @author wangfan
     * @date 2020年6月7日 下午9:06:13
     */
    public static Object formatData(String expression,Object obj){
        if(obj instanceof Map ) {
            obj=new DynamicBean((Map<?, ?>)obj).getObject();
        }
        return ep.parseExpression(expression,parseContext).getValue(obj);
    }
    public static <T> T formatData(String expression,Object obj,Class<T> type){
        Object data=null;
        if(obj instanceof Map) {
            data=new DynamicBean((Map<?, ?>)obj).getObject();
        }else{
            data=obj;
        }
        return ep.parseExpression(expression,parseContext).getValue(data,type);
    }

    /**paserContext 如果是模板时，expression 结果将不参与最终的计算结果，直接赋值到公式中 */
    public static Object parse(String expression) {
        return getExpression(expression).getValue();
    }
    /**
     * 将表达式进行计算，并获取表达式的最终计算结果
     * @param <T>
     * @param expression
     * @param clazz
     * @return
     * @author wangfan
     * @date 2020年6月7日 下午9:07:08
     */
    public static <T> T parse(String expression,Class<T> clazz) {
        return getExpression(expression).getValue(clazz);
    }
    public static Object parse(String expression,Object value) {
        return parse(expression,value,String.class);
    }
    public static <T> T parse(String expression,Object value,Class<T> clazz) {
    	 if(value instanceof Map ) {
            value=new DynamicBean((Map<?, ?>)value).getObject();
        }
        ctx=new StandardEvaluationContext(value);
        return getExpression(expression,value).getValue(ctx,clazz);
    }
    public static Boolean match(String expression,Object value) {
        return parse(expression, value, Boolean.class);
    }
}
/**
 * 动态添加对象属性
 * @author 王帆
 * @date  2019年6月10日 下午4:50:31
 */
class DynamicBean {
    /**动态生成的类*/
    private Object object = null;
    /**存放属性名称以及属性的类型*/
    private BeanMap beanMap = null;

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
