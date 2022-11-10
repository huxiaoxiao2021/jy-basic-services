package com.jdl.basic.provider.core.service.easyFreezeSite;

import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteDto;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteQueryDto;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.po.EasyFreezeSitePO;

import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 15:04
 * @Description:
 */
public interface EasyFreezeSiteService {

    /**
     *新增
     * @param
     * @return
     */
    Result<Boolean> insert(EasyFreezeSiteDto po, LoginUser loginUser);


    /**
     *根据主键获取详情
     * @param
     * @return
     */
    Result<EasyFreezeSiteDto> selectByPrimaryKey(Long id);

    /**
     *根据主键更新集箱包裹配置信息
     * @param
     * @return
     */
    Result<Boolean> updateByPrimaryKeySelective(EasyFreezeSiteDto po, LoginUser loginUser);

    /**
     * 根据条件获取易冻品站点配置列表
     * @param queryDto
     * @return
     */
    PageDto<EasyFreezeSiteDto> getEasyFreezeSiteListBypage(EasyFreezeSiteQueryDto queryDto);

    Result<Boolean> importEasyFreezeSiteList(List<EasyFreezeSiteDto> dtoList, LoginUser loginUser);
}
