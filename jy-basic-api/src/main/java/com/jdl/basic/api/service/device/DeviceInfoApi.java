package com.jdl.basic.api.service.device;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.deivce.DeviceInfo;
import com.jdl.basic.api.dto.device.qo.DeviceInfoQo;
import com.jdl.basic.api.dto.device.vo.DeviceInfoVo;

/**
 * 设备信息接口
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-04 15:35:49 周日
 */
public interface DeviceInfoApi {

    /**
     * 查询设备信息
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<Long> queryCount(DeviceInfoQo deviceInfoQo);

    /**
     * 查询设备信息分页列表
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<PageData<DeviceInfoVo>> queryPageList(DeviceInfoQo deviceInfoQo);

    /**
     * 添加一条设备记录
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    Result<Long> add(DeviceInfo deviceInfo);

    /**
     * 根据ID更新
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    Result<Boolean> updateById(DeviceInfo deviceInfo);

    /**
     * 根据ID逻辑删除
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    Result<Boolean> logicDeleteById(DeviceInfo deviceInfo);
}
