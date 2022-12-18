package com.jdl.basic.api.service.transferDp;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteUpdateDto;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteVo;

/**
 * description
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-11-23 17:29:43 周三
 */
public interface ConfigTransferDpApi {

    /**
     * 统计个数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<Long> queryCount(ConfigTransferDpSiteQo configTransferDpSiteQo);

    /**
     * 查询分页列表
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<PageData<ConfigTransferDpSiteVo>> queryPageList(ConfigTransferDpSiteQo configTransferDpSiteQo);

    /**
     * 添加一条记录
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    Result<Long> add(ConfigTransferDpSite configTransferDpSite);

    /**
     * 根据ID更新
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    Result<Boolean> updateById(ConfigTransferDpSite configTransferDpSite);

    /**
     * 根据ID逻辑删除
     * @param configTransferDpSite 配置记录
     * @return 处理结果
     */
    Result<Boolean> logicDeleteById(ConfigTransferDpSite configTransferDpSite);

    /**
     * 根据ID逻辑批量删除
     * @param configTransferDpSiteQo 配置记录
     * @return 处理结果
     */
    Result<Boolean> batchDeleteById(ConfigTransferDpSiteUpdateDto configTransferDpSiteUpdateDto);
}
