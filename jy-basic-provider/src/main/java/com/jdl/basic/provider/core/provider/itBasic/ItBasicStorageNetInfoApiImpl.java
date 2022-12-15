package com.jdl.basic.provider.core.provider.itBasic;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageNetInfo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageNetInfoQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicStorageNetInfoVo;
import com.jdl.basic.api.service.itBasic.ItBasicStorageNetInfoApi;
import com.jdl.basic.provider.core.service.itBasic.ItBasicStorageNetInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * IT区域基础信息接口
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-11 20:58:59 周日
 */
@Slf4j
@Service
public class ItBasicStorageNetInfoApiImpl implements ItBasicStorageNetInfoApi {

    @Resource
    private ItBasicStorageNetInfoService itBasicStorageNetInfoService;

    /**
     * 统计个数
     *
     * @param itBasicStorageNetInfoQo
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<Long> queryCount(ItBasicStorageNetInfoQo itBasicStorageNetInfoQo) {
        return itBasicStorageNetInfoService.queryCount(itBasicStorageNetInfoQo);
    }

    /**
     * 查询IP地址区域信息分页列表
     *
     * @param itBasicStorageNetInfoQo
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<PageData<ItBasicStorageNetInfoVo>> queryPageList(ItBasicStorageNetInfoQo itBasicStorageNetInfoQo) {
        return itBasicStorageNetInfoService.queryPageList(itBasicStorageNetInfoQo);
    }

    /**
     * 添加一条设备记录
     *
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Long> add(ItBasicStorageNetInfo itBasicStorageNetInfo) {
        return itBasicStorageNetInfoService.add(itBasicStorageNetInfo);
    }

    /**
     * 根据ID更新
     *
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> updateById(ItBasicStorageNetInfo itBasicStorageNetInfo) {
        return itBasicStorageNetInfoService.updateById(itBasicStorageNetInfo);
    }

    /**
     * 根据ID逻辑删除
     *
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> logicDeleteById(ItBasicStorageNetInfo itBasicStorageNetInfo) {
        return itBasicStorageNetInfoService.logicDeleteById(itBasicStorageNetInfo);
    }
}
