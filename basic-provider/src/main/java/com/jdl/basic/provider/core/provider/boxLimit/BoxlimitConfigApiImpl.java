package com.jdl.basic.provider.core.provider.boxLimit;


import com.jdl.basic.api.domain.po.BoxLimitConfigDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.api.service.BoxlimitConfigApi;

import com.jdl.basic.provider.core.service.boxLimit.BoxlimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 16:35
 * @Description:
 */
@Component
@Slf4j

public class BoxlimitConfigApiImpl implements BoxlimitConfigApi {

    @Autowired
    private BoxlimitService boxlimitService;

    @Override
    public JDResponse insertBoxlimitConfig(BoxLimitConfigDto po) {
        JDResponse response = new JDResponse();
        try{
            boxlimitService.insertBoxlimitConfig(po);
            response.setCode(1);
            response.setMessage("成功");
            return response ;
        }catch (Exception e){
            response.setCode(-1);
            response.setMessage(e.getMessage());
        }

        return response;
    }
}
