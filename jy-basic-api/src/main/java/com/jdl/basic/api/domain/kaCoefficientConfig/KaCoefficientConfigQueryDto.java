package com.jdl.basic.api.domain.kaCoefficientConfig;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

@Data
public class KaCoefficientConfigQueryDto extends BasePagerCondition {

    private String merchantCode;//商家编码

    private String statue;//状态编码
}
