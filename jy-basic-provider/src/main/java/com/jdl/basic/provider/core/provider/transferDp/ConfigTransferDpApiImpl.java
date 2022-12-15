package com.jdl.basic.provider.core.provider.transferDp;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteVo;
import com.jdl.basic.api.service.transferDp.ConfigTransferDpApi;
import com.jdl.basic.provider.core.service.transferDp.ConfigTransferDpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-11-23 17:29:43 周三
 */
@Slf4j
@Service
public class ConfigTransferDpApiImpl implements ConfigTransferDpApi {

    @Resource
    private ConfigTransferDpService configTransferDpService;

    /**
     * 统计个数
     *
     * @param configTransferDpSiteQo
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<Long> queryCount(ConfigTransferDpSiteQo configTransferDpSiteQo) {
        return configTransferDpService.queryCount(configTransferDpSiteQo);
    }

    /**
     * 查询分页列表
     *
     * @param configTransferDpSiteQo
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<PageData<ConfigTransferDpSiteVo>> queryPageList(ConfigTransferDpSiteQo configTransferDpSiteQo) {
        return configTransferDpService.queryPageList(configTransferDpSiteQo);
    }

    /**
     * 添加一条记录
     *
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    @Override
    public Result<Long> add(ConfigTransferDpSite configTransferDpSite) {
        return configTransferDpService.add(configTransferDpSite);
    }

    /**
     * 根据ID更新
     *
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> updateById(ConfigTransferDpSite configTransferDpSite) {
        return configTransferDpService.updateById(configTransferDpSite);
    }

    /**
     * 根据ID逻辑删除
     *
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    @Override
    public Result<Boolean> logicDeleteById(ConfigTransferDpSite configTransferDpSite) {
        return configTransferDpService.logicDeleteById(configTransferDpSite);
    }
}
