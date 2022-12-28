package com.jdl.basic.provider.core.provider.itBasic;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageIpRegion;
import com.jdl.basic.api.dto.itBasic.dto.MatchIpRegionDto;
import com.jdl.basic.api.dto.itBasic.po.ItBasicIpRegionPo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageIpRegionQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicStorageIpRegionVo;
import com.jdl.basic.api.service.itBasic.ItBasicIpRegionApi;
import com.jdl.basic.provider.core.service.itBasic.ItBasicIpRegionService;
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
public class ItBasicIpRegionApiImpl implements ItBasicIpRegionApi {

    @Resource
    private ItBasicIpRegionService itBasicIpRegionService;

    /**
     * 统计个数
     *
     * @param itBasicStorageIpRegionQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<Long> queryCount(ItBasicStorageIpRegionQo itBasicStorageIpRegionQo) {
        return itBasicIpRegionService.queryCount(itBasicStorageIpRegionQo);
    }

    /**
     * 查询IP地址区域信息分页列表
     *
     * @param itBasicStorageIpRegionQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<PageData<ItBasicStorageIpRegionVo>> queryPageList(ItBasicStorageIpRegionQo itBasicStorageIpRegionQo) {
        return itBasicIpRegionService.queryPageList(itBasicStorageIpRegionQo);
    }

    /**
     * 添加一条设备记录
     *
     * @param itBasicStorageIpRegion 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Long> add(ItBasicStorageIpRegion itBasicStorageIpRegion) {
        return itBasicIpRegionService.add(itBasicStorageIpRegion);
    }

    /**
     * 根据ID更新
     *
     * @param itBasicStorageIpRegion 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> updateById(ItBasicStorageIpRegion itBasicStorageIpRegion) {
        return itBasicIpRegionService.updateById(itBasicStorageIpRegion);
    }

    /**
     * 根据ID逻辑删除
     *
     * @param itBasicStorageIpRegion 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> logicDeleteById(ItBasicStorageIpRegion itBasicStorageIpRegion) {
        return itBasicIpRegionService.logicDeleteById(itBasicStorageIpRegion);
    }

    /**
     * 根据IP查询匹配的IP区段对应园区信息
     *
     * @param itBasicIpRegionPo 查询参数
     * @return 匹配的IP对应园区信息
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<MatchIpRegionDto> selectMatchRegionByIp(ItBasicIpRegionPo itBasicIpRegionPo) {
        return itBasicIpRegionService.selectMatchRegionByIp(itBasicIpRegionPo);
    }
}
