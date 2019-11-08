package com.sky.container.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContainerEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer groupId;		//分组id

    private String containerId;		//容器id

    private String containerName;	//容器名称

    private String imageId;			//镜像id

    private String imageName;		//镜像名称

    private String imageVersion;	//镜像版本

    private String jarPath;

    private String runPort;

    private String runCmds;

    private Integer status;
   
}