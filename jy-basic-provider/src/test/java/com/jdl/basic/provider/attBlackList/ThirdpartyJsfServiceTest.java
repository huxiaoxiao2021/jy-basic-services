package com.jdl.basic.provider.attBlackList;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyThirdpartyUser;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAgg;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAggQuery;
import com.jdl.basic.api.service.user.UserJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
@Slf4j
public class ThirdpartyJsfServiceTest {

    @Autowired
    private UserJsfService userJsfService;



    @Test
    public void insertTest() {
        JyThirdpartyUser jyThirdpartyUser =new JyThirdpartyUser();
        jyThirdpartyUser.setUserCode("52020119870603003X");
        jyThirdpartyUser.setUserName("测试用户2");
        jyThirdpartyUser.setMobile("18311112222");
        jyThirdpartyUser.setPresentDate(new Date());
        jyThirdpartyUser.setTaskBizId("JTU1730177157730959360");
        jyThirdpartyUser.setTaskDetailBizId("JTU1730177157730959366");
        jyThirdpartyUser.setNature("4");
        jyThirdpartyUser.setNatureDesc("外部工");
        jyThirdpartyUser.setSiteCode(910);
        jyThirdpartyUser.setDeadlineTime(new Date());
        jyThirdpartyUser.setCompanyName("人瑞");
        jyThirdpartyUser.setCreateUser("zhangsan");
        jyThirdpartyUser.setCreateUserName("lisi");
        jyThirdpartyUser.setUpdateUser("wangwu");
        jyThirdpartyUser.setUpdateUserName("zhangliu");
        jyThirdpartyUser.setTaskType(1);


        JyThirdpartyUser jyThirdpartyUser1 =new JyThirdpartyUser();
        jyThirdpartyUser1.setUserCode("13112322222222");
        jyThirdpartyUser1.setUserName("张亚南2");
        jyThirdpartyUser1.setMobile("18311112222");
        jyThirdpartyUser1.setPresentDate(new Date());
        jyThirdpartyUser1.setTaskBizId("JTU1730177157730951234");
        jyThirdpartyUser1.setTaskDetailBizId("JTU1730177157730954321");
        jyThirdpartyUser1.setNature("5");
        jyThirdpartyUser1.setNatureDesc("临时工");
        jyThirdpartyUser1.setSiteCode(910);
        jyThirdpartyUser1.setDeadlineTime(new Date());
        jyThirdpartyUser1.setCompanyName("人瑞");
        jyThirdpartyUser1.setCreateUser("zhangsan");
        jyThirdpartyUser1.setCreateUserName("lisi");
        jyThirdpartyUser1.setUpdateUser("wangwu");
        jyThirdpartyUser1.setUpdateUserName("zhangliu");
        jyThirdpartyUser1.setTaskType(1);

//        userJsfService.addJyThirdpartyUserOne(jyThirdpartyUser);

        JyThirdpartyUserSaveDto dto=new JyThirdpartyUserSaveDto();
        List<JyThirdpartyUser> list=new ArrayList<>();
        list.add(jyThirdpartyUser1);
        dto.setJyThirdpartyUserList(list);
        userJsfService.saveJyThirdpartyUser(dto);
        System.out.println("over");
    }

    @Test
    public void query() {JyThirdpartyUser jyThirdpartyUser =new JyThirdpartyUser();
        jyThirdpartyUser.setSiteCode(1);
        Result<List<JyThirdpartyUser>> rs=userJsfService.queryJyThirdpartyUserByCondition(jyThirdpartyUser);
        JyThirdpartyUser jyThirdpartyUser1 =new JyThirdpartyUser();
        jyThirdpartyUser1.setSiteCode(1);
        jyThirdpartyUser1.setYn(false);
        userJsfService.updateJyThirdpartyUserYn(jyThirdpartyUser1);
    }

    @Test
    public void queryA() {
        ReserveTaskDetailAggQuery query =new ReserveTaskDetailAggQuery();
        List<String> taskDetailBizIdList =new ArrayList<>();
        taskDetailBizIdList.add("JTU1730177157730959366");
        query.setTaskDetailBizIdList(taskDetailBizIdList);
        Result<List<ReserveTaskDetailAgg>> rs=userJsfService.countTpUserGroupByNature(query);

        System.out.println(JsonHelper.toJSONString(rs));
    }

}
