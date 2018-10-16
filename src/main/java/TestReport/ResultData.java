package TestReport;

import Utils.ConfigFile;
import Utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;


public class ResultData {

    private static Logger logger = LoggerFactory.getLogger(ResultData.class);
    public static List<String[]> AllCase = new ArrayList<String[]>();

    public static List<String> PassCase=new ArrayList<String>();

    public static List<String> FailCase=new ArrayList<String>();

    public static List<String> SkipCase=new ArrayList<String>();


    public static String ListToJson(){
        if(AllCase.isEmpty()){
            return null;
        }
        String json="";
        for(int i=0;i<AllCase.size();i++){
            String[] data=AllCase.get(i);
            if(data.length==0){ continue; }
            String result="---";
            if(data[7]=="PASS"){
                result="成功";
            }
            if(data[7]=="FAIL"){
                result="失败";
            }
            if(data[7]=="SKIP"){
                result="跳过";
            }
            if(i!=0){ json = json + ",\n"; }
            json=json+"\n{" +
                    "\"apiname\":\""+data[1]+ "\",\n" +
                    "\"casename\":\""+data[2]+ "\",\n" +
                    "\"requesttype\":\""+data[3]+ "\",\n" +
                    "\"requestbody\":\""+data[4]+ "\",\n" +
                    "\"excepct\":\""+data[5]+ "\",\n" +
                    "\"result\":\""+result+ "\",\n" +
                    "\"log\":[`"+data[6].replaceAll("\"","'")+ "`]\n" +
                    "}\n";
        }
        return json.trim();
    }

    public static String GetResultData(){
        String resultdata= "{" +
                "\"testName\":\""+InitExcelReport.TitleName+"\",\n"+
                "\"testadress\":\""+ ConfigFile.getUrl("test.url")+"\",\n"+
                "\"testAll\":"+AllCase.size()+",\n"+
                "\"testPass\":"+PassCase.size()+",\n"+
                "\"testSkip\":"+SkipCase.size()+",\n"+
                "\"testFail\":"+FailCase.size()+",\n"+
                "\"totalTime\":\""+DateUtils.format(DateUtils.ZH_DATE_FORMAT)+"\",\n"+
                "\"testResult\":["+ListToJson()+"]\n" +
                "}";
        return  resultdata;
    }

    public static int GetAll(){
        return AllCase.size();
    }

    public static int GetPass(){
        return PassCase.size();
    }

    public static int GetFail(){
        return FailCase.size();
    }

    public static int GetSkip(){
        return SkipCase.size();
    }

}
