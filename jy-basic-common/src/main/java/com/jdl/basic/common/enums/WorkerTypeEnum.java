package com.jdl.basic.common.enums;

import java.util.HashMap;

/**
 * 工种类型枚举
 */
public enum WorkerTypeEnum {

    /**
     * 正式工
     */
    FORMAL_EMPLOYEE(1,"formalEmployee"),
    /**
     * 派遣工
     */
    DISPATCH_EMPLOYEE(2,"dispatchEmployee"),
    /**
     * 外包工
     */
    OUTSOURCE_EMPLOYEE(3,"outsourceEmployee"),
    /**
     * 临时工
     */
    TMP_EMPLOYEE(4,"tmpEmployee"),
    /**
     * 小时工
     */
    HOURS_EMPLOYEE(5,"hoursEmployee");


    private Integer jobType;
    private String jobName;

    private static HashMap<Integer, String> typeToNameMap = new HashMap<Integer, String>();
    private static HashMap<String, Integer> nameToTypeMap = new HashMap<String, Integer>();
    static{
        for (WorkerTypeEnum value : WorkerTypeEnum.values()) {
            typeToNameMap.put(value.getJobType(), value.getJobName());
            nameToTypeMap.put(value.getJobName(), value.getJobType());
        }
    }
    WorkerTypeEnum(Integer jobType, String jobName){
        this.jobType = jobType;
        this.jobName = jobName;
    }

    public Integer getJobType() {
        return jobType;
    }

    public String getJobName() {
        return jobName;
    }

    public static Integer getJobTypeByName(String jobName){
        return nameToTypeMap.get(jobName);
    }

    public static String getNameByJobType(Integer jobType){
        return typeToNameMap.get(jobType);
    }
}
