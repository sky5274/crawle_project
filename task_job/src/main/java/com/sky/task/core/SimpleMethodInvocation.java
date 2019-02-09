package com.sky.task.core;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * Represents the AOP Alliance <code>MethodInvocation</code>.
 *
 * @author Ben Alex
 */
public class SimpleMethodInvocation implements MethodInvocation {
	// ~ Instance fields
	// ================================================================================================

	private Method method;
	private Object[] arguments;
	private Object targetObject;

	// ~ Constructors
	// ===================================================================================================

	public SimpleMethodInvocation(Object targetObject, Method method, Object... arguments) {
		this.targetObject = targetObject;
		this.method = method;
		this.arguments = arguments == null ? new Object[0] : arguments;
	}

	public SimpleMethodInvocation() {
	}

	// ~ Methods
	// ========================================================================================================

	public Object[] getArguments() {
		return arguments;
	}

	public Method getMethod() {
		return method;
	}

	public AccessibleObject getStaticPart() {
		throw new UnsupportedOperationException("mock method not implemented");
	}

	public Object getThis() {
		return targetObject;
	}

	public Object proceed() throws Throwable {
		throw new UnsupportedOperationException("mock method not implemented");
	}
}
