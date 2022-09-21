package com.jdl.basic.api.domain.sendHandover;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liwenji
 * @description 发货交接数据实体类
 * @date 2022-09-07 19:43
 */
@Data
public class SendTripartiteHandoverDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 场地编码
     */
    private Integer siteCode;
    /**
     * 方案名称
     */
    private String planName;
    /**
     * 收件人邮箱
     */
    private String toEmail;
    /**
     * 抄送人邮箱
     */
    private String ccEmail;
    /**
     * 邮件主题
     */
    private String mailTopic;
    /**
     * 创建人ERP
     */
    private String createUserErp;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审批状态
     */
    private Integer approvalStatus;
    /**
     * 最后一次操作时间
     */
    private Date lastOperateTime;
    /**
     * 更新人姓名
     */
    private String updateUserErp;
    /**
     * 更新时间
     */
    private Date update_time;
    /**
     * 删除逻辑
     */
    private Integer isDelete;
}
