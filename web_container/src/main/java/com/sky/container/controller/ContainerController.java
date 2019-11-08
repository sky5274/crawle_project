package com.sky.container.controller;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.container.bean.ContainerBean;
import com.sky.container.entity.ContainerEntity;
import com.sky.container.service.CloudContainerService;
import com.sky.container.service.CloudDockerService;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

@RestController
@RequestMapping("/container")
public class ContainerController {
	@Resource
	private CloudContainerService containerService;
	@Resource
	private CloudDockerService dockerService;

	@RequestMapping("querypage")
	public Result<Page<ContainerBean>> queryByPage(PageRequest<ContainerEntity> page) throws ResultException {
		return ResultUtil.getOk(containerService.queryContainer(page));
	}

	@RequestMapping("query/{id}")
	public Result<ContainerBean> queryByPage(@PathVariable String id) throws ResultException {
		return ResultUtil.getOk(containerService.queryContainerDocker(id));
	}

	@RequestMapping("create")
	public Result<ContainerBean> createContainer(@RequestBody ContainerEntity c) throws ResultException {
		return ResultUtil.getOk(containerService.addContainer(c));
	}

	@RequestMapping("update")
	public Result<ContainerBean> updateContainer(@RequestBody ContainerEntity c) throws ResultException {
		return ResultUtil.getOk(containerService.updateContainer(c));
	}

	@RequestMapping("start")
	public Result<String> startContainer(ContainerEntity c) throws ResultException {
		return ResultUtil.getOk(containerService.startContainer(c));
	}

	@RequestMapping("stop")
	public Result<String> stopContainer(ContainerEntity c) throws ResultException {
		return ResultUtil.getOk(containerService.stopContainer(c));
	}

	@RequestMapping("read/jar/info")
	public Result<Map<String, String>>  readJarManifest(String path) {
		String jarPath = dockerService.getAbsolutePath(path);
		return ResultUtil.getOk(dockerService.readJarManifest(jarPath));
	}
}
