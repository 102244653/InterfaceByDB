package TestDemo;

import BaseCase.BaseCase;
import TestCase.InitTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class test2 extends InitTest {
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
