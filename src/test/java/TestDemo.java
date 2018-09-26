
import BaseCase.BaseCase;
import CaseData.postcode;
import HttpUtils.HttpClientInit;
import Utils.DatabaseUtil;
import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestDemo {

    @Test
    public void testget(){
        HttpGet httpget=new HttpGet("http://localhost:8899/getdemo?name=lyn&age=18");
        httpget.setHeader("Cookie","login=true");
        HttpClientInit client=new HttpClientInit();
        String result = client.sendGet(httpget);
        System.out.println(result);
    }

    @Test
    public void testpost() throws IOException {
//        HttpPost httppost=new HttpPost("http://localhost:8899/postdemo");
//        //int num= ReadCase.count("countpost");
//        System.out.println(num);
//        post post=null;
//        for(int i=1;i<=num;i++){
//            post= DatabaseUtil.getSqlSession().selectOne("postcase",i);
//            System.out.println(post.toString());
//            StringEntity stringEntity = new StringEntity(post.toString(), "UTF-8");
//            stringEntity.setContentType(Config.CONTENT_TYPE_JSON_URL);
//            httppost.setEntity(stringEntity);
//            HttpClientInit client=new HttpClientInit();
//            String result = client.sendPost(httppost);
//
//        }
    }





    @Test
    public void test001() throws IOException {
        postcode casevalue= DatabaseUtil.getSqlSession().selectOne("postcode",1);
        System.out.println(casevalue);
    }

}
