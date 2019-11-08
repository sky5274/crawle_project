package com.sky.container.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import com.sky.container.convert.ReadStreamConvert;
import com.sky.container.service.CloudDockerService;
import com.sky.container.util.CommandUtil;
import com.sky.pub.controller.FileController;

@Service
public class CloudDockerServiceImpl implements CloudDockerService{

	private static String dockerFilePath=null;
	private static boolean isInit=false;
	
	@Override
	public String exec(String cmd, String... args) {
		
		return null;
	}

	@Override
	public String exec(String cmd) {
		
		return null;
	}

	@Override
	public String getDockerFilePath() {
		if(!isInit || dockerFilePath ==null) {
			dockerFilePath=FileController.getFilePrefix()+"/docker/Dockerfile";
			InputStream in = CloudDockerServiceImpl.class.getResourceAsStream("/static/docker/Dockerfile");
			try {
				File docf = FileController.getFile(dockerFilePath);
				//复制文件
				FileCopyUtils.copy(FileCopyUtils.copyToByteArray(in), docf);
				isInit=true;
			} catch (IOException e) {
				dockerFilePath=null;
				isInit=false;
			}
		}
		return dockerFilePath;
	}

	@Override
	public String getAbsolutePath(String path) {
		if(path.startsWith("/file")) {
			path=path.substring(4);
		}
		path=FileController.getFilePrefix()+path;
		File f=new File(path);
		if(!f.exists()) {
			path=null;
		}
		return path;
	}

	@Override
	public void exec(String cmd, OutputStream out) {
		
		
	}

	@Override
	public <T> List<T> exec(String cmd, ReadStreamConvert<T> convert) {
		
		return null;
	}

	@Override
	public Map<String, String> readJarManifest(String path) {
		try {
			@SuppressWarnings("resource")
			JarFile jarfile=new JarFile(path);
			JarEntry mifo = jarfile.getJarEntry("META-INF/MANIFEST.MF");
			InputStream in = jarfile.getInputStream(mifo);
			String outstr = CommandUtil.readStream(in, "UTF-8");
			List<String> attrs = Arrays.asList(outstr.replace("\n", "").split("\r"));
			Map<String,String> attrMap=new HashMap<String, String>();
			String lastAttr=null;
			for(int i=0;i<attrs.size();i++) {
				String attr=attrs.get(i);
				int splitIndex=attr.indexOf(":");
				if(splitIndex>-1) {
					String key =attr.substring(0,splitIndex);
					lastAttr=key;
					attrMap.put(key, attr.substring(splitIndex+1).trim().replace(" ", ""));
				}else {
					attrMap.put(lastAttr, attrMap.get(lastAttr)+attr.trim());
				}
			}
			return attrMap;
		} catch (Exception e) {
		}
		return null;
	}

}
