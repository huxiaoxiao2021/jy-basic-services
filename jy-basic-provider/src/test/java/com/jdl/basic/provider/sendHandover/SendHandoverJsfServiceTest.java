package com.jdl.basic.provider.sendHandover;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.api.service.sendHandover.SendHandoverJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author liwenji
 * @description TODO
 * @date 2022-09-11 17:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SendHandoverJsfServiceTest {

    @Autowired
    private SendHandoverJsfService service;

    @Test
    public void getList(){
        Result<List<SendTripartiteHandoverDetail>> listOrderByOperateTime = service.getListOrderByOperateTimeLimit(10098);
        for (SendTripartiteHandoverDetail datum : listOrderByOperateTime.getData()) {
            System.out.println(datum.getCreateTime());
        }
    }

    @Test
    public void update() {
        service.updateLastOperateTimeById(269L);
    }

    @Test
    public void getById() {
        Result<SendTripartiteHandoverDetail> infoById = service.getInfoById(269L);
        System.out.println(infoById.getData().getCcEmail());
    }
}
