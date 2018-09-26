package BaseCase;

import CaseData.postcode;
import HttpUtils.HttpUtil;
import TestReport.InitExcelReport;
import Utils.ConfigFile;
import Utils.DatabaseUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public class BaseCase {

    public String Excepct;
    public String[] Result;


    public void executecase(String caseqty,String casename)throws IOException{
        String TestUrl= ConfigFile.getUrl("test.url");
        //读取用例总数
        int qty=DatabaseUtil.getSqlSession().selectOne(caseqty);
        for(int i=1;i<=qty;i++){
            String request="";
            String type="";
            String isdo="";
            String response="";
            try {
                switch (casename){
                    case "postcode":
                        //读取用例并返回请求参数
                        postcode casevalue=DatabaseUtil.getSqlSession().selectOne("postcode",i);
                        request= casevalue.toString();//请求参数
                        type=casevalue.getRequestMethod().trim();//读取请求类型
                        Excepct=casevalue.getExpectResult().trim();//读取预期结果
                        isdo=casevalue.getISDO().trim();//是否执行用例
                        Result=casevalue.Result();//Excel报告结果
                        break;
                    default:
                       return;
                }
                //判断用例是否执行
                if(isdo.equals("F")){ return; }
                //判断请求类型
                if(type.equals("get")){
                    response=HttpUtil.GetURL(TestUrl+request);
                }else {
                    HttpPost httppost=new HttpPost("http://localhost:8899/postdemo");
                }
                Result[6]=response;
                if(this.checkresult(response)){
                    Result[7]="PASS";
                }else {
                    Result[7]="FAIL";
                }
            }catch (Exception e){
                Result[7]="SKIP";
            }
            InitExcelReport.InsertData(Result);
        }
    }


    public Boolean checkresult(String response){
        Boolean flag=false;
        int key=Excepct.trim().indexOf("+");
        String except1=Excepct.substring(0,key);
        String except2=Excepct.substring(key+1);
        JSONObject json=  JSONObject.parseObject(response);
        String success = json.getString("success");
        String adress="";
        if(except1.equals(success)){
            flag=true;
        }
//        if (!except2.isEmpty()){
//            JSONArray array = json.getJSONArray("result");
//            JSONObject obj = json.getJSONObject("lists");
//            String simcall =obj.getString("simcall");
//            if(except2.equals(adress)&&flag==true){
//                flag=true;
//            }
//        }
        return flag;
    }
}
