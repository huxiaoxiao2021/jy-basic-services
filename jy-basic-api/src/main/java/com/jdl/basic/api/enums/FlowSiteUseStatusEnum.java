package com.jdl.basic.api.enums;

/**
 * @ClassName FlowSiteUseStatusEnum
 * @Description 流向使用状态-枚举
 * @Author wyd
 * @Date 2022/1/4 16:49
 **/
public enum FlowSiteUseStatusEnum {
	UN_CONFIG(0,"否"),
	CONFIG(1,"是")
    ;
	
	private FlowSiteUseStatusEnum(Integer code, String name) {
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
    	FlowSiteUseStatusEnum data = getEnum(code);
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
    public static FlowSiteUseStatusEnum getEnum(Integer code) {
        for (FlowSiteUseStatusEnum value : FlowSiteUseStatusEnum.values()) {
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
