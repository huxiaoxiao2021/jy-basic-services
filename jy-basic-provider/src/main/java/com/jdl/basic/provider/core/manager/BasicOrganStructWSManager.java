package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.domain.BaseOrganStruct;

import java.util.List;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2023/6/15 2:28 PM
 */
public interface BasicOrganStructWSManager {

    /**
     * 根据父级编码获取下级机构
     * 
     * @param parentCode 父级编码
     * @return 机构列表
     */
    List<BaseOrganStruct> getBaseOrganStructByParentCode(String parentCode);
}
