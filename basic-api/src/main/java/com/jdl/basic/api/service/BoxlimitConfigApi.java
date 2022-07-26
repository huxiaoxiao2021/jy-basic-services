package com.jdl.basic.api.service;


import com.jdl.basic.api.domain.po.BoxLimitConfigDto;
import com.jdl.basic.api.response.JDResponse;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 16:20
 * @Description:
 */
public interface BoxlimitConfigApi {

    JDResponse insertBoxlimitConfig(BoxLimitConfigDto po);
}
