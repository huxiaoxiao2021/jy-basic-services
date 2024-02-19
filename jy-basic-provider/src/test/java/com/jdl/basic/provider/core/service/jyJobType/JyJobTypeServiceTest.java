package com.jdl.basic.provider.core.service.jyJobType;

import com.jdl.basic.api.domain.jyJobType.JyJobType;
import com.jdl.basic.api.domain.jyJobType.JyJobTypeQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigVo;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigService;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigServiceTest;
import com.jdl.basic.utils.EntityUtil;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author pengchong28
 * @description 拣运工种服务接口测试
 * @date 2024/2/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
@ActiveProfiles("test")
public class JyJobTypeServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkGridManagerTaskConfigServiceTest.class);

    @Autowired
    @Qualifier("jyJobTypeService")
    private JyJobTypeService jyJobTypeService;


    @Test
    public void test(){

        /*JyJobType insertData = new JyJobType();
        insertData.setName("达达1");
        insertData.setCode(5);
        Result<Boolean> insert = jyJobTypeService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());*/

        JyJobTypeQuery query = new JyJobTypeQuery();
        Result<PageDto<JyJobType>> pageDtoResult = jyJobTypeService.queryPageList(query);
        Assert.assertTrue(pageDtoResult != null && CollectionUtils.isNotEmpty(pageDtoResult.getData().getResult()));
        logger.info("queryPageList:{}"+ JsonHelper.toJSONString(pageDtoResult));

       /* JyJobType jyJobType = pageDtoResult.getData().getResult().get(0);
        jyJobType.setAutoSignOutHour(33);
        jyJobType.setCode(5);
        Result<Boolean> updateById = jyJobTypeService.updateById(jyJobType);
        Assert.assertTrue(updateById != null && updateById.getData());*/

        /*JyJobType jyJobType1 = new JyJobType();
        jyJobType1.setName("零时工");
        jyJobType1.setId(1L);
        List<JyJobType> jyJobTypes = jyJobTypeService.queryListByCondition(jyJobType1);
        Assert.assertTrue(CollectionUtils.isNotEmpty(jyJobTypes));

        JyJobType jyJobType2 = new JyJobType();
        jyJobType2.setId(1L);
        jyJobType1.setCode(1);
        List<JyJobType> jyJobTypes1 = jyJobTypeService.queryListByCondition(jyJobType2);
        Assert.assertTrue(CollectionUtils.isNotEmpty(jyJobTypes1));*/


    }
}
