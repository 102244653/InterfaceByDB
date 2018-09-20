# ApiDBTest
使用数据库维护接口测试用例

1.编写接口参数类：
    每个接口编写一个类，写好所有参数类型
    重写toString（）方法，使其返回结果为请求参数格式的数据
    添加一个Result（）方法，使其返回结果为ExcelReport数组结果（见4）

2.读取测试用例：
    在MyApiFarmeWork\src\main\resources\mapper\SQLMapper.xml中按接口用例表添加相应的读取用例的sql

 示例：
 <select id="USER" 【接口名称即表名】 parameterType="Integer"  【请求参数类型，即读取第几条用例】 resultType="CaseData.USER"  【返回的参数类型】>
            select * from myinfo WHERE id=#{id}  【使用#{   }的形式参数化变量】;
 </select>


3.数据库用例设计：
每个接口设计一张表，结构统一如下：
    表名==接口名称
    ID==用例编号
    CaseName==用例名称
    RequestMethod==请求方法（post/get）
    data==接口参数字段(一个参数一个字段)
    ExpectResult==预期结果
    ISDO==用例是否有效

示例：
    [USER]
    |  ID  |  CaseName   |  RequestMethod   |  uasername  |  password  |  type  |  ExpectResult  |   ISDO  |
       1        登录            post           zhangsan       123456        1         success        true


4.ExcelReport数据结构：
    ID==用例编号（从1自增）
    CaseName==用例名称（数据库读取）
    ApiName==接口名称（表名）
    TestURL==测试地址（代码里配置）
    RequestData==请求参数（数据库读取）
    ExpectResult==预期结果（数据库读取）
    ResponseData==响应结果
    Result==测试结果（Pass/Fail，默认为Fail）
    每条用例执行结束后组装ExccelReport数据，并写入结果


5.执行用例：
    先读取用例总数，然后for（）循环执行用例
