package com.sky.sm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.ProjectInfoBean;
import com.sky.sm.bean.ProjectPropertyBean;
import com.sky.sm.bean.PropertyValueEntity;
import com.sky.sm.bean.PropertyValueHistoryEntity;
import com.sky.sm.bean.req.PropertyValueReqEntity;
import com.sky.sm.service.PropertyValueService;

/**
 *   	属性值操作接口
 * @author 王帆
 * @date  2019年3月7日 下午2:18:52
 */
@RestController
@RequestMapping("/property")
public class PropertyValueController {
	
	@Autowired
	private PropertyValueService  propertyValueService;
	
	@RequestMapping(value="page",method=RequestMethod.POST)
	public Result<Page<PropertyValueEntity>> queryPageProperty(@RequestBody PageRequest<PropertyValueReqEntity> pageProperty) {
		return ResultUtil.getOk(propertyValueService.queryPageOfProperty(pageProperty));
	}
	
	/**
	 * 属性配置添加
	 * @param property
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年5月14日 下午1:47:58
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Result<Boolean> addProperty(@RequestBody PropertyValueEntity property) throws ResultException {
		return ResultUtil.getOk(propertyValueService.addProperty(property));
	}
	
	/**
	 * 属性配置--更新
	 * @param property
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年5月14日 下午1:48:14
	 */
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Result<Boolean> updateProperty(@RequestBody PropertyValueEntity property) throws ResultException {
		return ResultUtil.getOk(propertyValueService.updateProperty(property));
	}
	
	private static List<String> limitFileType=Arrays.asList(".properties",".yml",".yaml");
	/**
	 * 	根据上传的属性文件全局替换或新增
	 * @param property
	 * @param file
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年12月8日 下午4:34:45
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public Result<Boolean> uploadProperties(PropertyValueEntity property,MultipartFile file) throws ResultException {
		if(file==null) {
			return ResultUtil.getFailed(ResultCode.VALID, "无上传文件");
		}
		String fileName = file.getOriginalFilename();
		String type=fileName.substring(fileName.lastIndexOf("."));
		if(!limitFileType.contains(type)) {
			return ResultUtil.getFailed(ResultCode.VALID, String.format("服务只支持一下文件格式的属性解析，文件格式：%s", String.join(",", limitFileType)));
		}
		Properties pro=null;
		try {
			pro = parseProperties(fileName, file.getInputStream());
		} catch (IOException e) {
		}
		if(pro==null) {
			return ResultUtil.getFailed(ResultCode.VALID, String.format("文件properties 内容解析失败"));
		}
		return ResultUtil.getOk(propertyValueService.updateProperty(property,pro));
	}
	
	/**
	 * 根据id查询属性配置信息
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年5月14日 下午1:48:32
	 */
	@RequestMapping("query")
	public Result<PropertyValueEntity> queryByID(Integer id) {
		return ResultUtil.getOk(propertyValueService.queryById(id));
	}
	
	/**
	 * 查询属性配置修改历史
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年5月14日 下午1:48:49
	 */
	@RequestMapping("query/his")
	public Result<List<PropertyValueHistoryEntity>> queryByPID(Integer id) {
		return ResultUtil.getOk(propertyValueService.queryByPId(id));
	}
	
	/**
	 *  精准查询全局服务属性配置
	 * @param key
	 * @param project
	 * @return
	 * @author 王帆
	 * @date 2019年5月14日 下午2:10:10
	 */
	@RequestMapping("/get")
	public Result<String>  getProperty(String key,ProjectInfoBean project) {
		return ResultUtil.getOk(propertyValueService.getPropertyValue(getPropertyValue(key,project)));
	}
	
	/**
	 * 全局查询服务各环节-版本  配置属性
	 * @param project
	 * @return
	 * @author 王帆
	 * @date 2019年5月14日 下午2:11:05
	 */
	@RequestMapping("/by/project")
	public Result<List<ProjectPropertyBean>> getProjectProperty(ProjectInfoBean project) {
		return ResultUtil.getOk(propertyValueService.getProperties(getPropertyValue(null, project)));
	}
	
	@RequestMapping("/release/by/project")
	public Result<String> getProjectPropertyRelease(ProjectInfoBean project) {
		return ResultUtil.getOk(propertyValueService.getPropertyRelease(getPropertyValue(null, project)));
	}
	@RequestMapping("/release/update")
	public Result<String> updateProjectPropertyRelease(ProjectInfoBean project) {
		return ResultUtil.getOk(propertyValueService.updatePropertyRelease(getPropertyValue(null, project)));
	}
	
	private PropertyValueReqEntity getPropertyValue(String key,ProjectInfoBean project) {
		PropertyValueReqEntity property=new PropertyValueReqEntity();
		property.setKey(key);
		property.setProject(project.getServiceName());
		property.setProfile(project.getProfile());
		property.setVersionCode(project.getVersion());
		property.setLocal(project.getLocal());
		return property;
	}
	
	/**
	 * 	解析数据流程的属性结果
	 * @param fileName
	 * @param stream
	 * @return
	 * @author 王帆
	 * @date 2019年12月8日 下午4:44:55
	 */
	private Properties parseProperties(String fileName,InputStream stream) {
		Properties pro=null;
		if(fileName.endsWith(".properties")) {
			pro=new Properties();
			try {
				pro.load(stream);
			} catch (IOException e) {
			}
		}else if(fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
			YamlPropertiesFactoryBean yml=new YamlPropertiesFactoryBean();
			yml.setResources(new InputStreamResource(stream));
			pro = yml.getObject();
		}
		return pro;
	}
}
