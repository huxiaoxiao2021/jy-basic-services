package com.jdl.basic.provider.config.es;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.FactoryBean;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xumigen
 * @date : 2019/5/15
 */
public class TransportClientFactoryBean implements FactoryBean<TransportClient> {

    private String clusterName;
    private String ips;
    private int port;
    private boolean transportSniff = false;
    private String clusterUser;
    private String clusterPassword;

    @Override
    public TransportClient getObject() throws Exception {
        // 解决netty-transport版本冲突
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        return this.getTransportClientObject();
    }

    @Override
    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public TransportClient getTransportClientObject() throws Exception {
        Settings settings = this.getTransportClientSettings();
        TransportClient client = new PreBuiltTransportClient(settings);
        return this.addTransportAddress(client, this.ips);
    }

    private TransportClient addTransportAddress(TransportClient client, String address) throws Exception {
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("ES服务器地址不能为空！");
        }
        List<NodeAddress> addsArr = splitIpAddress(address);
        for (NodeAddress nodeAddress : addsArr) {
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(nodeAddress.getIp()), nodeAddress.port));
        }
        return client;
    }

    protected Settings getTransportClientSettings() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", transportSniff)
                .put("request.headers.Authorization", ElasticSearchClient.basicAuthHeaderValue(clusterUser, clusterPassword))
                .build();
        return settings;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private List<NodeAddress> splitIpAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("ES address 不能为空");
        }
        String[] addressArry = address.split(",");
        List<NodeAddress> ipAddressArray = new ArrayList<>();
        for(String item: addressArry){
            String[] ipPort = item.split(":");
            if(ipPort.length == 2){
                ipAddressArray.add(new NodeAddress(ipPort[0],Integer.parseInt(ipPort[1])));
            }else{
                ipAddressArray.add(new NodeAddress(ipPort[0],port));
            }

        }
        return ipAddressArray;
    }

    private static class NodeAddress{
        private final String ip;
        private final int port;

        public NodeAddress( String ip,int port) {
            this.ip = ip;
            this.port = port;
        }

        public int getPort() {
            return port;
        }

        public String getIp() {
            return ip;
        }
    }

    public void setTransportSniff(boolean transportSniff) {
        this.transportSniff = transportSniff;
    }

    public void setClusterUser(String clusterUser) {
        this.clusterUser = clusterUser;
    }

    public void setClusterPassword(String clusterPassword) {
        this.clusterPassword = clusterPassword;
    }
}
