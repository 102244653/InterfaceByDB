package CaseData;

import lombok.Data;

@Data
public class suitcase {
    public int id;
    public String casename;
    public String caseqty;
    public String effictive;

    @Override
    public String toString() {
        return "编号:" + id + ",测试接口名称:" + casename + ",接口用例数量:" + caseqty + ",本次是否执行(T-执行/F-不执行):" + effictive;
    }
}
