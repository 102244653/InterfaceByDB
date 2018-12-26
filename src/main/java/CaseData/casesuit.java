package CaseData;

import lombok.Data;

/**
 * 测试套件表
 * */

@Data
public class casesuit {
    public int id;
    public String interfacename;
    public String caseqty;
    public String effictive;

    @Override
    public String toString(){
        return "编号:"+id+",测试接口名称:"+ interfacename +",接口用例数量:"+caseqty+",本次是否执行(T-执行/F-不执行):"+effictive;
    }

}
