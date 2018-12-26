package TestReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;

public class SendEmail {
    private static Logger logger = LoggerFactory.getLogger(SendEmail.class);
    //配置文件路径
    String path=System.getProperty("user.dir")+"\\src\\main\\resources\\email.properties";
    //发送邮箱账号  xxx@xxx.com形式
    private String userName;
    //发送邮件密码
    private String passWord;
    //stmp服务器地址
    private String smtpHost;
    //smtp服务器端口
    private String smtpPort;
    //发件人地址
    private String from;
    //收件人地址
    private String tos;

    //读取邮箱配置文件
    public  SendEmail() {
        Properties prop = new Properties();
        try {
            //读取属性文件properties
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            prop.load(in);
            smtpHost = prop.getProperty("email.smtpHost");
            smtpPort = prop.getProperty("email.smtpPort");
            userName = prop.getProperty("email.username");
            passWord = prop.getProperty("email.passWord");
            from = prop.getProperty("email.from");
            tos = prop.getProperty("email.tos");
            in.close();
        } catch (Exception e) {
            logger.error("读取邮件配置信息失败");
            e.printStackTrace();
        }
    }

    /**
     *  title    标题
     *  content  内容
     *  file     附件
     */
    public void SendReport(String title, String content,String file) {
        Properties smtpProper=setSmtp(smtpHost, smtpPort, userName);
        Auth authenticator=new Auth(userName, passWord);
        try {
            //创建session实例
            Session session=Session.getInstance(smtpProper, authenticator);
            MimeMessage message=new MimeMessage(session);
            //是否开启调试模式
            session.setDebug(false);
            //设置from发件人邮箱地址
            message.setFrom(new InternetAddress(from));
            //收件人to LIST
            ArrayList<Address> toList=new ArrayList<Address>();
            //收件人字符串通过,号分隔收件人
            String[] toArray=tos.split(",");
            for (int i = 0; i < toArray.length; i++) {
                String str = toArray[i];
                toList.add(new InternetAddress(str));
            }
            //将收件人列表转换为收件人数组
            Address[] addresses=new Address[toList.size()];
            addresses=toList.toArray(addresses);
            //设置to收件人地址
            message.setRecipients(MimeMessage.RecipientType.TO, addresses);
            //设置邮件标题
            message.setSubject(title);
            // 创建附件消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 添加邮件内容
            messageBodyPart.setContent(content, "text/html;charset=UTF-8");
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            // 附件部分
            if(file!=null&&!file.isEmpty()) {
                try{
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(file);
                    multipart.addBodyPart(messageBodyPart);
                }catch (Exception e){
                    logger.error("邮件添加附件失败");
                    e.printStackTrace();
                }
            }
            // 发送完整消息
            message.setContent(multipart );
            Transport transport=session.getTransport();
            transport.connect(smtpHost,userName, passWord);
            transport.sendMessage(message,addresses);
            logger.info("发送邮件成功！");

        } catch (Exception e) {
            // TODO: handle exception
            logger.error("发送邮件失败！");
            e.printStackTrace();
        }
    }

    private Properties setSmtp(String smtpHost, String smtpPort, String userName) {
        //创建邮件配置文件
        Properties maiProperties = new Properties();
        //配置smtp发件服务器
        maiProperties.put("mail.transport.protocol", "smtp");
        //配置smtp服务器
        maiProperties.put("mail.smtp.host", smtpHost);
        //配置smtp服务器端口
        maiProperties.put("mail.smtp.port", smtpPort);
        //配置smtp用户名
        maiProperties.put("mail.user", userName);
        //配置smtp身份验证
        maiProperties.put("mail.smtp.auth", "true");
        return maiProperties;
    }



}

//smtp 身份验证类
class Auth extends Authenticator {
    Properties pwdProperties;
    //构造函数

    public Auth(String userName, String passWord) {
        pwdProperties = new Properties();
        try {
            pwdProperties.put(userName, passWord);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //必须实现 PasswordAuthentication 方法
    public PasswordAuthentication passwordAuthentication() {
        String userName = getDefaultUserName();
        //当前用户在密码表里
        if (pwdProperties.containsKey(userName)) {
            //取出密码，封装好后返回
            return new PasswordAuthentication(userName, pwdProperties.getProperty(userName).toString());

        } else {
            //如果当前用户不在密码表里就返回Null
            System.out.println("当前用户不存在");
            return null;
        }
    }
}


