package com.jdl.jy.msp.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ProjectName：JY-MSP
 * @Package： com.jdl.jy.msp.domain
 * @ClassName: JavaMailProxy
 * @Description: 京东内部的邮件发送，不能发送到群组
 * @Author： wuzuxiang
 * @CreateDate 2022/4/22 10:47
 * @Copyright: Copyright (c)2020 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version： V1.0
 */
@Data
@Configuration
@ConfigurationProperties(value = "jd.mail")
public class JavaMailProxy {

    private Properties properties = new Properties();

    private Map<String,String> author = new HashMap<>();

    private static String EMAIL_ADDRESS = "xnfjxm@jd.com";
    private static String RECIPIENT = "wuzuxiang@jd.com";

    public void send() throws UnsupportedEncodingException, MessagingException {
        // 邮件服务器auth对象
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(author.get("user"), author.get("pass"));
            }
        };
        //使用JavaMail发送邮件
        //1、获取session，可以放入容器，使用单例模式
        Session session = Session.getInstance(properties, auth);
//        //可以开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//        session.setDebug(true);
        //2、构建邮件消息
        Message message = createSimpleMail(session);
        //3、发送邮件
        Transport.send(message);
    }

    /**
     * 创建简单的邮件
     *
     * @param session 邮件session对象
     * @return {@link MimeMessage}
     * @throws Exception 异常
     */
    public static MimeMessage createSimpleMail(Session session) throws UnsupportedEncodingException, MessagingException {
        String senderName = "分拣小秘";
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人，如下的方式可以让邮件显示出
        String senderNameEncoded = MimeUtility.encodeText(senderName);
        message.setFrom(new InternetAddress(senderNameEncoded + "<" + EMAIL_ADDRESS + ">"));
        //指明邮件的收件人,会有相关的api，可以设置收件人、抄送人、米送人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT));
        //邮件的标题
        message.setSubject("只包含文本的简单邮件");
        //邮件的文本内容
        message.setContent("你好啊！", "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }
}
