package com.jdl.basic.api.utils;

import java.util.regex.Pattern;

public class BusinessKeyUtils {
    /**
     * 根据businessKey判断是不是WorkGrid
     *
     * @param businessKey
     * @return
     */
    public static boolean businessKeyIsWorkGrid(String businessKey) {
        if (businessKey == null || businessKey.length() == 0) {
            return false;
        }
        if (Pattern.matches("^CDWG\\d{11}$", businessKey)) {
            return true;
        }
        return false;
    }

    /**
     * 根据businessKey判断是不是WorkStationGrid
     *
     * @param businessKey
     * @return
     */
    public static boolean businessKeyIsWorkStationGrid(String businessKey) {
        if (businessKey == null || businessKey.length() == 0) {
            return false;
        }
        if (Pattern.matches("^CDGX\\d{11}$", businessKey)) {
            return true;
        }
        return false;
    }
}
