package com.jdl.basic.provider.core.dao.workMapFunc;

import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigEntity;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/8/10 13:59
 * @Description:
 */
public interface JyWorkMapFuncConfigDao {

    int insert(JyWorkMapFuncConfigEntity entity);

    int updateById(JyWorkMapFuncConfigEntity entity);

    int deleteById(JyWorkMapFuncConfigEntity entity);

    JyWorkMapFuncConfigEntity queryById(Long id);


    JyWorkMapFuncConfigEntity queryByBusinessKey(String businessKey);

    List<JyWorkMapFuncConfigEntity> queryList(JyWorkMapFuncQuery query);

    int queryCount(JyWorkMapFuncQuery query);

    List<JyWorkMapFuncConfigEntity> queryByCondition(JyWorkMapFuncConfigEntity entity);

    List<JyWorkMapFuncConfigEntity> queryFuncCodeMap();

    /**
     * 根据funcCodeList查询
     * @param functionList
     * @return
     */
    List<String> queryByFuncCodeList(@Param("functionList") List<String> functionList);
}
