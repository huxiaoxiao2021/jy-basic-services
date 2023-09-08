package com.jdl.basic.provider.attBlackList;

import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;
import com.jdl.basic.api.service.attBlackList.AttendanceBlackListJsfService;
import com.jdl.basic.common.utils.DateUtil;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
@Slf4j
public class AttendanceBlackListJsfServiceTest {

    @Autowired
    private AttendanceBlackListJsfService attendanceBlackListJsfService;


    @Test
    public void queryPageTest() {
        String s="131124198902152832";
        System.out.println(s.length());
        String start=s.substring(0,4);
        String end=s.substring(14,18);
        StringBuilder sn=new StringBuilder();
        System.out.println(sn.append(start).append("**********").append(end));
//        Date takeTime=DateUtil.parse("2023-08-29",DateUtil.FORMAT_DATE);
//        Date loseTime=DateUtil.parse("2023-09-07",DateUtil.FORMAT_DATE);
//        String date1=DateUtil.format(new Date(),DateUtil.FORMAT_DATE);
//        Date currentTime=DateUtil.parse(date1,DateUtil.FORMAT_DATE);
//        boolean bb=takeTime.before(currentTime);
//        boolean lose=loseTime.before(currentTime);
//        boolean current=currentTime.before(currentTime);
//
//        int sss= takeTime.compareTo(currentTime);
//        int nn= loseTime.compareTo(currentTime);
//        int mm= currentTime.compareTo(currentTime);
//        System.out.println("takeTime:"+takeTime+"loseTime:"+loseTime+"currentTime:"+currentTime);
//        System.out.println("bb:"+bb+"lose:"+lose+"current:"+current);
//        System.out.println("sss:"+sss+"nn:"+nn+"mm:"+mm);


//        AttendanceBlackListCondition attendanceBlackList = new AttendanceBlackListCondition();
//        attendanceBlackList.setPageNo(0);
//        attendanceBlackList.setPageSize(0);
//        attendanceBlackList.setUserCode("131124111111111121");
////        attendanceBlackList.setIdCard("1311241111111111112");
////        attendanceBlackList.setName("张");
////        attendanceBlackList.setTakeTime(DateUtil.parse("2023-08-29",DateUtil.FORMAT_DATE));
//        Result<PageDto<AttendanceBlackList>> rs=attendanceBlackListJsfService.queryByCondition(attendanceBlackList);
//        log.info("===========rs:{}", JsonHelper.toJSONString(rs));
    }

    @Test
    public void countTest() {
        AttendanceBlackListCondition attendanceBlackList = new AttendanceBlackListCondition();
        attendanceBlackList.setPageNo(0);
        attendanceBlackList.setPageSize(0);
        Result<Integer> rs= attendanceBlackListJsfService.countByCondition(attendanceBlackList);
        log.info("===========rs:{}", JsonHelper.toJSONString(rs));
    }

    @Test
    public void insertTest() {
        AttendanceBlackList attendanceBlackList = new AttendanceBlackList();
        attendanceBlackList.setUserCode("131124111111111121");
        attendanceBlackList.setName("马化腾21");
        attendanceBlackList.setTakeTime(DateUtil.parse("2023-08-21",DateUtil.FORMAT_DATE));
        attendanceBlackList.setLoseTime(DateUtil.parse("2023-09-28",DateUtil.FORMAT_DATE));
        attendanceBlackList.setNotes("bak12");
        attendanceBlackList.setOperatorErp("ext.sutengfei3");
        attendanceBlackList.setCancelFlag(0);
        Result<Integer> rs=attendanceBlackListJsfService.add(attendanceBlackList);
        AttendanceBlackList attendanceBlackList1 = new AttendanceBlackList();
        attendanceBlackList1.setUserCode("131124111111111121");
        attendanceBlackList1.setName("马化腾22");
        attendanceBlackList1.setTakeTime(DateUtil.parse("2023-08-21",DateUtil.FORMAT_DATE));
        attendanceBlackList1.setLoseTime(DateUtil.parse("2023-09-28",DateUtil.FORMAT_DATE));
        attendanceBlackList1.setNotes("bak12");
        attendanceBlackList1.setOperatorErp("ext.sutengfei3");
        attendanceBlackList1.setCancelFlag(0);
        Result<Integer> rs1=attendanceBlackListJsfService.add(attendanceBlackList1);
        log.info("===========rs:{}", JsonHelper.toJSONString(rs1));


    }
    @Test
    public void modifyTest() {
        AttendanceBlackList attendanceBlackList = new AttendanceBlackList();
        attendanceBlackList.setId(71L);
        attendanceBlackList.setUserCode("131124222222222222");
        attendanceBlackList.setName("赵六");
        attendanceBlackList.setTakeTime(DateUtil.parse("2023-07-01",DateUtil.FORMAT_DATE));
        attendanceBlackList.setLoseTime(DateUtil.parse("2023-07-28",DateUtil.FORMAT_DATE));
        attendanceBlackList.setNotes("bak34");
        attendanceBlackList.setOperatorErp("ext.sutengfei5");
        attendanceBlackList.setCancelFlag(1);
        Result<Integer>  rs= attendanceBlackListJsfService.modify(attendanceBlackList);
        log.info("===========rs:{}", JsonHelper.toJSONString(rs));
    }

    @Test
    public void deleteByIdTest() {
        Result<Integer>  rs= attendanceBlackListJsfService.remove(72);
        log.info("===========rs:{}", JsonHelper.toJSONString(rs));
    }
    @Test
    public void queryByUserCodeTest() {
        Result<AttendanceBlackList> rs=attendanceBlackListJsfService.queryByUerCode("131124199922222822");
        log.info("===========rs:{}", JsonHelper.toJSONString(rs));
    }
}
