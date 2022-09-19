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

import java.util.ArrayList;
import java.util.List;

/**
 * @author liwenji
 * @description 测试
 * @date 2022-09-11 17:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SendHandoverJsfServiceTest {

    @Autowired
    private SendHandoverJsfService service;

    @Test
    public void getList(){
        Result<List<SendTripartiteHandoverDetail>> listOrderByOperateTime = service.getListOrderByOperateTimeLimit(910);
        for (SendTripartiteHandoverDetail datum : listOrderByOperateTime.getData()) {
            System.out.println(datum.getCreateTime());
        }
    }

    @Test
    public void update() {
        List<Long> i = new ArrayList<>();
        i.add(297L);
        i.add(298L);
        i.add(299L);
        i.add(300L);
        service.updateLastOperateTimeById(i);
    }

    @Test
    public void getById() {
        Result<SendTripartiteHandoverDetail> infoById = service.getInfoById(307L);
        Result<SendTripartiteHandoverDetail> infoById2 = service.getInfoById(308L);
        Result<SendTripartiteHandoverDetail> infoById3 = service.getInfoById(309L);
        Result<SendTripartiteHandoverDetail> infoById4 = service.getInfoById(310L);
        Result<SendTripartiteHandoverDetail> infoById5 = service.getInfoById(311L);

        List<SendTripartiteHandoverDetail> details = new ArrayList<>();
        infoById.getData().setId(null);
        infoById2.getData().setId(null);
        infoById3.getData().setId(null);
        infoById4.getData().setId(null);
        infoById5.getData().setId(null);

        details.add(infoById.getData());
        details.add(infoById2.getData());
        details.add(infoById3.getData());
        details.add(infoById4.getData());
        details.add(infoById5.getData());
        for (Long datum : service.importDatas(details).getData()) {
            System.out.println(datum);
        }
    }

    @Test
    public void updateById() {
        List<Long> id = new ArrayList<>();
        id.add(297L);
        id.add(298L);
        id.add(299L);
        id.add(300L);
        Result<Boolean> result = service.updateApprovalStatusById(id, 2);
    }
}
