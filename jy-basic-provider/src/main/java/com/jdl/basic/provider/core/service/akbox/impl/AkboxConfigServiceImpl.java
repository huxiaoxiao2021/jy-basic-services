package com.jdl.basic.provider.core.service.akbox.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jdl.basic.api.domain.akbox.AkboxConfig;
import com.jdl.basic.api.domain.akbox.AkboxConfigQuery;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.provider.core.dao.akbox.AkboxConfigDao;
import com.jdl.basic.provider.core.service.akbox.IAkboxConfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class AkboxConfigServiceImpl implements IAkboxConfService {

    @Resource
    AkboxConfigDao akboxConfigDao;

    @Override
    public int save(AkboxConfig akboxConfig) {
        return akboxConfigDao.insert(akboxConfig);
    }

    @Override
    public int batchSave(List<AkboxConfig> akboxConfigList) {
        akboxConfigList.stream().forEach(akboxConfig -> {
            if (akboxConfigDao.selectBySiteCode(akboxConfig.getSiteCode()) != null) {
                akboxConfigDao.updateBySiteCode(akboxConfig);
            } else {
                akboxConfigDao.insert(akboxConfig);
            }
        });
        return 0;
    }

    @Override
    public int update(AkboxConfig akboxConfig) {
        return akboxConfigDao.updateById(akboxConfig);
    }

    @Override
    public int updateBySiteCode(AkboxConfig akboxConfig) {
        return akboxConfigDao.updateBySiteCode(akboxConfig);
    }

    @Override
    public AkboxConfig selectBySiteCode(Long siteCode) {
        return akboxConfigDao.selectBySiteCode(siteCode);
    }

    @Override
    public Pager<AkboxConfig> queryPageByCondition(Pager<AkboxConfigQuery> query) {
        Pager<AkboxConfig> pager = new Pager<>();
        pager.setPageNo(query.getPageNo());
        pager.setPageSize(query.getPageSize());

        Page<AkboxConfig> akboxConfigs = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPage(() -> akboxConfigDao.queryByCondition(query.getSearchVo()));
        if(akboxConfigs != null && CollectionUtils.isNotEmpty(akboxConfigs.getResult())){
            pager.setTotal((long) akboxConfigs.getTotal());
            pager.setData(akboxConfigs.getResult());
        }
        return pager;
    }
}
