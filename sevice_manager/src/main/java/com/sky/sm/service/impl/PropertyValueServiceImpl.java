package com.sky.sm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.ResultAssert;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.ProjectPropertyBean;
import com.sky.sm.bean.PropertyValueEntity;
import com.sky.sm.bean.PropertyValueHistoryEntity;
import com.sky.sm.bean.req.PropertyValueReqEntity;
import com.sky.sm.dao.PropertyValueEntityMapper;
import com.sky.sm.dao.PropertyValueHistoryEntityMapper;
import com.sky.sm.service.PropertyValueService;


@Service
public class PropertyValueServiceImpl implements PropertyValueService{
	@Autowired
	private PropertyValueEntityMapper propertyValueMapper;
	@Autowired
	private PropertyValueHistoryEntityMapper propertyValueHistoryMapper;
	
	@Override
	public Page<PropertyValueEntity> queryPageOfProperty(PageRequest<PropertyValueReqEntity> pageProperty) {
		pageProperty.initPage();
		Page<PropertyValueEntity> page= new Page<PropertyValueEntity>();
		page.setPageData(pageProperty);
		page.setList(propertyValueMapper.queryPropertyList(pageProperty,pageProperty.getData()));
		page.setTotal(propertyValueMapper.accoutProperty(pageProperty.getData()));
		return page;
	}

	@Override
	public Boolean addProperty(PropertyValueEntity property) throws ResultException {
		ResultAssert.isBlank(property.getKey(), "属性的key为空");
		ResultAssert.isBlank(property.getValue(), "属性的value为空");
		ResultAssert.isBlank(property.getProject(), "属性所属项目为空");
		return propertyValueMapper.insertSelective(property)>0;
	}

	@Override
	public Boolean updateProperty(PropertyValueEntity property) throws ResultException {
		PropertyValueEntity nowProperty = queryById(property.getId());
		if(nowProperty ==null) {
			throw new ResultException(ResultCode.VALID, "根据id:"+property.getId()+" 未找到配置数据");
		}
		PropertyValueHistoryEntity propertyHistory=new PropertyValueHistoryEntity();
		BeanUtils.copyProperties(nowProperty, propertyHistory);
		propertyHistory.setPid(nowProperty.getId());
		propertyHistory.setId(null);
		propertyValueHistoryMapper.insert(propertyHistory);
		if(propertyValueMapper.updateByPrimaryKeySelective(property)<=0) {
			throw new ResultException(ResultCode.FAILED,"更新配置数据",false);
		}
		return true;
	}

	@Override
	public PropertyValueEntity queryById(Integer id) {
		return propertyValueMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<PropertyValueHistoryEntity> queryByPId(Integer pid) {
		return propertyValueHistoryMapper.queryByPid(pid);
	}

	@Override
	public String getPropertyValue(PropertyValueReqEntity property) {
		List<PropertyValueEntity> list = propertyValueMapper.queryProperties(property);
		if(list !=null && list.size()==1) {
			return list.get(0).getValue();
		}
		return null;
	}
	
	@Override
	public List<ProjectPropertyBean> getProperties(PropertyValueEntity property) {
		return propertyValueMapper.queryProjectProperties(property);
	}
}
