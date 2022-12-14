package com.jdl.basic.provider.core.provider.itBasic;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.itBasic.ItBasicRegion;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicRegionQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicRegionVo;
import com.jdl.basic.api.service.itBasic.ItBasicRegionApi;
import com.jdl.basic.provider.core.service.itBasic.ItBasicRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * IT区域基础信息接口
 *
 * @copyright jd.com 京东物流JDL
 * @author fanggang7
 * @time 2022-12-04 10:40:36 周日
 */
@Slf4j
@Service
public class ItBasicRegionApiImpl implements ItBasicRegionApi {

    @Resource
    private ItBasicRegionService itBasicRegionService;

    /**
     * 统计区域个数
     *
     * @param itBasicRegionQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<Long> queryCount(ItBasicRegionQo itBasicRegionQo) {
        return itBasicRegionService.queryCount(itBasicRegionQo);
    }

    /**
     * 查询区域分页列表
     *
     * @param itBasicRegionQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<PageData<ItBasicRegionVo>> queryPageList(ItBasicRegionQo itBasicRegionQo) {
        return itBasicRegionService.queryPageList(itBasicRegionQo);
    }

    /**
     * 添加一条设备记录
     *
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Long> add(ItBasicRegion itBasicRegion) {
        return itBasicRegionService.add(itBasicRegion);
    }

    /**
     * 根据ID更新
     *
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> updateById(ItBasicRegion itBasicRegion) {
        return itBasicRegionService.updateById(itBasicRegion);
    }

    /**
     * 根据ID逻辑删除
     *
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> logicDeleteById(ItBasicRegion itBasicRegion) {
        return itBasicRegionService.logicDeleteById(itBasicRegion);
    }
}
