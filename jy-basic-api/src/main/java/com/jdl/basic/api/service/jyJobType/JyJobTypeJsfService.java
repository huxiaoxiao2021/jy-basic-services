package com.jdl.basic.api.service.jyJobType;

import com.jdl.basic.api.domain.jyJobType.JyJobType;
import com.jdl.basic.api.domain.jyJobType.JyJobTypeQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author pengchong28
 * @description 拣运工种jsf服务接口
 * @date 2024/2/1
 */
public interface JyJobTypeJsfService {
    /**
     * 插入一条数据
     *
     * @param insertData
     * @return
     */
    Result<Boolean> insert(JyJobType insertData);

    /**
     * 根据id更新数据
     *
     * @param updateData
     * @return
     */
    Result<Boolean> updateById(JyJobType updateData);

    /**
     * 按条件分页查询
     *
     * @param query
     * @return
     */
    Result<PageDto<JyJobType>> queryPageList(JyJobTypeQuery query);

    /**
     * 根据条件查询JyJobType列表
     *
     * @param query 查询条件
     * @return 符合条件的JyJobType列表
     */
    Result<List<JyJobType>> queryListByCondition(JyJobType query);

    /**
     * 查询所有JyJobType列表
     * @return 返回JyJobType列表
     */
    List<JyJobType> queryAllList();

    /**
     * 查询所有可用的JyJobType列表
     * @return 可用的JyJobType列表
     */
    List<JyJobType> queryAllAvailableList();

}
