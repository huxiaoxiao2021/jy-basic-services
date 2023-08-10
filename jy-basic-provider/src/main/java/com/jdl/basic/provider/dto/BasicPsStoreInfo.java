package com.jdl.basic.provider.dto;

import java.util.Date;

/**
 * 基础资料-仓
 *
 * @author hujiping
 * @date 2021/3/8 1:43 下午
 */
public class BasicPsStoreInfo {

    /**
     * 主键id
     */
    public static final String ID = "ID";
    /**
     * 分拣中心库房id，例如：wms-6-0
     */
    public static final String DMS_STORE_ID = "DMS_STORE_ID";
    /**
     * 分拣中心库房名称
     */
    public static final String DMS_STORE_NAME = "DMS_STORE_NAME";
    /**
     * 仓库id
     */
    public static final String STORE_ID = "STORE_ID";
    /**
     * LOC_NO
     */
    public static final String LOC_NO = "LOC_NO";
    /**
     * 区域ID
     */
    public static final String ORG_ID = "ORG_ID";
    /**
     * 创建时间
     */
    public static final String CREATE_DATE = "CREATE_DATE";
    /**
     * 备注
     */
    public static final String REMARK = "REMARK";
    /**
     * 分拣中心仓库类型
     */
    public static final String DMS_STORE_TYPE = "DMS_STORE_TYPE";
    /**
     * 分拣中心站点ID
     */
    public static final String DMS_SITE_ID = "DMS_SITE_ID";
    /**
     * 7位站点编号
     */
    public static final String DMS_CODE = "DMS_CODE";
    /**
     * 更新时间
     */
    public static final String UPDATE_TIME = "UPDATE_TIME";
    /**
     * 创建操作人账号
     */
    public static final String CREATE_OPERATOR_NAME = "CREATE_OPERATOR_NAME";
    /**
     * 更新操作人账号
     */
    public static final String UPDATE_OPERATOR_NAME = "UPDATE_OPERATOR_NAME";
    /**
     * 联系电话
     */
    public static final String TELEPHONE = "telephone";
    /**
     * 县ID
     */
    public static final String COUNTY_ID = "county_id";
    /**
     * 镇ID
     */
    public static final String TOWN_ID = "town_id";
    /**
     * 地址
     */
    public static final String ADDRESS = "address";
    /**
     * 联系人
     */
    public static final String CONNECTOR = "connector";
    /**
     * 省区编码
     */
    public static final String PROVINCE_AGENCY_CODE = "province_agency_code";
    /**
     * 省区名称
     */
    public static final String PROVINCE_AGENCY_NAME = "province_agency_name";
    /**
     * 省ID
     */
    public static final String PROVINCE_ID = "province_id";
    /**
     * 市ID
     */
    public static final String CITY_ID = "city_id";
    /**
     * 库房名称 拼音码
     */
    public static final String SITE_NAME_PYM = "site_name_pym";
    /**
     * 时间戳
     */
    public static final String TS = "ts";
    /**
     * 是否有效 1.有效 2.无效
     */
    public static final String YN = "YN";

    private Long id;                        //主键id
    private String dmsStoreId;             //分拣中心库房id，例如：wms-6-0
    private String dmsStoreName;            //分拣中心库房名称
    private Integer storeID;                //库房id
    private Integer locNo;                  //LOC_NO
    private Integer orgID;                   //库房机构id
    private String remark;                   //备注
    private String dmsStoreType;             //分拣中心仓库类型
    private Integer dmsSiteId;              //分拣中心站点ID
    private String dmsCode;                   //7位站点编号
    private String createOperatorName;       //创建操作人账号
    private String updateOperatorName;       //更新操作人账号
    private String telephone;                 //联系电话
    private Integer countyId;                //县ID
    private Integer townId;                  //镇ID
    private String address;                   //地址
    private String connector;                //联系人
    private String provinceAgencyCode;              //省区编码
    private String provinceAgencyName;              //省区名称
    private Integer provinceId;              //省id
    private Integer cityId;                  //市id
    private String siteNamePym;              //库房名称 拼音码
    private Date createDate;                //创建时间
    private Date updateTime;                //修改时间
    private Integer yn;                     // 1.有效 2.无效
    private Date ts;                        //时间戳

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDmsStoreId() {
        return dmsStoreId;
    }

    public void setDmsStoreId(String dmsStoreId) {
        this.dmsStoreId = dmsStoreId;
    }

    public String getDmsStoreName() {
        return dmsStoreName;
    }

    public void setDmsStoreName(String dmsStoreName) {
        this.dmsStoreName = dmsStoreName;
    }

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public Integer getLocNo() {
        return locNo;
    }

    public void setLocNo(Integer locNo) {
        this.locNo = locNo;
    }

    public Integer getOrgID() {
        return orgID;
    }

    public void setOrgID(Integer orgID) {
        this.orgID = orgID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDmsStoreType() {
        return dmsStoreType;
    }

    public void setDmsStoreType(String dmsStoreType) {
        this.dmsStoreType = dmsStoreType;
    }

    public Integer getDmsSiteId() {
        return dmsSiteId;
    }

    public void setDmsSiteId(Integer dmsSiteId) {
        this.dmsSiteId = dmsSiteId;
    }

    public String getDmsCode() {
        return dmsCode;
    }

    public void setDmsCode(String dmsCode) {
        this.dmsCode = dmsCode;
    }

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

    public String getUpdateOperatorName() {
        return updateOperatorName;
    }

    public void setUpdateOperatorName(String updateOperatorName) {
        this.updateOperatorName = updateOperatorName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getSiteNamePym() {
        return siteNamePym;
    }

    public void setSiteNamePym(String siteNamePym) {
        this.siteNamePym = siteNamePym;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

}
