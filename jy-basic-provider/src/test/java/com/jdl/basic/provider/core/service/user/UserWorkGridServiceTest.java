package com.jdl.basic.provider.core.service.user;


import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.*;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.config.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class UserWorkGridServiceTest {

    @Autowired
    private UserWorkGridService userWorkGridService;

    @Autowired
    CacheService jimdbCacheService;

    @Test
    public void  batchQueryUserWorkGridByGridKey() {
        UserWorkGridBatchRequest request = new UserWorkGridBatchRequest();
        List<UserWorkGrid> list = new ArrayList<>();
        UserWorkGrid userWorkGrid = new UserWorkGrid();
        userWorkGrid.setWorkGridKey("CDWG00000022001");
        list.add(userWorkGrid);
        request.setUserWorkGrids(list);
        Result<List<UserWorkGrid>> result = userWorkGridService.batchQueryUserWorkGridByGridKey(request);
        log.info("queryPageList response {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void batchInsert() {
        List<UserWorkGrid> list = new ArrayList<>();

        UserWorkGrid userWorkGrid1 = new UserWorkGrid();
        userWorkGrid1.setWorkGridKey("CDWG00000022111");
        userWorkGrid1.setNature("1");
        userWorkGrid1.setUserId(20L);
        userWorkGrid1.setCreateUserErp("wuyoude");
        userWorkGrid1.setCreateUserName("吴有德");
        userWorkGrid1.setCreateTime(new Date());
        userWorkGrid1.setUpdateUserErp("wuyoude");
        userWorkGrid1.setUpdateUserName("吴有德");
        userWorkGrid1.setUpdateTime(new Date());

        UserWorkGrid userWorkGrid2 = new UserWorkGrid();
        userWorkGrid2.setWorkGridKey("CDWG00000022111");
        userWorkGrid2.setNature("2");
        userWorkGrid2.setUserId(2L);
        userWorkGrid2.setCreateUserErp("wuyoude");
        userWorkGrid2.setCreateUserName("吴有德");
        userWorkGrid2.setCreateTime(new Date());
        userWorkGrid2.setUpdateUserErp("wuyoude");
        userWorkGrid2.setUpdateUserName("吴有德");
        userWorkGrid2.setUpdateTime(new Date());

        list.add(userWorkGrid1);


        UserWorkGridBatchRequest request = new UserWorkGridBatchRequest();
        request.setUserWorkGrids(list);
        request.setUpdateUserName("吴有德");
        request.setUpdateUserErp("wuyoude");
        request.setUpdateTime(new Date());
        request.setWorkGridKey("CDWG00000022111");

        Result result = userWorkGridService.batchInsert(request);
        log.info("batchInsert response {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void  batchDelete() {

        List<UserWorkGrid> list = new ArrayList<>();

        UserWorkGrid userWorkGrid1 = new UserWorkGrid();
        userWorkGrid1.setWorkGridKey("CDWG00000019007");
        userWorkGrid1.setNature("1");
        userWorkGrid1.setUserId(2L);
        userWorkGrid1.setCreateUserErp("wuyoude");
        userWorkGrid1.setCreateUserName("吴有德");
        userWorkGrid1.setCreateTime(new Date());
        userWorkGrid1.setUpdateUserErp("wuyoude");
        userWorkGrid1.setUpdateUserName("吴有德");
        userWorkGrid1.setUpdateTime(new Date());
        list.add(userWorkGrid1);

        UserWorkGridBatchRequest request = new UserWorkGridBatchRequest();
        request.setUserWorkGrids(list);

        userWorkGridService.batchDelete(request);
    }

    @Test
    public void batchUpdate() {
        List<UserWorkGrid> list = new ArrayList<>();

        UserWorkGrid userWorkGrid1 = new UserWorkGrid();
        userWorkGrid1.setWorkGridKey("CDWG00000022111");
        userWorkGrid1.setNature("1");
        userWorkGrid1.setUserId(20L);
        userWorkGrid1.setCreateUserErp("wuyoude");
        userWorkGrid1.setCreateUserName("吴有德");
        userWorkGrid1.setCreateTime(new Date());
        userWorkGrid1.setUpdateUserErp("wuyoude");
        userWorkGrid1.setUpdateUserName("吴有德");
        userWorkGrid1.setUpdateTime(new Date());

        UserWorkGrid userWorkGrid2 = new UserWorkGrid();
        userWorkGrid2.setWorkGridKey("CDWG00000022111");
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

        List<UserWorkGrid> list2 = new ArrayList<>();

        UserWorkGrid userWorkGrid3 = new UserWorkGrid();
        userWorkGrid3.setWorkGridKey("CDWG00000019007");
        userWorkGrid3.setNature("1");
        userWorkGrid3.setUserId(2L);
        userWorkGrid3.setCreateUserErp("wuyoude");
        userWorkGrid3.setCreateUserName("吴有德");
        userWorkGrid3.setCreateTime(new Date());
        userWorkGrid3.setUpdateUserErp("wuyoude");
        userWorkGrid3.setUpdateUserName("吴有德");
        userWorkGrid3.setUpdateTime(new Date());
        list2.add(userWorkGrid3);

        UserWorkGridBatchUpdateRequest request = new UserWorkGridBatchUpdateRequest();
        request.setDeleteUserWorkGrids(list);
        request.setAddUserWorkGrids(list2);
        userWorkGridService.batchUpdateUserWorkGrid(request);
    }

    @Test
    public void queryByUserIds() {
        List<UserWorkGrid> userWorkGrids = new ArrayList<>();
        UserWorkGrid userWorkGrid = new UserWorkGrid();
        userWorkGrid.setUserId(2L);
        userWorkGrids.add(userWorkGrid);
        UserWorkGridBatchRequest request = new UserWorkGridBatchRequest();
        request.setUserWorkGrids(userWorkGrids);
        Result result = userWorkGridService.queryByUserIds(request);
        log.info("getWorkGridDistributedStaff response {}", JsonHelper.toJSONString(result));

    }

    @Test
    public void getWorkGridDistributedStaff() {
        UserWorkGridRequest request = new UserWorkGridRequest();
        request.setWorkGridKey("CDWG00000022111");
        Result<List<JyUser>> result = userWorkGridService.getWorkGridDistributedStaff(request);

        log.info("getWorkGridDistributedStaff response {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void batchQueryDeletedUserWorkGrid() {
        UserWorkGridBatchRequest request = new UserWorkGridBatchRequest();
        List<UserWorkGrid> userWorkGrids = new ArrayList<>();
        UserWorkGrid userWorkGrid = new UserWorkGrid();
        userWorkGrid.setWorkGridKey("CDWG00000019007");
        userWorkGrids.add(userWorkGrid);
        request.setUserWorkGrids(userWorkGrids);
        request.setUpdateTime(DateUtils.addDays(new Date(), -15));
        Result<List<UserWorkGrid>> result = userWorkGridService.batchQueryDeletedUserWorkGrid(request);

        log.info("getWorkGridDistributedStaff response {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void batchUpdateUserWorkGrid() {
        UserWorkGridBatchUpdateRequest request = new UserWorkGridBatchUpdateRequest();

        List<UserWorkGrid> userWorkGrids = new ArrayList<>();
        UserWorkGrid userWorkGrid = new UserWorkGrid();
        userWorkGrid.setWorkGridKey("CDWG00000028001");
        userWorkGrid.setUserId(127L);
        userWorkGrids.add(userWorkGrid);

        request.setWorkGridKey("CDWG00000028001");
        request.setSiteCode(910);
        request.setDeleteUserWorkGrids(userWorkGrids);
        request.setUpdateUserErp("wuyoude");
        request.setUpdateUserName("wuyoude");
        request.setUpdateTime(new Date());
        userWorkGridService.batchUpdateUserWorkGrid(request);
    }

    @Test
    public void hMSet() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "3");
        map.put("3", "4");
        jimdbCacheService.hMSet("123456789", map);
        log.info("get all {}",JsonHelper.toJSONString(jimdbCacheService.hGetAll("123456789")));
    }

    @Test
    public void hMGet() {
        String[] fieldKey = new String[]{"153"};
        log.info("get all {}",JsonHelper.toJSONString(jimdbCacheService.hMGet("k_search_site_users:910", fieldKey)));
    }
}
