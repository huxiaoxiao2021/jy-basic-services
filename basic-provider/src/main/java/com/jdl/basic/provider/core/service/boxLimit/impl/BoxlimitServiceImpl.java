package com.jdl.basic.provider.core.service.boxLimit.impl;


import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.provider.core.dao.boxLimit.BoxLimitConfigDao;
import com.jdl.basic.provider.core.po.BoxLimitConfigPO;
import com.jdl.basic.provider.core.service.boxLimit.BoxlimitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 11:40
 * @Description:
 */

@Service
public class BoxlimitServiceImpl implements BoxlimitService {

    @Autowired
    private BoxLimitConfigDao boxLimitConfigDao;

    @Override
    public void insertBoxlimitConfig(BoxLimitConfigDto po) {

        BoxLimitConfigPO dto=  new BoxLimitConfigPO();
        BeanUtils.copyProperties(po,dto);

        boxLimitConfigDao.insert(dto);
    }
}
