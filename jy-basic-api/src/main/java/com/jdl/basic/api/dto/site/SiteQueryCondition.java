package com.jdl.basic.api.dto.site;

import java.io.Serializable;
import java.util.List;

/**
 * 站点查询条件
 *
 * @author hujiping
 * @date 2021/2/24 3:04 下午
 */
public class SiteQueryCondition implements Serializable {
    
    private static final long serialVersionUID = -6610921180344795755L;
    
    /**
     * 站点编号
     */
    private Integer siteCode;

    /**
     * 站点名称
     *  支持模糊字段
     */
    private String siteName;

    /**
     * 站点/分拣7位编码
     */
    private String dmsCode;

    /**
     * 站点名称拼音码
     */
    private String siteNamePym;
    
    /**
     * 站点类型
     */
    private List<Integer> siteTypes;

    /**
     * 子类型
     */
    private List<Integer> subTypes;

    /**
     * 地址
     *  支持模糊字段
     */
    private String address;
    
    /**
     * 省区编码
     */
    private String provinceAgencyCode;

    /**
     * 枢纽编码
     */
    private String areaCode;
    
    /**
     * 搜索字符串
     */
    private String searchStr;

    public Integer getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(Integer siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDmsCode() {
        return dmsCode;
    }

    public void setDmsCode(String dmsCode) {
        this.dmsCode = dmsCode;
    }

    public String getSiteNamePym() {
        return siteNamePym;
    }

    public void setSiteNamePym(String siteNamePym) {
        this.siteNamePym = siteNamePym;
    }

    public List<Integer> getSiteTypes() {
        return siteTypes;
    }

    public void setSiteTypes(List<Integer> siteTypes) {
        this.siteTypes = siteTypes;
    }

    public List<Integer> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<Integer> subTypes) {
        this.subTypes = subTypes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceAgencyCode() {
        return provinceAgencyCode;
    }

    public void setProvinceAgencyCode(String provinceAgencyCode) {
        this.provinceAgencyCode = provinceAgencyCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
}
