package com.sky.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import com.sky.cm.config.ConfigValueRegistStart;


/**
 * config-limit-trace-value config enable
 * @author 王帆
 * @date  2019年11月9日 下午9:18:26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(ConfigValueRegistStart.class)
public @interface EnableLimitValueConfig {
}
