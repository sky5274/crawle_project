package com.sky.flow.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import com.alibaba.fastjson.JSONObject;
import com.sky.flow.pub.TaskContants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程任务
 * @author 王帆
 * @date  2019年5月16日 下午2:45:47
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TaskFlowBean extends BaseTableBean{
    /***/
	private static final long serialVersionUID = -954941930545843458L;
	/**流程任务id*/
	private String id;
	/**流程任务名称*/
    private String name;
    
    /**流程任务状态：-1;异常;0,开启;1,进行中;2:回滚;3:结束*/
    private Integer status;
    private Integer statusName;
    /**记录信息*/
    private String message;
    /**流程id*/
    private String flowId;
    /**流程名称*/
    private String flowName;
    /**任务描述*/
    private String desc;
    /**任务参数*/
    private JSONObject params;
    
    /**任务节点信息*/
    private List<TaskFlowNodeBean> details;
    
    /**是否在任务中*/
    private Boolean inTask;
    /**是否在当前任务环节中*/
    private Boolean inNowTaskNode;
    /**操作任务编码*/
    private String opretorCode;
    
    public void putParam( TaskFlowNodeBean node) {
    	if(this.params==null) {
    		this.params=new JSONObject();
    	}
    	initParam();
    	this.putParam(node.getParam());
		this.putParam("node_user_code", node.getCreateCode());
		this.putParam("node_user_name", node.getCreateName());
		this.putParam("node_id", node.getId());
		this.putParam("node_name", node.getName());
		this.putParam("node_node_key", node.getNodeKey());
		this.putParam("node_node_name", node.getNodeName());
    }
    public void putParam( String key,Object value) {
    	if(this.params==null) {
    		this.params=new JSONObject();
    	}
    	this.params.put(key, value);
    }
    public void putParam( JSONObject params) {
    	if(this.params==null) {
    		this.params=params;
    	}else {
    		if(params!=null) {
    			Set<String> keys = params.keySet();
        		for(String key:keys) {
        			this.params.put(key, params.get(key));
        		}
    		}
    	}
    }
    
    public void initParam() {
    	if(this.params==null) {
    		this.params=new JSONObject();
    	}
    	Set<String> keys = this.params.keySet();
    	List<String> list=new LinkedList<>();
    	list.addAll(keys);
//    	System.err.println(JSON.toJSONString(its));
//    	while(its.hasNext()) {
//    		String key=its.next();
//    		if(key.startsWith(TaskContants.TASK_PREFIX)) {
//    			this.params.remove(key);
//    		}
//    	}
    	for(String key:list) {
    		if(key.startsWith(TaskContants.TASK_PREFIX)) {
			this.params.remove(key);
		}
    	}
    }
}