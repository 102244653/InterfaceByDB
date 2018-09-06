package CaseData;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter@Setter
@Data
public class MyInfo {

    public int id;
    public String name;
    public String age;
    public String desc;

    @Override
    public String toString(){
        return "{id:"+id+","+ "userName:"+id+","+ "password:"+name+","+ "age:"+age+","+ "sex:"+desc+"}";
    }

}
