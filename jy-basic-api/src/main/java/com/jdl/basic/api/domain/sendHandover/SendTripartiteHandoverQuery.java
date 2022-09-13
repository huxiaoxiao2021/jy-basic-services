package com.jdl.basic.api.domain.sendHandover;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

/**
 * @author liwenji
 * @description TODO
 * @date 2022-09-08 17:52
 */
@Data
public class SendTripartiteHandoverQuery extends BasePagerCondition {

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * ID
     */
    private Long id;

    /**
     * 场地编码
     */
    private String siteName;

    /**
     * 方案名称
     */
    private String planName;
    /**
     * 邮件主题
     */
    private String mailTopic;
    /**
     * 分页-pageSize
     */
    private Integer pageSize;
}
