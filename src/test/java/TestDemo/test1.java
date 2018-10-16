package TestDemo;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class test1 {

    @Test
    public void test01(){
        System.out.println("11111");
        Assert.assertTrue(true);
    }
    @Test
    public void test02(){
        Reporter.log("222222");
        throw new RuntimeException("648513463");
    }
    @Test
    public void test03(){
        Assert.assertEquals("dsf","13515");
    }
    @Test
    public void test04(){
        Assert.assertEquals("123","123");
    }
}
