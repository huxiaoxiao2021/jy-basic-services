package com.jdl.basic.api.service.workMapFunc;

import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigDetailVO;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigEntity;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 拣运功能配置对外jsf服务
 *
 * @author hujiping
 * @date 2022/4/6 6:13 PM
 */
public interface JyWorkMapFuncConfigJsfService {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    Result<Integer> addWorkMapFunConfig(JyWorkMapFuncConfigDetailVO record);

    /**
     * 根据id更新
     *
     * @param entity
     * @return
     */
    Result<Integer> updateById(JyWorkMapFuncConfigDetailVO entity);

    /**
     * 根据id将数据置为无效
     *
     * @param entity
     * @return
     */
    Result<Integer> deleteById(JyWorkMapFuncConfigEntity entity);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    Result<PageDto<JyWorkMapFuncConfigDetailVO>> queryPageList(JyWorkMapFuncQuery query);
}
