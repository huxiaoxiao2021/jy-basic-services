package com.jdl.basic.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 集包路由错误
 */
public enum BoxFlowRouteErrorTypeEnum {
    NO_ERROR(0, "无路由错误"),
    NO_ROUTE(1, "无匹配路由"),
    NOT_PASS_BOX_RECEIVE_SITE(2, "不经过拆包场地"),
    OD_MANY_ROUTE(3,  "OD多路由")
    ;
    private Integer code;

    private String name;

    BoxFlowRouteErrorTypeEnum() {
    }

    BoxFlowRouteErrorTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getNameByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        for(BoxFlowRouteErrorTypeEnum  type : BoxFlowRouteErrorTypeEnum.values()){
            if(code.equals(type.getName())){
                return type.getName();
            }
        }
        return null;
    }
}
