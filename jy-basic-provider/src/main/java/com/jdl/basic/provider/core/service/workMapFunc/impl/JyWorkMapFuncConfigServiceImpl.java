package com.jdl.basic.provider.core.service.workMapFunc.impl;


import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigEntity;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncQuery;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.provider.core.dao.workMapFunc.JyWorkMapFuncConfigDao;
import com.jdl.basic.provider.core.service.workMapFunc.JyWorkMapFuncConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 拣运功能与工序配置接口实现
 *
 * @author hujiping
 * @date 2022/4/6 6:05 PM
 */
@Service("jyWorkMapFuncConfigService")
public class JyWorkMapFuncConfigServiceImpl implements JyWorkMapFuncConfigService {


    @Autowired
    private JyWorkMapFuncConfigDao jyWorkMapFuncConfigDao;

    @Override
    public int addWorkMapFunConfig(JyWorkMapFuncConfigEntity entity) {
        return jyWorkMapFuncConfigDao.insert(entity);
    }

    @Override
    public int updateById(JyWorkMapFuncConfigEntity entity) {
        if(entity == null || jyWorkMapFuncConfigDao.queryById(entity.getId()) == null){
            throw new RuntimeException(String.format("id:%s不存在", entity.getId()));
        }
        return jyWorkMapFuncConfigDao.updateById(entity);
    }

    @Override
    public int deleteById(JyWorkMapFuncConfigEntity entity) {
        if(entity == null || jyWorkMapFuncConfigDao.queryById(entity.getId()) == null){
            throw new RuntimeException(String.format("id:%s不存在", entity.getId()));
        }
        return jyWorkMapFuncConfigDao.deleteById(entity);
    }

    @Override
    public PageDto<JyWorkMapFuncConfigEntity> queryPageList(JyWorkMapFuncQuery query) {
        if(query.getPageNumber() < Constants.YN_YES || query.getPageSize() <= Constants.YN_NO){
            throw new RuntimeException("分页参数不合法!");
        }
        PageDto<JyWorkMapFuncConfigEntity> pageDto = new PageDto<JyWorkMapFuncConfigEntity>();
        query.setLimit(query.getPageSize());
        query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        int count = jyWorkMapFuncConfigDao.queryCount(query);
        if(count > 0){
            pageDto.setTotalRow(count);
            pageDto.setResult(jyWorkMapFuncConfigDao.queryList(query));
        }else {
            pageDto.setResult(new ArrayList<JyWorkMapFuncConfigEntity>());
            pageDto.setTotalRow(0);
        }
        return pageDto;
    }

    @Override
    public List<JyWorkMapFuncConfigEntity> queryByCondition(JyWorkMapFuncConfigEntity entity) {
        if(entity == null || StringUtils.isEmpty(entity.getFuncCode()) || StringUtils.isEmpty(entity.getRefWorkKey())){
            throw new RuntimeException("请求参数不合法!");
        }
        return jyWorkMapFuncConfigDao.queryByCondition(entity);
    }
}