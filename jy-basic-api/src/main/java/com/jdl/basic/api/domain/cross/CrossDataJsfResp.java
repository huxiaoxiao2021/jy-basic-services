package com.jdl.basic.api.domain.cross;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CrossDataJsfResp implements Serializable {
    
    List<String> crossCodeList;
    
    private Integer totalPage;
}
