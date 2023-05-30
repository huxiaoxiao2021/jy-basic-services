package com.jdl.basic.provider.core.enums;


/**
 * 省区枚举
 *
 * @author hujiping
 * @date 2021/4/8 3:21 下午
 */
public enum BasicProvinceAgencyEnum {
    
    WULIUZONGBU("100000","物流总部"),
    BEIJING_PROVINCE_AGENCY("110000","北京区"),
    TIANJIN_PROVINCE_AGENCY("120000","天津区"),
    HEBEI_PROVINCE_AGENCY("130000","河北省区"),
    SHANXI_PROVINCE_AGENCY("140000","山西省区"),
    NEIMENG_PROVINCE_AGENCY("150000","内蒙省区"),
    LIAONING_PROVINCE_AGENCY("210000","辽宁省区"),
    JILIN_PROVINCE_AGENCY("220000","吉林省区"),
    HEILONGJIANG_PROVINCE_AGENCY("230000","黑龙江省区"),
    SHANGHAI_PROVINCE_AGENCY("310000","上海区"),
    JIANGSU_PROVINCE_AGENCY("320000","江苏省区"),
    ZHEJIANG_PROVINCE_AGENCY("330000","浙江省区"),
    ANHUI_PROVINCE_AGENCY("340000","安徽省区"),
    FUJIAN_PROVINCE_AGENCY("350000","福建省区"),
    JIANGXI_PROVINCE_AGENCY("360000","江西省区"),
    SHANDONG_PROVINCE_AGENCY("370000","山东省区"),
    HENAN_PROVINCE_AGENCY("410000","河南省区"),
    HUBEI_PROVINCE_AGENCY("420000","湖北省区"),
    HUNAN_PROVINCE_AGENCY("430000","湖南省区"),
    GUANGDONG_PROVINCE_AGENCY("440000","广东省区"),
    GUANGXI_PROVINCE_AGENCY("450000","广西省区"),
    HAINAN_PROVINCE_AGENCY("460000","海南省区"),
    CHONGQING_PROVINCE_AGENCY("500000","重庆区"),
    SICHUAN_PROVINCE_AGENCY("510000","四川省区"),
    GUIZHOU_PROVINCE_AGENCY("520000","贵州省区"),
    YUNNAN_PROVINCE_AGENCY("530000","云南省区"),
    XIZANG_PROVINCE_AGENCY("540000","西藏省区"),
    SHANGXI_PROVINCE_AGENCY("610000","陕西省区"),
    GANSU_PROVINCE_AGENCY("620000","甘肃省区"),
    QINGHAI_PROVINCE_AGENCY("630000","青海省区"),
    NINGXIA_PROVINCE_AGENCY("640000","宁夏省区"),
    XINJIANG_PROVINCE_AGENCY("650000","新疆省区"),
    TAIWAN_PROVINCE_AGENCY("710000","台湾省区"),
    XIANGGANG_PROVINCE_AGENCY("810000","香港特别行政区"),
    AOMEN_PROVINCE_AGENCY("820000","澳门特别行政区"),
    ;
    BasicProvinceAgencyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
