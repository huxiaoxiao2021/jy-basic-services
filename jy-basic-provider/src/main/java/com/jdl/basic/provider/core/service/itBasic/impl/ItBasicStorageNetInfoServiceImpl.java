package com.jdl.basic.provider.core.service.itBasic.impl;

import com.alibaba.fastjson.JSON;
import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageNetInfo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageNetInfoQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicStorageNetInfoVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.itBasic.ItBasicStorageNetInfoDao;
import com.jdl.basic.provider.core.service.itBasic.ItBasicStorageNetInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * IT区域基础信息接口
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-11 21:00:09 周日
 */
@Slf4j
@Service
public class ItBasicStorageNetInfoServiceImpl implements ItBasicStorageNetInfoService {
    
    @Resource
    private ItBasicStorageNetInfoDao itBasicStorageNetInfoDao;

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
        log.info("ItBasicStorageNetInfoServiceImpl.queryCount param {}", JSON.toJSONString(itBasicStorageNetInfoQo));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final long total = itBasicStorageNetInfoDao.queryCount(itBasicStorageNetInfoQo);
            result.setData(total);
        } catch (Exception e) {
            log.error("ItBasicStorageNetInfoServiceImpl.queryCount error ", e);
            result.toFail("查询异常");
        }
        return result;
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
        log.info("ItBasicStorageNetInfoServiceImpl.queryPageList param {}",JSON.toJSONString(itBasicStorageNetInfoQo));
        final Result<PageData<ItBasicStorageNetInfoVo>> result = Result.success();
        final PageData<ItBasicStorageNetInfoVo> pageData = new PageData<>(itBasicStorageNetInfoQo.getPageNumber(), itBasicStorageNetInfoQo.getPageSize());
        result.setData(pageData);
        try {
            final long total = itBasicStorageNetInfoDao.queryCount(itBasicStorageNetInfoQo);
            if(total > 0){
                pageData.setTotal(total);
                final List<ItBasicStorageNetInfo> itBasicRegions = itBasicStorageNetInfoDao.queryList(itBasicStorageNetInfoQo);
                List<ItBasicStorageNetInfoVo> ItBasicRegionVos = new ArrayList<>();
                for (ItBasicStorageNetInfo itBasicStorageNetInfo : itBasicRegions) {
                    final ItBasicStorageNetInfoVo ItBasicStorageNetInfoVo = new ItBasicStorageNetInfoVo();
                    BeanUtils.copyProperties(itBasicStorageNetInfo, ItBasicStorageNetInfoVo);
                    ItBasicRegionVos.add(ItBasicStorageNetInfoVo);
                }
                // todo查询父级信息
                pageData.setRecords(ItBasicRegionVos);
            }
        } catch (Exception e) {
            log.error("ItBasicStorageNetInfoServiceImpl.queryPageList error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

    /**
     * 添加一条设备记录
     *
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Long> add(ItBasicStorageNetInfo itBasicStorageNetInfo) {
        log.info("ItBasicStorageNetInfoServiceImpl.add param {}",JSON.toJSONString(itBasicStorageNetInfo));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final int insertCount = itBasicStorageNetInfoDao.insertSelective(itBasicStorageNetInfo);
            if(insertCount == 1){
                result.setData(itBasicStorageNetInfo.getId());
            }
        } catch (Exception e) {
            log.error("ItBasicStorageNetInfoServiceImpl.queryCount error ", e);
            result.toFail("插入异常");
        }
        return result;
    }

    /**
     * 根据ID更新
     *
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> updateById(ItBasicStorageNetInfo itBasicStorageNetInfo) {
        log.info("ItBasicStorageNetInfoServiceImpl.updateById param {}",JSON.toJSONString(itBasicStorageNetInfo));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            final int total = itBasicStorageNetInfoDao.updateByPrimaryKeySelective(itBasicStorageNetInfo);
            result.setData(total == 1);
        } catch (Exception e) {
            log.error("ItBasicStorageNetInfoServiceImpl.updateById error ", e);
            result.toFail("更新异常");
        }
        return result;
    }

    /**
     * 根据ID逻辑删除
     *
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> logicDeleteById(ItBasicStorageNetInfo itBasicStorageNetInfo) {
        log.info("ItBasicStorageNetInfoServiceImpl.logicDeleteById param {}",JSON.toJSONString(itBasicStorageNetInfo));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            itBasicStorageNetInfo.setYn(Constants.YN_NO);
            final int updateCount = itBasicStorageNetInfoDao.updateByPrimaryKeySelective(itBasicStorageNetInfo);
            if(updateCount == 1) {
                result.setData(true);
            }
        } catch (Exception e) {
            log.error("ItBasicStorageNetInfoServiceImpl.queryCount error ", e);
            result.toFail("更新异常");
        }
        return result;
    }
}
