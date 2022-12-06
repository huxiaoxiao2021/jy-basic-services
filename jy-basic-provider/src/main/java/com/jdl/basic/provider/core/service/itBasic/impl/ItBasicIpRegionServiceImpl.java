package com.jdl.basic.provider.core.service.itBasic.impl;

import com.jd.dms.java.utils.core.common.IpUtils;
import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.fastjson.JSON;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.itBasic.ItBasicRegion;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageIpRegion;
import com.jdl.basic.api.dto.itBasic.dto.MatchIpRegionDto;
import com.jdl.basic.api.dto.itBasic.po.ItBasicIpRegionPo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageIpRegionQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicStorageIpRegionVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.itBasic.ItBasicRegionDao;
import com.jdl.basic.provider.core.dao.itBasic.ItBasicStorageIpRegionDao;
import com.jdl.basic.provider.core.service.itBasic.ItBasicIpRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * IP资产位置信息接口实现
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-01 17:13:39 周四
 */
@Slf4j
@Service
public class ItBasicIpRegionServiceImpl implements ItBasicIpRegionService {

    @Resource
    private ItBasicStorageIpRegionDao itBasicStorageIpRegionDao;

    @Resource
    private ItBasicRegionDao itBasicRegionDao;

    /**
     * 统计个数
     *
     * @param itBasicStorageIpRegionQo 查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicIpRegionServiceImpl.queryCount", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Long> queryCount(ItBasicStorageIpRegionQo itBasicStorageIpRegionQo) {
        log.info("ItBasicIpRegionServiceImpl.queryCount param {}", JSON.toJSONString(itBasicStorageIpRegionQo));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final long total = itBasicStorageIpRegionDao.queryCount(itBasicStorageIpRegionQo);
            result.setData(total);
        } catch (Exception e) {
            log.error("ItBasicIpRegionServiceImpl.queryCount error ", e);
            result.toFail("查询异常");
        }
        return result;
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
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicIpRegionServiceImpl.queryPageList", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<PageData<ItBasicStorageIpRegionVo>> queryPageList(ItBasicStorageIpRegionQo itBasicStorageIpRegionQo) {
        log.info("DeviceInfoServiceImpl.queryPageList param {}", JSON.toJSONString(itBasicStorageIpRegionQo));
        final Result<PageData<ItBasicStorageIpRegionVo>> result = Result.success();
        final PageData<ItBasicStorageIpRegionVo> pageData = new PageData<>(itBasicStorageIpRegionQo.getPageNumber(), itBasicStorageIpRegionQo.getPageSize());
        result.setData(pageData);
        try {
            final long total = itBasicStorageIpRegionDao.queryCount(itBasicStorageIpRegionQo);
            if(total > 0){
                pageData.setTotal(total);
                final List<ItBasicStorageIpRegion> itBasicStorageIpRegions = itBasicStorageIpRegionDao.queryList(itBasicStorageIpRegionQo);
                List<ItBasicStorageIpRegionVo> ItBasicRegionVos = new ArrayList<>();
                for (ItBasicStorageIpRegion itBasicStorageIpRegion : itBasicStorageIpRegions) {
                    final ItBasicStorageIpRegionVo ItBasicStorageIpRegionVo = new ItBasicStorageIpRegionVo();
                    BeanUtils.copyProperties(itBasicStorageIpRegion, ItBasicStorageIpRegionVo);
                    ItBasicRegionVos.add(ItBasicStorageIpRegionVo);
                }
                // todo查询父级信息
                pageData.setRecords(ItBasicRegionVos);
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
     * @param itBasicStorageIpRegion 设备记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicIpRegionServiceImpl.add", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Long> add(ItBasicStorageIpRegion itBasicStorageIpRegion) {
        log.info("ItBasicIpRegionServiceImpl.add param {}", JSON.toJSONString(itBasicStorageIpRegion));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final int insertCount = itBasicStorageIpRegionDao.insertSelective(itBasicStorageIpRegion);
            if(insertCount == 1){
                result.setData(itBasicStorageIpRegion.getId());
            }
        } catch (Exception e) {
            log.error("ItBasicIpRegionServiceImpl.queryCount error ", e);
            result.toFail("添加异常");
        }
        return result;
    }

    /**
     * 根据ID更新
     *
     * @param itBasicStorageIpRegion 设备记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicIpRegionServiceImpl.updateById", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> updateById(ItBasicStorageIpRegion itBasicStorageIpRegion) {
        log.info("ItBasicIpRegionServiceImpl.updateById param {}", JSON.toJSONString(itBasicStorageIpRegion));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            final int total = itBasicStorageIpRegionDao.updateByPrimaryKeySelective(itBasicStorageIpRegion);
            result.setData(total == 1);
        } catch (Exception e) {
            log.error("ItBasicIpRegionServiceImpl.queryCount error ", e);
            result.toFail("更新异常");
        }
        return result;
    }

    /**
     * 根据ID逻辑删除
     *
     * @param itBasicStorageIpRegion 设备记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicIpRegionServiceImpl.logicDeleteById", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> logicDeleteById(ItBasicStorageIpRegion itBasicStorageIpRegion) {
        log.info("ItBasicIpRegionServiceImpl.logicDeleteById param {}", JSON.toJSONString(itBasicStorageIpRegion));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            itBasicStorageIpRegion.setYn(Constants.YN_NO);
            final int updateCount = itBasicStorageIpRegionDao.updateByPrimaryKeySelective(itBasicStorageIpRegion);
            if(updateCount == 1) {
                result.setData(true);
            }
        } catch (Exception e) {
            log.error("ItBasicIpRegionServiceImpl.queryCount error ", e);
            result.toFail("更新异常");
        }
        return result;
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
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ItBasicIpRegionServiceImpl.selectMatchRegionByIp", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<MatchIpRegionDto> selectMatchRegionByIp(ItBasicIpRegionPo itBasicIpRegionPo) {
        log.info("ItBasicIpRegionServiceImpl.selectMatchRegionByIp param {}", JSON.toJSONString(itBasicIpRegionPo));
        final Result<MatchIpRegionDto> result = Result.success();
        try {
            final ItBasicStorageIpRegion itBasicStorageIpRegionExist = itBasicStorageIpRegionDao.selectMatchRegionByIp(itBasicIpRegionPo);
            if (itBasicStorageIpRegionExist == null) {
                return result;
            }
            // 转换成IP
            final MatchIpRegionDto matchIpRegionDto = new MatchIpRegionDto();
            matchIpRegionDto.setIp(itBasicIpRegionPo.getIp());
            matchIpRegionDto.setIpNumber(IpUtils.ipToLong(itBasicIpRegionPo.getIp()));
            matchIpRegionDto.setStartIp(IpUtils.longToIP(itBasicStorageIpRegionExist.getStartIp()));
            matchIpRegionDto.setEndIp(IpUtils.longToIP(itBasicStorageIpRegionExist.getStartIp()));
            matchIpRegionDto.setStorageId(itBasicStorageIpRegionExist.getStorageId());
            result.setData(matchIpRegionDto);
            // 遍历查询区域父级树
            final ItBasicRegion itBasicRegion = itBasicRegionDao.selectByPrimaryKey(itBasicStorageIpRegionExist.getStorageId());
            if(itBasicRegion == null){
                return result;
            }
            matchIpRegionDto.setStorageName(itBasicRegion.getRegionName());
            matchIpRegionDto.setCityId(itBasicRegion.getParentId());
            if (itBasicRegion.getParentId() != null) {
                final ItBasicRegion itBasicRegionLevel3 = itBasicRegionDao.selectByPrimaryKey(itBasicStorageIpRegionExist.getStorageId());
                if(itBasicRegionLevel3 == null){
                    return result;
                }
                matchIpRegionDto.setCityName(itBasicRegionLevel3.getRegionName());
                matchIpRegionDto.setProvinceId(itBasicRegionLevel3.getParentId());
                if (itBasicRegionLevel3.getParentId() != null) {
                    final ItBasicRegion itBasicRegionLevel2 = itBasicRegionDao.selectByPrimaryKey(itBasicStorageIpRegionExist.getStorageId());
                    if(itBasicRegionLevel2 == null){
                        return result;
                    }
                    matchIpRegionDto.setProvinceName(itBasicRegionLevel2.getRegionName());
                    matchIpRegionDto.setRegionId(itBasicRegionLevel2.getParentId());

                    if (itBasicRegionLevel2.getParentId() != null) {
                        final ItBasicRegion itBasicRegionLevel1 = itBasicRegionDao.selectByPrimaryKey(itBasicStorageIpRegionExist.getStorageId());
                        if(itBasicRegionLevel1 == null){
                            return result;
                        }
                        matchIpRegionDto.setRegionName(itBasicRegionLevel1.getRegionName());
                    }
                }
            }
        } catch (Exception e) {
            log.error("ItBasicIpRegionServiceImpl.selectMatchRegionByIp error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

}
