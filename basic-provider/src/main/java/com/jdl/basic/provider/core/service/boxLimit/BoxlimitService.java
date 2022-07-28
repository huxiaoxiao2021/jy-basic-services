package com.jdl.basic.provider.core.service.boxLimit;



import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 11:33
 * @Description:
 */
@Validated
public interface BoxlimitService {

    void insertBoxlimitConfig(@Valid BoxLimitConfigDto po);
}
