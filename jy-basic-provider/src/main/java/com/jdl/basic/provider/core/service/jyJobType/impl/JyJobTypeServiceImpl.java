package com.jdl.basic.provider.core.service.jyJobType.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.jyJobType.JyJobType;
import com.jdl.basic.api.domain.jyJobType.JyJobTypeQuery;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.jyJobType.JyJobTypeDao;
import com.jdl.basic.provider.core.service.jyJobType.JyJobTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author pengchong28
 * @description 拣运工种服务实现
 * @date 2024/2/1
 */
@Slf4j
@Service("jyJobTypeService")
public class JyJobTypeServiceImpl implements JyJobTypeService {

    @Autowired
    @Qualifier("jyJobTypeDao")
    private JyJobTypeDao jyJobTypeDao;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeServiceImpl.insert", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> insert(JyJobType insertData) {
        Result<Boolean> result = Result.success();
        result.toFail();
        result.setData(Boolean.FALSE);
        Result<Boolean> booleanResult = checkParams(insertData, result);
        if (booleanResult != null) {
            return booleanResult;
        }

        try {
            int insert = jyJobTypeDao.insert(insertData);
            if (insert == 1) {
                result.setData(Boolean.TRUE);
                result.toSuccess();
            }
        } catch (Exception e) {
            result.setMessage("新增拣运工种异常！");
            log.error("JyJobTypeServiceImpl.insert新增拣运工种异常,异常信息:【{}】", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 根据ID更新数据
     *
     * @param updateData 要更新的数据
     * @return 更新操作结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeServiceImpl.updateById", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> updateById(JyJobType updateData) {
        Result<Boolean> result = Result.success();
        result.toFail();
        result.setData(Boolean.FALSE);
        Result<Boolean> booleanResult = checkUpdateParams(updateData, result);
        if (booleanResult != null) {
            return booleanResult;
        }

        try {
            int insert = jyJobTypeDao.updateById(updateData);
            if (insert == 1) {
                result.setData(Boolean.TRUE);
                result.toSuccess();
            }
        } catch (Exception e) {
            result.setMessage("更新拣运工种异常！");
            log.error("JyJobTypeServiceImpl.updateById更新拣运工种异常,异常信息:【{}】", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询分页列表
     *
     * @param query 查询条件
     * @return 分页列表结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<PageDto<JyJobType>> queryPageList(JyJobTypeQuery query) {
        Result<PageDto<JyJobType>> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
        if (!checkResult.isSuccess()) {
            return Result.fail(checkResult.getMessage());
        }
        PageDto<JyJobType> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
        long totalCount = jyJobTypeDao.queryCount(query);
        List<JyJobType> jyJobTypes = null;
        if (totalCount > 0) {
            jyJobTypes = jyJobTypeDao.queryList(query);
        }
        pageDto.setResult(jyJobTypes);
        pageDto.setTotalRow((int)totalCount);
        result.setData(pageDto);
        return result;
    }

    /**
     * 根据条件查询职位类型列表
     *
     * @param query 查询条件
     * @return 职位类型列表
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeServiceImpl.queryListByCondition", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<List<JyJobType>> queryListByCondition(JyJobType query) {
        Result<List<JyJobType>> result = Result.success();
        result.toFail();
        result.setData(null);
        try {
            List<JyJobType> jyJobTypes = jyJobTypeDao.queryListByCondition(query);
            result.setData(jyJobTypes);
            result.toSuccess();
        } catch (Exception e) {
            result.setMessage("查询拣运工种异常！");
            log.error("JyJobTypeServiceImpl.queryListByCondition查询拣运工种异常,异常信息:【{}】", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询所有状态启用的拣运工种列表
     * @return 返回拣运工种列表
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeServiceImpl.queryALlList", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public List<JyJobType> queryALlList() {
        List<JyJobType> result = new ArrayList<>();
        try {
            result = jyJobTypeDao.queryALlList();
        } catch (Exception e) {
            log.error("JyJobTypeServiceImpl.queryALlList查询拣运工种异常,异常信息:【{}】", e.getMessage(), e);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeServiceImpl.queryAllAvailableList", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public List<JyJobType> queryAllAvailableList() {
        List<JyJobType> result = new ArrayList<>();
        try {
            result = jyJobTypeDao.queryAllAvailableList();
        } catch (Exception e) {
            log.error("JyJobTypeServiceImpl.queryAllAvailableList查询拣运工种异常,异常信息:【{}】", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 对插入数据进行参数校验
     *
     * @param insertData 待插入的拣运工种对象
     * @param result     结果对象
     * @return 参数校验结果
     * @throws NullPointerException 如果insertData为空，则抛出空指针异常
     */
    private Result<Boolean> checkParams(JyJobType insertData, Result<Boolean> result) {
        // 参数校验
        if (Objects.isNull(insertData)) {
            result.setMessage("拣运工种对象不能为空!");
            return result;
        }
        if (StringUtils.isBlank(insertData.getName())) {
            result.setMessage("拣运工种类型不能为空!");
            return result;
        }
        if (Objects.isNull(insertData.getCode())) {
            result.setMessage("拣运工种编码不能为空!");
            return result;
        }

        // 工种类型和工种编码重复校验
        JyJobType jyJobType = new JyJobType();
        jyJobType.setName(insertData.getName());
        List<JyJobType> jyJobTypes = jyJobTypeDao.queryListByCondition(jyJobType);
        if (CollectionUtils.isNotEmpty(jyJobTypes)) {
            result.setMessage("拣运工种类型已存在，请勿重复添加!");
            return result;
        }
        JyJobType jyJobType1 = new JyJobType();
        jyJobType1.setCode(insertData.getCode());
        List<JyJobType> jyJobTypes1 = jyJobTypeDao.queryListByCondition(jyJobType1);
        if (CollectionUtils.isNotEmpty(jyJobTypes1)) {
            result.setMessage("拣运工种编码已存在，请勿重复添加!");
            return result;
        }
        return null;
    }

    /**
     * 校验更新参数
     *
     * @param updateData 待更新的拣运工种对象
     * @param result     结果对象
     * @return 结果对象，包含校验信息
     */
    private Result<Boolean> checkUpdateParams(JyJobType updateData, Result<Boolean> result) {
        // 参数校验
        if (Objects.isNull(updateData)) {
            result.setMessage("拣运工种对象不能为空!");
            return result;
        }
        if (Objects.isNull(updateData.getId())) {
            result.setMessage("拣运工种ID不能为空!");
            return result;
        }
        if (StringUtils.isBlank(updateData.getName())) {
            result.setMessage("拣运工种类型不能为空!");
            return result;
        }
        if (Objects.isNull(updateData.getStatus())) {
            result.setMessage("拣运工种状态不能为空!");
            return result;
        }
        if (StringUtils.isBlank(updateData.getBelong())) {
            result.setMessage("拣运工种所属不能为空!");
            return result;
        }
        if (Objects.isNull(updateData.getPhotoSupport())) {
            result.setMessage("拣运工种是否支持拍照签到不能为空!");
            return result;
        }
        if (Objects.isNull(updateData.getAutoSignOutHour())) {
            result.setMessage("拣运工种自动签退时间不能为空!");
            return result;
        }

        // 工种类型和工种编码重复校验
        JyJobType jyJobType = new JyJobType();
        jyJobType.setId(updateData.getId());
        jyJobType.setName(updateData.getName());
        List<JyJobType> jyJobTypes = jyJobTypeDao.queryListByCondition(jyJobType);
        if (CollectionUtils.isNotEmpty(jyJobTypes)) {
            result.setMessage("拣运工种类型已存在，请勿重复添加!");
            return result;
        }
        JyJobType jyJobType1 = new JyJobType();
        jyJobType1.setId(updateData.getId());
        jyJobType1.setCode(updateData.getCode());
        List<JyJobType> jyJobTypes1 = jyJobTypeDao.queryListByCondition(jyJobType1);
        if (CollectionUtils.isNotEmpty(jyJobTypes1)) {
            result.setMessage("拣运工种编码已存在，请勿重复添加!");
            return result;
        }
        return null;
    }

    /**
     * 检查用于查询页面列表的参数
     *
     * @param query 查询参数
     * @return 返回一个布尔类型的结果
     */
    public Result<Boolean> checkParamForQueryPageList(JyJobTypeQuery query) {
        Result<Boolean> result = Result.success();
        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        }
        query.setOffset(0);
        query.setLimit(query.getPageSize());
        if (query.getPageSize() == null || query.getPageNumber() > 0) {
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        }
        return result;
    }
}
