package com.jdl.basic.provider.core.service.work;

import com.jdl.basic.api.domain.work.WorkGridCandidate;
import com.jdl.basic.api.domain.work.WorkGridCandidateQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface WorkGridCandidateService {

    /**
     * 按条件分页查询
     * @param query
     * @return
     */
    Result<PageDto<WorkGridCandidate>> queryPageList(WorkGridCandidateQuery query);

    Result<Boolean> insert(WorkGridCandidate insertData);

    Result<Boolean> deleteBySiteCode(Integer siteCode);

    List<WorkGridCandidate> queryCandidateList(Integer siteCode);
}
