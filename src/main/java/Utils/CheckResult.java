package Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CheckResult {

    private static Logger logger = LoggerFactory.getLogger(CheckResult.class);
    /**
     * object格式：比较方式[字段名：预期结果]  ==  equals[key:vlaue]
     */
    public static boolean checkresult(String response,String exceptresult) throws Exception {
        int countflag=0;
        if(response.isEmpty() && exceptresult.isEmpty()) return false;
       // HashMap mapresult= (HashMap<String, String>) new AnalyticJson().AnylistJson(response);
        JSONObject responseJson = JSON.parseObject(response);
        String[] object= new CutStrUtil().SplitByIndex(exceptresult,";");
        for(int i=0;i<object.length;i++){
            String text=object[i];
            int point1=text.trim().indexOf("[");
            int point2=text.trim().indexOf(":");
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
            GetJsonValue gjv=new GetJsonValue();
            switch (type.trim()){
                case "equals":
                    try {
                        //String _value1= (String) mapresult.get(key);
                        String _value1=gjv.getValueByJPath(responseJson,key);
                        if(!_value1.equals(except)){
                            countflag++;
                            logger.info(text+"  结果校对错误");
                        }else {
                            logger.info(text+"  结果校对正确");
                        }
                    }catch (Exception e){
                        throw new Exception("预期结果断言错误: "+text);
                    }
                    break;
                case "contain":
                    try {
                        String _value2=gjv.getValueByJPath(responseJson,key);
                         if(!_value2.contains(except)){
                             countflag++;
                            logger.info(text+"  结果校对错误");
                         }else {
                            logger.info(text+"  结果校对正确");
                         }
                    }catch (Exception e){
                        throw new Exception("预期结果断言错误: "+text);
                     }
                    break;
                case "length":
                    try {
                        int _value5=gjv.getLenghtByJPath(responseJson,key);
                        if(!except.equals(String.valueOf(_value5))){
                            countflag++;
                            logger.info(text+"  结果校对错误");
                        }else {
                            logger.info(text+"  结果校对正确");
                        }
                    }catch (Exception e){
                        throw new Exception("预期结果断言错误: "+text);
                    }
                    break;
                case "size":
                    try {
                        int _value5=gjv.geySizeByJpath(responseJson,key);
                        if(!except.equals(String.valueOf(_value5))){
                            countflag++;
                            logger.info(text+"  结果校对错误");
                        }else {
                            logger.info(text+"  结果校对正确");
                        }
                    }catch (Exception e){
                        throw new Exception("预期结果断言错误: "+text);
                    }
                    break;
                case "start":
                    try {
                        String _value3=gjv.getValueByJPath(responseJson,key);
                        if(!_value3.startsWith(except)){
                            countflag++;
                            logger.info(text+"  结果校对错误");
                        }else {
                            logger.info(text+"  结果校对正确");
                        }
                    }catch (Exception e){
                        throw new Exception("预期结果断言错误: "+text);
                    }
                    break;
                case "end":
                    try {
                        String _value4=gjv.getValueByJPath(responseJson,key);
                        if(!_value4.endsWith(except)){
                            countflag++;
                            logger.info(text+"  结果校对错误");
                        }else {
                            logger.info(text+"  结果校对正确");
                        }
                    }catch (Exception e){
                        throw new Exception("预期结果断言错误: "+text);
                    }
                    break;
                default:
                    logger.info(text+"  校验类型错误: "+type);
                    break;
            }
        }
        if(countflag==0){
            return true;
        }else {
            return false;
        }
    }

}
