package com.jdl.basic.api.service.ncAllowedAutoSortList;

import com.jdl.basic.api.domain.ncAllowedAutoSortList.NCAllowedAutoSortListQuery;
import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.api.dto.ncAllowedAutoSortList.NCAllowedAutoSortListDTO;
import com.jdl.basic.api.dto.ncWhiteList.NCWhiteListDTO;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

public interface NCAllowedAutoSortListJsfService {

    Result<Integer> add(NCAllowedAutoSortListDTO ncAllowedAutoSortListDTO);

    Result<Integer> modify(NCAllowedAutoSortListDTO ncAllowedAutoSortListDTO);

    Result<Integer> remove(int id);

    Result<PageDto<NCAllowedAutoSortListDTO>> queryByCondition(NCAllowedAutoSortListQuery query);

    Result<Integer> countByCondition(NCAllowedAutoSortListQuery query);

    Result<String> evaluate(String length, String width, String height, String volume, String weight , String description);
}
