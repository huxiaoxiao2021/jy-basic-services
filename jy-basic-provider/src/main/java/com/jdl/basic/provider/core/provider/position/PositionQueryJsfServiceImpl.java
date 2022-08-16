package com.jdl.basic.provider.core.provider.position;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.position.PositionData;
import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.domain.position.PositionRecord;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.api.service.position.PositionQueryJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.position.PositionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Result<PageDto<PositionDetailRecord>> queryPageList(PositionQuery query) {
        log.info("岗位管理-queryPageList-{}",JSON.toJSONString(query));
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
        log.info("岗位管理-queryCountByCondition-{}",JSON.toJSONString(query));
        Result<Long> result = new Result<Long>();
        result.toFail();
        try {
            return positionRecordService.queryCountByCondition(query);
        }catch (Exception e){
            log.error("根据条件:{}查询总数异常!", JSON.toJSONString(query), e);
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
            log.error("根据岗位编码:{}更新异常!", positionRecord.getPositionCode(), e);
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
        log.info("岗位管理-updateByPositionCode-{}",positionCode);
        Result<PositionDetailRecord> result = new Result<>();
        result.toFail();
        try {
            return positionRecordService.queryOneByPositionCode(positionCode);
        }catch (Exception e){
            log.error("根据岗位编码获取岗位详情:{} 异常!", positionCode, e);
        }
        return result;
    }

    @Override
    public JDResponse<PositionData> queryPositionWithIsMatchAppFunc(String positionCode) {
        JDResponse<PositionData> response = new JDResponse<>();
        response.toFail();
        try {
            return positionRecordService.queryPositionWithIsMatchAppFunc(positionCode);
        }catch (Exception e){
            log.error("queryPositionWithIsMatchAppFunc 查询岗位信息:{} 异常!", positionCode, e);
        }
        return response;
    }

    @Override
    public JDResponse<PositionData> queryPositionInfo(String positionCode) {
        JDResponse<PositionData> response = new JDResponse<>();
        response.toFail();
        try {
            return positionRecordService.queryPositionInfo(positionCode);
        }catch (Exception e){
            log.error("queryPositionInfo 查询岗位信息:{} 异常!", positionCode, e);
        }
        return response;
    }
}
