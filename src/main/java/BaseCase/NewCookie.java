package BaseCase;

import CaseData.TestUrl;
import HttpUtils.Config;
import HttpUtils.HttpClientInit;
import Utils.AnalyticJson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;


public class NewCookie {

    public static String Cookie="";

    public NewCookie(String paramsJson){
        HttpPost httpPost = new HttpPost(TestUrl.TestUrl);// 创建httpPost
        // 设置json参数
        StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
        stringEntity.setContentType(Config.CONTENT_TYPE_JSON_URL);
        httpPost.setEntity(stringEntity);
        String response= new HttpClientInit().sendPost(httpPost);
        HashMap mapresult= (HashMap<String, String>) new AnalyticJson().AnylistJson(response);
        Cookie="access_token="+(String) mapresult.get("access_token");
    }

    public static String SetCookie(String URL,String paramsJson){
        String cookie=null;
        try{
            HttpPost httpPost = new HttpPost(URL);// 创建httpPost
            //设置头信息
            //httpPost.addHeader("Authorization", "");
            // 设置json参数
            StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
            stringEntity.setContentType(Config.CONTENT_TYPE_JSON_URL);
            httpPost.setEntity(stringEntity);
            String response= new HttpClientInit().sendPost(httpPost);
            HashMap mapresult= (HashMap<String, String>) new AnalyticJson().AnylistJson(response);
            cookie="access_token="+(String) mapresult.get("access_token");
        }catch (Exception e){
            e.printStackTrace();
        }
        return cookie;
    }

}

