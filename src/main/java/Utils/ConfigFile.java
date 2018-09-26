package Utils;


import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 读取resource下的application配置文件
 * */
public class ConfigFile {

    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(String name) {
        String url= bundle.getString(name);
        return url;
    }
}
