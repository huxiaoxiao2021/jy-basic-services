package com.jdl.basic.provider.workStation;

import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.SiteAttendPlanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SiteAttendPlanTest {

    @Autowired
    SiteAttendPlanService siteAttendPlanService;

    @Test
    public void importDatasTest(){

        HashMap<String, Integer> dayShift = new HashMap<>();
        dayShift.put("formalEmployee", 10);
        dayShift.put("dispatchEmployee", 10);
        dayShift.put("tmpEmployee", 10);
        dayShift.put("hoursEmployee", 10);
        dayShift.put("outsourceEmployee", 10);

        HashMap<String, Integer> midShift = new HashMap<>();
        midShift.put("formalEmployee", 10);
        midShift.put("dispatchEmployee", 10);
        midShift.put("tmpEmployee", 10);
        midShift.put("hoursEmployee", 10);
        midShift.put("outsourceEmployee", 10);

        HashMap<String, Integer> nightShift = new HashMap<>();
        nightShift.put("formalEmployee", 10);
        nightShift.put("dispatchEmployee", 10);
        nightShift.put("tmpEmployee", 10);
        nightShift.put("hoursEmployee", 10);
        nightShift.put("outsourceEmployee", 10);

        List<SiteAttendPlanVo> list = new ArrayList<>();
        SiteAttendPlanVo vo = new SiteAttendPlanVo();
        vo.setPlanAttendTime("2022/12/29");
        vo.setOrgCode(6);
        vo.setOrgName("华北");
        vo.setSiteCode(910);
        vo.setSiteName("马驹桥分拣中心");
        vo.setBusinessLineCode("64");
        vo.setBusinessLineName("分拣中心");
        vo.setDayShift(dayShift);
        vo.setMidShift(midShift);
        vo.setNightShift(nightShift);

        vo.setStatus(0);
        vo.setCreateUser("wuyoude");
        vo.setCreateUserName("吴有德");
        vo.setConfirmUser("wuyoude");
        vo.setConfirmUserName("吴有德");
        vo.setCreateTime(new Date());
        vo.setUpdateUser("wuyoude");
        vo.setUpdateUserName("吴有德");
        vo.setUpdateTime(new Date());

        SiteAttendPlanVo v1 = new SiteAttendPlanVo();
        v1.setPlanAttendTime("2022/12/30");
        v1.setOrgCode(6);
        v1.setOrgName("华北");
        v1.setSiteCode(910);
        v1.setSiteName("马驹桥分拣中心");
        v1.setBusinessLineCode("64");
        v1.setBusinessLineName("分拣中心");
        v1.setDayShift(dayShift);
        v1.setMidShift(midShift);
        v1.setNightShift(nightShift);

        v1.setStatus(0);
        v1.setCreateUser("wuyoude");
        v1.setCreateUserName("吴有德");
        v1.setConfirmUser("wuyoude");
        v1.setConfirmUserName("吴有德");
        v1.setCreateTime(new Date());
        v1.setUpdateUser("wuyoude");
        v1.setUpdateUserName("吴有德");
        v1.setUpdateTime(new Date());

        list.add(vo);
        list.add(v1);

        System.out.println(JsonHelper.toJSONString(siteAttendPlanService.importDatas(list)));

    }

    @Test
    public void confirmRecordsTest(){
        HashMap<String, Integer> dayShift = new HashMap<>();
        dayShift.put("formalEmployee", 10);
        dayShift.put("dispatchEmployee", 10);
        dayShift.put("tmpEmployee", 10);
        dayShift.put("hoursEmployee", 10);
        dayShift.put("outsourceEmployee", 10);

        HashMap<String, Integer> midShift = new HashMap<>();
        midShift.put("formalEmployee", 10);
        midShift.put("dispatchEmployee", 10);
        midShift.put("tmpEmployee", 10);
        midShift.put("hoursEmployee", 10);
        midShift.put("outsourceEmployee", 10);

        HashMap<String, Integer> nightShift = new HashMap<>();
        nightShift.put("formalEmployee", 10);
        nightShift.put("dispatchEmployee", 10);
        nightShift.put("tmpEmployee", 10);
        nightShift.put("hoursEmployee", 10);
        nightShift.put("outsourceEmployee", 10);

        List<SiteAttendPlanVo> list = new ArrayList<>();
        SiteAttendPlanVo vo = new SiteAttendPlanVo();
        vo.setPlanAttendTime("2022/12/29");
        vo.setOrgCode(6);
        vo.setOrgName("华北");
        vo.setSiteCode(910);
        vo.setSiteName("马驹桥分拣中心");
        vo.setBusinessLineCode("64");
        vo.setBusinessLineName("分拣中心");
        vo.setDayShift(dayShift);
        vo.setMidShift(midShift);
        vo.setNightShift(nightShift);

        vo.setStatus(0);
        vo.setCreateUser("wuyoude");
        vo.setCreateUserName("吴有德");
        vo.setConfirmUser("wuyoude");
        vo.setConfirmUserName("吴有德");
        vo.setCreateTime(new Date());
        vo.setUpdateUser("wuyoude");
        vo.setUpdateUserName("吴有德");
        vo.setUpdateTime(new Date());

        SiteAttendPlanVo v1 = new SiteAttendPlanVo();
        v1.setPlanAttendTime("2022/12/30");
        v1.setOrgCode(6);
        v1.setOrgName("华北");
        v1.setSiteCode(910);
        v1.setSiteName("马驹桥分拣中心");
        v1.setBusinessLineCode("64");
        v1.setBusinessLineName("分拣中心");
        v1.setDayShift(dayShift);
        v1.setMidShift(midShift);
        v1.setNightShift(nightShift);

        v1.setStatus(0);
        v1.setCreateUser("wuyoude");
        v1.setCreateUserName("吴有德");
        v1.setConfirmUser("wuyoude");
        v1.setConfirmUserName("吴有德");
        v1.setCreateTime(new Date());
        v1.setUpdateUser("wuyoude");
        v1.setUpdateUserName("吴有德");
        v1.setUpdateTime(new Date());

        list.add(vo);
        System.out.println(siteAttendPlanService.confirmRecords(list));
    }

    @Test
    public void queryPageList() {
        SiteAttendPlanQuery query = new SiteAttendPlanQuery();
//        query.setStartTimeStr("2022-12-29");
//        query.setEndTimeStr("2022-12-30");
        query.setOrgCode(6);
        query.setSiteCode(910);
        System.out.println(JsonHelper.toJSONString(siteAttendPlanService.queryPageList(query)));
    }

    @Test
    public void getJson(){
        System.out.println(15 < Constants.YN_YES);
    }
}
