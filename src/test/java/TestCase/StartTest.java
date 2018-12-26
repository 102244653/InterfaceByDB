package TestCase;

import BaseCase.BaseCase;
import com.aventstack.extentreports.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

public class StartTest extends InitTest{

    private static Logger logger = LoggerFactory.getLogger(StartTest.class);

    @Test(dataProvider="TestData",dataProviderClass= DataSource.class)
    public  void StartTest(String caseqty ,String casename){
        if(caseqty.isEmpty()  ||  casename.isEmpty()){
            logger.error("接口名称: "+casename+","+"用例数量: "+caseqty+" -----请检查！");
            return;
        }
        BaseCase baseCase=new BaseCase();
        try {
            baseCase.executecase(caseqty,casename);
        } catch (Exception e) {
            logger.error("\n本次测试执行过程中出现异常，具体原因如下：\n\n"+ ExceptionUtil.getStackTrace(e));
        }
    }
}
