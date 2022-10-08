package com.jdl.basic.provider.core.service.position.impl;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.position.PositionData;
import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.domain.position.PositionRecord;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigEntity;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringHelper;
import com.jdl.basic.provider.core.components.IGenerateObjectId;
import com.jdl.basic.provider.core.dao.position.PositionRecordDao;
import com.jdl.basic.provider.core.dao.workMapFunc.JyWorkMapFuncConfigDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.position.PositionRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位查询服务实现
 *
 * @author hujiping
 * @date 2022/2/25 5:47 PM
 */
@Service
public class PositionRecordServiceImpl implements PositionRecordService {

    private static final Logger logger = LoggerFactory.getLogger(PositionRecordServiceImpl.class);

    @Autowired
    private PositionRecordDao positionRecordDao;

    @Autowired
    private IGenerateObjectId genObjectId;

    @Autowired
    private WorkStationGridDao workStationGridDao;

    @Autowired
    private WorkStationDao workStationDao;

    @Autowired
    private JyWorkMapFuncConfigDao jyWorkMapFuncConfigDao;

    @Autowired
    private BaseMajorManager baseMajorManager;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.insertPosition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Integer> insertPosition(PositionRecord record) {
        Result<Integer> result = new Result<Integer>();
        result.toSuccess();
        if(StringUtils.isEmpty(record.getPositionCode())){
            record.setPositionCode(generalPositionCode());
        }
        result.setData(positionRecordDao.insert(record));
        return result;
    }

    private String generalPositionCode() {
        return DmsConstants.CODE_PREFIX_POSITION.concat(StringHelper.padZero(this.genObjectId.getObjectId(PositionRecord.class.getName()),8));
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Integer> batchInsert(List<PositionRecord> list) {
        Result<Integer> result = new Result<Integer>();
        result.toSuccess();
        for (PositionRecord positionRecord : list) {
            if(StringUtils.isEmpty(positionRecord.getPositionCode())){
                positionRecord.setPositionCode(generalPositionCode());
            }
        }
        result.setData(positionRecordDao.batchInsert(list));
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PageDto<PositionDetailRecord>> queryPageList(PositionQuery query) {
        Result<PageDto<PositionDetailRecord>> result = new Result<PageDto<PositionDetailRecord>>();
        result.toSuccess();
        if(query.getPageSize() == null || query.getPageSize() <= 0 || query.getPageNumber() <= 0) {
            result.toFail("分页参数错误!");
            return result;
        }
        query.setLimit(query.getPageSize());
        query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        PageDto<PositionDetailRecord> pageDto = new PageDto<PositionDetailRecord>(query.getPageNumber(), query.getPageSize());
        Long totalCount = positionRecordDao.queryCount(query);
        if(totalCount > 0){
            pageDto.setResult(positionRecordDao.queryList(query));
            pageDto.setTotalRow(Integer.parseInt(Long.toString(totalCount)));
        }else {
            pageDto.setResult(new ArrayList<PositionDetailRecord>());
            pageDto.setTotalRow(0);
        }
        result.setData(pageDto);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.queryOneByPositionCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PositionDetailRecord> queryOneByPositionCode(String positionCode) {
        Result<PositionDetailRecord> result = new Result<PositionDetailRecord>();
        PositionDetailRecord positionDetailRecord = positionRecordDao.queryDetailByPositionCode(positionCode);
        // 防止站点名称变更而表中的站点名称未及时更新导致查出的站点名称不对
        BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(positionDetailRecord.getSiteCode());
        if(baseSite != null && !StringUtils.isEmpty(baseSite.getSiteName())){
            positionDetailRecord.setSiteName(baseSite.getSiteName());
            result.toSuccess();
        }else {
            result.toFail("未查询到场地信息!");
        }
        result.setData(positionDetailRecord);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.updateByPositionCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> updateByPositionCode(PositionRecord positionRecord) {
        Result<Boolean> result = new Result<Boolean>();
        result.toSuccess();
        result.setData(positionRecordDao.updateByPositionCode(positionRecord) == 1);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.deleteByBusinessKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> deleteByBusinessKey(PositionRecord positionRecord) {
        Result<Boolean> result = new Result<Boolean>();
        result.toSuccess();
        result.setData(positionRecordDao.deleteByBusinessKey(positionRecord) > 0);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.queryCountByCondition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Long> queryCountByCondition(PositionQuery query) {
        Result<Long> result = new Result<Long>();
        result.toSuccess();
        result.setData(positionRecordDao.queryCount(query));
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.syncAllData", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public void syncAllData() {
        long startTime = System.currentTimeMillis();
        WorkStationGridQuery query = new WorkStationGridQuery();
        int totalCount = 0;
        int limit = 1000;
        int offset = 0;
        int count = 0;
        while (count < 200){
            query.setOffset(offset);
            query.setLimit(limit);
            List<WorkStationGrid> list = workStationGridDao.queryAllByPage(query);
            if(CollectionUtils.isEmpty(list)){
                break;
            }
            for (WorkStationGrid workStationGrid : list) {
                PositionRecord record = new PositionRecord();
                record.setSiteCode(workStationGrid.getSiteCode());
                record.setRefGridKey(workStationGrid.getBusinessKey());
                record.setPositionCode(generalPositionCode());
                record.setCreateUser(workStationGrid.getCreateUser());
                record.setUpdateUser(workStationGrid.getCreateUser());
                if(positionRecordDao.queryByBusinessKey(workStationGrid.getBusinessKey()) == null){
                    insertPosition(record);
                    totalCount ++;
                }
            }
            offset += limit;
            count ++;
        }
        logger.info("同步历史数据完成，共耗时：{}共同步:{}条记录", System.currentTimeMillis() - startTime, totalCount);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.queryPositionWithIsMatchAppFunc", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PositionData> queryPositionWithIsMatchAppFunc(String positionCode) {
        Result<PositionData> result = queryPositionInfo(positionCode);
        if(!result.isSuccess()){
            return result;
        }
        setDefaultMenuCode(positionCode, result);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".PositionRecordServiceImpl.queryPositionInfo", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PositionData> queryPositionInfo(String positionCode) {
        Result<PositionData> result = new Result<PositionData>();
        result.toSuccess();
        Result<PositionDetailRecord> positionDetailResult = this.queryOneByPositionCode(positionCode);
        if(positionDetailResult == null
                || positionDetailResult.getData() == null) {
            result.toFail("无效的上岗码！");
            return result;
        }
        PositionData positionData = new PositionData();
        BeanUtils.copyProperties(positionDetailResult.getData(), positionData);
        result.setData(positionData);
        return result;
    }

    private void setDefaultMenuCode(String positionCode, Result<PositionData> result) {
        PositionData positionData = result.getData();
        WorkStation workStation = new WorkStation();
        workStation.setAreaCode(positionData.getAreaCode());
        workStation.setWorkCode(positionData.getWorkCode());
        WorkStation queryWork = workStationDao.queryByBusinessKey(workStation);
        if(queryWork == null || StringUtils.isEmpty(queryWork.getBusinessKey())){
            result.toFail(String.format("岗位码:%s对应的工序不存在，请联系分拣小秘!", positionCode));
            return;
        }
        JyWorkMapFuncConfigEntity entity = jyWorkMapFuncConfigDao.queryByBusinessKey(queryWork.getBusinessKey());
        if(entity == null || StringUtils.isEmpty(entity.getFuncCode())){
            result.toFail(String.format("岗位码:%s对应的功能编码未配置，请联系分拣小秘!", positionCode));
            return;
        }
        positionData.setDefaultMenuCode(entity.getFuncCode());
    }

}
