package com.jdl.basic.api.service;


import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.ommon.utils.PageDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 16:20
 * @Description:
 */
public interface BoxlimitConfigApi {

    JDResponse insertBoxlimitConfig(BoxLimitConfigDto po);

    /**
     * 获取集箱包裹限制配置列表
     * @param dto
     * @return
     */
    JDResponse<PageDto<BoxLimitConfigDto>> listData(BoxLimitConfigQueryDto dto);

    /**
     * 根据站点获取站点名称
     * @param siteId
     * @return
     */
    JDResponse<String> getSiteNameById(Integer siteId);

    /**
     * 新增或修改集箱包裹限制配置
     * @param dto
     * @return
     */
    JDResponse saveOrUpdate(BoxLimitConfigDto dto, LoginUser loginUser);

    /**
     * 根据id删除集箱包裹限制配置信息
     * @param ids
     * @return
     */
    JDResponse delete(ArrayList<Long> ids, LoginUser loginUser);

    /**
     * 按站点集箱包裹限制配置导入
     * @param dataList
     * @return
     */
    JDResponse toImport(List<BoxLimitConfigDto> dataList, LoginUser loginUser);

    /**
     * 查询符合条件的数据条数
     * @param dto
     * @return
     */
    JDResponse<Integer> countByCondition(BoxLimitConfigQueryDto dto);

    /**
     * 获取箱号类型集合
     * @return
     */
    JDResponse<List<String>> getBoxTypeList();

    /**
     * 获取站点指定类型配置的集箱包裹数量限制值
     * @param createSiteCode
     * @param type
     * @return
     */
    JDResponse<Integer> getLimitNums( Integer createSiteCode, String type);

}
