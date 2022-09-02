package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSONObject;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridQuery;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridVo;
import com.jdl.basic.api.service.workStation.WorkAbnormalGridBindingJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkAbnormalGridBindingJsfServiceImplTest {


    @Autowired
    private WorkAbnormalGridBindingJsfService workAbnormalGridBindingJsfService;

    @Test
    public void testGetAbnormalGrid() {

        WorkStationFloorGridQuery po = new WorkStationFloorGridQuery();
        po.setSiteCode(100200);
        po.setFloor(5);
        po.setGridCode("1111-02");
        Result<WorkStationFloorGridVo> abnormalGrid = workAbnormalGridBindingJsfService.getAbnormalGrid(po);
        Assert.assertEquals(abnormalGrid.getData().getGridCode(),"1233-03");
        Assert.assertEquals(abnormalGrid.getData().getFloor(), Integer.valueOf(1));
        Assert.assertEquals(abnormalGrid.getData().getSiteCode(), Integer.valueOf(100200));
    }

}
