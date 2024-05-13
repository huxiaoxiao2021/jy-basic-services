package com.jdl.basic.api.domain.jyJobType;

import com.jdl.basic.api.domain.BasePagerCondition;

import java.io.Serializable;

/**
 * @author pengchong28
 * @description 拣运工种字典查询入参
 * @date 2024/2/1
 */
public class JyJobTypeQuery extends BasePagerCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工种类型
     * */
    private String name;
    /**
     * 工种启用状态,0-停用,1-启用
     */
    private Integer status;
    /**
     * 分页-pageSize
     */
    private Integer pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
