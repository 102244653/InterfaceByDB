package CaseData;

import Utils.ConfigFile;
import Utils.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUrl {
    public int id;
    public String type;
    public String url;
    public static String TestUrl;

    private static Logger logger = LoggerFactory.getLogger(TestUrl.class);

    //测试环境地址管理
    public static void gettesturl(String type) throws Exception {
        String url = DatabaseUtil.getSqlSession().selectOne("testadress",type);
         TestUrl=url;
    }

}
