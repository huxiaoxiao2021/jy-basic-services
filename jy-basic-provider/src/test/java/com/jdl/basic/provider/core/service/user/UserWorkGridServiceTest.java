package com.jdl.basic.provider.core.service.user;


import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;
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

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class UserWorkGridServiceTest {

    @Autowired
    private UserWorkGridService userWorkGridService;

    @Test
    public void queryPageList() {
        UserWorkGridRequest request = new UserWorkGridRequest();
        request.setPageNumber(1);

        Result<List<UserWorkGrid>> result = userWorkGridService.queryPageList(request);
        log.info("queryPageList response {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void  queryRecordDetail() {

    }

    @Test
    public void queryDifference() {

    }

    @Test
    public void queryTotal() {

        UserWorkGridRequest request = new UserWorkGridRequest();

        Result<Long> result = userWorkGridService.queryTotal(request);
        log.info("queryPageList response {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void batchInsert() {
        List<UserWorkGrid> list = new ArrayList<>();

        UserWorkGrid userWorkGrid1 = new UserWorkGrid();
        userWorkGrid1.setWorkGridKey("CDWG00000019007");
        userWorkGrid1.setNature("1");
        userWorkGrid1.setUserId(1L);
        userWorkGrid1.setCreateUserErp("wuyoude");
        userWorkGrid1.setCreateUserName("吴有德");
        userWorkGrid1.setCreateTime(new Date());
        userWorkGrid1.setUpdateUserErp("wuyoude");
        userWorkGrid1.setUpdateUserName("吴有德");
        userWorkGrid1.setUpdateTime(new Date());

        UserWorkGrid userWorkGrid2 = new UserWorkGrid();
        userWorkGrid2.setWorkGridKey("CDWG00000022001");
        userWorkGrid2.setNature("2");
        userWorkGrid2.setUserId(2L);
        userWorkGrid2.setCreateUserErp("wuyoude");
        userWorkGrid2.setCreateUserName("吴有德");
        userWorkGrid2.setCreateTime(new Date());
        userWorkGrid2.setUpdateUserErp("wuyoude");
        userWorkGrid2.setUpdateUserName("吴有德");
        userWorkGrid2.setUpdateTime(new Date());

        list.add(userWorkGrid1);
        list.add(userWorkGrid2);

        userWorkGridService.batchInsert(list);
    }

    @Test
    public void  batchDelete() {

        List<UserWorkGrid> list = new ArrayList<>();

        UserWorkGrid userWorkGrid1 = new UserWorkGrid();
        userWorkGrid1.setWorkGridKey("CDWG00000019007");
        userWorkGrid1.setNature("1");
        userWorkGrid1.setUserId(1L);
        userWorkGrid1.setCreateUserErp("wuyoude");
        userWorkGrid1.setCreateUserName("吴有德");
        userWorkGrid1.setCreateTime(new Date());
        userWorkGrid1.setUpdateUserErp("wuyoude");
        userWorkGrid1.setUpdateUserName("吴有德");
        userWorkGrid1.setUpdateTime(new Date());
        list.add(userWorkGrid1);

        userWorkGridService.batchDelete(list);
    }

    @Test
    public void queryByUserIds() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(2L);
        userWorkGridService.queryByUserIds(userIds);
    }

    @Test
    public void getWorkGridDistributedStaff() {

    }
}
