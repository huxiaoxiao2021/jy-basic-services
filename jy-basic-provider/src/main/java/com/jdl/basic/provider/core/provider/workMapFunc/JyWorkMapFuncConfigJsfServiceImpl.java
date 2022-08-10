package com.jdl.basic.provider.core.provider.workMapFunc;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigDetailVO;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigEntity;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncQuery;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.service.workMapFunc.JyWorkMapFuncConfigJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.workMapFunc.JyWorkMapFuncConfigService;
import com.jdl.basic.provider.core.service.workStation.WorkStationService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 拣运功能配置对外jsf服务
 *
 * @author hujiping
 * @date 2022/4/6 6:11 PM
 */
@Service
public class JyWorkMapFuncConfigJsfServiceImpl implements JyWorkMapFuncConfigJsfService {

    private static final Logger logger = LoggerFactory.getLogger(JyWorkMapFuncConfigJsfServiceImpl.class);

    @Autowired
    private JyWorkMapFuncConfigService jyWorkMapFuncConfigService;

    @Autowired
    private WorkStationService workStationService;


    @Override
    public Result<Integer> addWorkMapFunConfig(JyWorkMapFuncConfigDetailVO record) {
        Result<Integer> result = checkParams(record);
        if(!result.isSuccess()){
            return result;
        }
        try {
            WorkStation workStation = new WorkStation();
            workStation.setAreaCode(record.getAreaCode());
            workStation.setWorkCode(record.getWorkCode());
            com.jdl.basic.common.utils.Result<WorkStation> workStationResult = workStationService.queryByBusinessKey(workStation);
            if(workStationResult == null || workStationResult.getData() == null){
                result.toFail(String.format("根据作业区:%s和网格:%s未查询到数据!", record.getAreaCode(), record.getWorkCode()));
                return result;
            }
            JyWorkMapFuncConfigEntity entity = new JyWorkMapFuncConfigEntity();
            entity.setRefWorkKey(workStationResult.getData().getBusinessKey());
            entity.setFuncCode(record.getFuncCode());
            entity.setCreateUser(record.getCreateUser());
            entity.setUpdateUser(record.getUpdateUser());
            if(CollectionUtils.isNotEmpty(jyWorkMapFuncConfigService.queryByCondition(entity))){
                result.toFail("此配置已存在，请勿重复添加!");
                return result;
            }
            result.setData(jyWorkMapFuncConfigService.addWorkMapFunConfig(entity));
        }catch (Exception e){
            logger.error("新增数据:{}异常!", JSON.toJSONString(record), e);
        }
        return result;
    }

    private Result<Integer> checkParams(JyWorkMapFuncConfigDetailVO record) {
        Result<Integer> result = new Result<Integer>();
        if(record == null){
            result.toFail("入参不能为空!");
            return result;
        }
        if(StringUtils.isEmpty(record.getFuncCode())){
            result.toFail("功能编码不能为空!");
            return result;
        }
        if(StringUtils.isEmpty(record.getAreaCode())){
            result.toFail("作业区不能为空!");
            return result;
        }
        if(StringUtils.isEmpty(record.getWorkCode())){
            result.toFail("网格不能为空!");
            return result;
        }
        result.toSuccess();
        return result;
    }

    @Override
    public Result<Integer> updateById(JyWorkMapFuncConfigDetailVO detailVO) {
        Result<Integer> result = checkParams(detailVO);
        if(!result.isSuccess()){
            return result;
        }
        try {
            WorkStation workStation = new WorkStation();
            workStation.setAreaCode(detailVO.getAreaCode());
            workStation.setWorkCode(detailVO.getWorkCode());
            com.jdl.basic.common.utils.Result<WorkStation> workStationResult = workStationService.queryByBusinessKey(workStation);
            if(workStationResult == null || workStationResult.getData() == null){
                result.toFail(String.format("根据作业区:%s和网格:%s未查询到数据!", detailVO.getAreaCode(), detailVO.getWorkCode()));
                return result;
            }
            JyWorkMapFuncConfigEntity entity = new JyWorkMapFuncConfigEntity();
            entity.setId(detailVO.getId());
            entity.setRefWorkKey(workStationResult.getData().getBusinessKey());
            entity.setFuncCode(detailVO.getFuncCode());
            entity.setUpdateUser(detailVO.getUpdateUser());
            result.setData(jyWorkMapFuncConfigService.updateById(entity));
        }catch (Exception e){
            logger.error("根据id:{}更新数据异常!", detailVO.getId(), e);
        }
        return result;
    }

    @Override
    public Result<Integer> deleteById(JyWorkMapFuncConfigEntity entity) {
        Result<Integer> result = new Result<Integer>();
        result.toFail();
        try {
            result.toSuccess();
            result.setData(jyWorkMapFuncConfigService.deleteById(entity));
        }catch (Exception e){
            logger.error("根据id:{}更新数据异常!", entity.getId(), e);
        }
        return result;
    }

    @Override
    public Result<PageDto<JyWorkMapFuncConfigDetailVO>> queryPageList(JyWorkMapFuncQuery query) {
        Result<PageDto<JyWorkMapFuncConfigDetailVO>> result = new Result<PageDto<JyWorkMapFuncConfigDetailVO>>();
        PageDto<JyWorkMapFuncConfigDetailVO> pageDto = new PageDto<JyWorkMapFuncConfigDetailVO>();
        result.toSuccess();
        try {
            PageDto<JyWorkMapFuncConfigEntity> queryResult = jyWorkMapFuncConfigService.queryPageList(query);
            if(CollectionUtils.isEmpty(queryResult.getResult())){
                pageDto.setResult(new ArrayList<JyWorkMapFuncConfigDetailVO>());
                pageDto.setTotalRow(0);
                result.setData(pageDto);
                return result;
            }
            List<JyWorkMapFuncConfigDetailVO> list = new ArrayList<>();
            for (JyWorkMapFuncConfigEntity entity : queryResult.getResult()) {
                JyWorkMapFuncConfigDetailVO detailVO = new JyWorkMapFuncConfigDetailVO();
                detailVO.setId(entity.getId());
                detailVO.setFuncCode(entity.getFuncCode());
                detailVO.setRefWorkKey(entity.getRefWorkKey());
                detailVO.setCreateUser(entity.getCreateUser());
                detailVO.setCreateTime(entity.getCreateTime());
                detailVO.setUpdateUser(entity.getUpdateUser());
                detailVO.setUpdateTime(entity.getUpdateTime());
                com.jdl.basic.common.utils.Result<WorkStation> workStationResult = workStationService.queryByRealBusinessKey(entity.getRefWorkKey());
                if(workStationResult != null && workStationResult.getData() != null){
                    detailVO.setAreaCode(workStationResult.getData().getAreaCode());
                    detailVO.setAreaName(workStationResult.getData().getAreaName());
                    detailVO.setWorkCode(workStationResult.getData().getWorkCode());
                    detailVO.setWorkName(workStationResult.getData().getWorkName());
                }
                list.add(detailVO);
            }
            pageDto.setTotalRow(queryResult.getTotalRow());
            pageDto.setResult(list);
            result.setData(pageDto);
        }catch (Exception e){
            logger.error("根据条件:{}分页查询数据异常!", JSON.toJSONString(query), e);
        }
        return result;
    }
}
