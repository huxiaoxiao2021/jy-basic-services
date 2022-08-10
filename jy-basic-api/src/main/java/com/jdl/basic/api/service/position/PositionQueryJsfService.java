package com.jdl.basic.api.service.position;


import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.domain.position.PositionRecord;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 岗位查询对外jsf服务
 *
 * @author hujiping
 * @date 2022/2/26 8:47 PM
 */
public interface PositionQueryJsfService {

    /**
     * 按条件分页查询
     *
     * @param query
     * @return
     */
    Result<PageDto<PositionDetailRecord>> queryPageList(PositionQuery query);

    /**
     * 根据条件查询总数
     *
     * @param query
     * @return
     */
    Result<Long> queryCountByCondition(PositionQuery query);

    /**
     * 根据岗位编码更新
     *
     * @param positionRecord
     * @return
     */
    Result<Boolean> updateByPositionCode(PositionRecord positionRecord);

    /**
     * 同步全量数据
     *
     * @return
     */
    void syncAllData();

    /**
     * 根据岗位编码查询一条记录
     *
     * @param positionCode
     * @return
     */
    Result<PositionDetailRecord> queryOneByPositionCode(String positionCode);
}
