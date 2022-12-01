package com.jdl.basic.provider.workStation;

import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleVo;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.SiteWaveScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SiteWaveScheduleServiceTest {

    @Autowired
    SiteWaveScheduleService siteWaveScheduleService;

    @Test
    public void importDatasTest(){
        List<SiteWaveScheduleVo> list = new ArrayList<>();

        SiteWaveScheduleVo v1 = new SiteWaveScheduleVo();

        Map<String, String> dayShift = new HashMap<>();
        dayShift.put("time1", "07:00-11:00");
        dayShift.put("time2", "11:00-12:00");
        dayShift.put("time3", "");

        Map<String, String> midShift = new HashMap<>();
        midShift.put("time1", "12:00-14:00");
        midShift.put("time2", "14:00-16:00");
        midShift.put("time3", "16:00-17:00");

        Map<String, String> nightShift = new HashMap<>();
        nightShift.put("time1", "");
        nightShift.put("time2", "");
        nightShift.put("time3", "");

        v1.setOrgCode(6);
        v1.setSiteCode(910);
        v1.setDayShift(dayShift);
        v1.setMidShift(midShift);
        v1.setNightShift(nightShift);

        v1.setCreateUser("wuyoude");
        v1.setCreateUserName("吴有德");
        v1.setCreateTime(new Date());



        SiteWaveScheduleVo v2 = new SiteWaveScheduleVo();


        v2.setOrgCode(6);
        v2.setSiteCode(911);
        v2.setDayShift(dayShift);
        v2.setMidShift(midShift);
        v2.setNightShift(nightShift);

        v2.setCreateUser("wuyoude");
        v2.setCreateUserName("吴有德");
        v2.setCreateTime(new Date());

        list.add(v1);
        list.add(v2);

        System.out.println(JsonHelper.toJSONString(siteWaveScheduleService.importDatas(list)));
    }

    @Test
    public void queryPageTest(){

        SiteWaveScheduleQuery query = new SiteWaveScheduleQuery();
        query.setOrgCode(6);
        System.out.println(JsonHelper.toJSONString(siteWaveScheduleService.queryPageList(query)));
    }
}
