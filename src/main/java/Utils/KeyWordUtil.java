package Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 关键字格式：  $key:type
 * 示例： $Date:yyyymmdd
 * key--关键字
 * type--方法参数（只支持一个）
 */
public class KeyWordUtil {

    private static Logger logger = LoggerFactory.getLogger(KeyWordUtil.class);

    public static String KeyWord(String keyword) throws Exception {
        String text=keyword;
        try {
            if (keyword.startsWith("$") && keyword.length() < 2) {
                int point1 = keyword.trim().indexOf("$");
                int point2 = keyword.trim().indexOf(":");
                String key = keyword.substring(point1 + 1, point2).trim();
                String value = keyword.substring(point2 + 1).trim();
                switch (key) {
                    default:
                        logger.error("关键字错误：" + keyword);
                        break;
                    case "Date":
                        if (value.equals("null")) {
                            text = Date();
                        } else {
                            text = Date(value);
                        }
                        break;
                    case "RandomNum":
                        if (value.equals("null")) {
                            text = RandomNum(value);
                        } else {
                            text = RandomNum("8");
                        }
                        break;
                    case "RandomStr":
                        if (value.equals("null")) {
                            text = RandomStr(value);
                        } else {
                            text = RandomStr("8");
                        }
                        break;
                }
            }
        }catch (Exception e){
            throw  new  Exception(keyword+" 关键字解析异常："+e.getStackTrace());
        }
        return  text;
    }

    public static String Date(String type){
        return (String)DateUtil.format(type);
    }

    public static String Date(){
        return String.valueOf(DateUtil.time());
    }

    public static String RandomNum(String length){
        RandomUtil r=new RandomUtil();
       return String.valueOf(r.RandomNum(Integer.valueOf(length)));
    }

    public static String RandomStr(String length){
        RandomUtil r=new RandomUtil();
        return r.RandomStr(Integer.valueOf(length));
    }
}
