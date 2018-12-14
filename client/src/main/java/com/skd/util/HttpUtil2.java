package com.skd.util;

import com.skd.common.Constant;
import com.skd.common.Event;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;

public class HttpUtil2
{
	private static Logger log = LoggerFactory.getLogger(HttpUtil2.class);
	// org.apache.http.client.HttpClient;
	// 创建一个httpclient对象
	private static HttpClient httpclient = HttpClients.createDefault();
	
	/**
	 * @param event  0:删除  1：新增  2：修改
	 * @param file
	 * @return
	
	 * @Author Administrator
	 * @Date 2018年11月11日
	 */
	public static void doPost(Event event, File file)
	{
		try
		{
			// 创建http的发送方式对象，是GET还是POST
            String url = ServerUtil.getURL();
            HttpPost httppost = new HttpPost(url);
			log.info(" URL: " + url);

			// 创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传
			MultipartEntityBuilder fileEntity = MultipartEntityBuilder.create();

			// 设置编码
			Charset charset = Charset.forName(Constant.CHARSET);
			fileEntity.setCharset(charset);

			// 追加要发送的文本信息和文件信息
			fileEntity.addTextBody(Constant.TYPE, event.getValue());
            String relativePath = ServerUtil.getRelativePath(file.getAbsolutePath());
            fileEntity.addTextBody(Constant.FILEPATH, relativePath);

			// 删除文件时不发送文件对象
			if (!Event.FILE_DELETE.equals(event))
			{
				fileEntity.addPart(Constant.FILE, new FileBody(file));
			}

			httppost.setEntity(fileEntity.build());
			log.info("TYPE： " + event.getValue());
			log.info("FILEPATH： " + relativePath);
			log.info("FILE： " + file);

			// 执行httppost对象，从而获得信息
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity resEntity = response.getEntity();

			// 获得返回来的信息，转化为字符串string
			String respopnse = EntityUtils.toString(resEntity);
			log.info("respopnse: " + respopnse);

		} catch (Exception e)
		{
			log.error("doPost occur a exception", e);
		}
	}


}
