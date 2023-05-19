package com.jdl.basic.api.enums;

/**
 * @ClassName ConfigFlowStatusEnum
 * @Description 流向配置状态-枚举
 * @Author wyd
 * @Date 2022/1/4 16:49
 **/
public enum ConfigFlowStatusEnum {
	UN_CONFIG(0,"未配置"),
	CONFIG(1,"已配置")
    ;
	
	private ConfigFlowStatusEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	private Integer code;
	private String name;
	/**
	 * 根据code获取名称
	 * @param code
	 * @return
	 */
    public static String getNameByCode(Integer code) {
    	ConfigFlowStatusEnum data = getEnum(code);
    	if(data != null) {
    		return data.getName();
    	}
        return "";
    }
	/**
	 * 根据code获取enum
	 * @param code
	 * @return
	 */
    public static ConfigFlowStatusEnum getEnum(Integer code) {
        for (ConfigFlowStatusEnum value : ConfigFlowStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
    /**
     * 判断code是否存在
     * @param code
     * @return
     */
    public boolean exist(Integer code) {
        return null != getEnum(code);
    }

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
