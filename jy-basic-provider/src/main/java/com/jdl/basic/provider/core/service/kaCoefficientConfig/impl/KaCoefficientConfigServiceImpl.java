package com.jdl.basic.provider.core.service.kaCoefficientConfig.impl;

import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigDto;
import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigQueryDto;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.provider.core.dao.kaCoefficientConfig.KaCoefficientConfigDao;
import com.jdl.basic.provider.core.po.KaCoefficientConfigPO;
import com.jdl.basic.provider.core.service.kaCoefficientConfig.KaCoefficientConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ka积分卸车系数配置服务
 */

@Service
public class KaCoefficientConfigServiceImpl implements KaCoefficientConfigService {

    @Autowired
    private KaCoefficientConfigDao kaCoefficientConfigDao;

    @Override
    public PageDto<KaCoefficientConfigDto> geConfigByMerchantCodeAndStatus(KaCoefficientConfigQueryDto param) {
        PageDto<KaCoefficientConfigDto> result = new PageDto<>();

        //查询分页数据
        List<KaCoefficientConfigPO> data = kaCoefficientConfigDao.selectByMerchantCodeAndStatus(param);
        List<KaCoefficientConfigDto> dataTmp = new ArrayList<>();
        data.stream().forEach(obj -> {
            KaCoefficientConfigDto tmp = new KaCoefficientConfigDto();
            tmp.setId(obj.getId());
            tmp.setMerchantCode(obj.getMerchantCode());
            tmp.setMerchantName(obj.getMerchantName());
            tmp.setCoefficient(obj.getCoefficient());
            tmp.setStatus(obj.getStatus());
            tmp.setStatusName(obj.getStatusName());
            tmp.setCreateUser(obj.getCreateUser());
            tmp.setCreateUserName(obj.getCreateUserName());
            tmp.setUpdateUserName(obj.getUpdateUser());
            tmp.setUpdateUserName(obj.getUpdateUserName());
            tmp.setCreateTime(obj.getCreateTime());
            tmp.setUpdateTime(obj.getUpdateTime());
            dataTmp.add(tmp);
        });
        //统计总条数
        Integer total = kaCoefficientConfigDao.getTotalCount(param);
        result.setTotalRow(total);
        result.setResult(dataTmp);
        return result;
    }

    @Override
    public Integer addKaCoefficientConfig(KaCoefficientConfigDto param) {
        KaCoefficientConfigPO data = new KaCoefficientConfigPO(param);
        return kaCoefficientConfigDao.addAll(data);
    }

    @Override
    public Integer modifyCoefficient(KaCoefficientConfigDto param) {
        KaCoefficientConfigPO data = new KaCoefficientConfigPO();
        data.setCoefficient(param.getCoefficient());
        data.setId(param.getId());
        return kaCoefficientConfigDao.modifyCoefficient(data);
    }

    @Override
    public Integer delCoefficientById(KaCoefficientConfigDto param) {
        KaCoefficientConfigPO data = new KaCoefficientConfigPO();
        data.setId(param.getId());
        return kaCoefficientConfigDao.delById(data);
    }
}
