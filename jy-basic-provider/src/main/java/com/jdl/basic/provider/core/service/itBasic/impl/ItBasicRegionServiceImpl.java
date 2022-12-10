package com.jdl.basic.provider.core.service.itBasic.impl;

import com.alibaba.fastjson.JSON;
import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.itBasic.ItBasicRegion;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicRegionQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicRegionVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.itBasic.ItBasicRegionDao;
import com.jdl.basic.provider.core.service.itBasic.ItBasicRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * IT区域基础数据接口实现
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-01 17:13:39 周四
 */
@Slf4j
@Service
public class ItBasicRegionServiceImpl implements ItBasicRegionService {

    @Resource
    private ItBasicRegionDao itBasicRegionDao;

    /**
     * 统计区域个数
     *
     * @param itBasicRegionQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicRegionServiceImpl.queryCount", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Long> queryCount(ItBasicRegionQo itBasicRegionQo) {
        log.info("ItBasicRegionServiceImpl.queryCount param {}",JSON.toJSONString(itBasicRegionQo));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final long total = itBasicRegionDao.queryCount(itBasicRegionQo);
            result.setData(total);
        } catch (Exception e) {
            log.error("ItBasicRegionServiceImpl.queryCount error ", e);
            result.toFail("查询异常");
        }
        return result;
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
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicRegionServiceImpl.queryPageList", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<PageData<ItBasicRegionVo>> queryPageList(ItBasicRegionQo itBasicRegionQo) {
        log.info("ItBasicRegionServiceImpl.queryPageList param {}",JSON.toJSONString(itBasicRegionQo));
        final Result<PageData<ItBasicRegionVo>> result = Result.success();
        final PageData<ItBasicRegionVo> pageData = new PageData<>(itBasicRegionQo.getPageNumber(), itBasicRegionQo.getPageSize());
        result.setData(pageData);
        try {
            final long total = itBasicRegionDao.queryCount(itBasicRegionQo);
            if(total > 0){
                pageData.setTotal(total);
                final List<ItBasicRegion> itBasicRegions = itBasicRegionDao.queryList(itBasicRegionQo);
                List<ItBasicRegionVo> ItBasicRegionVos = new ArrayList<>();
                for (ItBasicRegion itBasicRegion : itBasicRegions) {
                    final ItBasicRegionVo ItBasicRegionVo = new ItBasicRegionVo();
                    BeanUtils.copyProperties(itBasicRegion, ItBasicRegionVo);
                    ItBasicRegionVos.add(ItBasicRegionVo);
                }
                // todo查询父级信息
                pageData.setRecords(ItBasicRegionVos);
            }
        } catch (Exception e) {
            log.error("ItBasicRegionServiceImpl.queryPageList error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

    /**
     * 添加一条设备记录
     *
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicRegionServiceImpl.add", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Long> add(ItBasicRegion itBasicRegion) {
        log.info("ItBasicRegionServiceImpl.add param {}",JSON.toJSONString(itBasicRegion));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final int insertCount = itBasicRegionDao.insertSelective(itBasicRegion);
            if(insertCount == 1){
                result.setData(itBasicRegion.getId());
            }
        } catch (Exception e) {
            log.error("ItBasicRegionServiceImpl.add error ", e);
            result.toFail("插入异常");
        }
        return result;
    }

    /**
     * 根据ID更新
     *
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicRegionServiceImpl.updateById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> updateById(ItBasicRegion itBasicRegion) {
        log.info("ItBasicRegionServiceImpl.updateById param {}",JSON.toJSONString(itBasicRegion));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            final int total = itBasicRegionDao.updateByPrimaryKeySelective(itBasicRegion);
            result.setData(total == 1);
        } catch (Exception e) {
            log.error("ItBasicRegionServiceImpl.updateById error ", e);
            result.toFail("更新异常");
        }
        return result;
    }

    /**
     * 根据ID逻辑删除
     *
     * @param itBasicRegion 设备记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicRegionServiceImpl.logicDeleteById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> logicDeleteById(ItBasicRegion itBasicRegion) {
        log.info("ItBasicRegionServiceImpl.logicDeleteById param {}",JSON.toJSONString(itBasicRegion));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            itBasicRegion.setYn(Constants.YN_NO);
            final int updateCount = itBasicRegionDao.updateByPrimaryKeySelective(itBasicRegion);
            if(updateCount == 1) {
                result.setData(true);
            }
        } catch (Exception e) {
            log.error("ItBasicRegionServiceImpl.logicDeleteById error ", e);
            result.toFail("更新异常");
        }
        return result;
    }
}
