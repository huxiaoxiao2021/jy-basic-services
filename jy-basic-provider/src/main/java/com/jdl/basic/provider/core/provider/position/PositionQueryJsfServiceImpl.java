package com.jdl.basic.provider.core.provider.position;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.position.PositionData;
import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.domain.position.PositionRecord;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigEntity;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.api.service.position.PositionQueryJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.position.PositionRecordService;
import com.jdl.basic.provider.core.service.workMapFunc.JyWorkMapFuncConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位查询对外jsf服务
 *
 * @author hujiping
 * @date 2022/2/26 8:50 PM
 */
@Slf4j
@Service
public class PositionQueryJsfServiceImpl implements PositionQueryJsfService {


    @Autowired
    private PositionRecordService positionRecordService;

    @Autowired
    private JyWorkMapFuncConfigService jyWorkMapFuncConfigService;
    
    @Override
    public Result<PageDto<PositionDetailRecord>> queryPageList(PositionQuery query) {
        if(log.isInfoEnabled()){
            log.info("岗位管理-queryPageList-{}",JSON.toJSONString(query));
        }
        
        if (!StringUtils.isEmpty(query.getFuncCode())) {
            JyWorkMapFuncConfigEntity condition = new JyWorkMapFuncConfigEntity();
            condition.setFuncCode(query.getFuncCode());
            List<JyWorkMapFuncConfigEntity> funcConfigEntities = jyWorkMapFuncConfigService.queryByFuncCode(condition);
            
            List<String> refStationKeyList = new ArrayList<>();
            for (JyWorkMapFuncConfigEntity funcConfigEntity : funcConfigEntities) {
                refStationKeyList.add(funcConfigEntity.getRefWorkKey());
            }
            query.setRefStationKeyList(refStationKeyList);
        }
        
        Result<PageDto<PositionDetailRecord>> result = new Result<PageDto<PositionDetailRecord>>();
        result.toFail();
        try {
            return positionRecordService.queryPageList(query);
        }catch (Exception e){
            log.error("根据条件:{}分页查询异常!", JSON.toJSONString(query), e);
        }
        return result;
    }

    @Override
    public Result<Long> queryCountByCondition(PositionQuery query) {
        if(log.isInfoEnabled()) {
            log.info("岗位管理-queryCountByCondition-{}",JSON.toJSONString(query));
        }
        Result<Long> result = new Result<Long>();
        result.toFail();
        try {
            return positionRecordService.queryCountByCondition(query);
        }catch (Exception e){
            log.error("根据条件:{}查询总数异常!{}", JSON.toJSONString(query),e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Result<String> queryPositionCodeByRefGridKey(String refGridKey) {
        if(log.isInfoEnabled()) {
            log.info("queryPositionCodeByRefGridKey-refGridKey={}", refGridKey);
        }
        Result<String> result = new Result<>();
        result.toSuccess();
        try {
            PositionRecord positionRecord = positionRecordService.queryPositionByRefGridKey(refGridKey);
            if (positionRecord == null) {
                log.warn("queryPositionCodeByRefGridKey查询为空:refGridKey={}", refGridKey);
                return result;
            }
            result.setData(positionRecord.getPositionCode());
        } catch (Exception e){
            log.error("queryPositionCodeByRefGridKey|根据业务主键查询岗位码出现异常:refGridKey={}", refGridKey, e);
            result.toError("服务器异常");
        }
        return result;
    }

    @Override
    public Result<Boolean> updateByPositionCode(PositionRecord positionRecord) {
        log.info("岗位管理-updateByPositionCode-{}",JSON.toJSONString(positionRecord));
        Result<Boolean> result = new Result<Boolean>();
        result.toFail();
        try {
            return positionRecordService.updateByPositionCode(positionRecord);
        }catch (Exception e){
            log.error("根据岗位编码:{}更新异常!-{}", positionRecord.getPositionCode(), e.getMessage(),e);
        }
        return result;
    }

    @Override
    public void syncAllData() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        positionRecordService.syncAllData();
                    }catch (Exception e){
                        log.error("同步历史数据异常!", e);
                    }
                }
            }).start();
        }catch (Exception e){
            log.error("同步历史数据异常!", e);
        }
    }

    @Override
    public Result<PositionDetailRecord> queryOneByPositionCode(String positionCode) {
        if(log.isInfoEnabled()){
            log.info("岗位管理-updateByPositionCode-{}",positionCode);
        }
        Result<PositionDetailRecord> result = new Result<>();
        result.toFail();
        try {
            return positionRecordService.queryOneByPositionCode(positionCode);
        }catch (Exception e){
            log.error("根据岗位编码获取岗位详情:{} 异常!-{}", positionCode, e.getMessage(),e);
        }
        return result;
    }

    @Override
    public Result<PositionData> queryPositionWithIsMatchAppFunc(String positionCode) {
        if(log.isInfoEnabled()){
            log.info("岗位管理-queryPositionWithIsMatchAppFunc-{}",positionCode);
        }
        Result<PositionData> response = new Result<>();
        response.toFail();
        try {
            return positionRecordService.queryPositionWithIsMatchAppFunc(positionCode);
        }catch (Exception e){
            log.error("queryPositionWithIsMatchAppFunc 查询岗位信息:{} 异常!-{}", positionCode, e.getMessage(),e);
        }
        return response;
    }

    @Override
    public Result<PositionData> queryPositionInfo(String positionCode) {
        if(log.isInfoEnabled()){
            log.info("岗位管理-queryPositionInfo-{}",positionCode);
        }
        Result<PositionData> response = new Result<>();
        response.toFail();
        try {
            return positionRecordService.queryPositionInfo(positionCode);
        }catch (Exception e){
            log.error("queryPositionInfo 查询岗位信息:{} 异常!-{}", positionCode, e.getMessage(),e);
        }
        return response;
    }

	@Override
	public Result<PositionData> queryPositionByGridKey(String gridKey) {
        if(log.isInfoEnabled()){
            log.info("岗位管理-queryPositionByGridKey-{}",gridKey);
        }
        Result<PositionData> response = new Result<>();
        response.toSuccess();
        try {
        	response.setData(positionRecordService.queryPositionByGridKeyWithCache(gridKey));
        }catch (Exception e){
            log.error("queryPositionByGridKey 查询岗位信息:{} 异常!-{}", gridKey, e.getMessage(),e);
            response.toFail("岗位信息查询异常！");
        }
        return response;
	}
}
