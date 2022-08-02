package com.jdl.basic.provider.config.jimdb;

import com.jd.etms.framework.utils.cache.keygenerator.ArgsMatchCacheKeyGenerator;
import com.jd.etms.framework.utils.cache.spring.CacheAspect;
import com.jd.jim.cli.Cluster;
import com.jd.jim.cli.ReloadableJimClientFactoryBean;
import com.jd.jim.cli.config.ClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: redis配置
 **/
@Configuration
public class JimdbConfig {

    /**
     * 云redis配置参数
     * @see "https://cf.jd.com/pages/viewpage.action?pageId=191573146"
     * */
    @Value("${jimdb.url}")
    private String jimUrl;
    @Value("${jimdb.cfg.timeout}")
    private Integer timeout;
    @Value("${jimdb.cfg.newTimeout}")
    private Integer newTimeout;
    @Value("${jimdb.cfg.poolConfig.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${jimdb.cfg.poolConfig.softMinEvictableIdleTimeMillis}")
    private Long softMinEvictableIdleTimeMillis;
    @Value("${jimdb.cfg.poolConfig.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;
    @Value("${jimdb.cfg.poolConfig.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${jimdb.cfg.poolConfig.minIdlePerKey}")
    private Integer minIdlePerKey;
    @Value("${jimdb.cfg.poolConfig.maxIdlePerKey}")
    private Integer maxIdlePerKey;
    @Value("${jimdb.cfg.poolConfig.maxTotalPerKey}")
    private Integer maxTotalPerKey;

    @Bean(name = "jimClient")
    public Cluster jimClient() throws Exception {
        ReloadableJimClientFactoryBean factoryBean = new ReloadableJimClientFactoryBean();
        factoryBean.setJimUrl(jimUrl);
        factoryBean.setClientConfig(clientConfig());
        return factoryBean.getClient();
    }

    @Bean(name = "jimClientConfig")
    public ClientConfig clientConfig() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setTimeout(timeout);
        clientConfig.setNewTimeout(newTimeout);
        clientConfig.setPoolConfig(poolConfig());
        return clientConfig;
    }

    @Bean(name = "poolConfig")
    public ClientConfig.PoolConfig poolConfig() {
        ClientConfig.PoolConfig poolConfig = new ClientConfig.PoolConfig();
        poolConfig.setLifo(true);
        poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        poolConfig.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        poolConfig.setMinIdlePerKey(minIdlePerKey);
        poolConfig.setMaxIdlePerKey(maxIdlePerKey);
        poolConfig.setMaxTotalPerKey(maxTotalPerKey);
        return poolConfig;
    }

    @Bean(name = "keyGenerator")
    public ArgsMatchCacheKeyGenerator keyGenerator() {
        ArgsMatchCacheKeyGenerator keyGenerator = new ArgsMatchCacheKeyGenerator();
        return keyGenerator;
    }

    @Bean(name = "cacheAspect")
    public CacheAspect cacheAspect() throws Exception {
        CacheAspect cacheAspect = new CacheAspect();
        cacheAspect.setCluster(jimClient());
        cacheAspect.setKeyGenerator(keyGenerator());
        return cacheAspect;
    }
}
