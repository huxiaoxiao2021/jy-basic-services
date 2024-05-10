package com.jdl.basic.api.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowInfoDto;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * 集包规则配置服务接口。
 * 提供集包规则配置的查询、新增、更新等操作。
 * <p>
 * 作者：wunan84
 * 日期：2023-04-14
 */
public interface CollectBoxFlowDirectionConfJsfService {
    /**
     * 根据ID查询集包规则配置。
     *
     * @param id 配置的唯一标识ID。
     * @return 返回指定ID的集包规则配置。
     */
    Result<CollectBoxFlowDirectionConf> selectById(Long id);

    /**
     * 新增集包规则配置。
     *
     * @param conf 新的集包规则配置信息。
     * @return 返回操作是否成功。
     */
    Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf);

    /**
     * 更新集包规则配置。
     *
     * @param conf 更新后的集包规则配置信息。
     * @return 返回操作是否成功。
     */
    Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf);

    /**
     * 根据条件和配置状态查询集包规则配置列表。
     *
     * @param pager 分页条件。
     * @param configed 配置状态，1：已配置，0：未配置，null：不区分。
     * @return 返回符合条件的集包规则配置列表。
     */
    Result<Pager<CollectBoxFlowDirectionConf>> listByParamAndWhetherConfiged(Pager<CollectBoxFlowDirectionConf> pager, Boolean configed);

    /**
     * 回滚到上一个版本的集包规则配置。
     *
     * @return 返回操作是否成功。
     */
    Result<Boolean> rollbackVersion();

    /**
     * 查询所有集包规则信息。
     *
     * @return 返回所有集包规则信息列表。
     */
    Result<List<CollectBoxFlowInfoDto>> selectAllCollectBoxFlowInfo();

    /**
     * 获取当前集包规则配置的版本。
     *
     * @return 返回当前配置版本。
     */
    Result<String> getCurrentVersion();

    /**
     * 更新集包规则信息。
     *
     * @param collectBoxFlowInfo 待更新的集包规则信息。
     * @return 返回更新操作影响的记录数。
     */
    Result<Integer> updateCollectBoxFlowInfo(CollectBoxFlowInfo collectBoxFlowInfo);

    /**
     * 批量更新集包规则配置。
     *
     * @param str 更新操作的参数字符串，具体格式和内容根据业务需求定义。
     * @return 返回操作是否成功。
     */
    Result<Boolean> batchUpdateCollectBoxFlowDirectionConf(String str);

    /**
     * 删除集包规则，只能删除集包要求为可混包的数据
     */
    Result<Boolean> deleteConfig(CollectBoxFlowDirectionConf conf);
}
