package com.jdl.basic.provider.core.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/8/3 11:31
 * @Description:
 */
@Data
@AllArgsConstructor
public class ObjectId implements Serializable {

    private static final long serialVersionUID = 2880565951426711601L;
    private Long id;
    private String objectName;
    private Long firstId;

}
