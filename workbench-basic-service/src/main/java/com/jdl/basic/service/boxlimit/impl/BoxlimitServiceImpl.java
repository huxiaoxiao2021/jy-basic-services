package com.jdl.basic.service.boxlimit.impl;

import com.jdl.basic.service.boxlimit.BoxlimitService;
import com.jdl.basic.dao.boxlimit.BoxLimitConfigDao;
import com.jdl.basic.domain.boxlimit.BoxLimitConfigPO;
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
    public void insertBoxlimitConfig(BoxLimitConfigPO po) {

        boxLimitConfigDao.addConfig(po);
    }
}
