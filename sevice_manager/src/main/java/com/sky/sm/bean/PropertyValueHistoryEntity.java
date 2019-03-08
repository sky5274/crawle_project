package com.sky.sm.bean;

/**
 * 	配置属性历史数据
 * @author 王帆
 * @date  2019年3月7日 下午5:02:49
 */
public class PropertyValueHistoryEntity extends  PropertyValueEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}