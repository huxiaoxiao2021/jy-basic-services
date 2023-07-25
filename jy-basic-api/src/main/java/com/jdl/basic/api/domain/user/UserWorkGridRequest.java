package com.jdl.basic.api.domain.user;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

import java.util.Date;

@Data
public class UserWorkGridRequest  {

    private static final long serialVersionUID = 1124727379456561276L;

    private Long userId;

    private String workGridKey;
    
    private Integer nature;

    private String createUserErp;

    private String createUserName;

    private Date createTime;

    private String updateUserErp;

    private String updateUserName;

    private Date updateTime;
    
    private Integer yn;

    public String getWorkGridKey() {
        return workGridKey;
    }

    public void setWorkGridKey(String workGridKey) {
        this.workGridKey = workGridKey;
    }
}
