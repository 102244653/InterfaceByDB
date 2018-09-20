package CaseData;

import lombok.Data;


@Data
public class post {
    public int id;
    public String name;
    public int age;

    @Override
    public String toString(){
        return "{\"name\":\""+name+"\",\"age\":\""+age+"\"}";
    }


}
