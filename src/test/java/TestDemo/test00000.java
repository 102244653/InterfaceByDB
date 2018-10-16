package TestDemo;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


public class test00000 {

    Map<String, String> MapJson = null;

    private static class Fans{

        private String fans;
        private User user;
        private String name;

        private class User {
            private String name;
            private int age;

            @Override
            public String toString() {
                return "User{" +
                        "name='" + name + '\'' +
                        ", age=" + age +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Fans{" +
                    "fans='" + fans + '\'' +
                    ", user=" + user +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        //list string
        String jsonString = "{\"fans\":[" +
                "{\"name\":\"小王\",\"age\":7}," +
                "{\"name\":\"小尼玛\",\"age\":10}]," +
                "\"name\":\"王尼玛\"}";
        Fans fans = JSON.parseObject(jsonString, Fans.class);
        System.out.println(fans.toString());


//            JSONObject object = JSON.parseObject(jsonString);
//
//

//            for(Map.Entry<String, String> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + "/" + entry.getValue());
//            }

//            if (map.get("fans") instanceof List) {
//                for (Object object : (List)map.get("fans")) {
//                    System.out.println(object);
//                }
//            }
//
//            if (map.get("name") instanceof String) {
//                System.out.println(map.get("name"));
//            }
//
////map list Integer
//            String jsonString2 = "{\"data\": {\"pages\": [ {\"comment\": \"just for test\"},{\"comment\": \"just for test\"}],\"total_count\": 2 },\"errcode\": 0}";
//            Map map2 = JSON.parseObject(jsonString2);
//            if (map2.get("data") instanceof Map) {
//                Map<String, Object> data = (Map<String, Object>) map2.get("data");
//                System.out.println(data.get("total_count"));
//                if (data.get("pages") instanceof List){
//                    for (Object object : (List)data.get("pages")) {
//                        System.out.println(object);
//                    }
//                }
//            }
//
//            if (map2.get("errcode") instanceof Integer) {
//                System.out.println(map2.get("errcode"));
//            }
    }


    public static Map checkFormat(String str) {
        String _str = str.trim();
        Map _map = null;
        if (_str.startsWith("{") && _str.endsWith("}")) {
            Map map = JSON.parseObject(str);
        } else if (_str.startsWith("[") && _str.endsWith("]")) {
            _str.substring(1, _str.length() - 1);
            return null;
        } else {
            return null;
        }
        return _map;
    }


}
