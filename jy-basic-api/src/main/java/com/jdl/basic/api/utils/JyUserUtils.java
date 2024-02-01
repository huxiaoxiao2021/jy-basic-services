package com.jdl.basic.api.utils;

import org.apache.commons.lang.StringUtils;

public class JyUserUtils {
    private static final String ID_CARD_NO_REGEX = "(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    /**
     * 是否是身份证
     * @param userCode
     * @return
     */
    public static boolean isIdCard(String userCode) {
        if (StringUtils.isEmpty(userCode)) {
            return false;
        }
        return userCode.matches(ID_CARD_NO_REGEX);
    }
}
