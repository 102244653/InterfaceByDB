package CaseData;

import BaseCase.CaseUtil;
import Utils.ConfigFile;
import Utils.StaticData;
import Utils.ToMD5;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.jws.Oneway;
import java.util.HashMap;
import java.util.Map;

@Data
public class login {
    public int ID;
    public String CaseType;
    public String CaseName;
    public String RequestMethod;
    public String username;
    public String password;
    public String version;
    public String ExpectResult;
    public String Effective;

    public Map<String, String> setloginbody() {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap=Body.setkey("0",this.version,"",ToMD5.sign(System.currentTimeMillis() / 1000, "", "0", this.version,"login"));
        paramsMap.put("password",ToMD5.md5(String.valueOf(this.password)));
        paramsMap.put("username", this.username);

        return paramsMap;
    }

    @Override
    public String toString(){
        return "username="+this.username+",password="+this.password+",version="+this.version;
    }


    //输入excel报告的结果集合
    public String[] Result() throws Exception {
        return CaseUtil.setResult("login", CaseName, RequestMethod,this.toString(), ExpectResult);
    }

}
