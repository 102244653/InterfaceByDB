package CaseData;

import lombok.Data;

@Data
public class MyInfo {
    public int id;
    public String name;
    public String age;
    public String mydesc;

    @Override
    public String toString(){
        return "{id:"+id+","+ "userName:"+id+","+ "password:"+name+","+ "age:"+age+","+ "sex:"+mydesc+"}";
    }


    
}
