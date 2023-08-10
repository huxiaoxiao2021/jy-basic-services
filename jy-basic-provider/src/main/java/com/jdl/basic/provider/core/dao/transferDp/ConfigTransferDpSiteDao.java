package com.jdl.basic.provider.core.dao.transferDp;

import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteUpdateDto;
import com.jdl.basic.provider.core.dao.base.BaseDao;

import java.util.List;

/**
 * Description: <br>
 * Copyright: Copyright (c) 2020<br>
 * Company: jd.com 京东物流JDL<br>
 * 
 * @author fanggang7
 * @time 2022-11-23 17:29:43 周三
 */
public interface ConfigTransferDpSiteDao extends BaseDao<ConfigTransferDpSite, ConfigTransferDpSiteQo> {

    /**
     * 删除数据
     * @param configTransferDpSite
     * @return
     */
    int deleteByPrimaryKey(ConfigTransferDpSite configTransferDpSite);

    /**
     * 批量删除
     * @return 删除影响条数 >= 1 删除成功，= 0 删除失败
     * @author fanggang7
     * @time 2019-12-20 18:47:26 周五
     */
    long batchDeleteByPrimaryKeys(ConfigTransferDpSiteUpdateDto configTransferDpSiteUpdateDto);

    /**
     * 刷数-分页查询
     * @param startId
     * @return
     */
    List<ConfigTransferDpSite> brushQueryAllByPage(Integer startId);

    /**
     * 刷数-批量更新
     * @param list
     * @return
     */
    Integer brushUpdateById(List<ConfigTransferDpSite> list);
}
