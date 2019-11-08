package com.sky.container.valid;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * jcommander 正则校验
 * @author 王帆
 * @date  2019年10月24日 下午4:52:57
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface Regex {

  String value() default "";

}
