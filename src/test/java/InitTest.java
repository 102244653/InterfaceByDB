import TestReport.InitExcelReport;
import TestReport.InitHtmlReport;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class InitTest {

    @BeforeSuite
    public void beforetest(){
        InitExcelReport.InitExcel();
    }


    @AfterSuite
    public void aftertest(){
        new InitHtmlReport().CreatHtmlReport();
    }
}
