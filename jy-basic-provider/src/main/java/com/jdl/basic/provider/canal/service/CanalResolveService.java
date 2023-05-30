package com.jdl.basic.provider.canal.service;

import com.jdl.basic.provider.canal.ChangeOfRow;

/**
 * @author : xumigen
 * @date : 2019/5/16
 */
public interface CanalResolveService {

    ChangeOfRow formatCanalMessage(String jsonMessage) throws Exception;
}
