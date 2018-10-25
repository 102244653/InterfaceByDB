package CaseData;
import Utils.ConfigFile;
import lombok.Data;

/**
*postcode接口表
*/

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
