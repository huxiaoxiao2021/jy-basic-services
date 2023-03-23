package com.jdl.basic.provider.core.service.position;

import com.jdl.basic.api.domain.position.PositionData;
import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.domain.position.PositionRecord;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * 岗位查询服务
 *
 * @author hujiping
 * @date 2022/2/25 5:47 PM
 */
public interface PositionRecordService {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    Result<Integer> insertPosition(PositionRecord record);

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    Result<Integer> batchInsert(List<PositionRecord> list);

    /**
     * 按条件分页查询
     *
     * @param query
     * @return
     */
    Result<PageDto<PositionDetailRecord>> queryPageList(PositionQuery query);

    /**
     * 根据岗位编码查询一条记录
     * 
     * @param positionCode
     * @return
     */
    Result<PositionDetailRecord> queryOneByPositionCode(String positionCode);

    /**
     * 根据岗位编码更新
     *
     * @param positionRecord
     * @return
     */
    Result<Boolean> updateByPositionCode(PositionRecord positionRecord);

    /**
     * 根据业务主键删除
     *
     * @param positionRecord
     * @return
     */
    Result<Boolean> deleteByBusinessKey(PositionRecord positionRecord);

    /**
     * 根据条件查询总数
     *
     * @param query
     * @return
     */
    Result<Long> queryCountByCondition(PositionQuery query);

    /**
     * 同步所有数据
     *
     * @return
     */
    void syncAllData();
    /**
     * 查询岗位信息
     * @param positionCode
     * @return
     */
    Result<PositionData> queryPositionWithIsMatchAppFunc(String positionCode);

    /**
     * 查询岗位信息，并校验是否关联作业app功能
     *
     * @param positionCode
     * @return
     */
    Result<PositionData> queryPositionInfo(String positionCode);
    /**
     * 根据gridKey-查询岗位信息
     * @param gridKey
     * @return
     */
    PositionData queryPositionByGridKeyWithCache(String gridKey);

}
