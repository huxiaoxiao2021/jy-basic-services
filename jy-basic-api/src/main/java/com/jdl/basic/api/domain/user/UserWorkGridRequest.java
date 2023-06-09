package com.jdl.basic.api.domain.user;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

import java.util.Date;

@Data
public class UserWorkGridRequest extends BasePagerCondition {
    private Long userId;

    private String userErp;

    private String workGridKey;
    
    private Integer nature;

    private Date createTime;

    private Date updateTime;
    
    private Integer yn;
}
