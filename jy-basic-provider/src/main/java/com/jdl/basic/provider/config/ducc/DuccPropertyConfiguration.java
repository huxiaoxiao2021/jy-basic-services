package com.jdl.basic.provider.config.ducc;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

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
    
    /**
     * 场地班次计划省区切换开关
     */
    @Value("${province_switch_siteWaveSchedule:true}")
    private boolean siteWaveScheduleProvinceSwitch;

    /**
     * 场地出勤计划省区切换开关
     */
    @Value("${province_switch_siteAttendPlan:true}")
    private boolean siteAttendPlanProvinceSwitch;


    public boolean getSiteWaveScheduleProvinceSwitch() {
        return siteWaveScheduleProvinceSwitch;
    }

    public void setSiteWaveScheduleProvinceSwitch(boolean siteWaveScheduleProvinceSwitch) {
        this.siteWaveScheduleProvinceSwitch = siteWaveScheduleProvinceSwitch;
    }

    public boolean getSiteAttendPlanProvinceSwitch() {
        return siteAttendPlanProvinceSwitch;
    }

    public void setSiteAttendPlanProvinceSwitch(boolean siteAttendPlanProvinceSwitch) {
        this.siteAttendPlanProvinceSwitch = siteAttendPlanProvinceSwitch;
    }

    public boolean getSiteQueryDowngradeSwitch() {
        return siteQueryDowngradeSwitch;
    }

    public void setSiteQueryDowngradeSwitch(boolean siteQueryDowngradeSwitch) {
        this.siteQueryDowngradeSwitch = siteQueryDowngradeSwitch;
    }
}
