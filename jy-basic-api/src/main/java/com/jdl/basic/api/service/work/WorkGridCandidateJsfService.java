package com.jdl.basic.api.service.work;

import com.jdl.basic.api.domain.work.WorkGridCandidate;
import com.jdl.basic.api.domain.work.WorkGridCandidateQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface WorkGridCandidateJsfService {

    Result<Boolean> insert(WorkGridCandidate insertData);

    Result<Boolean> deleteBySiteCode(Integer siteCode);

    Result<PageDto<WorkGridCandidate>> queryPageList(WorkGridCandidateQuery query);

    List<WorkGridCandidate> queryCandidateList(Integer siteCode);
}
