package com.jdl.basic.api.dto.site;

import java.io.Serializable;

/**
 * 省区实体
 *
 * @author hujiping
 * @date 2023/5/30 6:20 PM
 */
public class ProvinceAgencyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 省区编码
     */
    private String provinceAgencyCode;
    /**
     * 省区名称
     */
    private String provinceAgencyName;

    public String getProvinceAgencyCode() {
        return provinceAgencyCode;
    }

    public void setProvinceAgencyCode(String provinceAgencyCode) {
        this.provinceAgencyCode = provinceAgencyCode;
    }

    public String getProvinceAgencyName() {
        return provinceAgencyName;
    }

    public void setProvinceAgencyName(String provinceAgencyName) {
        this.provinceAgencyName = provinceAgencyName;
    }
}
