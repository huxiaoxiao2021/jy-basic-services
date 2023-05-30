package com.jdl.basic.provider.dto;

import java.util.Date;
import java.util.List;

public class BasicSiteChangeMQ {


    /**
     * 站点编号
     */
    private Integer siteCode;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 机构Id
     */
    private Integer orgId;
    /**
     * 站点类型
     */
    private Integer siteType;
    /**
     * 站点子类型
     */
    private Integer subType;

    /**
     * 站点三级子类型
     */
    private Integer thirdType;

    /**
     * 站点7位编码
     */
    private String dmsCode;

    /**
     * 站点绑定分拣中心ID
     */
    private Integer dmsId;

    /**
     * 站点经度
     */
    private Double longitude;
    /**
     * 站点纬度
     */
    private Double latitude;
    /**
     * 省Id
     */
    private Integer provinceId;
    /**
     * 市Id
     */
    private Integer cityId;
    /**
     * 县Id
     */
    private Integer countyId;
    /**
     * 乡Id
     */
    private Integer townId;
    /**
     * 详细地址
     */
    private String address;

    /**
     * 省公司编号
     */
    private String provinceCompanyCode;

    /**
     * 片区编号
     */
    private String areaCode;

    /**
     * 分区编号
     */
    private String partitionCode;

    /**
     * 组织架构编码
     */
    private String organCode;

    /**
     * 联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 是否支持无人车标识 1：支持无人车，其它不支持，数据库对应字段EXPAND_3
     */
    private String supportVehicle;

    /**
     * 是否开通迷你仓
     */
    private Integer isMiniWarehouse;

    /**
     * 站点业务类型
     * 1-京配配送、自提服务
     * 2-京配配送服务
     * 3-自提服务
     * 4-非京配配送服务
     * 5-京配配送、自提服务、生鲜自提
     * 6-自提服务、生鲜自提
     * 7-非京配配送服务、自提服务
     */
    private Integer siteBusinessType;

    /**
     * 特色服务
     */
    private String specialService;

    /**
     * 自营订单时效
     */
    private String orderTimeEffective;

    /**
     * 外单时效产品：1-当日达 2-次日达  3-隔日达 4-4日及以上达
     */
    private String outerOrderProduct;

    /**
     * 运营状态（线上运营-1，线下运营-2，关闭-0）
     */
    private Integer operateState;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 站点面积大小
     */
    private Double siteArea;

    /**
     * 营业时间 起始时间，格式：HH:mm
     */
    private String businessHoursStart;

    /**
     * 营业时间 结束时间，格式：HH:mm
     */
    private String businessHoursEnd;

    /**
     * 自提点可见区域：一级地址-1，二级地址-2，三级地址-3，四级地址-4
     */
    private Integer visibleArea;

    /**
     * 时效
     */
    private Integer fixTime;


    /**
     * 配送半径(单位公里)
     */
    private Double dispatchRange;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 备注
     */
    private String memo;

    /**
     * 是否有效：1-有效，0-无效
     */
    private Integer yn;


    /**
     * 自提柜厂商编码
     */
    private Integer companyCode;


    /**
     * 归属站点
     */
    private Integer belongCode;


    /**
     * 限关键字下单：0否 1是
     */
    private Integer isKeyword;

    /**
     * 特殊提示
     */
    private String specialRemark;

    /**
     * 关键字
     */
    private List<String> keywords;


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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public String getDmsCode() {
        return dmsCode;
    }

    public void setDmsCode(String dmsCode) {
        this.dmsCode = dmsCode;
    }

    public Integer getDmsId() {
        return dmsId;
    }

    public void setDmsId(Integer dmsId) {
        this.dmsId = dmsId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public String getProvinceCompanyCode() {
        return provinceCompanyCode;
    }

    public void setProvinceCompanyCode(String provinceCompanyCode) {
        this.provinceCompanyCode = provinceCompanyCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSupportVehicle() {
        return supportVehicle;
    }

    public void setSupportVehicle(String supportVehicle) {
        this.supportVehicle = supportVehicle;
    }

    public Integer getIsMiniWarehouse() {
        return isMiniWarehouse;
    }

    public void setIsMiniWarehouse(Integer isMiniWarehouse) {
        this.isMiniWarehouse = isMiniWarehouse;
    }

    public Integer getSiteBusinessType() {
        return siteBusinessType;
    }

    public void setSiteBusinessType(Integer siteBusinessType) {
        this.siteBusinessType = siteBusinessType;
    }

    public String getSpecialService() {
        return specialService;
    }

    public void setSpecialService(String specialService) {
        this.specialService = specialService;
    }

    public String getOrderTimeEffective() {
        return orderTimeEffective;
    }

    public void setOrderTimeEffective(String orderTimeEffective) {
        this.orderTimeEffective = orderTimeEffective;
    }

    public String getOuterOrderProduct() {
        return outerOrderProduct;
    }

    public void setOuterOrderProduct(String outerOrderProduct) {
        this.outerOrderProduct = outerOrderProduct;
    }

    public Integer getOperateState() {
        return operateState;
    }

    public void setOperateState(Integer operateState) {
        this.operateState = operateState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSiteArea() {
        return siteArea;
    }

    public void setSiteArea(Double siteArea) {
        this.siteArea = siteArea;
    }

    public String getBusinessHoursStart() {
        return businessHoursStart;
    }

    public void setBusinessHoursStart(String businessHoursStart) {
        this.businessHoursStart = businessHoursStart;
    }

    public String getBusinessHoursEnd() {
        return businessHoursEnd;
    }

    public void setBusinessHoursEnd(String businessHoursEnd) {
        this.businessHoursEnd = businessHoursEnd;
    }

    public Integer getVisibleArea() {
        return visibleArea;
    }

    public void setVisibleArea(Integer visibleArea) {
        this.visibleArea = visibleArea;
    }

    public Integer getFixTime() {
        return fixTime;
    }

    public void setFixTime(Integer fixTime) {
        this.fixTime = fixTime;
    }

    public Double getDispatchRange() {
        return dispatchRange;
    }

    public void setDispatchRange(Double dispatchRange) {
        this.dispatchRange = dispatchRange;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getBelongCode() {
        return belongCode;
    }

    public void setBelongCode(Integer belongCode) {
        this.belongCode = belongCode;
    }

    public Integer getIsKeyword() {
        return isKeyword;
    }

    public void setIsKeyword(Integer isKeyword) {
        this.isKeyword = isKeyword;
    }

    public String getSpecialRemark() {
        return specialRemark;
    }

    public void setSpecialRemark(String specialRemark) {
        this.specialRemark = specialRemark;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
