package com.jdl.basic.provider.core.dao.transferDp;

import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteUpdateDto;
import com.jdl.basic.provider.core.dao.base.BaseDao;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;

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

}
