package com.jdl.basic.api.service.easyFreeze;

import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteDto;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteQueryDto;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 15:51
 * @Description:
 */
public interface EasyFreezeSiteJsfService {
    /**
     *新增
     * @param
     * @return
     */
    Result<Boolean> insert(EasyFreezeSiteDto Dto, LoginUser loginUser);


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
    Result<PageDto<EasyFreezeSiteDto>> getEasyFreezeSiteListBypage(EasyFreezeSiteQueryDto queryDto);

    /**
     * 导入易冻品配置数据
     * @param dtoList
     * @param loginUser
     * @return
     */
    Result<Boolean> importEasyFreezeSiteList(List<EasyFreezeSiteDto> dtoList, LoginUser loginUser);
}
