package com.jdl.basic.api.service.kaCoefficientConfig;

import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigDto;
import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigQueryDto;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface KaCoefficientConfigJsfService {

    /**
     * 分页查询ka积分卸车配置
     * @param param
     * @return
     */
    Result<PageDto<KaCoefficientConfigDto>> getConfigByMerchantCodeAndStatus(KaCoefficientConfigQueryDto param);

    /**
     * 添加配置
     * @param param
     * @return
     */
    Result<Boolean> addKaCoefficientConfig(KaCoefficientConfigDto param);

    /**
     * 修改系数
     * @param param
     * @return
     */
    Result<Boolean> modifyCoefficient(KaCoefficientConfigDto param);

    /**
     * 根据ID删除配置数据
     * @param param
     * @return
     */
    Result<Boolean> delCoefficientById(KaCoefficientConfigDto param);
}
