package com.crawl.config.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.crawl.config.service.ConfigTableService;
import com.crawl.data.config.dao.ConfigTableColumnEntityMapper;
import com.crawl.data.config.dao.ConfigTableEntityMapper;
import com.crawl.data.config.dao.entity.ConfigTableColumnEntity;
import com.crawl.data.config.dao.entity.ConfigTableEntity;
import com.sky.pub.Page;
import com.sky.pub.ResultAssert;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;

/**
 * 表及其属性配置
 * @author 王帆
 * @date  2018年12月25日 上午9:21:53
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class ConfigTableServiceImpl implements ConfigTableService{
	@Resource
	private ConfigTableEntityMapper configTableMapper;
	@Resource
	private ConfigTableColumnEntityMapper configTableColumnMapper;
	
	@Override
	public List<ConfigTableEntity> getConfigTableList(ConfigTableEntity table) {
		return configTableMapper.queryTableList(table);
	}

	@Override
	public Page<ConfigTableEntity> getConfigTablePage(ConfigTableEntity table) {
		int total=configTableMapper.accountData(table);
		return new Page<>(configTableMapper.queryTableByPage(table),total);
	}

	@Override
	public ConfigTableEntity getConfigTableById(Integer id) {
		return configTableMapper.queryTableByID(id);
	}

	@Override
	public List<ConfigTableColumnEntity> getConfigTableColumnList(ConfigTableColumnEntity column) {
		return configTableColumnMapper.queryListByTable(column.getTableId());
	}

	@Override
	public ConfigTableEntity addConfigTable(ConfigTableEntity table) throws ResultException {
		ResultAssert.isEmpty(table, "表数据不允许为空");
		ResultAssert.isBlank(table.getTableCode(), "表编码不允许为空");
		int size=configTableMapper.insertSelective(table);
		List<ConfigTableColumnEntity> list = table.getColumns();
		if(list!=null && !list.isEmpty()) {
			for(ConfigTableColumnEntity col:list) {
				col.setCreateid(table.getCreateid());
				col.setTableId(table.getId());
			}
			ResultAssert.isFalse( configTableColumnMapper.insetBatch(list)<=0,ResultCode.DATA_OPT_FAIL,"表属性添加失败");
		}
		return size>0? table:null;
	}

	@Override
	public ConfigTableEntity updateConfigTable(ConfigTableEntity table) throws ResultException {
		ResultAssert.isEmpty(table, "表数据不允许为空");
		ResultAssert.isBlank(table.getTableCode(), "表编码不允许为空");
		
		int size=configTableMapper.updateByPrimaryKeySelective(table);
		List<ConfigTableColumnEntity> list = table.getColumns();
		if(list!=null && list.isEmpty()) {
			ResultAssert.isFalse(configTableColumnMapper.delByTableId(table.getId())<=0,ResultCode.DATA_OPT_FAIL,"表属性删除失败");
			for(ConfigTableColumnEntity col:list) {
				col.setCreateid(table.getCreateid());
				col.setTableId(table.getId());
			}
			ResultAssert.isFalse( configTableColumnMapper.insetBatch(list)<=0,ResultCode.DATA_OPT_FAIL,"表属性更新失败");
		}
		return size>0? table:null;
	}

	@Override
	public Boolean delConfigTable(ConfigTableEntity table) throws ResultException {
		ResultAssert.isEmpty(table, "表数据不允许为空");
		ResultAssert.isEmpty(table.getId(), "请提交表对应id");
		int size=configTableColumnMapper.delByTableId(table.getId());
		ResultAssert.isFalse(size<=0,ResultCode.DATA_OPT_FAIL,"表属性删除失败");
		
		return configTableMapper.deleteByPrimaryKey(table)>0;
	}

	@Override
	public String getConfigTableCreateSql(ConfigTableEntity table) throws ResultException{
		if(table==null) {
			throw new ResultException(ResultCode.FAILED,"表数据不允许为空");
		}
		if(table.getId()==null) {
			throw new ResultException(ResultCode.VALID,"请提交表对应id");
		}
		ConfigTableEntity tempTable = configTableMapper.queryTableByID(table.getId());
		if(tempTable==null || table.getId()==null) {
			throw new ResultException(ResultCode.VALID,"根据id="+table.getId()+" 未找到对应的表数据");
		}
		return getTableCreateConfigSqlString(tempTable);
	}
	
	@Override
	public String getTableCreateConfigSqlString(ConfigTableEntity tempTable) throws ResultException {
		StringBuilder str=new StringBuilder();
		if(tempTable.getColumns()!=null && !tempTable.getColumns().isEmpty()) {
			str.append("CREATE TABLE").append(tabCart).append(keyCart).append(tempTable.getTableCode()).append(keyCart).append("(");
			ConfigTableColumnEntity privateKey=null;
			for(ConfigTableColumnEntity col:tempTable.getColumns()) {
				str.append(getColumnConfigString(col));
				if(col.getIsPrimary()==1) {
					if(privateKey==null) {
						privateKey=col;
					}else {
						throw new ResultException(ResultCode.VALID,"键:"+col.getAttr()+"与键："+privateKey.getAttr()+" 不允许同时为主键");
					}
				}
			}
			if(privateKey!=null) {
				str.append("PRIMARY KEY (").append(keyCart).append(privateKey.getAttr()).append(keyCart).append(")");
			}
			str.append(")").append("COMMENT=\"").append(tempTable.getRemark()).append("\";");
		}
		return str.toString();
	}
	
	private String tabCart=" ";
	private String keyCart="`";
	
	/**
	 * 	表定义字段
	 * @param col
	 * @return
	 * @author 王帆
	 * @date 2019年1月7日 下午2:17:21
	 */
	private String getColumnConfigString(ConfigTableColumnEntity col) {
		StringBuilder str=new StringBuilder();
		str.append(keyCart).append(col.getAttr()).append(keyCart).append(tabCart).append(col.getType()).append(tabCart).append(col.getIsNull()==1 ? "NOT NULL":"");
		if(!StringUtils.isEmpty(col.getRemark())) {
			str.append("DEFAULT").append(tabCart).append(col.getRemark());
		}
		str.append(",");
		return str.toString();
	}
}
