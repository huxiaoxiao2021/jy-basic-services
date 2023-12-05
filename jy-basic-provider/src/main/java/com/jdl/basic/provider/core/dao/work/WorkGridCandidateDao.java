package com.jdl.basic.provider.core.dao.work;

import com.jdl.basic.api.domain.work.*;

import java.util.List;

/**
 * 作业区巡检-项目明细表--Dao接口
 *
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridCandidateDao {
    /**
     * 插入一条数据
     * @param insertData
     * @return
     */
    int insert(WorkGridCandidate insertData);
    /**
     * 根据id更新数据
     * @param updateData
     * @return
     */
    int updateById(WorkGridCandidate updateData);


    Long queryCount(WorkGridCandidateQuery query);

    List<WorkGridCandidate> queryList(WorkGridCandidateQuery query);

    void deleteBySiteCode(Integer siteCode);

    List<WorkGridCandidate> queryCandidateList(Integer siteCode);

}
