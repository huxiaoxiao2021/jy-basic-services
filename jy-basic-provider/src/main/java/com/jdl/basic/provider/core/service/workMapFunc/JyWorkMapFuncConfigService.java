package com.jdl.basic.provider.core.service.workMapFunc;


import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigDetailVO;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigEntity;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.HashMap;
import java.util.List;

/**
 * 拣运功能与工序配置接口
 *
 * @author hujiping
 * @date 2022/4/6 6:04 PM
 */
public interface JyWorkMapFuncConfigService {

    /**
     * 新增数据
     *
     * @param entity
     * @return
     */
    int addWorkMapFunConfig(JyWorkMapFuncConfigEntity entity);

    /**
     * 根据id更新
     *
     * @param entity
     * @return
     */
    int updateById(JyWorkMapFuncConfigEntity entity);

    /**
     * 根据id将数据置为无效
     *
     * @param entity
     * @return
     */
    int deleteById(JyWorkMapFuncConfigEntity entity);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageDto<JyWorkMapFuncConfigEntity> queryPageList(JyWorkMapFuncQuery query);

    /**
     * 根据条件查询
     *
     * @param query
     * @return
     */
    List<JyWorkMapFuncConfigEntity> queryByCondition(JyWorkMapFuncConfigEntity query);

    /**
     * 根据工序编码获取功能编码
     * @return
     */
    HashMap<String, String> queryFuncCodeMap();
}
