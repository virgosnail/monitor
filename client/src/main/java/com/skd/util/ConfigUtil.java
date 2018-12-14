package com.skd.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil
{
	private static ConfigUtil common;
	private static Properties pp;
	
	public static ConfigUtil getInstance(){
		if (null == common)
		{
			common = new ConfigUtil();
		}
		return common;
	}
	
	public String getValue(String key){
		return pp.getProperty(key);
	}
	
	private ConfigUtil(){
		init();
	}

	private void init()
	{
		try
		{
			pp = new Properties();
			pp.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config/conf.properties"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}


}
