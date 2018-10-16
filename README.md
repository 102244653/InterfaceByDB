# ApiDBTest[接口自动化测试]
一、简介：

1、使用mybatis在数据库维护接口测试用例

2、使用java发起http/https请求测试接口

3、同时生成html和Excel测试报告

4、直接部署在jenkins平台自动测试

5、持续维护测试用例，随时新增用例，仅执行有效用例

二、编写接口参数类：

    ①每个接口编写一个类，写好所有参数类型
    
    ②重写toString（）方法，使其返回结果为请求参数格式的数据
    
    ③添加一个Result（）方法，使其返回结果为ExcelReport数组结果
    
    public class postcode {
    public int ID;
    public String CaseName;
    public String RequestMethod;
    public String postcode;
    public String areaname;
    public String appkey;
    public String sign;
    public String format;
    public String ExpectResult;
    public String Effective;

    @Override
    public String toString(){
        if(!postcode.equals("null")){
            return "?app=life.postcode&postcode="+postcode+"&appkey="+appkey+"&sign="+sign+"&format="+format;
        }else {
            return "?app=life.postcode&areaname="+areaname+"&appkey="+appkey+"&sign="+sign+"&format="+format;
        }

    }

    //组装报告的结果数组
    public String[] Result(){
        String[] result =new String[8];
        result[0]= ConfigFile.getUrl("test.url");
        result[1]="PostCode";
        result[2]=CaseName;
        result[3]=RequestMethod;
        result[4]=this.toString();
        result[5]=ExpectResult;
        return result;
    }
    }

三、读取测试用例：

    在src\main\resources\mapper\SQLMapper.xml中按接口用例表添加相应的读取用例的sql
    
 示例：
 
    #查询接口用例总数：
     <select id="postcodecount"  resultType="Integer">
         select count(*) from postcode;
    </select>
    
    #查询接口单条用例：
    <select id="postcode" 【接口名称即表名】 parameterType="Integer" 【请求参数类型，即读取第几条用例】 resultType="CaseData.postcode" 【返回      的参数类型】>
        select * from postcode where ID=#{id} 【使用#{   }的形式参数化变量】;
    </select>

四、数据库用例设计：

每个接口设计一张表，结构统一如下：

    ①表名==接口名称
    
    ②ID==用例编号
    
    ③CaseName==用例名称
    
    ④RequestMethod==请求方法（post/get）
    
    ⑤data==接口参数字段(一个参数一个字段)
    
    ⑥ExpectResult==预期结果
    
    ⑦Effective==用例是否有效

数据库用例表结构示例：
    
    登录接口参数{用户名|密码|用户类型}
    [USER]
    | 用例编号 |   用例名称 |      请求方式    |   用户名  |   密码    | 用户类型 |    预期结果   |  用例是否有效 | 
    |   ID    |  CaseName  |  RequestMethod  | uasername | password |  type   |  ExpectResult |   Effective  |
    
        1     正确账号密码登录      post         zhangsan    123456       1     equals[success,1]       T


五、编写测试Case：

    InitTest用于生成报告，Case类需继承它：
    
    public class InitTest {

    @BeforeSuite
    public void beforetest(){
        InitExcelReport.InitExcel();
    }

    @AfterSuite
    public void aftertest(){
        new InitHtmlReport().CreatHtmlReport();
    }
    }
    
    用于执行的Case类,调用BaseCase基类处理数据、执行用例、记录结果：
    public class Case extends InitTest{
    @Test
    public void TestPostCode(){
        BaseCase baseCase=new BaseCase();
        try {
            baseCase.executecase("postcodecount","postcode");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    
    
六、结果断言：
    
    预期结果的格式：
    
    比较方式[字段名：预期结果] ，比较方式[字段名：预期结果] 
    例： contain[key：vlaue],equals[key：vlaue]
    
    1.可自定义支持结果长度的比较，需要根据实际情况在AnylistJson类修改getsize（）方法即可
    2.同时支持多个字段的校验，使用 ，分隔即可

七、ExcelReport数据结构：

    ①ID==用例编号（从1自增）
    
    ②CaseName==用例名称（数据库读取）
    
    ③ApiName==接口名称（表名）
    
    ④TestURL==测试地址（代码里配置）
    
    ⑤RequestData==请求参数（数据库读取）
    
    ⑥ExpectResult==预期结果（数据库读取）
    
    ⑦ResponseData==响应结果
    
    ⑧Result==测试结果（Pass/Fail，默认为Fail）
    
    ⑨每条用例执行结束后组装ExccelReport和HtnlReport数据，并写入结果
    
八、执行用例：

    【先读取用例总数，然后for（）循环执行用例】
    使用testng.xml文件配置要执行的用例，在持续平台上直接执行testng.xml文件即可：
    
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
    <suite name="API自动化测试" >
    <test name="测试">
        <classes>
            <class name="test2"></class>
        </classes>
    </test>
    </suite>
    
    执行结束后在TestExcelReport查看Excel报告和TestReport查看html报告
