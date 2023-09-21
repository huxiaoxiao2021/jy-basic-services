package com.jdl.basic.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CollectClaimEnum {
    FINISHED_PRODUCT(1, "成品包"),
    MIXABLE(2, "可混包"),
    
    MIXABLE_BY_FLOW(3, "定向混包");
    
    public int code;

    private String name;
    
    public static String getName(Integer code){
        if(code == null){
            return "";
        }
        for(CollectClaimEnum e : CollectClaimEnum.values()){
            if(e.getCode() == code){
                return e.getName();
            }
        }
        return null;
    }
    
}
