package com.sky.demo.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.sky.cm.annotation.Limit;
import com.sky.cm.annotation.Val;
import com.sky.cm.bean.PropertyEnumValueBean;
import com.sky.cm.core.SkyConfig;
import com.sky.demo.data.service.DemoService;
import com.sky.demo.data.service.DemoSqlService;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.util.SpringUtil;
import com.sky.transaction.annotation.MTransaction;

@RestController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PageController {
	@Autowired
	SkyConfig skyconfig;
	@Autowired
	private DemoService demoService;
	@Autowired
	private DemoSqlService sqlServcie;
	@Val("${demo.test.dd:test}")
	private String str;
	
	
	@RequestMapping("/page/{name}")
	public ModelAndView goPage(@PathVariable String name) {
		return new ModelAndView(name);
	}
	
	@Limit(name="sayword",period=20,prefix="test")
	@RequestMapping("/say/{word}")
	public Result<String> say(@PathVariable String word) {
		return ResultUtil.getOk("you say:"+word);
	}
	@RequestMapping("/talk")
	public Result<String> talk(String word) {
		return ResultUtil.getOk("you talk:"+word+" with:"+str);
	}
	@RequestMapping("/property")
	public Result<String> getproperty(String key) {
		return ResultUtil.getOk(skyconfig.getProperty(key));
	}
	@RequestMapping("/property/enmu")
	public Result<String> getpropertyEnum(String groupNo) {
		return ResultUtil.getOk(SpringUtil.getEvnProperty(groupNo));
	}
	@RequestMapping("/property/value")
	public Result<String> getpropertyValue(String key) {
		return ResultUtil.getOk(SpringUtil.getEvnProperty(key));
	}
	@RequestMapping("/property/enum")
	public Result<List<PropertyEnumValueBean>> getpropertyEnmu(String groupNo) {
		return ResultUtil.getOk(skyconfig.getProjectEnums(groupNo));
	}
	@RequestMapping("/property/enum/value")
	public Result<PropertyEnumValueBean> getpropertyEnmu(String groupNo,String enumNo) {
		return ResultUtil.getOk(skyconfig.getProjectEnum(groupNo,enumNo));
	}
	@RequestMapping("/property/enum/bean")
	public Result<EnumGroupTestBean> getpropertyEnmu(EnumGroupTestBean bean) {
		return ResultUtil.getOk(skyconfig.parseProjectEnum(bean));
	}
	@RequestMapping("/writer")
	public Result<Map<String, String[]>> say(HttpServletRequest req) {
		return ResultUtil.getOk(req.getParameterMap());
	}
	
	@RequestMapping("sql")
	@MTransaction(timeout=60*1000)
	public Result<List<Map<String, String>>> testSql(final String table){
//		final FlowSqlMapper mapper=sqlMapper;
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				mapper.queryBySql("select * from "+table);
//			}
//		}).start();;
//		demoService.queryTable(table);
//		return ResultUtil.getOk(mapper.queryBySql("select * from "+table));
		demoService.queryTable(table);
		
		return ResultUtil.getOk(sqlServcie.queryTable(table));
	}
	
	@RequestMapping(value="do",method=RequestMethod.POST)
	public Result<Map<String, String[]>> dosome(HttpServletRequest req) {
		return ResultUtil.getOk(req.getParameterMap());
	}
}
