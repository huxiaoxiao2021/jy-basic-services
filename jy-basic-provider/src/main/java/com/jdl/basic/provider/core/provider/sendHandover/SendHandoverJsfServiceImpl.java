package com.jdl.basic.provider.core.provider.sendHandover;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.api.service.sendHandover.SendHandoverJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.sendHandover.SendHandoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwenji
 * @description 发货交接清单
 * @date 2022-09-07 19:57
 */
@Slf4j
@Service
public class SendHandoverJsfServiceImpl implements SendHandoverJsfService {

    @Autowired
    private SendHandoverService sendHandoverService;

    @Override
    public Result<Boolean> insert(SendTripartiteHandoverDetail detail) {
        if (sendHandoverService.insert(detail).getData()>0){
            return Result.success(Boolean.TRUE);
        }else {
            return Result.fail("新增交接配置邮件失败");
        }
    }

    @Override
    public Result<PageDto<SendTripartiteHandoverDetail>> getPageOrderByOperateTime(SendTripartiteHandoverQuery query) {
        return sendHandoverService.getPageOrderByOperateTime(query);
    }

    @Override
    public Result<List<SendTripartiteHandoverDetail>> getListOrderByOperateTime(SendTripartiteHandoverQuery query) {
        return sendHandoverService.getListOrderByOperateTime(query);
    }
}
