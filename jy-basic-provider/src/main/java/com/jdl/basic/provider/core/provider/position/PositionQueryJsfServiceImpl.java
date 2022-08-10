package com.jdl.basic.provider.core.provider.position;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.domain.position.PositionRecord;
import com.jdl.basic.api.service.position.PositionQueryJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.position.PositionRecordService;
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
@Service
public class PositionQueryJsfServiceImpl implements PositionQueryJsfService {

    private static final Logger logger = LoggerFactory.getLogger(PositionQueryJsfServiceImpl.class);

    @Autowired
    private PositionRecordService positionRecordService;

    @Override
    public Result<PageDto<PositionDetailRecord>> queryPageList(PositionQuery query) {

        Result<PageDto<PositionDetailRecord>> result = new Result<PageDto<PositionDetailRecord>>();
        result.toFail();
        try {
            return positionRecordService.queryPageList(query);
        }catch (Exception e){
            logger.error("根据条件:{}分页查询异常!", JSON.toJSONString(query), e);
        }
        return result;
    }

    @Override
    public Result<Long> queryCountByCondition(PositionQuery query) {

        Result<Long> result = new Result<Long>();
        result.toFail();
        try {
            return positionRecordService.queryCountByCondition(query);
        }catch (Exception e){
            logger.error("根据条件:{}查询总数异常!", JSON.toJSONString(query), e);
        }
        return result;
    }

    @Override
    public Result<Boolean> updateByPositionCode(PositionRecord positionRecord) {
        Result<Boolean> result = new Result<Boolean>();
        result.toFail();
        try {
            return positionRecordService.updateByPositionCode(positionRecord);
        }catch (Exception e){
            logger.error("根据岗位编码:{}更新异常!", positionRecord.getPositionCode(), e);
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
                        logger.error("同步历史数据异常!", e);
                    }
                }
            }).start();
        }catch (Exception e){
            logger.error("同步历史数据异常!", e);
        }
    }

    @Override
    public Result<PositionDetailRecord> queryOneByPositionCode(String positionCode) {

        Result<PositionDetailRecord> result = new Result<>();
        result.toFail();
        try {
            return positionRecordService.queryOneByPositionCode(positionCode);
        }catch (Exception e){
            logger.error("根据岗位编码获取岗位详情:{} 异常!", positionCode, e);
        }
        return result;
    }
}
