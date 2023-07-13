package com.jdl.basic.api.enums;

/**
 * @ClassName WorkFinishTypeEnum
 * @Description 网格推送规则,1-随机推送
 * @Author wyd
 * @Date 2023/5/30 16:49
 **/
public enum WorkFinishTypeEnum {
	ONE_DAY (1, "一天内完成"),
	ONE_WEEK (2, "一周内完成")
    ;
	
	private WorkFinishTypeEnum(Integer code, String name) {
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
    	WorkFinishTypeEnum data = getEnum(code);
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
    public static WorkFinishTypeEnum getEnum(Integer code) {
        for (WorkFinishTypeEnum value : WorkFinishTypeEnum.values()) {
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
