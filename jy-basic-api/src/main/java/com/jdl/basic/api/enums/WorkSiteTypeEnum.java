package com.jdl.basic.api.enums;

import java.util.*;

/**
 * @author liuluntao1
 * @description 具体信息见 BasicSiteSubTypeEnum
 * @date 2022/9/15
 */

public enum WorkSiteTypeEnum {
    //分拣中心：一级分拣中心 中转站 航空分拣中心 二级分拨 三级中转场 逆向退货组 云分拣
    DMS_TYPE(1, "分拣中心", Arrays.asList(64, 256, 101, 6409, 6410, 101111),12351,123511,null),
    //拣运 ：企配仓 快运中心
    DTS_TYPE(2, "转运中心", Arrays.asList(6450, 6420, 6460, 44079),12351,123513,null),
    //接货仓
    RWMS_TYPE(3, "接货仓", Arrays.asList(6430),12352,null,null),
    //退货组
    RETURN_CENTER(4, "退货组", Collections.singletonList(6408),12354,123541,null),
    //冷链
    COLD_CHAIN_TYPE(5, "冷链转运", new ArrayList<Integer>(),12351,123515,null),
    //冷链医药
    COLD_CHAIN_MEDICINE_TYPE(6, "医药转运", new ArrayList<Integer>(),12351,123516,null),
    //终端站点
    TERMINAL_TYPE(7,"终端站点", Arrays.asList(18, 19, 9601, 9602, 9605, 9607, 9609, 4, 11001, 6580),null,null,null),
    //其他
    OTHER(0, "其他", new ArrayList<Integer>(),null,null,null);

    private Integer code;
    private String name;
    private List<Integer> subTypes;


    /**
     * 基础资料新三级类型对应的一级类型
     */
    private Integer firstTypesOfThird;
    /**
     * 基础资料新三级类型对应的二级类型
     */
    private Integer secondTypesOfThird;
    /**
     * 基础资料新三级类型对应的三级类型
     */
    private Integer thirdTypesOfThird;

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


    public Integer getFirstTypesOfThird() {
        return firstTypesOfThird;
    }

    public void setFirstTypesOfThird(Integer firstTypesOfThird) {
        this.firstTypesOfThird = firstTypesOfThird;
    }

    public Integer getSecondTypesOfThird() {
        return secondTypesOfThird;
    }

    public void setSecondTypesOfThird(Integer secondTypesOfThird) {
        this.secondTypesOfThird = secondTypesOfThird;
    }

    public Integer getThirdTypesOfThird() {
        return thirdTypesOfThird;
    }

    public void setThirdTypesOfThird(Integer thirdTypesOfThird) {
        this.thirdTypesOfThird = thirdTypesOfThird;
    }

    WorkSiteTypeEnum(Integer code, String name, List<Integer> subTypes, Integer firstTypesOfThird, Integer secondTypesOfThird, Integer thirdTypesOfThird) {
        this.code = code;
        this.subTypes = subTypes;
        this.name = name;
        this.firstTypesOfThird = firstTypesOfThird;
        this.secondTypesOfThird = secondTypesOfThird;
        this.thirdTypesOfThird =  thirdTypesOfThird;
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
    /**
     * 根据基础资料新的三级类型获取场地类型枚举
     * @param firstType
     * @param secondType
     * @param thirdType
     * @return
     */
    public static WorkSiteTypeEnum getWorkingSiteTypeByThird(Integer firstType,Integer secondType,Integer thirdType) {
        if (firstType == null && secondType == null && thirdType == null) {
            return null;
        }
        for (WorkSiteTypeEnum item : WorkSiteTypeEnum.values()){
            if (Objects.equals(firstType,item.firstTypesOfThird) && Objects.equals(secondType,item.secondTypesOfThird) && Objects.equals(thirdType,item.thirdTypesOfThird)){
                return item;
            }
        }
        return null;
    }
}
