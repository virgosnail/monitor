package com.skd.client.util;

import com.skd.client.common.Constant;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/22 11:45
 */
@Slf4j
public class HttpUtil {

    /**
     *  创建文本类型的 HttpEntity
     * @param map 参数
     * @return HttpEntity
     */
    public static HttpEntity createStringEntity(Map map) {
        JSONObject object = new JSONObject();
        Set entrySet = map.entrySet();
        for (Iterator iterator = entrySet.iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            object.put(entry.getKey(), entry.getValue());
        }
        StringEntity httpEntity = new StringEntity(object.toString(), ContentType.APPLICATION_JSON);
        return httpEntity;
    }

    /**
     * 创建文件类型的 HttpEntity
     * @param file 文件参数
     * @param map 文本参数
     * @return HttpEntity
     */
    public static HttpEntity createFileEntity(File file, Map<String, String> map) {

        // 创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传
        MultipartEntityBuilder fileEntity = MultipartEntityBuilder.create();
        // 设置编码
        Charset charset = Charset.forName(Constant.CHARSET);
        fileEntity.setCharset(charset);
        Set entrySet = map.entrySet();
        for (Iterator iterator = entrySet.iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            // 追加要发送的文本信息并设置编码格式
            fileEntity.addTextBody(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()), ContentType.APPLICATION_JSON);
        }
        final ContentType contentType = getContentType(file);
        log.info(contentType.getMimeType());
        fileEntity.addPart(Constant.FILE, new FileBody(file,contentType));
        HttpEntity httpEntity = fileEntity.build();
        return httpEntity;
    }

    private static ContentType getContentType(File file){
        ContentType contentType = ContentType.APPLICATION_OCTET_STREAM;
        String fileName = file.getName();
        if (fileName.contains(".")){
            String type = fileName.toLowerCase().split("\\.")[1];
            switch (type) {
                case "xml":
                    contentType = ContentType.APPLICATION_XML;
                    break;
                case "jpg":
                case "jpeg":
                    contentType = ContentType.IMAGE_JPEG;
                    break;
                case "png":
                    contentType = ContentType.IMAGE_PNG;
                    break;
                case "gif":
                    contentType = ContentType.IMAGE_GIF;
                    break;
                default:
                    contentType = ContentType.MULTIPART_FORM_DATA;
                    break;
            }

        }
        return contentType;
    }
    /**
     *  获取URL
     * @param hasFile  是否包含文件
     * @param ip 服务器IP
     * @param port 服务监听端口
     * @return url
     */
    public static String getURL(boolean hasFile, String ip, String port) {
        String url;
        if (hasFile){
            url = "http://" + ip + ":" + port + Constant.FILE_BODY_URL;
        } else {
            url = "http://" + ip + ":" + port + Constant.FILE_INFO_URL;
        }
        return url;
    }
}
