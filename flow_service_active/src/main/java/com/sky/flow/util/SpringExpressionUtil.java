package com.sky.flow.util;

import java.util.Map;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import com.sky.flow.bean.DynamicBean;

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
			return "${";
		}
	};
	static EvaluationContext ctx =null;
	
	private static String initExpression(String expression) {
		return expression.replaceAll("\\#\\{|\\}", "").trim();
	}
	
	private static Expression getExpression(String expression) {
		boolean isTemp=expression.contains(parseContext.getExpressionPrefix());
		if(!isTemp) {
			expression=initExpression(expression);
		}
		return ep.parseExpression(expression,isTemp?parseContext:null);
	}
	private static Expression getExpression(String expression,Object value) {
		boolean isTemp=expression.contains(parseContext.getExpressionPrefix());
		if(isTemp) {
			expression=parse(expression, value).toString();
		}
		expression=initExpression(expression);
		return ep.parseExpression(expression);
	}
	
	/**paserContext 如果是模板时，expression 结果将不参与最终的技术结果 */
	public static Object parse(String expression) {
		return getExpression(expression).getValue();
	}
	public static <T> T parse(String expression,Class<T> clazz) {
		return getExpression(expression).getValue(clazz);
	}
	public static Object parse(String expression,Object value) {
		ctx=new StandardEvaluationContext(value);
		return getExpression(expression).getValue(ctx,String.class);
	}
	public static <T> T parse(String expression,Object value,Class<T> clazz) {
		ctx=new StandardEvaluationContext(value);
		return getExpression(expression,value).getValue(ctx,clazz);
	}
	public static Object parse(String expression,Map<String, Object> value) {
		return parse(expression, new DynamicBean(value).getObject());
	}
	public static <T> T parse(String expression,Map<String, Object> value,Class<T> clazz) {
		return parse(expression, new DynamicBean(value).getObject(),clazz);
	}
	
	public static Boolean match(String expression,Object value) {
		return parse(expression, value, Boolean.class);
	}
	public static Boolean match(String expression,Map<String, Object> value) {
		return parse(expression, value, Boolean.class);
	}
	
}
