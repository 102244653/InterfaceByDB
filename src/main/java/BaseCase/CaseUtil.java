package BaseCase;

import CaseData.TestUrl;
import CaseData.login;
import CaseData.postcode;
import Utils.DatabaseUtil;

public class CaseUtil {

    /**
     * getcasedata方法主要用来获取每个接口对象并获取对象的用例参数值，新增接口需要增加对应方法
     * */
    public static BaseCase getcasedata(String casename,int i)throws Exception{
        BaseCase casedata=new BaseCase();
        if (casename.equals("postcode")){
            postcode casevalue= DatabaseUtil.getSqlSession().selectOne("postcode",i);
            casedata.setRequestbody(casevalue.toString()); //请求参数
            casedata.setType(casevalue.getRequestMethod().trim().toLowerCase()); //读取请求类型
            casedata.setExcepct(casevalue.getExpectResult().trim()); //读取预期结果
            casedata.setIsdo(casevalue.getEffective().trim());//是否执行用例
            casedata.setResultExcel(casevalue.Result());//Excel报告结果
        }
        if (casename.equals("login")){
            login casevalue= DatabaseUtil.getSqlSession().selectOne("login",i);
            casedata.setRequestbody(casevalue.toString()); //请求参数
            casedata.setType(casevalue.getRequestMethod().trim().toLowerCase()); //读取请求类型
            casedata.setUri(casevalue.getURI());//接口地址uri
            casedata.setExcepct(casevalue.getExpectResult().trim()); //读取预期结果
            casedata.setIsdo(casevalue.getEffective().trim());//是否执行用例
            casedata.setResultExcel(casevalue.Result());//Excel报告结果
        }
        return casedata;
    }


    //设置报告结果数组参数
    public static String[] setResult(String apiname,String casename,String method,String request,String expect) throws Exception {
        String[] result =new String[8];
        result[0]= TestUrl.TestUrl;
        result[1]=apiname;
        result[2]=casename;
        result[3]=method;
        result[4]=request;
        result[5]=expect;
        return result;
    }
}
