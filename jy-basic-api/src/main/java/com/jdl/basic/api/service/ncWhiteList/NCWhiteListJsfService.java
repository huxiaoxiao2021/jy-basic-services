package com.jdl.basic.api.service.ncWhiteList;

import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.api.dto.ncWhiteList.NCWhiteListDTO;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface NCWhiteListJsfService {

    Result<Integer> add(NCWhiteListDTO ncWhiteList);

    Result<Integer> modify(NCWhiteListDTO ncWhiteList);

    Result<Integer> remove(int id);

    Result<PageDto<NCWhiteListDTO>> queryByCondition(NCWhiteListQuery query);

    Result<Integer> countByCondition(NCWhiteListQuery query);

}
