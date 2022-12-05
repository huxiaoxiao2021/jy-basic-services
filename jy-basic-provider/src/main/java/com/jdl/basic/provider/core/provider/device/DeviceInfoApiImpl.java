package com.jdl.basic.provider.core.provider.device;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.device.DeviceInfo;
import com.jdl.basic.api.dto.device.qo.DeviceInfoQo;
import com.jdl.basic.api.dto.device.vo.DeviceInfoVo;
import com.jdl.basic.api.service.device.DeviceInfoApi;
import com.jdl.basic.provider.core.service.device.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设备信息接口
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-04 15:35:49 周日
 */
@Slf4j
@Service
public class DeviceInfoApiImpl implements DeviceInfoApi {

    @Resource
    private DeviceInfoService deviceInfoService;


    /**
     * 查询设备信息
     *
     * @param deviceInfoQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<Long> queryCount(DeviceInfoQo deviceInfoQo) {
        return deviceInfoService.queryCount(deviceInfoQo);
    }

    /**
     * 查询设备信息分页列表
     *
     * @param deviceInfoQo deviceInfoQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<PageData<DeviceInfoVo>> queryPageList(DeviceInfoQo deviceInfoQo) {
        return deviceInfoService.queryPageList(deviceInfoQo);
    }

    /**
     * 添加一条设备记录
     *
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Long> add(DeviceInfo deviceInfo) {
        return deviceInfoService.add(deviceInfo);
    }

    /**
     * 根据ID更新
     *
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> updateById(DeviceInfo deviceInfo) {
        return deviceInfoService.updateById(deviceInfo);
    }

    /**
     * 根据ID逻辑删除
     *
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> logicDeleteById(DeviceInfo deviceInfo) {
        return deviceInfoService.logicDeleteById(deviceInfo);
    }
}
