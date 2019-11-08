package com.sky.container.bean;

import java.util.Map;

import com.sky.container.entity.ContainerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * web 容器bean
 * @author 王帆
 * @date  2019年10月28日 上午9:54:49
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContainerBean extends ContainerEntity{
	/***/
	private static final long serialVersionUID = 200149356377282946L;
	private String runStatsDesc;		//运行状态描述
	private String runportDesc;			//运行端口描述
	private String runCmdsDesc;			//运行命令描述
	
	private Map<String, Object> container;
}
