package com.sky.sm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
	@Autowired
	private  RedisTemplate<String,String> redisTemplate; 

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
		if(!CollectionUtils.isEmpty(list)) {
			boolean flag=property.getLocal()==null;
			for(PropertyValueEntity pv:list) {
				try {
					if((flag?pv.getLocal()==null || Locale.getDefault().toString().equals(pv.getLocal()):property.getLocal().equals(pv.getLocal()))
							&& pv.getValue() !=null && pv.getKey().equals(property.getKey())) {
						return pv.getValue();
					}
				} catch (Exception e) {
				}
			}
			return list.get(0).getValue();
		}
		return null;
	}

	@Override
	public List<ProjectPropertyBean> getProperties(PropertyValueEntity property) {
		return propertyValueMapper.queryProjectProperties(property);
	}

	@Override
	public Boolean updateProperty(PropertyValueEntity property, Properties pro) throws ResultException {
		ResultAssert.isTure(pro==null || pro.isEmpty(), "配置资源为空");
		List<String> errors=new LinkedList<String>();
		if(property==null) {
			errors.add("配置属性参数为空");
		}else {
			if(StringUtils.isEmpty(property.getProfile()))     		errors.add("项目");
			if(StringUtils.isEmpty(property.getProfile()))     		errors.add("运行环境");
			if(StringUtils.isEmpty(property.getVersionCode()))     	errors.add("运行版本");
		}
		//复制properties 数据，添加或更新到数据库中
		List<PropertyValueEntity> list=new LinkedList<>();
		for(Entry<Object, Object> e:pro.entrySet()) {
			if(e!=null && e.getKey() !=null && e.getValue() !=null) {
				PropertyValueEntity pv=new PropertyValueEntity();
				BeanUtils.copyProperties(property, pv);
				pv.setId(null);
				pv.setVersion(null);
				pv.setKey(e.getKey().toString());
				pv.setValue(e.getValue().toString());
				list.add(pv);
			}
		}
		return propertyValueMapper.insertBatch(list)>0;
	}

	private String getPropertyReleaseKey(PropertyValueEntity propertyValue) {
		return String.format("%s#%s@%s", propertyValue.getProject(),propertyValue.getProfile(),propertyValue.getVersionCode());
	}
	private String getPropertyReleaseValue(PropertyValueEntity propertyValue) {
		return String.format("%s#%s@%s-%s", propertyValue.getProject(),propertyValue.getProfile(),propertyValue.getVersionCode(),propertyValue.getVersion());
	}
	
	@Override
	public String getPropertyRelease(PropertyValueReqEntity propertyValue) {
		String key = getPropertyReleaseKey(propertyValue);
		String value = redisTemplate.opsForValue().get(key);
		if(StringUtils.isEmpty(value)) {
			PropertyValueEntity pv = propertyValueMapper.queryPropertyVersion(propertyValue);
			if(pv !=null) {
				value=getPropertyReleaseValue(pv);
				redisTemplate.opsForValue().set(key, value);
			}
		}
		return value;
	}

	@Override
	public String updatePropertyRelease(PropertyValueEntity propertyValue) {
		String value = getPropertyReleaseValue(propertyValue);
		redisTemplate.opsForValue().set(getPropertyReleaseKey(propertyValue), value);
		return value;
	}
}
