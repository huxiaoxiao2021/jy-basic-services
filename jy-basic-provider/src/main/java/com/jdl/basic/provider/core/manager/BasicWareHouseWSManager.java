package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.domain.PsStoreInfo;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.dto.PsStoreInfoRequest;
import com.jd.ql.basic.util.PageUtil;

import java.util.List;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2023/6/28 4:01 PM
 */
public interface BasicWareHouseWSManager {

    /**
     * 根据条件分页查询库房数据
     * 
     * @param psStoreInfoRequest
     * @param pageUtil
     * @return
     */
    PageDto<List<PsStoreInfo>> query(PsStoreInfoRequest psStoreInfoRequest, PageUtil pageUtil);
}
