package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.dto.BaseStoreInfoDto;
import com.jd.ql.basic.dto.PageDto;

import java.util.List;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2022/10/8 11:16 AM
 */
public interface BaseMajorManager {

    BaseStaffSiteOrgDto getBaseStaffByErp(String erp);
    /**
     * 根据场地ID查询场地信息
     *
     * @param siteId
     * @return
     */
    BaseStaffSiteOrgDto getBaseSiteBySiteId(Integer siteId);

    /**
     * 分页查询站点
     *
     * @param subType 站点(分拣)子类型，可不传，不传则获取所有站点ID
     * @param pageIndex
     * @return
     */
    PageDto<List<Integer>> getBaseSiteCodeNoYnByPage(Integer subType, Integer pageIndex);

    /**
     * 分页获取库房
     *
     * @param pageIndex
     * @return
     */
    PageDto<List<BaseStoreInfoDto>> getBaseStoreInfoByPage(Integer pageIndex);

    public abstract BaseStaffSiteOrgDto getBaseSiteByDmsCode(String siteCode);
}
