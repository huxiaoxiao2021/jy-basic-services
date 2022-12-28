package com.jdl.basic.provider.core.dao.easyFreezeSite;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteDto;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteQueryDto;
import com.jdl.basic.provider.core.po.EasyFreezeSitePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 11:19
 * @Description:
 */
public interface EasyFreezeSiteDao {



    /**
     *新增
     * @param
     * @return
     */
    int insert(EasyFreezeSitePO po);


    /**
     *根据主键获取详情
     * @param
     * @return
     */
    EasyFreezeSitePO selectByPrimaryKey(Long id);

    /**
     * 根据条件获取单个配置信息
     * @param dto
     * @return
     */
    EasyFreezeSitePO selectOneByCondition(EasyFreezeSiteDto dto);

    /**
     * 根据站点id获取站点配置信息
     * @param siteCode
     * @return
     */
    EasyFreezeSitePO selectOneBysiteCode(Integer siteCode);

    /**
     *根据主键更新集箱包裹配置信息
     * @param
     * @return
     */
    int updateByPrimaryKeySelective(EasyFreezeSitePO po);


    /**
     * 获取配置列表
     * @param dto
     * @return
     */
    List<EasyFreezeSitePO> selectByCondition(EasyFreezeSiteQueryDto dto);

    /**
     * 获取符合条件的数据个数
     * @param dto
     * @return
     */
    int countByCondition(EasyFreezeSiteQueryDto dto);

    int batchUpdateBySiteCode(List<EasyFreezeSitePO> pos);

    int batchInsert(List<EasyFreezeSitePO> pos);

    /**
     * 根据站点id获取站点配置信息
     * @param siteCode
     * @param useState
     * @return
     */
    @Cache(key = "EasyFreezeSiteDao.selectOneConfigBysiteCode@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
            , redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
    EasyFreezeSitePO selectOneConfigBysiteCode(@Param("siteCode") Integer siteCode, @Param("useState") Integer useState);

}
