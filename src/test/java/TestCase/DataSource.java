package TestCase;

import Utils.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import CaseData.casesuit;
import java.io.IOException;

public class DataSource {
    private static Logger logger = LoggerFactory.getLogger(DataSource.class);

    //数据驱动从数据库读取测试套件
    @DataProvider
    public static Object[][] TestData(){
        Object[][] suitcase;
        try {
            //需要测试的接口数量
            int qty= DatabaseUtil.getSqlSession().selectOne("Tsuit");
            //接口总数量
            int suitqty= DatabaseUtil.getSqlSession().selectOne("allsuit");
            if(qty==0 || String.valueOf(qty)==null){
                logger.error("未查找到需要测试的接口用例套件，请检查casesuit表设置！");
                return null;
            }
            suitcase=new Object[qty][2];
            for(int i=1,k=0;i<=suitqty;i++){
                casesuit suit=DatabaseUtil.getSqlSession().selectOne("casesuit",i);
                if(suit.getEffictive().equals("F")){
                    logger.error("casesuit表第"+i+"行 "+suit.getApiname()+" 用例本次设置为不执行，将自动跳过！");
                    continue;
                }
                if(!suit.getApiname().isEmpty() && !suit.getCaseqty().isEmpty() ) {
                    suitcase[k][0] = suit.caseqty;
                    suitcase[k][1] = suit.apiname;
                    k++;
                }else {
                    logger.error("casesuit表第"+i+"行 "+suit.getApiname()+" | "+suit.getCaseqty()+" 数据异常，请检查！");
                }
            }
        } catch (IOException e) {
            logger.error("读取测试套件表casesuit异常，请检查数据库连接或重新执行！！！");
            return null;
        }
        return suitcase;
    }
}
