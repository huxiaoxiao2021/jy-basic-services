package com.jdl.basic.api.utils;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class BusinessKeyUtilsTest {

    @org.junit.jupiter.api.Test
    void businessKeyIsWorkGrid() {
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkGrid("CDWG00000005720"), true);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkGrid("CDWG000000057201"), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkGrid("1CDWG000000057201"), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkGrid("CDWG"), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkGrid(""), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkGrid(null), false);
    }

    @org.junit.jupiter.api.Test
    void businessKeyIsWorkStationGrid() {
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkStationGrid("CDGX00003296125"), true);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkStationGrid("CDGX000032961251"), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkStationGrid("1CDGX00003296125"), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkStationGrid("CDGX"), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkStationGrid(""), false);
        Assertions.assertEquals(BusinessKeyUtils.businessKeyIsWorkStationGrid(null), false);
    }
}