package com.jdl.basic.provider.core.service.transferDp.impl;

import com.alibaba.fastjson.JSON;
import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.dms.java.utils.sdk.constants.ResultCodeConstant;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.util.DateUtil;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.enums.ConfigStatusEnum;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteUpdateDto;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.core.dao.transferDp.ConfigTransferDpSiteDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.transferDp.ConfigTransferDpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-11-23 17:29:43 周三
 */
@Slf4j
@Service
public class ConfigTransferDpServiceImpl implements ConfigTransferDpService {

    @Resource
    private ConfigTransferDpSiteDao configTransferDpSiteDao;

    @Resource
    private BaseMajorManager baseMajorManager;

    /**
     * 统计个数
     *
     * @param configTransferDpSiteQo
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpServiceImpl.queryCount", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Long> queryCount(ConfigTransferDpSiteQo configTransferDpSiteQo) {
        log.info("ConfigTransferDpServiceImpl.queryCount param {}", JSON.toJSONString(configTransferDpSiteQo));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final long total = configTransferDpSiteDao.queryCount(configTransferDpSiteQo);
            result.setData(total);
        } catch (Exception e) {
            log.error("ConfigTransferDpServiceImpl.queryCount error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

    /**
     * 查询分页列表
     *
     * @param configTransferDpSiteQo
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpServiceImpl.queryPageList", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<PageData<ConfigTransferDpSiteVo>> queryPageList(ConfigTransferDpSiteQo configTransferDpSiteQo) {
        log.info("ConfigTransferDpServiceImpl.queryPageList param {}",JSON.toJSONString(configTransferDpSiteQo));
        final Result<PageData<ConfigTransferDpSiteVo>> result = Result.success();
        final PageData<ConfigTransferDpSiteVo> pageData = new PageData<>(configTransferDpSiteQo.getPageNumber(), configTransferDpSiteQo.getPageSize());
        result.setData(pageData);
        try {
            final Result<Void> checkResult = this.checkParam4QueryPage(configTransferDpSiteQo);
            if (!checkResult.isSuccess()) {
                log.warn("ConfigTransferDpServiceImpl.queryPageList checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSiteQo));
                return result.toFail(checkResult.getMessage(), checkResult.getCode());
            }
            if(StringUtils.isNotBlank(configTransferDpSiteQo.getEffectiveStartTimeStr())){
                configTransferDpSiteQo.setEffectiveStartTime(DateUtil.parse(configTransferDpSiteQo.getEffectiveStartTimeStr(), DateUtil.FORMAT_DATE_TIME));
            }
            if(StringUtils.isNotBlank(configTransferDpSiteQo.getEffectiveStopTimeStr())){
                configTransferDpSiteQo.setEffectiveStopTime(DateUtil.parse(configTransferDpSiteQo.getEffectiveStopTimeStr(), DateUtil.FORMAT_DATE_TIME));
            }
            final long total = configTransferDpSiteDao.queryCount(configTransferDpSiteQo);
            if(total > 0){
                pageData.setTotal(total);
                final List<ConfigTransferDpSite> configTransferDpSiteList = configTransferDpSiteDao.queryList(configTransferDpSiteQo);
                List<ConfigTransferDpSiteVo> configTransferDpSiteVoList = new ArrayList<>();
                for (ConfigTransferDpSite configTransferDpSite : configTransferDpSiteList) {
                    final ConfigTransferDpSiteVo configTransferDpSiteVo = new ConfigTransferDpSiteVo();
                    BeanUtils.copyProperties(configTransferDpSite, configTransferDpSiteVo);
                    configTransferDpSiteVo.setConfigStatusStr(ConfigStatusEnum.getEnumNameByCode(configTransferDpSite.getConfigStatus()));
                    configTransferDpSiteVoList.add(configTransferDpSiteVo);
                }
                pageData.setRecords(configTransferDpSiteVoList);
            }
        } catch (Exception e) {
            log.error("ConfigTransferDpServiceImpl.queryPageList error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

    private Result<Void> checkParam4QueryPage(ConfigTransferDpSiteQo configTransferDpSiteQo) {
        Result<Void> result = Result.success();
        if (configTransferDpSiteQo == null) {
            return result.toFail("参数错误，查询参数不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        return result;
    }

    /**
     * 添加一条记录
     *
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpServiceImpl.add", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Long> add(ConfigTransferDpSite configTransferDpSite) {
        log.info("ConfigTransferDpServiceImpl.add param {}", JSON.toJSONString(configTransferDpSite));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            final Result<Void> checkResult = this.checkParam4Add(configTransferDpSite);
            if (!checkResult.isSuccess()) {
                log.warn("ConfigTransferDpServiceImpl.add checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSite));
                return result.toFail(checkResult.getMessage(), checkResult.getCode());
            }

            final ConfigTransferDpSiteQo configTransferDpSiteQo = new ConfigTransferDpSiteQo();
            configTransferDpSiteQo.setPreSortSiteCode(configTransferDpSite.getPreSortSiteCode());
            // configTransferDpSiteQo.setEffectiveStartTime(configTransferDpSite.getEffectiveStartTime());
            // configTransferDpSiteQo.setEffectiveStopTime(configTransferDpSite.getEffectiveStopTime());
            final long existCount = configTransferDpSiteDao.queryCount(configTransferDpSiteQo);
            if (existCount > 0) {
                return result.toFail(String.format("已存在交接场地%s-%s、预分拣站点%s-%s的数据", configTransferDpSite.getHandoverSiteCode(), configTransferDpSite.getHandoverSiteName(), configTransferDpSite.getPreSortSiteCode(), configTransferDpSite.getHandoverSiteName()));
            }

            final BaseStaffSiteOrgDto handoverSiteInfo = baseMajorManager.getBaseSiteBySiteId(configTransferDpSite.getHandoverSiteCode());
            if (handoverSiteInfo == null) {
                return result.toFail(String.format("未找到交接场地ID为%s的数据", configTransferDpSite.getHandoverSiteCode()));
            }
            configTransferDpSite.setHandoverSiteName(handoverSiteInfo.getSiteName());
            configTransferDpSite.setHandoverOrgId(handoverSiteInfo.getOrgId());
            configTransferDpSite.setHandoverOrgName(handoverSiteInfo.getOrgName());


            final BaseStaffSiteOrgDto preSortSiteInfo = baseMajorManager.getBaseSiteBySiteId(configTransferDpSite.getPreSortSiteCode());
            if (preSortSiteInfo == null) {
                return result.toFail(String.format("未找到预分拣场地ID为%s的数据", configTransferDpSite.getPreSortSiteCode()));
            }
            configTransferDpSite.setPreSortSiteName(preSortSiteInfo.getSiteName());

            configTransferDpSite.setCreateTime(new Date());

            final int insertCount = configTransferDpSiteDao.insertSelective(configTransferDpSite);
            if(insertCount == 1){
                result.setData(configTransferDpSite.getId());
            }
        } catch (Exception e) {
            log.error("ConfigTransferDpServiceImpl.add error ", e);
            result.toFail("插入异常");
        }
        return result;
    }

    private Result<Void> checkParam4Add(ConfigTransferDpSite configTransferDpSite) {
        Result<Void> result = Result.success();
        if (configTransferDpSite == null) {
            return result.toFail("参数错误，参数不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (configTransferDpSite.getHandoverSiteCode() == null) {
            return result.toFail("参数错误，handoverSiteCode不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (configTransferDpSite.getPreSortSiteCode() == null) {
            return result.toFail("参数错误，preSortSiteCode不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (configTransferDpSite.getEffectiveStartTime() == null) {
            return result.toFail("参数错误，effectiveStartTime不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (configTransferDpSite.getEffectiveStopTime() == null) {
            return result.toFail("参数错误，effectiveStopTime数不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        return result;
    }

    private static int maxBatchInsertCount = 3000;

    /**
     * 批量添加记录
     *
     * @param configTransferDpSiteList 配置记录
     * @return 处理结果
     */
    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpServiceImpl.batchAdd", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Long> batchAdd(List<ConfigTransferDpSite> configTransferDpSiteList) {
        log.info("ConfigTransferDpServiceImpl.batchAdd param {}", JSON.toJSONString(configTransferDpSiteList));
        final Result<Long> result = Result.success();
        result.setData(0L);
        try {
            if(CollectionUtils.isEmpty(configTransferDpSiteList)){
                return result.toFail("参数错误，参数不能为空");
            }
            if(configTransferDpSiteList.size() > maxBatchInsertCount){
                return result.toFail(String.format("参数错误，一次最多插入%s条", maxBatchInsertCount));
            }
            for (ConfigTransferDpSite configTransferDpSite : configTransferDpSiteList) {
                final Result<Void> checkResult = this.checkParam4Add(configTransferDpSite);
                if (!checkResult.isSuccess()) {
                    log.warn("ConfigTransferDpServiceImpl.batchAdd checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSite));
                    return result.toFail(checkResult.getMessage(), checkResult.getCode());
                }

                final ConfigTransferDpSiteQo configTransferDpSiteQo = new ConfigTransferDpSiteQo();
                configTransferDpSiteQo.setPreSortSiteCode(configTransferDpSite.getPreSortSiteCode());
                // configTransferDpSiteQo.setEffectiveStartTime(configTransferDpSite.getEffectiveStartTime());
                // configTransferDpSiteQo.setEffectiveStopTime(configTransferDpSite.getEffectiveStopTime());
                final long existCount = configTransferDpSiteDao.queryCount(configTransferDpSiteQo);
                if (existCount > 0) {
                    return result.toFail(String.format("已存在交接场地%s-%s、预分拣站点%s-%s的数据", configTransferDpSite.getHandoverSiteCode(), configTransferDpSite.getHandoverSiteName(), configTransferDpSite.getPreSortSiteCode(), configTransferDpSite.getHandoverSiteName()));
                }

                final BaseStaffSiteOrgDto handoverSiteInfo = baseMajorManager.getBaseSiteBySiteId(configTransferDpSite.getHandoverSiteCode());
                if (handoverSiteInfo == null) {
                    return result.toFail(String.format("未找到交接场地ID为%s的数据", configTransferDpSite.getHandoverSiteCode()));
                }
                configTransferDpSite.setHandoverSiteName(handoverSiteInfo.getSiteName());
                configTransferDpSite.setHandoverOrgId(handoverSiteInfo.getOrgId());
                configTransferDpSite.setHandoverOrgName(handoverSiteInfo.getOrgName());


                final BaseStaffSiteOrgDto preSortSiteInfo = baseMajorManager.getBaseSiteBySiteId(configTransferDpSite.getPreSortSiteCode());
                if (preSortSiteInfo == null) {
                    return result.toFail(String.format("未找到预分拣场地ID为%s的数据", configTransferDpSite.getPreSortSiteCode()));
                }
                configTransferDpSite.setPreSortSiteName(preSortSiteInfo.getSiteName());

                configTransferDpSite.setCreateTime(new Date());
                configTransferDpSite.setUpdateUser("");
                configTransferDpSite.setUpdateUserName("");
                configTransferDpSite.setYn(Constants.YN_YES);
            }

            final long insertCount = configTransferDpSiteDao.batchInsert(configTransferDpSiteList);
            if(insertCount == configTransferDpSiteList.size()){
                result.setData(insertCount);
            }
        } catch (Exception e) {
            log.error("ConfigTransferDpServiceImpl.batchAdd error ", e);
            result.toFail("插入异常");
        }
        return result;
    }

    /**
     * 根据ID更新
     *
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpServiceImpl.updateById", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> updateById(ConfigTransferDpSite configTransferDpSite) {
        log.info("ConfigTransferDpServiceImpl.updateById param {}",JSON.toJSONString(configTransferDpSite));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            final Result<Void> checkResult = this.checkParam4UpdateById(configTransferDpSite);
            if (!checkResult.isSuccess()) {
                log.warn("ConfigTransferDpServiceImpl.updateById checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSite));
                return result.toFail(checkResult.getMessage(), checkResult.getCode());
            }
            if (configTransferDpSite.getHandoverSiteCode() != null) {
                final BaseStaffSiteOrgDto handoverSiteInfo = baseMajorManager.getBaseSiteBySiteId(configTransferDpSite.getHandoverSiteCode());
                if (handoverSiteInfo == null) {
                    return result.toFail(String.format("未找到交接场地ID为%s的数据", configTransferDpSite.getHandoverSiteCode()));
                }
                configTransferDpSite.setHandoverSiteName(handoverSiteInfo.getSiteName());
                configTransferDpSite.setHandoverOrgId(handoverSiteInfo.getOrgId());
                configTransferDpSite.setHandoverOrgName(handoverSiteInfo.getOrgName());
            }
            if (configTransferDpSite.getPreSortSiteCode() != null) {
                final BaseStaffSiteOrgDto preSortSiteInfo = baseMajorManager.getBaseSiteBySiteId(configTransferDpSite.getPreSortSiteCode());
                if (preSortSiteInfo == null) {
                    return result.toFail(String.format("未找到预分拣场地ID为%s的数据", configTransferDpSite.getPreSortSiteCode()));
                }
                configTransferDpSite.setPreSortSiteName(preSortSiteInfo.getSiteName());
            }
            final int total = configTransferDpSiteDao.updateByPrimaryKeySelective(configTransferDpSite);
            result.setData(total == 1);
        } catch (Exception e) {
            log.error("ConfigTransferDpServiceImpl.updateById error ", e);
            result.toFail("更新异常");
        }
        return result;
    }

    private Result<Void> checkParam4UpdateById(ConfigTransferDpSite configTransferDpSite) {
        Result<Void> result = Result.success();
        if (configTransferDpSite == null) {
            return result.toFail("参数错误，参数不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (configTransferDpSite.getId() == null) {
            return result.toFail("参数错误，id不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (StringUtils.isEmpty(configTransferDpSite.getUpdateUser())) {
            return result.toFail("参数错误，updateUser不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (StringUtils.isEmpty(configTransferDpSite.getUpdateUserName())) {
            return result.toFail("参数错误，updateUserName不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        return result;
    }

    /**
     * 根据ID逻辑删除
     *
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpServiceImpl.logicDeleteById", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> logicDeleteById(ConfigTransferDpSite configTransferDpSite) {
        log.info("ConfigTransferDpServiceImpl.logicDeleteById param {}",JSON.toJSONString(configTransferDpSite));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            final Result<Void> checkResult = this.checkParam4UpdateById(configTransferDpSite);
            if (!checkResult.isSuccess()) {
                log.warn("ConfigTransferDpServiceImpl.logicDeleteById checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSite));
                return result.toFail(checkResult.getMessage(), checkResult.getCode());
            }
            if (configTransferDpSite.getUpdateTime() == null) {
                configTransferDpSite.setUpdateTime(new Date());
            }
            final int updateCount = configTransferDpSiteDao.deleteByPrimaryKey(configTransferDpSite.getId());
            if(updateCount == 1) {
                result.setData(true);
            }
        } catch (Exception e) {
            log.error("ConfigTransferDpServiceImpl.logicDeleteById error ", e);
            result.toFail("更新异常");
        }
        return result;
    }
    /**
     * 根据ID逻辑删除
     *
     * @param configTransferDpSiteUpdateDto 配置记录
     * @return 处理结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpServiceImpl.batchDeleteById", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> batchDeleteById(ConfigTransferDpSiteUpdateDto configTransferDpSiteUpdateDto) {
        log.info("ConfigTransferDpServiceImpl.batchDeleteById param {}",JSON.toJSONString(configTransferDpSiteUpdateDto));
        final Result<Boolean> result = Result.success();
        result.setData(false);
        try {
            final Result<Void> checkResult = this.checkParam4batchDeleteById(configTransferDpSiteUpdateDto);
            if (!checkResult.isSuccess()) {
                log.warn("ConfigTransferDpServiceImpl.logicDeleteById checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSiteUpdateDto));
                return result.toFail(checkResult.getMessage(), checkResult.getCode());
            }
            if (configTransferDpSiteUpdateDto.getUpdateTime() == null) {
                configTransferDpSiteUpdateDto.setUpdateTime(new Date());
            }
            final long updateCount = configTransferDpSiteDao.batchDeleteByPrimaryKeys(configTransferDpSiteUpdateDto);
            if(updateCount > 1) {
                result.setData(true);
            }
        } catch (Exception e) {
            log.error("ConfigTransferDpServiceImpl.logicDeleteById error ", e);
            result.toFail("更新异常");
        }
        return result;
    }

    private Result<Void> checkParam4batchDeleteById(ConfigTransferDpSiteUpdateDto configTransferDpSiteUpdateDto) {
        Result<Void> result = Result.success();
        if (configTransferDpSiteUpdateDto == null) {
            return result.toFail("参数错误，参数不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (CollectionUtils.isEmpty(configTransferDpSiteUpdateDto.getIds())) {
            return result.toFail("参数错误，ids不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (StringUtils.isEmpty(configTransferDpSiteUpdateDto.getUpdateUser())) {
            return result.toFail("参数错误，updateUser不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (StringUtils.isEmpty(configTransferDpSiteUpdateDto.getUpdateUserName())) {
            return result.toFail("参数错误，updateUserName不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        return result;
    }
}
