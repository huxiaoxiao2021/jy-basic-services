package com.jdl.basic.provider.core.provider.cross;

import com.jdl.basic.api.domain.cross.SortCrossDetail;
import com.jdl.basic.api.domain.cross.SortCrossQuery;
import com.jdl.basic.api.domain.cross.SortCrossUpdateRequest;
import com.jdl.basic.api.service.cross.SortCrossJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liwenji
 * @date 2022-11-09 10:46
 */
@Component
@Slf4j
public class SortCrossJsfServiceImpl implements SortCrossJsfService {
    
    @Autowired
    private SortCrossService sortCrossService;
    
    @Override
    public Result<PageDto<SortCrossDetail>> queryPage(SortCrossQuery query) {
        log.info("开始分页查询滑道笼车配置，查询条件为：{}",query);
        Result<PageDto<SortCrossDetail>> result = new Result<>();
        result.toFail("分页查询滑道笼车配置失败");
        PageDto<SortCrossDetail> queryPage = sortCrossService.queryPage(query);
        if (queryPage != null) {
            result.setData(queryPage);
            result.toSuccess();
        }
        return result;
    }

    @Override
    public Result<Boolean> updateEnableByIds(SortCrossUpdateRequest request) {
        log.info("开始更新滑道笼车配置的状态: {}", JsonHelper.toJSONString(request));
        Result<Boolean> result = new Result<>();
        result.toFail("批量更新滑道笼车配置状态失败");
        if (sortCrossService.updateEnableByIds(request)) {
            return result.toSuccess("更新成功");
        }
        return result;
    }
}
