package com.sky.data.core.handel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.mchange.io.FileUtils;
import com.sky.data.contant.BatchDataContant;
import com.sky.data.core.bean.DataBatchBean;
import com.sky.rpc.annotation.RpcProvider;

@RpcProvider
public class DataBatchCollectQueryHandel {
	
	public List<DataBatchBean> queryByBatch(String id) {
		File taskFile = new File(BatchDataContant.getFilePrefix()+"/"+id);
		if(taskFile.exists()) {
			if(taskFile.isDirectory()) {
				File[] datafiles = taskFile.listFiles();
				List<DataBatchBean> list=new LinkedList<>();
				for( File f:datafiles) {
					if(f.isFile() && f.getName().endsWith(".json") && f.getName().contains("_0_")) {
						String[] fnd = f.getName().split("_");
						DataBatchBean dbb=new DataBatchBean();
						dbb.setFileName(f.getName());
						dbb.setTaskId(id);
						dbb.setIp(fnd[0]);
						String nend = fnd[fnd.length-1];
						//执行次数
						dbb.setTimes(Integer.valueOf(nend.substring(0,nend.lastIndexOf("."))));
						try {
							dbb.setDatas(JSON.parseArray(FileUtils.getContentsAsString(f)));
						} catch (IOException e) {
							e.printStackTrace();
						}
						list.add(dbb);
						//f.renameTo(new File(f.getAbsolutePath().replaceAll("_0_", "_1_")));
						//查询完删除文件
						f.deleteOnExit();
					}
				}
				return list;
			}
		}
		return null;
	}
}
