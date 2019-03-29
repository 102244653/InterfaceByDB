package TestCase;

import BaseCase.NewCookie;
import CaseData.TestUrl;
import TestReport.InitExcelReport;
import TestReport.InitHtmlReport;
import TestReport.SendEmail;
import Utils.ConfigFile;
import org.testng.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitTest {

    private static Logger logger = LoggerFactory.getLogger(InitTest.class);

    //读取测试地址
    @BeforeSuite
    public void readURL() throws Exception {
        TestUrl.gettesturl("test");
    }

    //初始化报告文件
    @BeforeTest
    public void beforeTest(){

    }

    //生成html报告文件
    @AfterTest
    public void afterTest(){
        try {
            new InitHtmlReport().CreatHtmlReport();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //标题
    protected static String title="【自动化测试报告】---请查收！";
    //文本内容
    protected static String content="   报告详情请查看附件(使用浏览器打开)";
    //报告路径
    protected static String path=System.getProperty("user.dir")+"\\TestReport\\report.xls";

    // @AfterSuite
    public void aftersuit(){
        new SendEmail().SendReport(title,content,path);
    }
}
