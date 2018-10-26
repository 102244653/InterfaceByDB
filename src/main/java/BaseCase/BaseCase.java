package BaseCase;

import CaseData.TestUrl;
import CaseData.postcode;
import HttpUtils.HttpUtil;
import TestReport.InitExcelReport;
import TestReport.ResultData;
import Utils.CheckResult;
import Utils.DatabaseUtil;
import com.aventstack.extentreports.utils.ExceptionUtil;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Data;

@Data
public class BaseCase {

    private static Logger logger = LoggerFactory.getLogger(BaseCase.class);
    public  String Excepct;
    public  String[] ResultExcel;
    public  String request;
    public  String type;
    public  String isdo;

    public void executecase(String caseqty,String casename) throws Exception {
       String URL= TestUrl.gettesturl();
        //读取用例总数
        int qty=DatabaseUtil.getSqlSession().selectOne(caseqty);
        for(int i=1;i<=qty;i++) {
            this.TestAPI(CaseUtil.getcasedata(casename,i),casename,i,URL);
            }
        }

    public void TestAPI(BaseCase basecase,String casename,int i,String URL){
        String response="";
        try {
            logger.info(casename+"用例["+i+"] : "+basecase.ResultExcel[1]+" | "+basecase.ResultExcel[2]+" | "+basecase.ResultExcel[3]+" | "+basecase.ResultExcel[4]+" | "+basecase.ResultExcel[5]);
            //判断用例是否执行
            if(basecase.isdo.equals("F")){ 
                logger.info("当前用例不执行\n"); 
                return;
            }
            //判断请求类型
            if(basecase.type.equals("get")){
                response=HttpUtil.GetURL(URL+basecase.request);
            }else {
                HttpPost httppost=new HttpPost("http://localhost:8899/postdemo");
            }
            basecase.ResultExcel[6]=response;
            if(CheckResult.checkresult(response,basecase.Excepct)){
                //测试结果
                basecase.ResultExcel[7]="PASS";
                ResultData.PassCase.add(basecase.ResultExcel[2]);
            }else {
                //测试结果
                basecase.ResultExcel[7]="FAIL";
                ResultData.FailCase.add(basecase.ResultExcel[2]);
            }
        }catch (Exception e){
            basecase.ResultExcel[6]= ExceptionUtil.getStackTrace(e);
            //测试结果
            basecase.ResultExcel[7]="SKIP";
            ResultData.SkipCase.add(basecase.ResultExcel[2]);
        }
        InitExcelReport.InsertData(basecase.ResultExcel);//插入excel报告结果
        ResultData.AllCase.add(basecase.ResultExcel);//插入html报告结果
    }

}
