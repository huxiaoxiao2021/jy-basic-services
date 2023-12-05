package com.jdl.basic.api.enums;

import java.util.*;

/**
 * @author liuluntao1
 * @description 具体信息见 BasicSiteSubTypeEnum
 * @date 2022/9/15
 *
 *  2023年12月04日17:27:24 新增三级类型 未来要淘汰掉subType用三级类型区分  需要注意配置时必须要从 一级 二级 三级的顺序配置，不允许只配置二级 不配置一级
 */

public enum WorkSiteTypeEnum {
    //分拣中心：一级分拣中心 中转站 航空分拣中心 二级分拨 三级中转场 逆向退货组 云分拣
    DMS_TYPE(1, "分拣中心", Arrays.asList(64, 256, 101, 6408, 6409, 6410, 101111),Arrays.asList(12351),Arrays.asList(123511),new ArrayList<Integer>()),
    //拣运 ：企配仓 快运中心
    DTS_TYPE(2, "转运中心", Arrays.asList(6450, 6420, 6460, 44079),Arrays.asList(12351),Arrays.asList(123513),new ArrayList<Integer>()),
    //接货仓
    RWMS_TYPE(3, "接货仓", Arrays.asList(6430),Arrays.asList(12352),new ArrayList<Integer>(),new ArrayList<Integer>()),
    //冷链
    COLD_CHAIN_TYPE(4, "冷链转运", new ArrayList<Integer>(),Arrays.asList(12351),Arrays.asList(123515),new ArrayList<Integer>()),
    //冷链医药
    COLD_CHAIN_MEDICINE_TYPE(5, "医药转运", new ArrayList<Integer>(),Arrays.asList(12351),Arrays.asList(123516),new ArrayList<Integer>()),
    //其他
	OTHER(0, "其他", new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
	
    private Integer code;
    private String name;
    private List<Integer> subTypes;


    /**
     * 基础资料新三级类型对应的一级类型
     */
    private List<Integer> firstTypesOfThird;
    /**
     * 基础资料新三级类型对应的二级类型
     */
    private List<Integer> secondTypesOfThird;
    /**
     * 基础资料新三级类型对应的三级类型
     */
    private List<Integer> thirdTypesOfThird;

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


    public List<Integer> getFirstTypesOfThird() {
        return firstTypesOfThird;
    }

    public void setFirstTypesOfThird(List<Integer> firstTypesOfThird) {
        this.firstTypesOfThird = firstTypesOfThird;
    }

    public List<Integer> getSecondTypesOfThird() {
        return secondTypesOfThird;
    }

    public void setSecondTypesOfThird(List<Integer> secondTypesOfThird) {
        this.secondTypesOfThird = secondTypesOfThird;
    }

    public List<Integer> getThirdTypesOfThird() {
        return thirdTypesOfThird;
    }

    public void setThirdTypesOfThird(List<Integer> thirdTypesOfThird) {
        this.thirdTypesOfThird = thirdTypesOfThird;
    }

    WorkSiteTypeEnum(Integer code, String name,  List<Integer> subTypes, List<Integer> firstTypesOfThird,List<Integer> secondTypesOfThird,List<Integer> thirdTypesOfThird) {
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

    private static Map<String,WorkSiteTypeEnum> thirdTypeMap = new HashMap<>();

    private static String thirdTypeMapKeyFormat = "%s:%s:%s";

    static {
        for (WorkSiteTypeEnum typeEnum : WorkSiteTypeEnum.values()) {
            for (Integer firstType : typeEnum.getFirstTypesOfThird()) {
                if(typeEnum.getSecondTypesOfThird().isEmpty()){
                    String mapKey = String.format(thirdTypeMapKeyFormat,firstType,"","");
                    thirdTypeMap.put(mapKey,typeEnum);
                }
                for (Integer secondType : typeEnum.getSecondTypesOfThird()) {
                    if(typeEnum.getThirdTypesOfThird().isEmpty()){
                        String mapKey = String.format(thirdTypeMapKeyFormat,firstType,secondType,"");
                        thirdTypeMap.put(mapKey,typeEnum);
                    }
                    for (Integer thirdType : typeEnum.getThirdTypesOfThird()) {
                        String mapKey = String.format(thirdTypeMapKeyFormat,firstType,secondType,thirdType);
                        thirdTypeMap.put(mapKey,typeEnum);
                    }
                }
            }
        }
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
        return thirdTypeMap.get(String.format(thirdTypeMapKeyFormat,firstType,secondType,thirdType));
    }


}
