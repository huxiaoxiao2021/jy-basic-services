package com.jdl.basic.api.service.businessWhite;


import com.jdl.basic.api.domain.businessWhite.BusinessWhiteList;
import com.jdl.basic.api.domain.businessWhite.BusinessWhiteListCondition;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface BusinessWhiteListJsfService {

    /**
     * 删除数据
     * @param param
     * @return
     */
    Result<Boolean> deleteById (BusinessWhiteList param);


    /**
     * 查询数据
     * @param param
     * @return
     */
    Result<List<BusinessWhiteList>> selectByParam(BusinessWhiteListCondition param);

    /**
     * 获取总数据量
     * @param param
     * @return
     */
    Result<Long> count(BusinessWhiteListCondition param);

    /**
     * 添加数据
     * @param record
     * @return
     */
    Result<Boolean> insert(BusinessWhiteList record);
}
