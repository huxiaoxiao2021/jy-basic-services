package com.jdl.basic.provider.core.provider.work;

import com.jdl.basic.api.domain.work.WorkGridCandidate;
import com.jdl.basic.api.domain.work.WorkGridCandidateQuery;
import com.jdl.basic.api.service.work.WorkGridCandidateJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.work.WorkGridCandidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("workGridCandidateJsfService")
public class WorkGridCandidateJsfServiceImpl implements WorkGridCandidateJsfService {

    @Autowired
    private WorkGridCandidateService workGridCandidateService;

    @Override
    public Result<Boolean> insert(WorkGridCandidate insertData) {
        return workGridCandidateService.insert(insertData);
    }

    @Override
    public Result<Boolean> deleteBySiteCode(Integer siteCode) {
        return workGridCandidateService.deleteBySiteCode(siteCode);
    }

    @Override
    public Result<PageDto<WorkGridCandidate>> queryPageList(WorkGridCandidateQuery query) {
        return workGridCandidateService.queryPageList(query);
    }

    @Override
    public List<WorkGridCandidate> queryCandidateList(Integer siteCode) {
        return workGridCandidateService.queryCandidateList(siteCode);
    }
}
