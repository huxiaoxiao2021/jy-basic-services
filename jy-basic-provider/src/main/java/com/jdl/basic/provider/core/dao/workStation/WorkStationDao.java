package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.domain.workStation.WorkStationCountVo;
import com.jdl.basic.api.domain.workStation.WorkStationQuery;

import java.util.List;


/**
 * @author wuyoude
 * @ClassName: WorkStationDao
 * @Description: 网格工序信息表--Dao接口
 * @date 2022年02月23日 11:01:53
 */
public interface WorkStationDao {
    /**
     * 插入一条数据
     *
     * @param insertData
     * @return
     */
    int insert(WorkStation insertData);

    /**
     * 根据id更新数据
     *
     * @param updateData
     * @return
     */
    int updateById(WorkStation updateData);

    /**
     * 根据id删除数据
     *
     * @param deleteData
     * @return
     */
    int deleteById(WorkStation deleteData);

    /**
     * 根据业务主键删除
     *
     * @param data
     * @return
     */
    int deleteByBusinessKey(WorkStation data);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    WorkStation queryById(Long id);

    /**
     * 根据业务主键查询
     *
     * @param data
     * @return
     */
    WorkStation queryByBusinessKey(WorkStation data);

    /**
     * 根据业务主键查询
     *
     * @param businessKey
     * @return
     */
    WorkStation queryByRealBusinessKey(String businessKey);

    /**
     * 按条件分页查询
     *
     * @param query
     * @return
     */
    List<WorkStation> queryList(WorkStationQuery query);

    /**
     * 按条件查询数量
     *
     * @param query
     * @return
     */
    Long queryCount(WorkStationQuery query);

    /**
     * 查询作业区字典
     *
     * @return
     */
    List<WorkStation> queryAreaDictList(WorkStationQuery query);

    /**
     * 查询工序字典
     *
     * @return
     */
    List<WorkStation> queryWorkDictList(WorkStationQuery query);

    /**
     * 按条件查询统计信息
     *
     * @param query
     * @return
     */
    WorkStationCountVo queryPageCount(WorkStationQuery query);

    /**
     * 导出查询列表
     *
     * @param query
     * @return
     */
    List<WorkStation> queryListForExport(WorkStationQuery query);

    /**
     * 根据id列表查询
     *
     * @param deleteRequest
     * @return
     */
    List<WorkStation> queryByIds(DeleteRequest<WorkStation> deleteRequest);

    /**
     * 根据id批量删除
     *
     * @param deleteRequest
     * @return
     */
    int deleteByIds(DeleteRequest<WorkStation> deleteRequest);
}