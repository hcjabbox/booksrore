package com.xidian.bookstore.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class SendEmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 发送邮件
     * @param title 邮件主题
     * @param name 收件箱列表邮件名称
     * @param titleWithName 是否在主题后面添加名称
     * @param content 邮件内容
     * @param contentWithName 是否在内容后面添加名称
     * @param email 收件人的邮箱
     */

    public void sendMail(String title,String name,boolean titleWithName,String content,boolean contentWithName,String email ){
        MimeMessage mimeMessage =null;
        try{
            mimeMessage = javaMailSender.createMimeMessage(); //创建要发送的消息
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(new InternetAddress(sender,name,"UTF-8")); //设置发送者是谁
            helper.setTo(email); //接收者邮箱
            title=titleWithName?title+"-"+name:title; //标题内容
            helper.setSubject(title); //发送邮件的标题
            if(contentWithName){
                content +="<p style='text-align:right'>"+name+"</p>";
                content +="<p style='text-align:right'>"+TimeUtil.dateToString("yyyy-MM-dd HH:mm:ss")+"</p>";
            }
            helper.setText(content,true); //发送的内容 是否为HTML
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取六位随机验证码
     * @return int类型的验证码
     */
    public static String getVerificationCode(){
      int code = (int)((Math.random()*9+1)*100000);
      return String.valueOf(code);
    }


}
