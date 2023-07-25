package com.jdl.basic.api.dto.site;

import java.io.Serializable;

/**
 * 枢纽实体
 *
 * @author hujiping
 * @date 2023/5/30 6:20 PM
 */
public class AreaVO implements Serializable {
    
    private static final long serialVersionUID = -8875635332950430244L;
    
    /**
     * 枢纽编码
     */
    private String areaCode;
    /**
     * 枢纽名称
     */
    private String areaName;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
