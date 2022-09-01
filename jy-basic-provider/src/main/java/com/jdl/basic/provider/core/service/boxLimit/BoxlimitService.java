package com.jdl.basic.provider.core.service.boxLimit;


import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 11:33
 * @Description:
 */

public interface BoxlimitService {

    void insertBoxlimitConfig(BoxLimitConfigDto po);

    /**
     * PDA建箱包裹数 列表查询
     */
    PageDto<BoxLimitConfigDto> listData(BoxLimitConfigQueryDto dto);

    /**
     * 导入模板数据
     */
    Result importData(List<BoxLimitConfigDto> data, LoginUser operator);

    /**
     * PDA建箱包裹数 创建配置
     */
    Result create(BoxLimitConfigDto dto, LoginUser operator);

    /**
     * PDA建箱包裹数 更新配置
     */
    Result update(BoxLimitConfigDto dto, LoginUser operator);

    /**
     * 根据ID批量删除
     */
    Result delete(List<Long> ids, String operatorErp);

    /**
     * 查询展示名称
     */
    Result<String> querySiteNameById(Integer siteId);


    /**
     * 查询符合条件的数据条数
     *
     * @param dto
     * @return
     */
    Result<Integer> countByCondition(BoxLimitConfigQueryDto dto);

    /**
     * 获取箱号类型集合
     *
     * @return
     */
    Result<List<String>> getBoxTypeList();

    Result<Integer> getLimitNums(Integer createSiteCode, String type);

}
