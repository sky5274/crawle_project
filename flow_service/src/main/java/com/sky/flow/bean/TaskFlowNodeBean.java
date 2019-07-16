package com.sky.flow.bean;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class TaskFlowNodeBean  extends BaseTableBean{
    /***/
	private static final long serialVersionUID = 6106419444955721364L;
	/**任务节点id*/
	private String id;
	/**任务节点名称*/
	private String name;
	/**任务流程id*/
    private String taskId;
    /**流程id*/
    private String flowId;
    /**流程名称*/
    private String flowName;
    /**流程节点id*/
    private String nodeId;
    /**流程节点key*/
    private String nodeKey;
    /**流程节点名称*/
    private String nodeName;
    /**任务节点内容*/
    private String content;
    /**任务节点描述*/
    private String desc;
    /**任务节点参数*/
    private JSONObject param;
    /**指定（限定）人员*/
    private String limitUser;
    
	public void putParams(String key, Object value) {
		if(this.param==null) {
			this.param=new JSONObject();
		}
		param.put(key, value);
	}
}