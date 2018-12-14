package com.skd.manager;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;

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
