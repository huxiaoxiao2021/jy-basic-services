package com.jdl.basic.provider.core.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * 集包规则配置服务接口。
 * 定义了集包规则配置相关的操作，包括查询、新增、更新、版本控制等功能。
 * <p>
 * 作者：wunan84
 * 日期：2023-04-14
 */
public interface ICollectBoxFlowDirectionConfService {
    /**
     * 根据ID查询集包规则配置。
     *
     * @param id 配置ID
     * @return 配置详情
     */
    Result<CollectBoxFlowDirectionConf> selectById(Long id);

    /**
     * 新增集包规则配置。
     *
     * @param conf 配置信息
     * @return 操作结果
     */
    Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf);

    /**
     * 更新集包规则配置。
     *
     * @param conf 更新后的配置信息
     * @return 操作结果
     */
    Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf);
    /**
     * 根据条件和是否已经配置查询
     *
     * @param pager
     * @param configed 是否已经配置，1：已经配置，0：未配置，null：全部
     * @return
     */
    Result<Pager<CollectBoxFlowDirectionConf>> listByParamAndWhetherConfiged(Pager<CollectBoxFlowDirectionConf> pager, Boolean configed);

    /**
     * 更新或新增集包规则配置。
     *
     * @param conf 配置信息
     * @return 操作结果
     */
    Result<Boolean> updateOrNewConfig(CollectBoxFlowDirectionConf conf);

    /**
     * 根据版本号删除配置。
     *
     * @param version     版本号
     * @param deleteCount 删除数量限制
     * @return 删除计数
     */
    int deleteByVersion(String version, Integer deleteCount);

    /**
     * 切换到新版本的集包规则配置。
     *
     * @throws Exception 操作异常
     */
    void switchNewVersion() throws Exception;

    /**
     * 回滚到上一个版本的集包规则配置。
     *
     * @return 操作结果
     */
    Result<Boolean> rollbackVersion();

    /**
     * 查询所有集包规则信息。
     *
     * @return 集包规则信息列表
     */
    List<CollectBoxFlowInfo> selectAllCollectBoxFlowInfo();

    /**
     * 获取当前集包规则配置的版本。
     *
     * @return 当前版本号
     */
    String getCurrentVersion();

    /**
     * 更新集包规则信息。
     *
     * @param collectBoxFlowInfo 集包规则信息
     * @return 更新计数
     */
    int updateCollectBoxFlowInfo(CollectBoxFlowInfo collectBoxFlowInfo);

    /**
     * 查询集包规则配置列表。
     *
     * @param pager 分页条件
     * @return 配置列表
     */
    Result<Pager<CollectBoxFlowDirectionConf>> listCollectBoxFlowDirectionConf(Pager<CollectBoxFlowDirectionConf> pager);

    /**
     * 更新集包规则配置。
     *
     * @param conf 配置信息
     * @return 操作结果
     */
    Result<Boolean> updateCollectBoxFlowDirectionConf(CollectBoxFlowDirectionConf conf);

    /**
     * 删除集包规则，只能删除集包要求为可混包的数据
     */
    Result<Boolean> deleteConfig(CollectBoxFlowDirectionConf conf);
}
