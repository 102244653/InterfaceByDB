package CaseData;

import lombok.Data;
/**
*suitcase为测试套件表
*/

@Data
public class suitcase {
    public int id;
    public String casename;//接口名字
    public String caseqty;//接口对应的用例数量
    public String effictive;//是否执行接口用例

    @Override
    public String toString(){
        return "编号:"+id+",测试接口名称:"+casename+",接口用例数量:"+caseqty+",本次是否执行(T-执行/F-不执行):"+effictive;
    }
}
