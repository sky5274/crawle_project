package com.sky.cm.config;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sky.cm.config.handle.AnnotationPropertyConverterUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import com.sky.cm.config.handle.AnnotationPropertyConvertHandle;
import com.sky.cm.config.handle.impl.ConfigSkyValPropertyConvertHandleImpl;

/**
 * config value annotation auto inspect process
 * @author 王帆
 * @date  2019年11月9日 下午8:49:47
 */
@Component
public class SkyConfigValueAnnotationBeanProcessor extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware {
	static{
		AnnotationPropertyConverterUtil.addPropertyConverter(new ConfigSkyValPropertyConvertHandleImpl());
	}

	protected final Log logger = LogFactory.getLog(getClass());
	private String requiredParameterName = "required";
	private boolean requiredParameterValue = true;
	private int order = Ordered.LOWEST_PRECEDENCE-10;
	private ConfigurableListableBeanFactory beanFactory;
	private final Map<String, InjectionMetadata> injectionMetadataCache =new ConcurrentHashMap<String, InjectionMetadata>(256);
	private List<AnnotationPropertyConvertHandle> propertyConvertHandles= AnnotationPropertyConverterUtil.getPropertyConverterHandles();

	public SkyConfigValueAnnotationBeanProcessor() {
	}



	/**
	 * Set the name of a parameter of the annotation that specifies
	 * whether it is required.
	 * @see #setRequiredParameterValue(boolean)
	 */
	public void setRequiredParameterName(String requiredParameterName) {
		this.requiredParameterName = requiredParameterName;
	}

	/**
	 * Set the boolean value that marks a dependency as required
	 * <p>For example if using 'required=true' (the default),
	 * this value should be {@code true}; but if using
	 * 'optional=false', this value should be {@code false}.
	 * @see #setRequiredParameterName(String)
	 */
	public void setRequiredParameterValue(boolean requiredParameterValue) {
		this.requiredParameterValue = requiredParameterValue;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
			throw new IllegalArgumentException("AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
		}
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}


	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		if (beanType != null) {
			InjectionMetadata metadata = findAutowiringMetadata(beanName, beanType, null);
			metadata.checkConfigMembers(beanDefinition);
		}
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeanCreationException {
		InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass(), pvs);
		try {
			metadata.inject(bean, beanName, pvs);
		}catch (BeanCreationException ex) {
			throw ex;
		}catch (Throwable ex) {
			throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
		}
		return pvs;
	}

	/**
	 * 'Native' processing method for direct calls with an arbitrary target instance,
	 * resolving all of its fields and methods which are annotated with {@code @Autowired}.
	 * @param bean the target instance to process
	 * @throws BeanCreationException if autowiring failed
	 */
	public void processInjection(Object bean) throws BeanCreationException {
		Class<?> clazz = bean.getClass();
		InjectionMetadata metadata = findAutowiringMetadata(clazz.getName(), clazz, null);
		try {
			metadata.inject(bean, null, null);
		}catch (BeanCreationException ex) {
			throw ex;
		}catch (Throwable ex) {
			throw new BeanCreationException("Injection of autowired dependencies failed for class [" + clazz + "]", ex);
		}
	}


	private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
		// Fall back to class name as cache key, for backwards compatibility with custom callers.
		String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
		// Quick check on the concurrent map first, with minimal locking.
		InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
		if (InjectionMetadata.needsRefresh(metadata, clazz)) {
			synchronized (this.injectionMetadataCache) {
				metadata = this.injectionMetadataCache.get(cacheKey);
				if (InjectionMetadata.needsRefresh(metadata, clazz)) {
					if (metadata != null) {
						metadata.clear(pvs);
					}
					try {
						metadata = buildAutowiringMetadata(clazz);
						this.injectionMetadataCache.put(cacheKey, metadata);
					}catch (NoClassDefFoundError err) {
						throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() +
								"] for autowiring metadata: could not find class that it depends on", err);
					}
				}
			}
		}
		return metadata;
	}

	private InjectionMetadata buildAutowiringMetadata(final Class<?> clazz) {
		LinkedList<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();
		Class<?> targetClass = clazz;
		do {
			final LinkedList<InjectionMetadata.InjectedElement> currElements =new LinkedList<InjectionMetadata.InjectedElement>();

			ReflectionUtils.doWithLocalFields(targetClass, new ReflectionUtils.FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					AnnotationAttributes ann = findAutowiredAnnotation(field);
					if (ann != null) {
						if (Modifier.isStatic(field.getModifiers())) {
							if (logger.isWarnEnabled()) {
								logger.warn("Autowired annotation is not supported on static fields: " + field);
							}
							return;
						}
						boolean required = determineRequiredStatus(ann);
						currElements.add(new ConfigValueFieldElement(field, required,findTypeConverter(ann)));
					}
				}
			});

			elements.addAll(0, currElements);
			targetClass = targetClass.getSuperclass();
		}while (targetClass != null && targetClass != Object.class);

		return new InjectionMetadata(clazz, elements);
	}

	private AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao) {
		if (ao.getAnnotations().length > 0) {
			for(AnnotationPropertyConvertHandle pch:propertyConvertHandles) {
				AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, pch.getAnnotation());
				if (attributes != null) {
					return attributes;
				}
			}
		}
		return null;
	}
	private TypeConverter findTypeConverter(AnnotationAttributes ann) {
		if (ann !=null && propertyConvertHandles !=null) {
			for(AnnotationPropertyConvertHandle pch:propertyConvertHandles) {
				if(pch.getAnnotation().getClass().getName().equals(ann.annotationType().getClass().getName())) {
					return pch.getTypeConverter();
				}
			}
		}
		return null;
	}

	/**
	 * Determine if the annotated field or method requires its dependency.
	 * <p>A 'required' dependency means that autowiring should fail when no beans
	 * are found. Otherwise, the autowiring process will simply bypass the field
	 * or method when no beans are found.
	 * @param ann the Autowired annotation
	 * @return whether the annotation indicates that a dependency is required
	 */
	protected boolean determineRequiredStatus(AnnotationAttributes ann) {
		return (!ann.containsKey(this.requiredParameterName) ||
				this.requiredParameterValue == ann.getBoolean(this.requiredParameterName));
	}

	/**
	 * Obtain all beans of the given type as autowire candidates.
	 * @param type the type of the bean
	 * @return the target beans, or an empty Collection if no bean of this type is found
	 * @throws BeansException if bean retrieval failed
	 */
	protected <T> Map<String, T> findAutowireCandidates(Class<T> type) throws BeansException {
		if (this.beanFactory == null) {
			throw new IllegalStateException("No BeanFactory configured - " +
					"override the getBeanOfType method or specify the 'beanFactory' property");
		}
		return BeanFactoryUtils.beansOfTypeIncludingAncestors(this.beanFactory, type);
	}

	/**
	 * Class representing injection information about an annotated field.
	 */
	private class ConfigValueFieldElement extends InjectionMetadata.InjectedElement {
		private TypeConverter typeConverter;

		public ConfigValueFieldElement(Field field, boolean required, TypeConverter typeConverter) {
			super(field, null);
			this.typeConverter=typeConverter;
		}

		@Override
		protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
			Field field = (Field) this.member;
			if(typeConverter !=null) {
				Object value=typeConverter.convertIfNecessary(bean, field.getType(),field);
				if (value != null) {
					ReflectionUtils.makeAccessible(field);
					field.set(bean, value);
				}
			}
		}
	}

}

