package com.jdl.basic.provider.core.dao.position;



import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.domain.position.PositionRecord;

import java.util.List;

/**
 * 岗位查询DAO
 *
 * @author hujiping
 * @date 2022/2/25 5:46 PM
 */
public interface PositionRecordDao {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(PositionRecord record);

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    int batchInsert(List<PositionRecord> list);

    /**
     * 根据岗位编码更新
     *
     * @param positionRecord
     * @return
     */
    int updateByPositionCode(PositionRecord positionRecord);

    /**
     * 根据业务主键逻辑删除
     *
     * @param positionRecord
     * @return
     */
    int deleteByBusinessKey(PositionRecord positionRecord);

    /**
     * 根据岗位编码查询
     *
     * @param positionCode
     * @return
     */
    PositionRecord queryByPositionCode(String positionCode);
    /**
     * 根据岗位编码查询详细信息
     *
     * @param positionCode
     * @return
     */
    @Cache(key = "PositionRecordDao.queryDetailByPositionCode@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
    PositionDetailRecord queryDetailByPositionCode(String positionCode);

    /**
     * 根据业务主键查询数据
     *
     * @param businessKey
     * @return
     */
    PositionRecord queryByBusinessKey(String businessKey);

    /**
     * 根据条件分页查询
     *
     * @param query
     * @return
     */
    List<PositionDetailRecord> queryList(PositionQuery query);

    /**
     * 根据条件查询总数
     *
     * @param query
     * @return
     */
    Long queryCount(PositionQuery query);
    /**
     * 根据gridKey查询数据
     *
     * @param gridKey
     * @return
     */
    PositionRecord queryByGridKey(String gridKey);
}
