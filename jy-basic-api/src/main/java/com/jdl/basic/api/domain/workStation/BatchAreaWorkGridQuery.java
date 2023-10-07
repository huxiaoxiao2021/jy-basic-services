package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BatchAreaWorkGridQuery implements Serializable {

    private static final long serialVersionUID = -1601531731359187497L;

    private List<Integer> siteCodes;

    private List<String> areaCodes;
}
