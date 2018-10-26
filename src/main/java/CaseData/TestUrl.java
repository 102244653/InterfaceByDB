package CaseData;

import Utils.ConfigFile;
import Utils.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class TestUrl {
    public static String TestUrl;

    private static Logger logger = LoggerFactory.getLogger(TestUrl.class);

    //测试环境地址管理
    public static String gettesturl()throws Exception{
        String testurl= DatabaseUtil.getSqlSession().selectOne("testurl");
        switch(testurl){
            case "SIT":
                TestUrl=ConfigFile.getUrl("SIT.url");//读取测试地址
                break;
            case "UAT":
                TestUrl=ConfigFile.getUrl("UAT.url");
                break;
            case "PRO":
                TestUrl=ConfigFile.getUrl("PRO.url");
                break;
            default:
                logger.error("测试环境配置异常请检查： "+testurl);
                return null;
        }
        return TestUrl;
    }

}
