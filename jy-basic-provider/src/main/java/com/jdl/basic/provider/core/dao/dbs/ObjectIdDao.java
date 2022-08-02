package com.jdl.basic.provider.core.dao.dbs;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/8/2 17:10
 * @Description:
 */
public interface ObjectIdDao {

    Integer updateFirstIdByName(String objectName, Integer count);

    int insertObjectId(String objectName, Integer firstId);

    Integer selectFirstIdByName(String objectName);

    int updateFirstIdByNameAndCurrId(String objectName, Integer currId,Integer count);
}
