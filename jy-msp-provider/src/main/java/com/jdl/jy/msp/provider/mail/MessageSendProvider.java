package com.jdl.jy.msp.provider.mail;

import com.jdl.jy.msp.api.IMessageSendApi;
import com.jdl.jy.msp.domain.JavaMailProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @ProjectName：JY-MSP
 * @Package： com.jdl.jy.msp.provider.mail
 * @ClassName: MessageSendProvider
 * @Description:
 * @Author： wuzuxiang
 * @CreateDate 2022/4/22 14:25
 * @Copyright: Copyright (c)2020 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version： V1.0
 */
@Service
@Slf4j
public class MessageSendProvider implements IMessageSendApi {

    @Autowired
    private JavaMailProxy javaMailProxy;

    @Override
    public boolean sendMail() {
        try {
            javaMailProxy.send();
        } catch (UnsupportedEncodingException | MessagingException e) {
            log.error("发送邮件失败：",e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
