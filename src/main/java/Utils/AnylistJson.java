package Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnylistJson {

    private static Logger logger = LoggerFactory.getLogger(AnylistJson.class);

    public Map AnylistJson(String str) {
        HashMap map=new HashMap<String, String>();
        try {
            Map json = this.cutjson(str);
            ArrayList<ArrayList<String>> result=this.tostr(json);
            int j=result.get(0).size();
            for(int i=0;i<j;i++){
                ArrayList<ArrayList<String>> result0=null;
                if(this.checkvalue(result.get(1).get(i))){
                    Map json0 = this.cutjson(result.get(1).get(i));
                    result0=this.tostr(json0);
                    this.putmap(map,result0);
                }else {
                    this.putmap(map,result.get(0).get(i),result.get(1).get(i));
                }
            }
            return map;
        }catch (Exception e){
            logger.error("结果解析错误:\n"+str);
            return null;
        }
    }


    public Map cutjson(String str) {
        Map json = (Map) JSONObject.parse(str);
        return json;
    }

    public ArrayList<ArrayList<String>> tostr(Map json) {
        ArrayList<ArrayList<String>> RSTR0 = new ArrayList<ArrayList<String>>();
        ArrayList<String> line1 = new ArrayList<String>();
        ArrayList<String> line2 = new ArrayList<String>();
        RSTR0.add(line1);
        RSTR0.add(line2);
        for (Object map : json.entrySet()) {
            String key = String.valueOf(((Map.Entry) map).getKey()).trim();
            String value = String.valueOf(((Map.Entry) map).getValue()).trim();
            if(!key.isEmpty() && !value.isEmpty()){
                RSTR0.get(0).add(key);
                RSTR0.get(1).add(value);
            }
        }
        return RSTR0;
    }

    public Boolean checkvalue(String str){
        boolean flag=false;
        if((str.startsWith("{") && str.endsWith("}"))){
            flag=true;
        }
        // (str.startsWith("[") && str.endsWith("]"))
        return flag;
    }

    public void putmap(HashMap<String, String> map,ArrayList<ArrayList<String>> result){
        int k=result.get(0).size();
        for(int i=0;i<k;i++) {
            if (!result.get(0).get(i).isEmpty() && !result.get(1).get(i).isEmpty()) {
                map.put(result.get(0).get(i), result.get(1).get(i));
            }
        }
    }

    public void putmap(HashMap<String, String> map,String key,String value){
        if(!key.isEmpty()  && !value.isEmpty()){
            map.put(key,value);
        }
    }


    public int getsize(String str){
        return 0;
    }

}
