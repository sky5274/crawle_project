package com.sky.demo;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.sky.demo.test.MethodInterFace;
import com.sky.demo.test.RpcConsumerTest;

/**
 * spring  start listener
 * @author 王帆
 * @date  2019年6月7日 下午11:47:09
 */
@Component
@Order(Integer.MAX_VALUE)
public class ApplicationReadListener1  implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	Log log=LogFactory.getLog(getClass());
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(90*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("start bean test");
				String[] methodNames = applicationContext.getBeanNamesForType(MethodInterFace.class);
				for(String name:methodNames) {
					MethodInterFace m = (MethodInterFace)applicationContext.getBean(name);
					m.invoker("bean test");
				}
				
				RpcConsumerTest interf = applicationContext.getBean(RpcConsumerTest.class);
				interf.test();
				MethodInterFace method2 = (MethodInterFace) applicationContext.getBean("rpc_"+MethodInterFace.class.getSimpleName());
				method2.invoker("bean test2");
			}
		}).start();;

	}

}
