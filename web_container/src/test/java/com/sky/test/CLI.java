package com.sky.test;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.JCommander;
import com.sky.test.cli.JCommanderExample;

import junit.framework.Assert;

/**
 * 
 * @author Sayi
 * @version
 */
@SuppressWarnings("deprecation")
public class CLI {


	public static void main(String[] args) {
		JCommanderExample jct = new JCommanderExample();
		String[] argv = { "-log", "2", "-groups", "unit" };
		JCommander jc = new JCommander(jct, argv);
		System.err.println(JSON.toJSONString(jct));
		Assert.assertEquals(jct.getVerbose().intValue(), 2);
		jc.usage();
		JCommander.getConsole().print(JSON.toJSONString(argv));
	}

}