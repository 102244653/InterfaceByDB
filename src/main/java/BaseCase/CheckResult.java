package BaseCase;

import Utils.CutStrUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckResult {

    private static Logger logger = LoggerFactory.getLogger(CheckResult.class);
    /**
     * object格式：比较方式[字段名：预期结果]  ==  eq[key,vlaue]
     */
    public static boolean checkresult(String response,String exceptresult){
        int countflag=0;
        if(response.isEmpty() && exceptresult.isEmpty()) return false;
        String[] object= new CutStrUtils().SplitByIndex(exceptresult,";");
        for(int i=0;i<object.length;i++){
            String text=object[i];
            int point1=text.trim().indexOf("[");
            int point2=text.trim().indexOf(",");
            int point3=text.trim().indexOf("]");
            String type="";
            if(point1!=0){
                type=text.substring(0,point1);
            }
            String key="";
            if(point1+1!=point2){
                 key=text.substring(point1+1,point2);
            }
            String except="";
            if(point2+1!=point3){
                except=text.substring(point2+1,point3);
            }
            switch (type.trim()){
                case "equals":
                    String value="\""+key+"\":\""+except+"\"";
                    if(!response.contains(value)){
                        countflag++;
                        logger.info(text+"  结果校对错误");
                    }else {
                        logger.info(text+"  结果校对正确");
                    }
                    break;
                case "contain":
                    if(!response.contains(except)){
                        countflag++;
                        logger.info(text+"  结果校对错误");
                    }else {
                        logger.info(text+"  结果校对正确");
                    }
                    break;
                case "length":
                    logger.info("length方法暂未实现");
                    break;
                default:
                    logger.info(text+"  校验类型错误："+type);
                    break;
            }
        }
        if(countflag==0){
            return true;
        }else {
            return false;
        }
    }


    public Boolean check(String response,String except){
        Boolean flag=false;
        int key=except.trim().indexOf("+");
        String except1=except.substring(0,key);
        String except2=except.substring(key+1);
        JSONObject json=  JSONObject.parseObject(response);
        String success = json.getString("success");
        String adress="";
        if(except1.equals(success)){
            flag=true;
        }
//        if (!except2.isEmpty()){
//            JSONArray array = json.getJSONArray("result");
//            JSONObject obj = json.getJSONObject("lists");
//            String simcall =obj.getString("simcall");
//            if(except2.equals(adress)&&flag==true){
//                flag=true;
//            }
//        }
        return flag;
    }
}
