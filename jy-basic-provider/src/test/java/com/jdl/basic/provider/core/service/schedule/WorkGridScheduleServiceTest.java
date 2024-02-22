package com.jdl.basic.provider.core.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.jim.cli.driver.types.KeyScanResult;
import com.jd.jim.cli.driver.types.ScanOptions;
import com.jdl.basic.api.domain.schedule.*;
import com.jdl.basic.api.enums.ScheduleTypeEnum;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.config.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridScheduleServiceTest {

    @Autowired
    private WorkGridScheduleService workGridScheduleService;

    @Autowired
    CacheService jimdbCacheService;

    public void batchInsert() {

    }
    public void batchDeleteByWorkGridKey() {

    }

    public void batchQueryByWorkGridKey() {

    }

    @Test
    public void querySiteScheduleByCondition() {
        WorkGridScheduleRequest request = new WorkGridScheduleRequest();
        request.setSiteCode(910);
        request.setSourceType(ScheduleTypeEnum.WORK_GRID.getCode());
        request.setWorkGridKey("CDWG00000022154");
        Result<List<WorkGridSchedule>> result = workGridScheduleService.querySiteScheduleByCondition(request);
        log.info("result {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void cleanWorkGridScheduleOldTime() {
        BatchCleanOldTimeRequest request = new BatchCleanOldTimeRequest();
        request.setWorkGridKeys(Arrays.asList("CDWG00000022020"));
        request.setOldStartTime(Constants.EMPTY_FILL);
        request.setOldEndTime(Constants.EMPTY_FILL);
        Result<Boolean> result = workGridScheduleService.cleanWorkGridScheduleOldTime(request);
        log.info("result {}",result);
    }

    @Test
    public void batchDeleteByScheduleKey() throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date invalidTime = sdf.parse("2024-02-02 04:00:00");
        WorkGridSchedule schedule = new WorkGridSchedule();
        schedule.setScheduleKey("CDWG00000022056-1-1");
//        schedule.setInvalidTime(invalidTime);
        schedule.setStartTime("02:00");
        schedule.setEndTime("04:00");
        WorkGridScheduleBatchRequest request = new WorkGridScheduleBatchRequest();
        request.setUpdateUserErp("zhangsan");
        request.setUpdateUserName("张三");
        request.setUpdateTime(new Date());
        request.setWorkGridSchedules(Collections.singletonList(schedule));
        Result<Boolean> result = workGridScheduleService.batchDeleteByScheduleKey(request);
        log.info("result {}",result);
    }

    @Test
    public void listValidWorkGridScheduleByTime() {
        ValidWorkGridScheduleRequest request = new ValidWorkGridScheduleRequest();
        request.setWorkGridKey("CDWG00000038006");
        Date validTime = DateHelper.parseDate("2024-02-17 00:00:00", DateHelper.DATE_FORMAT);
        Date invalidTime = DateHelper.parseDate("2024-02-18 00:00:00", DateHelper.DATE_FORMAT);
        request.setValidTime(validTime);
        request.setInvalidTime(invalidTime);
        String cacheKey = String.format(CacheKeyConstants.CACHE_KEY_ALL_VALID_WORK_GRID_SCHEDULE_IGNORE_YN, request.getWorkGridKey());
        jimdbCacheService.del(cacheKey);
        Result<List<ScheduleValidTimeDto>> result = workGridScheduleService.listValidCutWorkGridScheduleByTime(request);
        log.info("result {}",result);
    }
}
