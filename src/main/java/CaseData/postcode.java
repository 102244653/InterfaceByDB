package CaseData;
import BaseCase.CaseUtil;
import lombok.Data;

@Data
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

    //输入excel报告的结果集合
    public String[] Result()throws Exception{
        return CaseUtil.setResult("PostCode",CaseName,RequestMethod,this.toString(),ExpectResult);
    }
}
