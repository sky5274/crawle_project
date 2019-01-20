package com.crawl.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.druid.util.StringUtils;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

/**
 * 	通用文件服务
 * @author 王帆
 * @date  2019年1月20日 下午3:45:50
 */
@RestController
@RequestMapping("file")
public class FileController {
	private String prefix="/file";
	public static Map<String, String> medieType=new HashMap<>();
	private Random random=new Random(1000);
	static {
		medieType.put("js", "application/javascript;charset=UTF-8");
		medieType.put("json", MediaType.APPLICATION_JSON_UTF8_VALUE);
		medieType.put("pdf", MediaType.APPLICATION_PDF_VALUE);
		medieType.put("xml", MediaType.APPLICATION_XML_VALUE);
		medieType.put("gif", MediaType.IMAGE_GIF_VALUE);
		medieType.put("jpeg", MediaType.IMAGE_JPEG_VALUE);
		medieType.put("png", MediaType.IMAGE_PNG_VALUE);
		medieType.put("txt", MediaType.TEXT_PLAIN_VALUE);
		medieType.put("html", MediaType.TEXT_HTML_VALUE);
	}
	
	
	@RequestMapping("/**")
	public ResponseEntity<byte[]> downLownFile(HttpServletRequest req) throws IOException, ResultException {
		String path=req.getRequestURI().replaceAll("/file", "");
		String type=getFileType(path);
		String contentType = medieType.get(type);
		if(StringUtils.isEmpty(contentType)) {
			throw new ResultException(ResultCode.PARAM_VALID,"未找到对应文件类型");
		}
		HttpHeaders headers = new HttpHeaders();    
		headers.set(HttpHeaders.CONTENT_TYPE,contentType); 
//		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.set("Accept-Ranges", "bytes"); 
		BufferedReader   bufinp=new BufferedReader(new FileReader(getClass().getResource(prefix+path).getFile()));
		StringBuilder str=new StringBuilder();
		char[] temp=new char[1];
		while(bufinp.read(temp)>-1) {
			str.append(temp);
		}
		return new ResponseEntity<byte[]>(str.toString().getBytes() , headers, HttpStatus.CREATED) ; 
	}
	
	private String getFileType(String path) {
		return path.substring(path.lastIndexOf(".")+1, path.length());
	}
	
	@RequestMapping("create")
	public Result<String> replaceFile(String content ,String type,String name,HttpServletRequest req){
		try {
			String path=req.getParameter("path");
			if(StringUtils.isEmpty(path)) {
				path = getActiveFilePath(name+"."+type);
			}
			File file = new File( getFilePrefix()+path);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileWriter writer=new FileWriter(file);
			writer.write(content);
			writer.flush();
			writer.close();
			return ResultUtil.getOk(ResultCode.OK, path);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return ResultUtil.getFailed(ResultCode.FAILED, "文件替换失败");
		}
	}
	
	@RequestMapping("replace")
	public Result<String> replaceFile(MultipartFile file,String path){
		try {
			file.transferTo(new File(getFilePrefix()+path));
			return ResultUtil.getOk(ResultCode.OK, path);
		} catch (IllegalStateException | IOException e) {
			return ResultUtil.getFailed(ResultCode.FAILED, "文件替换失败");
		}
	}
	
	@RequestMapping("upload")
	public Result<List<String>> upLoadFiles(MultipartFile[] file){
		List<String> paths=new LinkedList<>();
		for(MultipartFile f:file) {
			try {
				paths.add(upLoadFile(f));
			} catch (IllegalStateException | IOException e) {
				paths.add("error");
			}
		}
		return ResultUtil.getOk(ResultCode.OK, paths);
	}
	
	private String upLoadFile(MultipartFile file) throws IllegalStateException, IOException {
		String newPath = getActiveFilePath(file.getOriginalFilename());
		file.transferTo(new File( getFilePrefix()+newPath));
		return newPath;
	}
	
	
	private String getFilePrefix() {
		return getClass().getResource("/").getFile()+prefix;
	}
	private String getActiveFilePath(String fileName) {
		String type=getFileType(fileName);
		String random_str=random.nextInt()+"";
		return "/"+type+"/"+new SimpleDateFormat("yyyy/MM").format(new Date())+"/"+random_str.replace("-", "")+"-"+fileName;
	}
	
}
