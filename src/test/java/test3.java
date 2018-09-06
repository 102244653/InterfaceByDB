import CaseData.MyInfo;
import Utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class test3 {

    @Test
    public void test() throws IOException {
        SqlSession  sql= DatabaseUtil.getSqlSession();
        MyInfo myInfo=sql.selectOne("MyInfo",1);
        System.out.println(myInfo.toString());
        int a=sql.selectOne("count",myInfo);
        System.out.println(a);
        MyInfo AA=new  MyInfo();
        AA.setId(5);
        AA.setName("dsgsg");
        AA.setAge("12");
        AA.setDesc("4683");
        sql.insert("add",AA);
        int b=sql.selectOne("count",myInfo);
        System.out.println(b);
    }
}
