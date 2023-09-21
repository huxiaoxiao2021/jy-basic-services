package com.jdl.basic.provider.config.ducc;

import org.springframework.beans.factory.annotation.Value;

/**
 * DUCC属性配置
 *
 * @author hujiping
 * @date 2021/3/19 11:12 上午
 */
public class DuccPropertyConfiguration {

    /**
     * 站点查询降级开关
     *  false：查基础资料
     *  true：查es
     */
    @Value("${siteQuery_downgrade_switch:false}")
    private boolean siteQueryDowngradeSwitch;

    @Value("${jyOrganizationCodeStr:}")
    private String jyOrganizationCodeStr;
    
    public boolean getSiteQueryDowngradeSwitch() {
        return siteQueryDowngradeSwitch;
    }

    public void setSiteQueryDowngradeSwitch(boolean siteQueryDowngradeSwitch) {
        this.siteQueryDowngradeSwitch = siteQueryDowngradeSwitch;
    }

    public String getJyOrganizationCodeStr() {
        return jyOrganizationCodeStr;
    }

    public void setJyOrganizationCodeStr(String jyOrganizationCodeStr) {
        this.jyOrganizationCodeStr = jyOrganizationCodeStr;
    }
}
