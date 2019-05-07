package com.sky.auth.config.controller;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.service.UserLoginService;
import com.sky.auth.config.util.UserUtil;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;


@RestController
@RequestMapping("auth")
@SessionAttributes("authorizationRequest")
public class UserLoginController {
	@Resource
	private UserLoginService userService;
	@Value("${own.config.client.id:dev}")
	private String clientId;
	@Value("${own.config.client.secret:dev}")
	private String clientSecret;
	@Value("${own.config.redirectUri:/auth/user/info}")
	private String redirectUri;
	
	@RequestMapping(value="/user/info",method=RequestMethod.GET)
	public Result<Principal> getUserInfo(Principal user) {
		return ResultUtil.getOk(ResultCode.OK, user);
	}


	@RequestMapping("/login/regist")
	public ModelAndView  registPage(HttpServletRequest req) {
		return new ModelAndView("regist");
	}
	@RequestMapping("/login/page")
	public ModelAndView  loginPage(HttpServletRequest req) {
		ModelAndView view =new ModelAndView("login");
		Enumeration<String> names = req.getParameterNames();
		Map<String, Object> params=new HashMap<>();
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			params.put(name, req.getParameter(name));
		}
		String tempRedirectUri = req.getParameter("redirect_uri");
		params.put("clientId",clientId);
		params.put("clientSecret",clientSecret);
		if(StringUtils.isEmpty(tempRedirectUri)) {
			params.put("redirect_uri",redirectUri);
			req.getSession().setAttribute("redirect_uri", redirectUri);
		}else {
			req.getSession().setAttribute("redirect_uri", tempRedirectUri);
		}
		view.addObject("paramsData", params);
		return view;
	}
	
	@RequestMapping("authorize")
	public ModelAndView doauthorize(HttpServletRequest req) {
		String viewName = "redirect:";
		ModelAndView view =new ModelAndView();
		
		if(StringUtils.isEmpty(req.getParameter("access_token"))){
			viewName+="/auth/login/page";
			Enumeration<String> names = req.getParameterNames();
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				view.addObject(name, req.getParameter(name));
			}
		}else {
			viewName+="/oauth/authorize";
			boolean flag=true;
			Enumeration<String> names = req.getParameterNames();
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				viewName+=(flag?"?":"&")+name+"="+req.getParameter(name);
				if(flag) {
					flag=false;
				}
			}
		}
		view.setViewName(viewName);
		return view;
	}

	@RequestMapping(value="/login/progress",method=RequestMethod.POST)
	public ModelAndView loginUserProgess(UserEntity user,HttpServletRequest req) throws ResultException{
		 ModelAndView view = new ModelAndView("redirect:/say?word=sdfse");
		UserDetailEntitiy userinfo = userService.login(user);
		//存储信息到session中
		UserUtil.saveUser(userinfo);
		req.getSession().removeAttribute("redirect_uri");
		return view;
	}
//	@RequestMapping(value="/login",method=RequestMethod.POST)
//	public Result<UserDetailEntitiy> loginUser(UserEntity user,HttpServletRequest req) throws ResultException{
//		UserDetailEntitiy userinfo = userService.login(user);
//		//存储信息到session中
//		req.getSession().removeAttribute("redirect_uri");
//		UserUtil.saveUser(userinfo);
//		return  ResultUtil.getOk(ResultCode.OK, userinfo);
//	}

	@RequestMapping("/user/regist")
	public Result<UserEntity> registUser(UserEntity  user) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, userService.regist(user));
	}
}
