package Utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            int j=result.get(0).size();//判断有多少个key
            for(int i=0;i<j;i++){
                ArrayList<ArrayList<String>> result0=null;
                if(this.checkvalue(result.get(1).get(i))){//如果value以{}开头结尾则继续解析
                    Map json0 = this.cutjson(result.get(1).get(i));
                    result0=this.tostr(json0);
                    this.putmap(map,result0);//解析结果保存至map
                }else {//不满足条件直接保存至map
                    this.putmap(map,result.get(0).get(i),result.get(1).get(i));
                }
            }
            return map;
        }catch (Exception e){
            logger.error("结果解析错误:\n"+str);
            return null;
        }
    }


    //解析str成json
    public Map cutjson(String str) {
        Map json = (Map) JSONObject.parse(str);
        return json;
    }

    //申明一个ArrayList保存key，value，然后返回
    public ArrayList<ArrayList<String>> tostr(Map json) {
        ArrayList<ArrayList<String>> RSTR0 = new ArrayList<ArrayList<String>>();
        ArrayList<String> line1 = new ArrayList<String>();//key值
        ArrayList<String> line2 = new ArrayList<String>();//value值
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

    //判断字符串结果是否以{}开头结尾，满足则继续解析
    public Boolean checkvalue(String str){
        boolean flag=false;
        if((str.startsWith("{") && str.endsWith("}"))){
            flag=true;
        }
        // (str.startsWith("[") && str.endsWith("]"))
        return flag;
    }

    //将解析的结果放进map保存
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
