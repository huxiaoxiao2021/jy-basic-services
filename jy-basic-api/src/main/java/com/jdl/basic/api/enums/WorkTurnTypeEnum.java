package com.jdl.basic.api.enums;

/**
 * @ClassName WorkTurnTypeEnum
 * @Description 网格推送规则,1-随机推送
 * @Author wyd
 * @Date 2023/5/30 16:49
 **/
public enum WorkTurnTypeEnum {
	RANDOM (1, "随机推送")
    ;
	
	private WorkTurnTypeEnum(Integer code, String name) {
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
    	WorkTurnTypeEnum data = getEnum(code);
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
    public static WorkTurnTypeEnum getEnum(Integer code) {
        for (WorkTurnTypeEnum value : WorkTurnTypeEnum.values()) {
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
