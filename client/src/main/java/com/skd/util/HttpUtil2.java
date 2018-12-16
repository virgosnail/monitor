package com.skd.util;

import com.skd.common.Constant;
import com.skd.common.Event;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class HttpUtil2
{
	private static Logger log = LoggerFactory.getLogger(HttpUtil2.class);
	/**
	 * org.apache.commons.httpclient.HttpClient
 	 */
	private static HttpClient httpClient = new HttpClient();

	/**
	 * @param event  0:删除  1：新增  2：修改
	 * @param file
	 * @return
	
	 * @Author Administrator
	 * @Date 2018年11月11日
	 */
	public static void doPostMethod(Event event, File file)
	{
		try
		{
			PostMethod post = new PostMethod(FileUtil.getURL());
			post.setRequestHeader("Content-Type","application/json");
			List<Part> list = new ArrayList<>();
			list.add(new StringPart(Constant.TYPE, event.getValue()));
			list.add(new StringPart(Constant.RELATIVE_PATH, file.getPath()));
			
			if (!Event.FILE_DELETE.equals(event))
			{
				list.add(new FilePart(Constant.FILE, file));
			}
			Part[] parts = new Part[list.size()];
			list.toArray();
			post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
			
			log.info("request： " + post);
			
			httpClient.executeMethod(post);
			String response = post.getResponseBodyAsString();
			log.info("respopnse: " + response);
		} catch (Exception e)
		{
			log.error("doPostMethod occur a exception", e);
		}
	}

}
