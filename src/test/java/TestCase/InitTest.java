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
        InitExcelReport.InitExcel();
    }


    @AfterTest
    public void aftertest(){
        new InitHtmlReport().CreatHtmlReport();
    }
}
