package com.jdl.basic.provider.core.service.device.impl;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.device.DeviceInfo;
import com.jdl.basic.api.dto.device.qo.DeviceInfoQo;
import com.jdl.basic.api.dto.device.vo.DeviceInfoVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.device.DeviceInfoDao;
import com.jdl.basic.provider.core.service.device.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息实现
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-04 16:51:28 周日
 */
@Slf4j
@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {

    @Resource
    private DeviceInfoDao deviceInfoDao;

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
        log.info("DeviceInfoServiceImpl.queryCount param {}", JSON.toJSONString(deviceInfoQo));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final long total = deviceInfoDao.queryCount(deviceInfoQo);
            result.setData(total);
        } catch (Exception e) {
            log.error("DeviceInfoServiceImpl.queryCount error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

    /**
     * 查询设备信息分页列表
     *
     * @param deviceInfoQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<PageData<DeviceInfoVo>> queryPageList(DeviceInfoQo deviceInfoQo) {
        log.info("DeviceInfoServiceImpl.queryPageList param {}", JSON.toJSONString(deviceInfoQo));
        final Result<PageData<DeviceInfoVo>> result = Result.success();
        final PageData<DeviceInfoVo> pageData = new PageData<>(deviceInfoQo.getPageNumber(), deviceInfoQo.getPageSize());
        result.setData(pageData);
        try {
            final long total = deviceInfoDao.queryCount(deviceInfoQo);
            if(total > 0){
                pageData.setTotal(total);
                final List<DeviceInfo> deviceInfos = deviceInfoDao.queryList(deviceInfoQo);
                List<DeviceInfoVo> deviceInfoVos = new ArrayList<>();
                for (DeviceInfo deviceInfo : deviceInfos) {
                    final DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
                    BeanUtils.copyProperties(deviceInfo, deviceInfoVo);
                    deviceInfoVos.add(deviceInfoVo);
                }
                pageData.setRecords(deviceInfoVos);
            }
        } catch (Exception e) {
            log.error("DeviceInfoServiceImpl.queryPageList error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

    /**
     * 添加一条设备记录
     *
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Long> add(DeviceInfo deviceInfo) {
        log.info("DeviceInfoServiceImpl.add param {}", JSON.toJSONString(deviceInfo));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final int insertCount = deviceInfoDao.insertSelective(deviceInfo);
            if(insertCount == 1){
                result.setData(deviceInfo.getId());
            }
        } catch (Exception e) {
            log.error("DeviceInfoServiceImpl.queryCount error ", e);
            result.toFail("插入异常");
        }
        return result;
    }

    /**
     * 根据ID更新
     *
     * @param deviceInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> updateById(DeviceInfo deviceInfo) {
        log.info("DeviceInfoServiceImpl.updateById param {}", JSON.toJSONString(deviceInfo));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            final int total = deviceInfoDao.updateByPrimaryKeySelective(deviceInfo);
            result.setData(total == 1);
        } catch (Exception e) {
            log.error("DeviceInfoServiceImpl.queryCount error ", e);
            result.toFail("更新异常");
        }
        return result;
    }

    /**
     * 根据ID逻辑删除
     *
     * @param deviceInfo 设备信息
     * @return 处理结果
     */
    @Override
    public Result<Boolean> logicDeleteById(DeviceInfo deviceInfo) {
        log.info("DeviceInfoServiceImpl.logicDeleteById param {}", JSON.toJSONString(deviceInfo));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            deviceInfo.setYn(Constants.YN_NO);
            final int updateCount = deviceInfoDao.updateByPrimaryKeySelective(deviceInfo);
            if(updateCount == 1) {
                result.setData(true);
            }
        } catch (Exception e) {
            log.error("DeviceInfoServiceImpl.queryCount error ", e);
            result.toFail("更新异常");
        }
        return result;
    }
}
