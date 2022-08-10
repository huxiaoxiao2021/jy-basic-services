package com.jdl.basic.api.domain.workMapFunc;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 拣运功能与工序映射明细
 *
 * @author hujiping
 * @date 2022/4/7 3:17 PM
 */
@Data
public class JyWorkMapFuncConfigDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    // 功能编码
    private String funcCode;
    // 工序表业务主键
    private String refWorkKey;
    // 作业区编码
    private String areaCode;
    // 作业区名称
    private String areaName;
    // 网格编码
    private String workCode;
    // 网格名称
    private String workName;
    // 创建人
    private String createUser;
    // 创建时间
    private Date createTime;
    // 修改人
    private String updateUser;
    // 修改时间
    private Date updateTime;
    private Integer yn;
    private Date ts;

}
