package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jdl.basic.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhengchengfa
 * @Date 2021-01-27 15:04
 * Mq通用服务实现
 */
@Component
@Slf4j
public class JMQCommonServiceImpl implements JMQCommonService {

    @Value("${uat:false}")
    private String isUat;

    /**
     * 根据UAT标位匹配UAT环境，与线上环境隔离
     */
    @Override
    public boolean checkUatProduct(Message message) {
        String uatFlag = message.getAttribute("JMQ_QL_IS_UAT");
        if(StringUtils.isBlank(isUat)) {
            return true;
        }else if("true".equals(isUat)) {
            //如果是UAT环境消费， MQ中必须存在UAT标识，才会处理
            if(StringUtils.isNotBlank(uatFlag) && isUat.trim().equals(uatFlag.trim())) {
                return true;
            }
            return false;
        }else {
            //如果是线上环境，除了MQ中标位是UAT不消费，其他都消费
            if(StringUtils.isNotBlank(uatFlag) && !isUat.trim().equals(uatFlag.trim())) {
                return false;
            }
            return true;
        }
    }
}
