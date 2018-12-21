package Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import Utils.GetJsonByJpath;
import java.util.HashMap;

public class CheckResult {

    private static Logger logger = LoggerFactory.getLogger(CheckResult.class);
    /**
     * object格式：比较方式[字段名：预期结果]  ==  equals[key:vlaue]
     */
    public static boolean checkresult(String response,String exceptresult) throws Exception {
        int countflag=0;
        if(response.isEmpty() && exceptresult.isEmpty()) return false;
        //HashMap mapresult= (HashMap<String, String>) new AnylistJson().AnylistJson(response);//旧方法解析json
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
            switch (type.trim()){
                case "equals":
                    try {
                        //String _value1= (String) mapresult.get(key);
                        String _value1=GetJsonByJpath.getValueByJPath(key);
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
                         //String _value2= (String) mapresult.get(key);
                         String _value2=GetJsonByJpath.getValueByJPath(key);
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
                    logger.info("length方法暂未实现:请根据实际情况扩展");
                    break;
                case "start":
                    try {
                        //String _value3= (String) mapresult.get(key);
                         String _value3=GetJsonByJpath.getValueByJPath(key);
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
                        //String _value4= (String) mapresult.get(key);
                        String _value4=GetJsonByJpath.getValueByJPath(key);
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
