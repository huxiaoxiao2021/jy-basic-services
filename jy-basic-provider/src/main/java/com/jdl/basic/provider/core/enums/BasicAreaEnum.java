package com.jdl.basic.provider.core.enums;


/**
 * 枢纽枚举
 *
 * @author hujiping
 * @date 2021/4/8 3:21 下午
 */
public enum BasicAreaEnum {
    
    CHENGDU_AREA("100201","成都枢纽"),
    DEIZHOU_AREA("100202","德州枢纽"),
    GUANGZHOU_AREA("100203","广州枢纽"),
    HANGZHOU_AREA("100204","杭州枢纽"),
    KUNSHAN_AREA("100205","昆山枢纽"),
    SHENYANG_AREA("100206","沈阳枢纽"),
    TIANJIN_AREA("100207","天津枢纽"),
    WUHAN_AREA("100208","武汉枢纽"),
    XIAN_AREA("100209","西安枢纽"),
    ZHENGZHOU_AREA("100210","郑州枢纽"),
    ;
    BasicAreaEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
