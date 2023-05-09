package com.jdl.basic.provider.core.dao.businessWhite;

import com.jdl.basic.api.domain.businessWhite.BusinessWhiteList;
import com.jdl.basic.api.domain.businessWhite.BusinessWhiteListCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务白名单
 */
public interface BusinessWhiteListDao {

    int deleteById(Long id);

    List<BusinessWhiteList> selectByParam(BusinessWhiteListCondition param);

    long count(BusinessWhiteListCondition param);

    int insert(@Param("po")BusinessWhiteList record);

    /**
     * 根据一级大类和关键字获取记录
     * @param record
     */
    List<BusinessWhiteList> selectByClasAndContent(BusinessWhiteList record);
}
