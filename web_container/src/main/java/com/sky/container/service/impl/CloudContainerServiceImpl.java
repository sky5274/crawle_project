package com.sky.container.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.container.bean.ContainerBean;
import com.sky.container.dao.ContainerEntityMapper;
import com.sky.container.entity.ContainerEntity;
import com.sky.container.service.CloudContainerService;
import com.sky.container.service.CloudDockerService;
import com.sky.container.util.CommandUtil;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.ResultAssert;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.controller.FileController;

/**
 * cloud container web event service impl
 * @author 王帆
 * @date  2019年10月28日 下午3:31:28
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class CloudContainerServiceImpl implements CloudContainerService{
	@Resource
	private ContainerEntityMapper containerMapper;
	@Resource
	private CloudDockerService dockerService;
	protected static String defTagVersion="last";
	private String prefix=FileController.getFilePrefix().substring(1);
	private static Log log=LogFactory.getLog(CloudContainerServiceImpl.class);

	@Override
	public ContainerBean addContainer(ContainerEntity c) throws ResultException {
		//校验文件路径
		ResultAssert.isBlank(c.getJarPath(), "jar 路径缺失");
		String absPath = dockerService.getAbsolutePath(c.getJarPath());
		//根据文件路径，使用docker 生成镜像
		c=createImage(c,absPath);
		//启动镜像
		if(StringUtils.isEmpty(c.getContainerName()))  c.setContainerName(c.getImageName());
		
		String containerId = runStartContainer(c);
		//根据镜像与容器创建容器记录
		c.setContainerId(containerId);
		c.setStatus(1);
		if(c.getId() ==null) {
			containerMapper.insertSelective(c);
		}else {
			containerMapper.updateByPrimaryKeySelective(c);
		}
		return queryContainerDocker(c.getContainerId());
	}
	
	/**
	 *	创建docker容器 
	 * @param c
	 * @param path
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年10月29日 下午2:51:57
	 */
	private ContainerEntity createImage(ContainerEntity c,String path) throws ResultException {
		String dockerfile = dockerService.getDockerFilePath();
		ResultAssert.isBlank(dockerfile, "容器初始化环境失败");
		log.info("create image from jar:"+path);
		Map<String, String> jarAttr = dockerService.readJarManifest(path);
		ResultAssert.isEmpty(jarAttr, "创建的容器文件不是jar文件");
		if(dockerfile.startsWith("/"))		dockerfile=dockerfile.substring(1);
		if(path.startsWith("/"))			path=path.substring(1);
		if(StringUtils.isEmpty(c.getImageName()))   	c.setImageName(jarAttr.get("Implementation-Title").trim());
		if(StringUtils.isEmpty(c.getImageVersion()))   	c.setImageVersion(jarAttr.get("Implementation-Version").trim());;
		//构建docker 容器镜像
		String imageprocessMsg = CommandUtil.exec(String.format("docker build -f %s --build-arg JAR_FILE=%s --tag %s:%s %s",dockerfile, path.replace(prefix, "."),c.getImageName(),c.getImageVersion(),prefix));
		String imageId=null;
		if(imageprocessMsg.contains("Successfully built")) {
			imageId=imageprocessMsg.substring(imageprocessMsg.indexOf("Successfully built")+18,imageprocessMsg.lastIndexOf("Successfully")).replace("\n", "").trim();
		}else {
			ResultAssert.isTure(true, imageprocessMsg);
		}
		c.setImageId(imageId);
		return c;
		
	}

	@Override
	public ContainerBean updateContainer(ContainerEntity c) throws ResultException {
		//清除容器与镜像
		if(!StringUtils.isEmpty(c.getContainerId())) {
			CommandUtil.exec(String.format("docker stop %s", c.getContainerId()));
			CommandUtil.exec(String.format("docker rm %s", c.getContainerId()));
			c.setContainerId(null);
		}
		if(!StringUtils.isEmpty(c.getImageId())) {
			CommandUtil.exec(String.format("docker rmi %s", c.getImageId()));
			c.setImageId(null);
		}
		//创建新的容器
		return addContainer(c);
	}
	
	public String runStartContainer(ContainerEntity c) {
		if(StringUtils.isEmpty(c.getContainerId())) {
			return CommandUtil.exec(String.format("docker run -d %s --name %s %s:%s", StringUtils.isEmpty(c.getRunPort())?"-P":"-p "+c.getRunPort(), c.getContainerName(),c.getImageName(),c.getImageVersion()));
		}else {
			return CommandUtil.exec(String.format("docker restart %s", c.getContainerId()));
		}
	}
	
	@Override
	public String startContainer(ContainerEntity c) throws ResultException {
		String containerId=runStartContainer(c);
		if(c.getId() !=null) {
			ContainerEntity cp=new ContainerEntity();
			cp.setId(c.getId());
			cp.setContainerId(containerId);
			cp.setStatus(1);
			int size = containerMapper.updateByPrimaryKeySelective(cp);
			ResultAssert.isTure(size==0, "容器状态更新失败");
		}
		return containerId;
	}

	@Override
	public String stopContainer(ContainerEntity c) throws ResultException {
		ResultAssert.isBlank(c.getContainerId(), "容器id未提交");
		String containerId = CommandUtil.exec(String.format("docker stop %s", c.getContainerId()));
		ResultAssert.isTure(!c.getContainerId().equals(containerId), "容器停止失败");
		if(c.getId() !=null) {
			ContainerEntity cp=new ContainerEntity();
			cp.setId(c.getId());
			cp.setContainerId(containerId);
			cp.setStatus(0);
			int size=containerMapper.updateByPrimaryKeySelective(cp);
			ResultAssert.isTure(size==0, "容器状态更新失败");
		}
		return containerId;
	}

	@Override
	public Page<ContainerBean> queryContainer(PageRequest<ContainerEntity> page) {
		Page<ContainerBean> pageData=new Page<>();
		page.initPage();
		pageData.setTotal(containerMapper.count(page.getData()));
		pageData.setList(containerMapper.queryPage(page.getData(),page));
		pageData.setPageData(page);
		return pageData;
	}

	@Override
	public ContainerBean queryContainerDocker(String cid) {
		ContainerBean cont=containerMapper.queryByContainerId(cid);
		if(cont !=null) {
			String info = CommandUtil.exec(String.format("docker inspect %s", cid));
			if(!StringUtils.isEmpty(info)) {
				cont.setContainer(JSON.parseArray(info).getJSONObject(0));
			}
		}
		return cont;
	}

}
