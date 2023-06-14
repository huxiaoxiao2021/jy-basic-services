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
     * 场地班次计划省区切换开关
     */
    @Value("${siteQuery_downgrade_switch:false}")
    private boolean siteQueryDowngradeSwitch;

    public boolean getSiteQueryDowngradeSwitch() {
        return siteQueryDowngradeSwitch;
    }

    public void setSiteQueryDowngradeSwitch(boolean siteQueryDowngradeSwitch) {
        this.siteQueryDowngradeSwitch = siteQueryDowngradeSwitch;
    }
}
