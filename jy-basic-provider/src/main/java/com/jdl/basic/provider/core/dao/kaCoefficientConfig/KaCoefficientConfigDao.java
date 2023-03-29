package com.jdl.basic.provider.core.dao.kaCoefficientConfig;
import java.util.List;

import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigQueryDto;
import com.jdl.basic.provider.core.po.KaCoefficientConfigPO;

public interface KaCoefficientConfigDao {

    /**
     * 根据商家编码和状态查询数据
     * @return
     */
    List<KaCoefficientConfigPO> selectByMerchantCodeAndStatus(KaCoefficientConfigQueryDto param);

    /**
     * 获取分页查询的总量
     * @param param
     * @return
     */
    Integer getTotalCount(KaCoefficientConfigQueryDto param);

    /**
     * 添加配置
     * @param param
     * @return
     */
    Integer addAll(KaCoefficientConfigPO param);

    /**
     * 修改系数
     * @param param
     * @return
     */
    Integer modifyCoefficient(KaCoefficientConfigPO param);

    /**
     * 根据ID删除配置数据
     * @param param
     * @return
     */
    Integer delById(KaCoefficientConfigPO param);
}
