package com.sky.data.core.handel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.data.contant.BatchDataContant;
import com.sky.data.handel.BatchDataCollectHandel;
import com.sky.rpc.annotation.RpcProvider;
import com.sky.rpc.call.RpcConnectCall;
import com.sky.rpc.call.RpcConnectCallFactory;
import com.sky.rpc.zk.RpcClientManager;

/**
 * 数据收集保存器
 * @author 王帆
 * @date  2019年7月26日 上午11:06:02
 */
@RpcProvider
public class BatchDataCollectSaveOperate implements BatchDataCollectHandel{

	@Override
	public String save(String taskId,List<JSONObject> array) {
		return save(taskId, 0,0,array);
	}
	
	/**
	 * 
	 * @param taskId
	 * @param status  文件状态（0:正常，1：读取中，2：过滤数据，3：异常数据）
	 * @param times    执行此次
	 * @param array
	 * @return
	 * @author 王帆
	 * @date 2019年7月26日 上午10:36:35
	 */
	public String save(String taskId,int status, int times,List<JSONObject> array) {
		String filePrefix = BatchDataContant.getFilePrefix();
		RpcConnectCall nowcall = RpcConnectCallFactory.getNowConnectCall();
		String addr=null;
		if(nowcall!=null) {
			addr = nowcall.getSocketAddress().toString();
		}else {
			addr=RpcClientManager.getIp();
		}
		
		File file = new File(filePrefix+"/"+taskId+"/"+addr+"_"+status+"_"+new Date().getTime()+"_"+times+".json");
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileWriter print=null;
		try {
			file.createNewFile();
			print=new FileWriter(file);
			print.write(JSON.toJSONString(array));
			print.flush();
		} catch (IOException e) {
		}finally {
			if(print!=null) {
				try {
					print.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file.getAbsolutePath();
	}
	
	public void deleteFile(String taskId) {
		File f=new File(BatchDataContant.getFilePrefix()+"/"+taskId);
		if(f.exists()) {
			f.deleteOnExit();
		}
	}
	public void deleteData(String taskId,String fileName) {
		File f=new File(BatchDataContant.getFilePrefix()+"/"+taskId+"/"+fileName);
		if(f.exists()) {
			f.deleteOnExit();
		}
	}

}
