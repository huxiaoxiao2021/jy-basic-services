package com.jdl.basic.api.enums;

/**
 * @ClassName EditTypeEnum
 * @Description 编辑类型
 * @Author wyd
 * @Date 2023/5/30 16:49
 **/
public enum EditTypeEnum {
	NON(0,"未操作"),
	ADD(1,"新增"),
	MODIFY(2,"修改"),
	DELETE (3,"删除"),
    ;
	
	private EditTypeEnum(Integer code, String name) {
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
    	EditTypeEnum data = getEnum(code);
    	if(data != null) {
    		return data.getName();
    	}
        return null;
    }
	/**
	 * 根据code获取enum
	 * @param code
	 * @return
	 */
    public static EditTypeEnum getEnum(Integer code) {
        for (EditTypeEnum value : EditTypeEnum.values()) {
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
