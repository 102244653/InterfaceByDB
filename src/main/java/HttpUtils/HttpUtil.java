package HttpUtils;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static final HttpClientInit client=new HttpClientInit();
    private static  Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static HttpClientInit getClient(){
        return client;
    }

    public static String GetURL(String httpUrl) {
        // 创建get请求
        logger.info("开始发送GET请求...");
        HttpGet httpGet = new HttpGet(httpUrl);
        return client.sendGet(httpGet);
    }

    /**
     * post发送文件
     * */
    public static String PostFile(String httpUrl, Map<String, String> maps, List<File> fileLists) {
        logger.info("正在发送POST（上传文件）请求...");
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if (maps != null) {
            for (String key : maps.keySet()) {
                meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
            }
        }
        if (fileLists != null) {
            for (File file : fileLists) {
                FileBody fileBody = new FileBody(file);
                meBuilder.addPart("files", fileBody);
            }
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return client.sendPost(httpPost);
    }

    /**
     * post发送json
     * */
    public static String PostJason(String httpUrl, String paramsJson) {
        logger.info("正在发送POST（Jason）请求...");
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置头信息
            //httpPost.addHeader("Authorization", "");
            // 设置json参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(Config.CONTENT_TYPE_JSON_URL);
                httpPost.setEntity(stringEntity);
            }else {
                logger.error("请求参数为空...");
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return client.sendPost(httpPost);
    }

    /**
     * post发送xml
     * */
    public static String PostXml(String httpUrl, String paramsXml) {
        logger.info("开始发送POST(XML)请求...");
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            // 设置参数
            if (paramsXml != null && paramsXml.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsXml, "UTF-8");
                stringEntity.setContentType(Config.CONTENT_TYPE_TEXT_HTML);
                httpPost.setEntity(stringEntity);
            }else {
                logger.error("请求参数为空...");
            }
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return client.sendPost(httpPost);
    }

    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     * @param parameterMap
     * 需要转化的键值对集合
     * @return 字符串
     */
    public static String mapparameter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value =(String) parameterMap.get(key);
                } else {value = "";}
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

}

