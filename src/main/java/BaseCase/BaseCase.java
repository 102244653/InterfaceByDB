package BaseCase;

import CaseData.postcode;
import HttpUtils.HttpUtil;
import TestReport.InitExcelReport;
import TestReport.ResultData;
import Utils.ConfigFile;
import Utils.DatabaseUtil;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class BaseCase {

    private static Logger logger = LoggerFactory.getLogger(BaseCase.class);
    public String Excepct;
    public String[] ResultExcel;

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
                        type=casevalue.getRequestMethod().trim().toLowerCase();//读取请求类型
                        Excepct=casevalue.getExpectResult().trim();//读取预期结果
                        isdo=casevalue.getEffective().trim();//是否执行用例
                        ResultExcel=casevalue.Result();//Excel报告结果
                        break;
                    default:
                       return;
                }
                logger.info(casename+"["+i+"] : "+ResultExcel[1]+" | "+ResultExcel[2]+" | "+ResultExcel[3]+" | "+ResultExcel[4]+" | "+ResultExcel[5]);
                //判断用例是否执行
                if(isdo.equals("F")){ continue; }
                //判断请求类型
                if(type.equals("get")){
                    response=HttpUtil.GetURL(TestUrl+request);
                }else {
                    HttpPost httppost=new HttpPost("http://localhost:8899/postdemo");
                }
                ResultExcel[6]=response;
                if(CheckResult.checkresult(response,Excepct)){
                    //测试结果
                    ResultExcel[7]="PASS";
                    ResultData.PassCase.add(ResultExcel[2]);
                }else {
                    //测试结果
                    ResultExcel[7]="FAIL";
                    ResultData.FailCase.add(ResultExcel[2]);
                }
            }catch (Exception e){
                ResultExcel[6]= String.valueOf(e.getStackTrace());
                //测试结果
                ResultExcel[7]="SKIP";
                ResultData.SkipCase.add(ResultExcel[2]);
            }
            InitExcelReport.InsertData(ResultExcel);
            ResultData.AllCase.add(ResultExcel);
        }
    }



}
