package com.jdl.basic.provider.core.provider.jyJobType;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.jyJobType.JyJobType;
import com.jdl.basic.api.domain.jyJobType.JyJobTypeQuery;
import com.jdl.basic.api.service.jyJobType.JyJobTypeJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.jyJobType.JyJobTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengchong28
 * @description 拣运工种jsf服务实现
 * @date 2024/2/1
 */
@Slf4j
@Service("jyJobTypeJsfService")
public class JyJobTypeJsfServiceImpl implements JyJobTypeJsfService {

    @Autowired
    @Qualifier("jyJobTypeService")
    private JyJobTypeService jyJobTypeService;

    /**
     * 插入JyJobType数据
     *
     * @param insertData 要插入的JyJobType对象
     * @return 插入操作的结果，返回布尔值
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeJsfServiceImpl.insert", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> insert(JyJobType insertData) {
        return jyJobTypeService.insert(insertData);
    }

    /**
     * 根据ID更新工种类型信息
     *
     * @param updateData 待更新的工种类型数据
     * @return 更新是否成功的结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeJsfServiceImpl.updateById", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> updateById(JyJobType updateData) {
        return jyJobTypeService.updateById(updateData);
    }

    /**
     * 查询工种类型分页列表
     *
     * @param query 工种类型查询条件
     * @return 分页列表结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeJsfServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<PageDto<JyJobType>> queryPageList(JyJobTypeQuery query) {
        return jyJobTypeService.queryPageList(query);
    }

    /**
     * 根据条件查询工种类型列表
     *
     * @param query 查询条件
     * @return 任务类型列表
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeJsfServiceImpl.queryListByCondition", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public Result<List<JyJobType>> queryListByCondition(JyJobType query) {
        return jyJobTypeService.queryListByCondition(query);
    }

    /**
     * 查询所有职位类型列表
     * @return 职位类型列表
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeJsfServiceImpl.queryAllList", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public List<JyJobType> queryAllList() {
        return jyJobTypeService.queryALlList();
    }

    /**
     * 查询所有可用职位类型列表
     * @return 可用职位类型列表
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".JyJobTypeJsfServiceImpl.queryAllAvailableList", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public List<JyJobType> queryAllAvailableList() {
        return jyJobTypeService.queryAllAvailableList();
    }
}
