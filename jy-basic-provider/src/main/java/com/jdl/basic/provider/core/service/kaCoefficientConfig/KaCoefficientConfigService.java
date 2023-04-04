package com.jdl.basic.provider.core.service.kaCoefficientConfig;

import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigDto;
import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigQueryDto;
import com.jdl.basic.common.utils.PageDto;

import java.util.List;

public interface KaCoefficientConfigService {

    PageDto<KaCoefficientConfigDto> geConfigByMerchantCodeAndStatus(KaCoefficientConfigQueryDto param);


    /**
     * 添加配置
     * @param param
     * @return
     */
    Integer addKaCoefficientConfig(KaCoefficientConfigDto param);

    /**
     * 修改系数
     * @param param
     * @return
     */
    Integer modifyCoefficient(KaCoefficientConfigDto param);

    /**
     * 根据ID删除配置数据
     * @param param
     * @return
     */
    Integer delCoefficientById(KaCoefficientConfigDto param);

    /**
     * 查询生效的数据量
     * @return
     */
    Integer getCountOfInEffectState();
}
