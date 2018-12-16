package com.skd.util;

import com.skd.common.Constant;
import com.skd.common.Event;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class HttpUtil
{
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	/**
	 * 	org.apache.http.client.HttpClient;
	 * 	创建一个httpclient对象
 	 */
	private static HttpClient httpclient = HttpClients.createDefault();
	
	/**
	 * @param event  0:删除  1：新增  2：修改
	 * @param file
	 * @return
	
	 * @Author Administrator
	 * @Date 2018年11月11日
	 */
	public static void doPost(Event event, File file, boolean isDir, boolean hasFile)
	{
		try
		{
			// 创建http的发送方式对象，是GET还是POST
            String url = FileUtil.getURL();
            HttpPost httppost = new HttpPost(url);
			log.info(" URL: " + url);
			httppost.addHeader("Charset",Constant.CHARSET);
			httppost.addHeader("Content-Disposition","attachment;filename=UTF-8"+URLEncoder.encode(file.getName(),"UTF-8"));
			HttpEntity httpEntity = createHttpEntity(event, file, isDir, hasFile);
			Header contentType = httpEntity.getContentType();
			Header contentEncoding = httpEntity.getContentEncoding();
			httppost.setEntity(httpEntity);
			// 执行httppost对象并获得response
			HttpResponse response = httpclient.execute(httppost);
			// 状态码
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			// 获得返回来的信息，转化为字符串string
			String respopnse = EntityUtils.toString(resEntity);
			log.info("respopnse body: " + respopnse);
		} catch (Exception e)
		{
			log.error("doPost occur a exception", e);
		}
	}

	/**
	 *
	 * @param event
	 * @param file
	 * @param isDir
	 * @param hasFile
	 * @return
	 */
	public static HttpEntity createHttpEntity(Event event, File file, boolean isDir, boolean hasFile){
		HttpEntity httpEntity = null;
		if (hasFile){
			httpEntity = createFileEntity(event,file,isDir);
		} else {
			httpEntity = createStringEntity(event,file,isDir);
		}
		return httpEntity;
	}

	/**
	 *
	 * @param event
	 * @param file
	 * @param isDir
	 * @return
	 */
	public static HttpEntity createStringEntity(Event event, File file, boolean isDir){
		JSONObject object = new JSONObject();
		object.put(Constant.TYPE, event.getValue());
		String relativePath = FileUtil.getRelativePath(file.getAbsolutePath());
		object.put(Constant.RELATIVE_PATH, relativePath);
		object.put(Constant.ISDIR, String.valueOf(isDir));
		StringEntity httpEntity = new StringEntity(object.toString(),ContentType.APPLICATION_JSON);
		return httpEntity;
	}

	/**
	 * @param event
	 * @param file
	 * @param isDir
	 * @return
	 */
	public static HttpEntity createFileEntity(Event event, File file, boolean isDir){

		// 创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传
		MultipartEntityBuilder fileEntity = MultipartEntityBuilder.create();
		// 设置编码
		Charset charset = Charset.forName(Constant.CHARSET);
		fileEntity.setCharset(charset);
		// 追加要发送的文本信息并设置编码格式
		fileEntity.addTextBody(Constant.TYPE, event.getValue(),ContentType.APPLICATION_JSON);
		log.info("type： " + event.getValue());
		String relativePath = FileUtil.getRelativePath(file.getAbsolutePath());
		fileEntity.addTextBody(Constant.RELATIVE_PATH, relativePath,ContentType.APPLICATION_JSON);
		log.info("relative_path： " + relativePath);
		fileEntity.addTextBody(Constant.ISDIR, String.valueOf(isDir),ContentType.APPLICATION_JSON);
		log.info("isDir： " + isDir);
		if (Event.FILE_CREATE.equals(event) || Event.FILE_CHANGE.equals(event)){
//			fileEntity.addPart(Constant.FILE, new FileBody(file,ContentType.create("multipart/form-data","UTF-8"),file.getName()));
			fileEntity.addPart(Constant.FILE, new FileBody(file,ContentType.APPLICATION_OCTET_STREAM));
		}
		log.info("file： " + file);
		HttpEntity httpEntity = fileEntity.build();
		return httpEntity;
	}

}
