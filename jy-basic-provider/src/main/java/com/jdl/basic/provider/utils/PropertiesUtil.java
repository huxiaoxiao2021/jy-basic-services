package com.jdl.basic.provider.utils;

import com.jdl.basic.provider.config.es.ElasticSearchClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件信息加载
 * <p/>
 * Created by fangqi1 on 2016/3/29.
 */
public class PropertiesUtil {
    private Properties properties;
    private static final Log logger = LogFactory.getLog(PropertiesUtil.class);

    public PropertiesUtil(String propertiesFileName) {
        String filepath = "/" + propertiesFileName;
        InputStream in = ElasticSearchClient.class.getResourceAsStream(filepath);
        properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            logger.error("加载配置文件出错！", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("关闭IO流失败！");
            }
        }
    }

    /**
     * 获取某一个key的值
     *
     * @param propertyKey
     * @return
     */
    public String getPropertyValue(String propertyKey) {
        return properties.getProperty(propertyKey);
    }
}
