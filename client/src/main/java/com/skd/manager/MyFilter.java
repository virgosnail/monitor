package com.skd.manager;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;

/**
 * @Description  过滤器是否接受此文件，在此过滤器里面可以筛选要处理的文件格式
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class MyFilter implements IOFileFilter
{

	@Override
	public boolean accept(File arg0)
	{
		return true;
	}

	@Override
	public boolean accept(File arg0, String arg1)
	{
		return true;
	}

}
