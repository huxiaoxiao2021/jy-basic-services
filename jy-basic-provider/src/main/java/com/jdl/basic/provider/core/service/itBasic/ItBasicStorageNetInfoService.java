package com.jdl.basic.provider.core.service.itBasic;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageNetInfo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageNetInfoQo;
import com.jdl.basic.api.dto.itBasic.vo.ItBasicStorageNetInfoVo;

/**
 * IT区域基础信息接口
 *
 * @copyright jd.com 京东物流JDL
 * @author fanggang7
 * @time 2022-12-04 10:40:36 周日
 */
public interface ItBasicStorageNetInfoService {

    /**
     * 统计个数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<Long> queryCount(ItBasicStorageNetInfoQo itBasicStorageNetInfoQo);

    /**
     * 查询IP地址区域信息分页列表
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<PageData<ItBasicStorageNetInfoVo>> queryPageList(ItBasicStorageNetInfoQo itBasicStorageNetInfoQo);

    /**
     * 添加一条设备记录
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    Result<Long> add(ItBasicStorageNetInfo itBasicStorageNetInfo);

    /**
     * 根据ID更新
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    Result<Boolean> updateById(ItBasicStorageNetInfo itBasicStorageNetInfo);

    /**
     * 根据ID逻辑删除
     * @param itBasicStorageNetInfo 设备记录
     * @return 处理结果
     */
    Result<Boolean> logicDeleteById(ItBasicStorageNetInfo itBasicStorageNetInfo);

}
