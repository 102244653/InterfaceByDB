package TestCase;

import TestReport.InitExcelReport;
import TestReport.InitHtmlReport;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class InitTest {

    @BeforeTest
    public void beforetest(){
        try {
            InitExcelReport.InitExcel();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @AfterTest
    public void aftertest(){
        try {
            new InitHtmlReport().CreatHtmlReport();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
