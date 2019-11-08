package com.sky.container.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.controller.FileController;

@Controller
public class PubComController {
	@RequestMapping("/page/{model}/{path}")
	public String goConfigPagePath(@PathVariable("path") String path,@PathVariable("model") String model,HttpServletRequest req,Model mod) {
		Enumeration<String> params = req.getParameterNames();
		while(params.hasMoreElements()) {
			String key=params.nextElement();
			mod.addAttribute(key, req.getParameter(key));
		}
		return model+"/"+path;
	}
	
	@ResponseBody
	@RequestMapping("uploadFile")
	public Result<String> upLoadFiles(MultipartFile file){
			try {
				return ResultUtil.getOk(ResultCode.OK, upLoadFile(file));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return ResultUtil.getFailed(ResultCode.DATA_OPT_FAIL,"文件长传失败");
			}
		
	}
	private String upLoadFile(MultipartFile file) throws IllegalStateException, IOException {
		String newPath = getActiveFilePath(file.getOriginalFilename());
		file.transferTo(FileController.getFile( FileController.getFilePrefix()+newPath));
		return newPath;
	}
	private String getActiveFilePath(String fileName) {
		String type=FileController.getFileType(fileName);
		return "/"+type+"/"+new SimpleDateFormat("yyyy/MM").format(new Date())+"/"+fileName;
	}
}

