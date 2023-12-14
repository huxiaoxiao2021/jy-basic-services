package com.jdl.basic.provider.config.mybatis;

import com.jd.mt.core.ts.ITableFilter;
import com.jd.mt.core.ts.TableNameFilter;
import com.jd.mt.core.ts.TenantHandler;
import com.jd.mt.core.ts.jsqlparser.TenantSqlTransform;
import com.jd.mt.plugin.mybatis.TenantInterceptor;
import com.jdl.basic.api.enums.TenantEnum;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.sorting.tech.tenant.core.context.TenantContext;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>mybatis数据隔离-拦截器配置</p>
 *
 * @author zhongyicun
 */
@Configuration
public class MybatisConfig {

    private static final String TENANT_CODE_COLUMN = "tenant_code";

    @Bean
    public Interceptor createTenantInterceptor() {

        //添加需要数据隔离的表
        ITableFilter filter = new TableNameFilter()
                .addFilterTableName("work_station")
                .addFilterTableName("work_station_grid")
                .addFilterTableName("work_grid")
                .addFilterTableName("work_area");

        TenantInterceptor tenantInterceptor = new TenantInterceptor();

        TenantHandler<String> tenantHandler = new TenantHandler<String>() {
            @Override
            public String getTenantId(boolean where) {
                return StringUtils.isNotBlank(TenantContext.getTenantCode()) ?
                        TenantContext.getTenantCode() :
                        TenantEnum.TENANT_JY.getCode();
            }

            @Override
            public String getTenantIdColumn() {
                return TENANT_CODE_COLUMN;
            }

            @Override
            public String getAccountId() {
                return null;
            }

            @Override
            public String getAccountIdColumn() {
                return null;
            }

        };

        TenantSqlTransform transform = new TenantSqlTransform(tenantHandler);

        transform.setTableFilter(filter);

        tenantInterceptor.setTransform(transform);

        return tenantInterceptor;
    }


}