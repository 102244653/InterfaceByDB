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



    //过滤二维数组空元素,未使用
    public static Object[][] deletenull(Object[][] objects){
        int firstDimesion = objects.length;//二维数组初始长度
        for(int i=0; i<objects.length; i++){
            if(objects[i].length == 1 && objects[i][0] == null)//过滤掉一维为空的元素后的长度
                firstDimesion --;
        }
        result = new Object[firstDimesion][];//返回的一维数组均为非空
        firstDimesion = 0;//一维初始化从0开始
        for(int i=0; i<objects.length; i++) {
            if (objects[i].length == 1 && objects[i][0] == null ) {//为空则直接跳过
                continue;
            }
            temp = new Object[]{objects[i][0], objects[i][1]};//存储非null元素
            result[firstDimesion] = new Object[2];//第一个值含两个元素
            for (int k = 0; k < 2; k++){
                result[firstDimesion][k] = temp[k];//给两个元素赋值
            }
            if(i!=objects.length){
                firstDimesion ++; //长度增一，初始化下一个数组
            }
        }
        return result;
    }
    private static Object[] temp; //temp存储非null元素
    private static Object[][] result;//最终结果
}