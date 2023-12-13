package com.jdl.basic.api.enums;

import com.jdl.basic.common.utils.StringUtils;

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
    DMS_TYPE(1, "分拣中心", Arrays.asList(64, 256, 101, 6409, 6410, 101111),12351,123511,null,"SORTING_CENTER"),
    //拣运 ：企配仓 快运中心
    DTS_TYPE(2, "转运中心", Arrays.asList(6450, 6420, 6460, 44079),12351,123513,null,"TRANSPORT_CENTER"),
    //接货仓
    RWMS_TYPE(3, "接货仓", Arrays.asList(6430),12352,null,null,"RECEIVING_WAREHOUSE"),
    //退货组
    RETURN_CENTER(4, "退货组", Collections.singletonList(6408),12354,123541,null,"RETURN_CENTER"),
    // 站点: 0-快运终端 9601-配送运输-运输车队 9602-配送运输-直送车队 9605-配送运输-集配站 9607-配送运输-城配车队 9609-配送运输-冷链医药车队 4-营业部、11001-德邦门店、6580-德邦虚拟运作部
    APPEAL_SITE(5, "申诉站点", Arrays.asList(0, 9601, 9602, 9605, 9607, 9609, 4, 11001, 6580),null,null,null,""),
    //终端站点
    TERMINAL_TYPE(7,"终端站点", Arrays.asList(18, 19, 9601, 9602, 9605, 9607, 9609, 4, 11001, 6580),null,null,null,""),
    //冷链
    COLD_CHAIN_TYPE(8, "冷链转运", new ArrayList<Integer>(),12351,123515,null,"CODE_CENTER"),
    //冷链医药
    COLD_CHAIN_MEDICINE_TYPE(9, "医药转运", new ArrayList<Integer>(),12351,123516,null,"MEDICINE_CENTER"),

    //其他
    OTHER(0, "其他", new ArrayList<Integer>(),null,null,null,null);

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
    /**
     *
     */
    private String aliesCode;

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

    public String getAliesCode() {
        return aliesCode;
    }

    public void setAliesCode(String aliesCode) {
        this.aliesCode = aliesCode;
    }

    WorkSiteTypeEnum(Integer code, String name, List<Integer> subTypes, Integer firstTypesOfThird, Integer secondTypesOfThird, Integer thirdTypesOfThird,String aliesCode) {
        this.code = code;
        this.subTypes = subTypes;
        this.name = name;
        this.firstTypesOfThird = firstTypesOfThird;
        this.secondTypesOfThird = secondTypesOfThird;
        this.thirdTypesOfThird =  thirdTypesOfThird;
        this.aliesCode =  aliesCode;
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

    /**
     * 根据工作站点类型代码获取工作站点类型枚举
     * @param code 工作站点类型代码
     * @return 工作站点类型枚举
     */
    public static WorkSiteTypeEnum getWorkingSiteTypeByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (WorkSiteTypeEnum item : WorkSiteTypeEnum.values()){
            if (Objects.equals(code,item.getCode())){
                return item;
            }
        }
        return null;
    }
    /**
     * 根据别名代码获取工作站点类型。
     *
     * @param aliesCode 别名代码
     * @return 工作站点类型
     */
    public static WorkSiteTypeEnum getWorkingSiteTypeByAliesCode(String aliesCode) {
        if (StringUtils.isBlank(aliesCode)) {
            return null;
        }
        for (WorkSiteTypeEnum item : WorkSiteTypeEnum.values()){
            if (aliesCode.equals(item.getAliesCode())){
                return item;
            }
        }
        return null;
    }
    /**
     * 根据名称获取工作地点类型枚举
     * @param name 工作地点类型名称
     * @return 对应的工作地点类型枚举，如果找不到则返回null
     */
    public static WorkSiteTypeEnum getWorkingSiteTypeByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (WorkSiteTypeEnum typeEnum : WorkSiteTypeEnum.values()) {
            if (typeEnum.getName().equals(name)) {
                return typeEnum;
            }
        }
        return null;
    }
}
