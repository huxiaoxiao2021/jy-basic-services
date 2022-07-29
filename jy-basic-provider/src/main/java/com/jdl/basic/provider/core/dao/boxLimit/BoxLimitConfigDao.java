package com.jdl.basic.provider.core.dao.boxLimit;


import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.provider.core.po.BoxLimitConfigPO;

import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 11:19
 * @Description:
 */
public interface BoxLimitConfigDao {

    /**
     *根据逐渐删除
     * @param
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     *新增
     * @param
     * @return
     */
    int insert(BoxLimitConfigPO po);

    /**
     *新增
     * @param
     * @return
     */
    int insertSelective(BoxLimitConfigPO po);

    /**
     *根据主键获取详情
     * @param
     * @return
     */
    BoxLimitConfigPO selectByPrimaryKey(Long id);

    /**
     *根据主键更新集箱包裹配置信息
     * @param
     * @return
     */
    int updateByPrimaryKeySelective(BoxLimitConfigPO po);

    /**
     *根据主键更新集箱包裹配置信息
     * @param
     * @return
     */
    int updateByPrimaryKey(BoxLimitConfigPO po);

    /**
     *批量插入
     * @param
     * @return
     */
    int batchInsert(List<BoxLimitConfigPO> dataList);

    /**
     *根据条件查询
     * @param
     * @return
     */
    List<BoxLimitConfigPO> queryByCondition(BoxLimitConfigQueryDto dto);

    /**
     *根据条件获取符合条件的
     * @param
     * @return
     */
    Integer countByCondition(BoxLimitConfigQueryDto dto);

    /**
     *根据机构ID查询记录是否存在
     * @param
     * @return
     */
    List<BoxLimitConfigPO> queryBySiteIds(List<Integer> siteIds);

    /**
     *根据箱号类型获取通用包裹限制数量
     * @param
     * @return
     */
    Integer queryLimitNumBySiteId(BoxLimitConfigDto dto);


    /**
     *根据机构ID查询记录是否存在
     * @param ids
     * @return
     */
    int batchDelete(List<Long> ids);


    /**
     * 根据箱号类型获取通用包裹限制数量
     * @param boxNumberType
     * @return
     */
    Integer queryCommonLimitNum(String boxNumberType);

    /**
     * 批量更新
     * @param dtos
     * @return
     */
    int batchUpdate(List<BoxLimitConfigPO> dtos);
}
