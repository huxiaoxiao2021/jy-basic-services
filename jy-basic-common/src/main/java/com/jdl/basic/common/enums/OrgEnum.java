package com.jdl.basic.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构枚举
 */
public enum OrgEnum {


    HuaBei(6, "华北区"),
    HuaNan(10, "华南区"),
    HuaDong(3, "华东区"),
    HuaZhong(600, "华中区"),
    DongBei(611, "东北区"),
    XiNan(4, "西南区"),
    XiBei(645, "西北区");

    public static Map<Integer, OrgEnum> orgEnumMap;
    public static List<Integer> orgIds;
    static {
        HashMap<Integer, OrgEnum> orgMap = new HashMap<>();
        OrgEnum[] values = OrgEnum.values();
        for (OrgEnum value : values) {
            orgMap.put(value.getOrgCode(), value);
        }
        OrgEnum.orgEnumMap = orgMap;
        orgIds=new ArrayList<>();
        for (OrgEnum value : OrgEnum.values()) {
            orgIds.add(value.getOrgCode());
        }
    }

    @Override
    public String toString() {
        return "OrgEnum{" +
                "orgCode=" + orgCode +
                ", orgName='" + orgName + '\'' +
                '}';
    }

    private Integer orgCode;
    private String orgName;

    OrgEnum(Integer orgCode, String orgName) {
        this.orgCode = orgCode;
        this.orgName = orgName;
    }

    public Integer getOrgCode() {

        return orgCode;
    }

    public static OrgEnum getOrgEnum(Integer orgCode){
        OrgEnum[] orgEnums = OrgEnum.values();
        for (OrgEnum orgEnum:orgEnums){
            if (orgEnum.orgCode.equals(orgCode)){
                return orgEnum;
            }
        }
        return null;
    }

    public void setOrgCode(Integer orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
