package com.jdl.basic.api.domain.businessWhite;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BusinessWhiteListCondition  implements Serializable {

    private Integer classId;//分类id
    private Date beginTime;
    private Date endTime;
    private int pageNumber;
    private int pageSize;
    private int limitStart;

    public void setLimitStart() {
        this.limitStart = this.pageSize * (this.pageNumber - 1);
    }
}
