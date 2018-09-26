package CaseData;
import Utils.ConfigFile;
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
    public String ISDO;

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
        result[0]="PostCode";
        result[1]=CaseName;
        result[2]=RequestMethod;
        result[3]= ConfigFile.getUrl("test.url");
        result[4]=this.toString();
        result[5]=ExpectResult;
        return result;
    }



}
