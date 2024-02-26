package com.jdl.basic.provider.core.dao.jyJobType;

import com.jdl.basic.api.domain.jyJobType.JyJobType;
import com.jdl.basic.api.domain.jyJobType.JyJobTypeQuery;

import java.util.List;

/**
 * 拣运工种表--Dao接口
 *
 * @author pengchong
 * @date 2024/2/1
 */
public interface JyJobTypeDao {
    /**
     * 插入一条数据
     *
     * @param insertData
     * @return
     */
    int insert(JyJobType insertData);

    /**
     * 根据id更新数据
     *
     * @param updateData
     * @return
     */
    int updateById(JyJobType updateData);

    /**
     * 根据id删除数据
     *
     * @param deleteData
     * @return
     */
    int deleteById(JyJobType deleteData);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    JyJobType queryById(Long id);

    /**
     * 按条件分页查询
     *
     * @param query
     * @return
     */
    List<JyJobType> queryList(JyJobTypeQuery query);

    /**
     * 按条件查询数量
     *
     * @param query
     * @return
     */
    long queryCount(JyJobTypeQuery query);

    /**
     * 根据条件查询JyJobType列表
     * @param query 查询条件
     * @return 符合条件的JyJobType列表
     */
    List<JyJobType> queryListByCondition(JyJobType query);

    /**
     * 查询所有JyJobType列表
     * @return JyJobType列表
     */
    List<JyJobType> queryALlList();

    /**
     * 查询所有可用的职位类型列表
     * @return 可用的职位类型列表
     */
    List<JyJobType> queryAllAvailableList();
}
