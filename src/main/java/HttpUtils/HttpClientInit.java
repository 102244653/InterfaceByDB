package HttpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 实例化HttpClient
 * */

public class HttpClientInit {

    private static Logger logger = LoggerFactory.getLogger(HttpClientInit.class);
    /**
     * Post请求
     */
    public  String sendPost(HttpPost httpPost) {
        CloseableHttpClient httpClient;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpSetting.getHttpClient();
            // 配置请求信息
            httpPost.setConfig(HttpSetting.requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            int Statuscode=response.getStatusLine().getStatusCode();
            logger.info("状态码："+String.valueOf(Statuscode));
            if ( Statuscode>= 300) {
                logger.error("接口请求失败:"+Statuscode);
                throw new Exception("HTTP Request is not success, Response code is " + Statuscode);
            }
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, Config.CHARSET_UTF_8);
                logger.info(responseContent);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return responseContent;
    }


    /**
     * 发送Get请求
     * @return
     */
    public String sendGet(HttpGet httpGet) {
        CloseableHttpClient httpClient;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpSetting.getHttpClient();
            // 配置请求信息
            httpGet.setConfig(HttpSetting.requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            //System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            int statuscode=response.getStatusLine().getStatusCode();
            if (statuscode >= 300) {
                logger.error("响应异常，状态码："+statuscode);
                throw new Exception("HTTP Request is not success, Response code is " + statuscode);
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, Config.CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    logger.info("请求完成，释放资源...");
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return responseContent;
    }

}