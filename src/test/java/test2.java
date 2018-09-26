import BaseCase.BaseCase;
import HttpUtils.HttpUtil;
import org.testng.annotations.Test;

import java.io.IOException;

public class test2 {
    @Test
    public void testpostcode(){
        BaseCase baseCase=new BaseCase();
        try {
            baseCase.executecase("postcodecount","postcode");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
