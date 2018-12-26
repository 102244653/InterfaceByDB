package CaseData;

import BaseCase.CaseUtil;
import lombok.Data;

@Data
public class login {
    public int ID;
    public String CaseName;
    public String RequestMethod;
    public String URI;
    public String username;
    public String password;
    public String ExpectResult;
    public String Effective;

    @Override
    public String toString(){
        return "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
    }

    //输入excel报告的结果集合
    public String[] Result()throws Exception{
        return CaseUtil.setResult("login",CaseName,RequestMethod,this.toString(),ExpectResult);
    }

}
