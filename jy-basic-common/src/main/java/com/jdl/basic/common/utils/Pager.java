package com.jdl.basic.common.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author: hujiping
 * @date: 2020/3/25 15:49
 */
@Data
public class Pager<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 每页显示数量
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Long total;

    /**
     * 查询条件
     */
    private T searchVo;

    /**
     * 数据
     */
    private List<T> data;

}
