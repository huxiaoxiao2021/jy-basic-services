package com.jdl.basic.api.dto.site;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 站点查询条件
 *
 * @author hujiping
 * @date 2021/2/24 3:04 下午
 */
@Data
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
     * 机构ID
     */
    private Integer orgId;
    
    /**
     * 省区编码
     */
    private String provinceAgencyCode;

    /**
     * 枢纽编码
     */
    private String areaCode;

    /**
     * 省id
     */
    private Integer provinceId;
    /**
     * 市id
     */
    private Integer cityId;
    /**
     * 县id
     */
    private Integer countryId;
    
    /**
     * 搜索字符串
     */
    private String searchStr;

    /**
     * 分拣中心三级分类-一级
     */
    private Integer sortType;
    /**
     * 分拣中心三级分类-二级
     */
    private Integer sortSubType;
    /**
     * 分拣中心三级分类-三级
     */
    private Integer sortThirdType;
}
