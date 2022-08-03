package com.jdl.basic.provider.core.dao.dbs;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/8/2 17:10
 * @Description:
 */
public interface ObjectIdDao {

    Integer updateFirstIdByName(@Param("objectName") String objectName,@Param("count") Integer count);

    int insertObjectId(@Param("objectName")String objectName, @Param("firstId")Integer firstId);

    Integer selectFirstIdByName(@Param("objectName")String objectName);

    int updateFirstIdByNameAndCurrId(@Param("objectName")String objectName, @Param("currId")Integer currId,@Param("count")Integer count);
}
