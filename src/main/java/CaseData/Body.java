package CaseData;

import Utils.StaticData;
import java.util.HashMap;
import java.util.Map;

public class Body {

    /**公共请求参数*/
    public static Map<String, String> setkey(String uid,String version,String token,String sign) {
        Map<String, String> Map = new HashMap<String, String>();
        Map.put("uid", uid);
        Map.put("version",version );
        Map.put("clientType", "1");
        Map.put("network", "1");
        Map.put("lang", "0");
        Map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        Map.put("uuid", StaticData.uuid);
        Map.put("token", token);
        Map.put("sign", sign);
        return Map;
    }
}
