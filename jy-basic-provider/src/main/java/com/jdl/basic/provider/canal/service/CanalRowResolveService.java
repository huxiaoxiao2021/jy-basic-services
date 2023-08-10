package com.jdl.basic.provider.canal.service;

import com.jdl.basic.provider.canal.ChangeOfRow;

/**
 * Created by chenxin26 on 2017/10/30.
 */
public interface CanalRowResolveService {

    void doService(ChangeOfRow row) throws Exception;
}

