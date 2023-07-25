package com.jdl.basic.api.enums;

import java.util.ArrayList;

/**
 * @author liuluntao1
 * @description 具体信息见 BasicSiteSubTypeEnum
 * @date 2022/9/15
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum WorkSiteTypeEnum {
    //分拣中心：一级分拣中心 中转站 航空分拣中心 二级分拨 三级中转场 逆向退货组 云分拣
    DMS_TYPE(1, "分拣中心", Arrays.asList(64, 256, 101, 6408, 6409, 6410, 101111)),
    //拣运 ：企配仓 快运中心
    DTS_TYPE(2, "转运中心", Arrays.asList(6450, 6420, 6460, 44079)),
    //接货仓
    RWMS_TYPE(3, "接货仓", Collections.singletonList(6430)),
    //其他
	OTHER(0, "其他", new ArrayList<Integer>());
	
    private Integer code;
    private String name;
    private List<Integer> subTypes;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Integer> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<Integer> subTypes) {
        this.subTypes = subTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    WorkSiteTypeEnum(Integer code, String name, List<Integer> subTypes) {
        this.code = code;
        this.subTypes = subTypes;
        this.name = name;
    }

    public static WorkSiteTypeEnum getWorkingSiteTypeBySubType(Integer subType) {
        if (subType == null) {
            return null;
        }
        for (WorkSiteTypeEnum typeEnum : WorkSiteTypeEnum.values()) {
            for (Integer sub : typeEnum.getSubTypes()) {
                if (subType.equals(sub)) {
                    return typeEnum;
                }
            }
        }
        return null;
    }
}
