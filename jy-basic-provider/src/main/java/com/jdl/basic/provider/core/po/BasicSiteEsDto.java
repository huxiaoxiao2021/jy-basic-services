package com.jdl.basic.provider.core.po;

import com.jd.dms.java.utils.core.annotation.EsColumn;
import com.jd.dms.java.utils.core.annotation.EsEntity;

import java.io.Serializable;

/**
 * 精简站点es实体对象
 *
 * @author: hujiping
 * @date: 2021/02/23 18:12
 */
@EsEntity
public class BasicSiteEsDto implements Serializable {

    public static final String SITE_CODE = "site_code";
    public static final String DMS_SITE_CODE = "dms_site_code";
    public static final String SITE_NAME = "site_name";
    public static final String SITE_NAME_PYM = "site_name_pym";
    public static final String DMS_ID = "dms_id";
    public static final String DMS_NAME = "dms_name";
    public static final String PARENT_SITE_CODE = "parent_site_code";
    public static final String PARENT_SITE_NAME = "parent_site_name";
    public static final String ORG_ID = "org_id";
    public static final String ORG_NAME = "org_name";
    public static final String SITE_TYPE = "site_type";
    public static final String SUB_TYPE = "sub_type";
    public static final String PROVINCE_ID = "province_id";
    public static final String PROVINCE_NAME = "province_name";
    public static final String PROVINCE_AGENCY_CODE = "province_agency_code";
    public static final String PROVINCE_AGENCY_NAME = "province_agency_name";
    public static final String CITY_ID = "city_id";
    public static final String CITY_NAME = "city_name";
    public static final String COUNTRY_ID = "country_id";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRYSIDE_ID = "countryside_id";
    public static final String COUNTRYSIDE_NAME = "countryside_name";
    public static final String ADDRESS = "address";
    public static final String SITE_CONTACT = "site_contact";
    public static final String SITE_EMAIL = "site_email";
    public static final String SITE_BUSINESS_TYPE = "site_business_type";
    public static final String PROVINCE_COMPANY_CODE = "province_company_code";
    public static final String PROVINCE_COMPANY_NAME = "province_company_name";
    public static final String AREA_CODE = "area_code";
    public static final String AREA_NAME = "area_name";
    public static final String PARTITION_CODE = "partition_code";
    public static final String PARTITION_NAME = "partition_name";
    public static final String YN = "yn";
    public static final String OPERATE_STATE = "operate_state";

    /**
     * 站点ID
     */
    @EsColumn(fieldName = SITE_CODE)
    private Integer siteCode;
    /**
     * 站点7位编码
     */
    @EsColumn(fieldName = DMS_SITE_CODE)
    private String dmsSiteCode;
    /**
     * 站点名称
     */
    @EsColumn(fieldName = SITE_NAME)
    private String siteName;
    /**
     * 站点名称拼音码
     *  以,隔开，如：北辰自提点 bcztd,bczdd,bczsd
     */
    @EsColumn(fieldName = SITE_NAME_PYM)
    private String siteNamePym;
    /**
     * 站点所属分拣中心ID
     */
    @EsColumn(fieldName = DMS_ID)
    private Integer dmsId;
    /**
     * 站点所属分拣中心名称
     */
    @EsColumn(fieldName = DMS_NAME)
    private String dmsName;
    /**
     * 速递ID
     */
    @EsColumn(fieldName = PARENT_SITE_CODE)
    private Integer parentSiteCode;
    /**
     * 速递名称
     */
    @EsColumn(fieldName = PARENT_SITE_NAME)
    private String parentSiteName;
    /**
     * 区域ID
     */
    @EsColumn(fieldName = ORG_ID)
    private Integer orgId;
    /**
     * 区域名称
     */
    @EsColumn(fieldName = ORG_NAME)
    private String orgName;
    /**
     * 站点类型
     */
    @EsColumn(fieldName = SITE_TYPE)
    private Integer siteType;
    /**
     * 站点子类型
     */
    @EsColumn(fieldName = SUB_TYPE)
    private Integer subType;
    /**
     * 省ID
     */
    @EsColumn(fieldName = PROVINCE_ID)
    private Integer provinceId;
    /**
     * 省名称
     */
    @EsColumn(fieldName = PROVINCE_NAME)
    private String provinceName;
    /**
     * 省区编码
     */
    @EsColumn(fieldName = PROVINCE_AGENCY_CODE)
    private String provinceAgencyCode;
    /**
     * 省区名称
     */
    @EsColumn(fieldName = PROVINCE_AGENCY_NAME)
    private String provinceAgencyName;
    /**
     * 市ID
     */
    @EsColumn(fieldName = CITY_ID)
    private Integer cityId;
    /**
     * 市名称
     */
    @EsColumn(fieldName = CITY_NAME)
    private String cityName;
    /**
     * 县ID
     */
    @EsColumn(fieldName = COUNTRY_ID)
    private Integer countryId;
    /**
     * 县名称
     */
    @EsColumn(fieldName = COUNTRY_NAME)
    private String countryName;
    /**
     * 乡ID
     */
    @EsColumn(fieldName = COUNTRYSIDE_ID)
    private Integer countrySideId;
    /**
     * 乡名称
     */
    @EsColumn(fieldName = COUNTRYSIDE_NAME)
    private String countrySideName;
    /**
     * 详细地址
     */
    @EsColumn(fieldName = ADDRESS)
    private String address;
    /**
     * 站点联系人
     */
    @EsColumn(fieldName = SITE_CONTACT)
    private String siteContact;
    /**
     * 站点邮箱
     */
    @EsColumn(fieldName = SITE_EMAIL)
    private String siteMail;
    /**
     * 站点业务类型
     *  1-京配配送、自提服务
     *  2-京配配送服务
     *  3-自提服务
     *  4-非京配配送服务
     *  5-京配配送、自提服务、生鲜自提
     *  6-自提服务、生鲜自提
     *  7-非京配配送服务、自提服务
     */
    @EsColumn(fieldName = SITE_BUSINESS_TYPE)
    private Integer siteBusinessType;
    /**
     * 省公司
     */
    @EsColumn(fieldName = PROVINCE_COMPANY_CODE)
    private String provinceCompanyCode;
    @EsColumn(fieldName = PROVINCE_COMPANY_NAME)
    private String provinceCompanyName;
    /**
     * 片区
     */
    @EsColumn(fieldName = AREA_CODE)
    private String areaCode;
    @EsColumn(fieldName = AREA_NAME)
    private String areaName;
    /**
     * 分区
     */
    @EsColumn(fieldName = PARTITION_CODE)
    private String partitionCode;
    @EsColumn(fieldName = PARTITION_NAME)
    private String partitionName;
    /**
     * 是否有效
     */
    @EsColumn(fieldName = YN)
    private Integer yn;
    /**
     * 运营状态：
     *  线上运营：1
     *  线下运营：2
     *  关闭：0
     *  不存在：-1
     */
    @EsColumn(fieldName = OPERATE_STATE)
    private Integer operateState;

    public Integer getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(Integer siteCode) {
        this.siteCode = siteCode;
    }

    public String getDmsSiteCode() {
        return dmsSiteCode;
    }

    public void setDmsSiteCode(String dmsSiteCode) {
        this.dmsSiteCode = dmsSiteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteNamePym() {
        return siteNamePym;
    }

    public void setSiteNamePym(String siteNamePym) {
        this.siteNamePym = siteNamePym;
    }

    public Integer getDmsId() {
        return dmsId;
    }

    public void setDmsId(Integer dmsId) {
        this.dmsId = dmsId;
    }

    public String getDmsName() {
        return dmsName;
    }

    public void setDmsName(String dmsName) {
        this.dmsName = dmsName;
    }

    public Integer getParentSiteCode() {
        return parentSiteCode;
    }

    public void setParentSiteCode(Integer parentSiteCode) {
        this.parentSiteCode = parentSiteCode;
    }

    public String getParentSiteName() {
        return parentSiteName;
    }

    public void setParentSiteName(String parentSiteName) {
        this.parentSiteName = parentSiteName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getSiteType() {
        return siteType;
    }

    public void setSiteType(Integer siteType) {
        this.siteType = siteType;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountrySideId() {
        return countrySideId;
    }

    public void setCountrySideId(Integer countrySideId) {
        this.countrySideId = countrySideId;
    }

    public String getCountrySideName() {
        return countrySideName;
    }

    public void setCountrySideName(String countrySideName) {
        this.countrySideName = countrySideName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSiteContact() {
        return siteContact;
    }

    public void setSiteContact(String siteContact) {
        this.siteContact = siteContact;
    }

    public String getSiteMail() {
        return siteMail;
    }

    public void setSiteMail(String siteMail) {
        this.siteMail = siteMail;
    }

    public Integer getSiteBusinessType() {
        return siteBusinessType;
    }

    public void setSiteBusinessType(Integer siteBusinessType) {
        this.siteBusinessType = siteBusinessType;
    }

    public String getProvinceCompanyCode() {
        return provinceCompanyCode;
    }

    public void setProvinceCompanyCode(String provinceCompanyCode) {
        this.provinceCompanyCode = provinceCompanyCode;
    }

    public String getProvinceCompanyName() {
        return provinceCompanyName;
    }

    public void setProvinceCompanyName(String provinceCompanyName) {
        this.provinceCompanyName = provinceCompanyName;
    }

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

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getPartitionName() {
        return partitionName;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getOperateState() {
        return operateState;
    }

    public void setOperateState(Integer operateState) {
        this.operateState = operateState;
    }
}
