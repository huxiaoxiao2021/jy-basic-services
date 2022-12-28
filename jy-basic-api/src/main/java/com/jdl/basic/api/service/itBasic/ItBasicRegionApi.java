package com.jdl.basic.api.service.itBasic;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.itBasic.ItBasicRegion;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicRegionQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicRegionVo;

/**
 * IT区域基础信息接口
 *
 * @copyright jd.com 京东物流JDL
 * @author fanggang7
 * @time 2022-12-04 10:40:36 周日
 */
public interface ItBasicRegionApi {

    /**
     * 统计区域个数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<Long> queryCount(ItBasicRegionQo itBasicRegionQo);

    /**
     * 查询区域分页列表
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<PageData<ItBasicRegionVo>> queryPageList(ItBasicRegionQo itBasicRegionQo);

    /**
     * 添加一条设备记录
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    Result<Long> add(ItBasicRegion itBasicRegion);

    /**
     * 根据ID更新
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    Result<Boolean> updateById(ItBasicRegion itBasicRegion);

    /**
     * 根据ID逻辑删除
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    Result<Boolean> logicDeleteById(ItBasicRegion itBasicRegion);

}
