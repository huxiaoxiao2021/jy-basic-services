package com.jdl.basic.provider.config.es;

import com.jdl.basic.provider.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author yangfan23
 * @date 2016年2月29日
 */
public class ElasticSearchClient {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchClient.class);

    private TransportClient client;

    @SuppressWarnings("resource")
    private ElasticSearchClient() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");

        PropertiesUtil appPropertiesUtil = new PropertiesUtil("app.properties");

        String SecurityKey = "request.headers.Authorization";
        String SecurityUser= appPropertiesUtil.getPropertyValue("es.cluster.user");
        String SecurityPassword= appPropertiesUtil.getPropertyValue("es.cluster.password");

        Settings settings = Settings.builder()
                .put("cluster.name", appPropertiesUtil.getPropertyValue("es.cluster.name"))
                .put("client.transport.sniff", false)
                .put(SecurityKey, basicAuthHeaderValue(SecurityUser, SecurityPassword))
                .build();
        int tryTimes = 0;
        do {
            try{
				logger.error("newTransportClient {}", tryTimes);
                client = new PreBuiltTransportClient(settings);
                String[] ipAddressArray = splitIpAddress(appPropertiesUtil.getPropertyValue("es.cluster.ip"));
                for (int i = 0; i < ipAddressArray.length; i++) {
                	if(ipAddressArray[i].contains(":")){
                		String[] nodeAddressArray = splitNodesAddress(ipAddressArray[i]);
                		if(nodeAddressArray.length == 2){
                            client.addTransportAddress(new
                                    TransportAddress(InetAddress.getByName(nodeAddressArray[0]), Integer.parseInt(nodeAddressArray[1])));
                        }
                	}else{
                		client.addTransportAddress(new
                                TransportAddress(InetAddress.getByName(ipAddressArray[i]), Integer.parseInt(appPropertiesUtil.getPropertyValue("es.cluster.port"))));
                	}
                }
                if(tryTimes > 1){
                    logger.info("ElasticSearchClient tryTimes is {}",tryTimes);
                }
                break;
            }catch(Exception e){
                tryTimes++;
                try {
                    logger.error("the ElasticSearchClient exception is",e);
                    Thread.sleep(Long.parseLong(appPropertiesUtil.getPropertyValue("sleep.times")));
                }catch (InterruptedException e1){
                    logger.error("sleep Exception",e1);
                    Thread.currentThread().interrupt();
                }
            }
        }while(true);
    }

    public static Client getInstance() {
        return ElasticSearchClientHolder.instance.client;
    }

    private static class ElasticSearchClientHolder {
        private static ElasticSearchClient instance = new ElasticSearchClient();
    }

    private String[] splitIpAddress(String ipAddress) {
        String[] ipAddressArray = null;
        if (StringUtils.isNotEmpty(ipAddress)) {
            ipAddressArray = ipAddress.split(",");
        }
        return ipAddressArray;
    }
    
    private String[] splitNodesAddress(String nodeAddress){
        String[] nodeAddressArray = {};
        if(StringUtils.isNotEmpty(nodeAddress)){
        	nodeAddressArray = nodeAddress.split(":");
        }
        return nodeAddressArray;
    }

    /**
     * 基础的base64生成
     * @param username 用户名
     * @param passwd 密码
     * @return
     */
    public static String basicAuthHeaderValue(String username, String passwd) {
        CharBuffer chars = CharBuffer.allocate(username.length() + passwd.length() + 1);
        byte[] charBytes = null;
        try {
            chars.put(username).put(':').put(passwd.toCharArray());
            charBytes = toUtf8Bytes(chars.array());

            String basicToken = Base64.getEncoder().encodeToString(charBytes);
            return "Basic " + basicToken;
        } finally {
            Arrays.fill(chars.array(), (char) 0);
            if (charBytes != null) {
                Arrays.fill(charBytes, (byte) 0);
            }
        }
    }

    public static byte[] toUtf8Bytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
}
