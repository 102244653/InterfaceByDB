import HttpUtils.HttpUtil;
import org.testng.annotations.Test;

public class test2 {
    @Test
    public void  test(){
        System.out.println(HttpUtil.GetURL("https://d.tigerwallet.cn"));

    }
}
