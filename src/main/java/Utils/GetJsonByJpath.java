package Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

public class GetJsonByJpath {

    public static String getValueByJPath(JSONObject responseJson, String jpath){
        Object obj = responseJson;
        for(String s : jpath.split("/")) {
            if(!s.isEmpty()) {
                if(!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                }else if(s.contains("[") || s.contains("]")) {
                    obj =((JSONArray)((JSONObject)obj).get(s.split("\\[")[0]))
                            .get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));
                }
            }
        }
        return obj.toString();
    }

    

    @Test
    public void test(){
        String jsonvalue=
            "{" +
                    "\"current_page\":1," +
                    "\"data\":[" +
                    "{\"id\":49,\"uid\":10000024,\"orderId\":138531,\"changeCredit\":\"1050000.00\",\"datetime\":\"2018-12-21 16:02:23\"}," +
                    "{\"id\":48,\"uid\":10000024,\"orderId\":138530,\"changeCredit\":\"1050005.62\",\"datetime\":\"2018-12-21 16:01:43\"}," +
                    "{\"info\":[{\"test\":\"12\"}," +
                                "{\"test\":\"mytest\"}" +
                              "]}" +
                    "]," +
                    "\"newCredit\":\"43202874.28\"," +
                    "\"changeCredit\":\"4800009.36\"" +
                    "}";
        JSONObject responseJson = JSON.parseObject(jsonvalue);
        System.out.println(this.getValueByJPath(responseJson,"current_page"));//1
        System.out.println(this.getValueByJPath(responseJson,"data[0]/orderId"));//138531
        System.out.println(this.getValueByJPath(responseJson,"data[1]/uid"));//10000024
        System.out.println(this.getValueByJPath(responseJson,"data[1]/changeCredit"));//1050005.62
        System.out.println(this.getValueByJPath(responseJson,"data[2]/info[1]/test"));//mytest
        System.out.println(this.getValueByJPath(responseJson,"newCredit"));//43202874.28
        System.out.println(this.getValueByJPath(responseJson,"changeCredit"));//4800009.36
    }
}
