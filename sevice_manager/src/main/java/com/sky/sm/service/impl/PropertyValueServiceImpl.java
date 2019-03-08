package com.sky.sm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.pub.Page;
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
	private PropertyValueHistoryEntityMapper propertyValueHistoryMapper;
	
	@Override
	public Page<PropertyValueEntity> queryPageOfProperty(PropertyValueReqEntity property) {
		property.initPage();
		Page<PropertyValueEntity> page= new Page<PropertyValueEntity>();
		page.setPageData(property.getPage());
		page.setList(propertyValueMapper.queryPropertyList(property));
		page.setTotal(propertyValueMapper.accoutProperty(property));
		return page;
	}

	@Override
	public Boolean addProperty(PropertyValueEntity property) throws ResultException {
		return propertyValueMapper.insertSelective(property)>0;
	}

	@Override
	public Boolean updateProperty(PropertyValueEntity property) throws ResultException {
		PropertyValueEntity nowProperty = queryById(property.getId());
		if(nowProperty ==null) {
			throw new ResultException(ResultCode.VALID, "根据id:"+property.getId()+" 未找到配置数据");
		}
		PropertyValueHistoryEntity propertyHistory=(PropertyValueHistoryEntity) nowProperty;
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
	public String getPropertyValue(PropertyValueEntity property) {
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
