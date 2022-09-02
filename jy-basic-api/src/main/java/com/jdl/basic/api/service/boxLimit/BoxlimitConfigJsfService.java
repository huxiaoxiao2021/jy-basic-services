package com.jdl.basic.api.service.boxLimit;


import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 16:20
 * @Description:
 */
public interface BoxlimitConfigJsfService {

    Result insertBoxlimitConfig(BoxLimitConfigDto po);

    /**
     * 获取集箱包裹限制配置列表
     * @param dto
     * @return
     */
    Result<PageDto<BoxLimitConfigDto>> listData(BoxLimitConfigQueryDto dto);

    /**
     * 根据站点获取站点名称
     * @param siteId
     * @return
     */
    Result<String> getSiteNameById(Integer siteId);

    /**
     * 新增或修改集箱包裹限制配置
     * @param dto
     * @return
     */
    Result saveOrUpdate(BoxLimitConfigDto dto, LoginUser loginUser);

    /**
     * 根据id删除集箱包裹限制配置信息
     * @param ids
     * @return
     */
    Result delete(ArrayList<Long> ids, LoginUser loginUser);

    /**
     * 按站点集箱包裹限制配置导入
     * @param dataList
     * @return
     */
    Result toImport(List<BoxLimitConfigDto> dataList, LoginUser loginUser);

    /**
     * 查询符合条件的数据条数
     * @param dto
     * @return
     */
    Result<Integer> countByCondition(BoxLimitConfigQueryDto dto);

    /**
     * 获取箱号类型集合
     * @return
     */
    Result<List<String>> getBoxTypeList();

    /**
     * 获取站点指定类型配置的集箱包裹数量限制值
     * @param createSiteCode
     * @param type
     * @return
     */
    Result<Integer> getLimitNums( Integer createSiteCode, String type);

}
