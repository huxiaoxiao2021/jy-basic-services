package com.jdl.basic.provider.config.ducc;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
    
	/**
	 * 需要按作业区校验流向的列表
	 */
	@Value("${ducc.workAreaCodesForFlowCheck:[FJZCQ]}")
	private List<String> workAreaCodesForFlowCheck;  

    public boolean getSiteQueryDowngradeSwitch() {
        return siteQueryDowngradeSwitch;
    }

    public void setSiteQueryDowngradeSwitch(boolean siteQueryDowngradeSwitch) {
        this.siteQueryDowngradeSwitch = siteQueryDowngradeSwitch;
    }

	public List<String> getWorkAreaCodesForFlowCheck() {
		return workAreaCodesForFlowCheck;
	}

	public void setWorkAreaCodesForFlowCheck(List<String> workAreaCodesForFlowCheck) {
		this.workAreaCodesForFlowCheck = workAreaCodesForFlowCheck;
	}
	public boolean needAreaCodesForFlowCheck(String areaCode) {
		if(CollectionUtils.isNotEmpty(workAreaCodesForFlowCheck) && areaCode != null) {
			return workAreaCodesForFlowCheck.contains(areaCode);
		}
		return false;
	}
}
