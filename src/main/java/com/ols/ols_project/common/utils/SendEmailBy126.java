package com.ols.ols_project.common.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * 发送邮件
 * @author yuyy
 * @date 20-3-7 下午9:44
 */
@Slf4j
@Component
public class SendEmailBy126 {

    /**
     * 邮箱账户
     */
    private static String emailAccount;

    /**
     * 第三方代理授权码
     */
    private static String authorizationCode;

    @Value(value = "${email.account}")
    public void setEmailAccount(String account) {
        SendEmailBy126.emailAccount = account;
    }

    @Value(value = "${email.authorizationCode}")
    public void setAuthorizationCode(String authorizationCode) {
        SendEmailBy126.authorizationCode = authorizationCode;
    }

    @Async("myTaskAsyncPool")  //myTaskAsynPool即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
    public void sendEmail(String to , String tittle, String content) {
    //1、创建连接对象
    Properties props = new Properties();
    //1.1设置邮件发送的协议
    props.put("mail.transport.protocol" , "smtp");
    //1.2设置发送邮件的服务器
    props.put("mail.smtp.host" , "smtp.126.com");
    //1.3需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
    props.put("mail.smtp.auth" , "true");
    //1.4下面一串是发送邮件用465端口，如果不写就是以25端口发送，阿里云已经关闭了25端口
    props.setProperty("mail.smtp.socketFactory.class" , "javax.net.ssl.SSLSocketFactory");
    props.setProperty("mail.smtp.socketFactory.fallback" , "false");
    props.setProperty("mail.smtp.port" , "465");
    props.setProperty("mail.smtp.socketFactory.port" , "465");
    //1.5认证信息
    Session session=Session.getInstance(props , new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(emailAccount , authorizationCode);
        }
    });

    //2、创建邮件对象
    Message message = new MimeMessage( session );
    //2.1设置发件人
        try {
            message.setFrom( new InternetAddress(emailAccount) );
        } catch (MessagingException e) {
            log.error("发件人：{}，收件人：{}，邮件主题：{}，邮件内容：{}，设置邮件发件人失败，{}"
                    ,emailAccount,to,tittle,content,e.toString());
        }
        //2.2设置收件人
        try {
            message.setRecipient(RecipientType.TO , new InternetAddress( to));
        } catch (MessagingException e) {
            log.error("发件人：{}，收件人：{}，邮件主题：{}，邮件内容：{}，设置邮件收件人失败，{}"
                    ,emailAccount,to,tittle,content,e.toString());
        }
        //2.3设置抄送者（PS:没有这一条网易会认为这是一条垃圾短信，而发不出去）
        try {
            message.setRecipient(RecipientType.CC , new InternetAddress(emailAccount));
        } catch (MessagingException e) {
            log.error("发件人：{}，收件人：{}，邮件主题：{}，邮件内容：{}，设置邮件抄送者失败，{}"
                    ,emailAccount,to,tittle,content,e.toString());
        }
        //2.4设置邮件的主题
        try {
            message.setSubject(tittle);
        } catch (MessagingException e) {
            log.error("发件人：{}，收件人：{}，邮件主题：{}，邮件内容：{}，设置邮件的主题失败，{}"
                    ,emailAccount,to,tittle,content,e.toString());
        }
        //2.5设置邮件的内容
        try {
            message.setContent(content, "text/html;charset=utf-8");
        } catch (MessagingException e) {
            log.error("发件人：{}，收件人：{}，邮件主题：{}，邮件内容：{}，设置邮件的内容失败，{}"
                    ,emailAccount,to,tittle,content,e.toString());
        }

        //3、发送邮件
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("发件人：{}，收件人：{}，邮件主题：{}，邮件内容：{}，发送邮件失败，{}"
                    ,emailAccount,to,tittle,content,e.toString());
        }
    }
}
