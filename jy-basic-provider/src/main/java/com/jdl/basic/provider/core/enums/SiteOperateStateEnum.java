package com.jdl.basic.provider.core.enums;


/**
 * 站点运营状态枚举
 *
 * @author hujiping
 * @date 2021/4/8 3:21 下午
 */
public enum SiteOperateStateEnum {

    SITE_OPERATE_STATE_NOT_EXIST(-1,"无运营状态"),
    /**
     * 站点正常运营状态
     *  0：关闭
     *  1：线上
     *  2：线下
     */
    SITE_OPERATE_STATE_CLOSE(0,"关闭状态"),
    SITE_OPERATE_STATE_ONLINE(1,"线上运营"),
    SITE_OPERATE_STATE_OFFLINE(2,"线下运营");

    SiteOperateStateEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
