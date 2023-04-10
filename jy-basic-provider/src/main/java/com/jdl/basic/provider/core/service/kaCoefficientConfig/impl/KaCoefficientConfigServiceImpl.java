package com.jdl.basic.provider.core.service.kaCoefficientConfig.impl;

import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigDto;
import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigQueryDto;
import com.jdl.basic.api.enums.KaCoefficientStatusEnum;
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

        // 查询有效数据条数
        Integer kaConfigCount = kaCoefficientConfigDao.getCountOfInEffectState();
        //查询分页数据
        List<KaCoefficientConfigPO> data = kaCoefficientConfigDao.selectByMerchantCodeAndStatus(param);
        List<KaCoefficientConfigDto> dataTmp = new ArrayList<>();
        data.stream().forEach(obj -> {
            KaCoefficientConfigDto tmp = assertDto(obj);
            tmp.setKaConfigCount(kaConfigCount);
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
        data.setMerchantCode(param.getMerchantCode());
        data.setMerchantName(param.getMerchantName());
        data.setUpdateUserName(param.getUpdateUserName());
        data.setUpdateUser(param.getUpdateUser());
        data.setId(param.getId());
        data.setUpdateTime(param.getUpdateTime());
        return kaCoefficientConfigDao.modifyCoefficient(data);
    }

    @Override
    public Integer delCoefficientById(KaCoefficientConfigDto param) {
        KaCoefficientConfigPO data = new KaCoefficientConfigPO();
        data.setId(param.getId());
        data.setStatus(KaCoefficientStatusEnum.LOSE_EFFICACY.getCode());
        data.setStatusName(KaCoefficientStatusEnum.LOSE_EFFICACY.getName());
        data.setUpdateUser(param.getUpdateUser());
        data.setUpdateUserName(param.getUpdateUserName());
        data.setUpdateTime(new Date());
        return kaCoefficientConfigDao.delById(data);
    }

    @Override
    public Integer getCountOfInEffectState() {
        return kaCoefficientConfigDao.getCountOfInEffectState();
    }

    @Override
    public KaCoefficientConfigDto getInEffectKaCoefficientConfig(KaCoefficientConfigDto param) {
        KaCoefficientConfigPO data = kaCoefficientConfigDao.getInEffectKaCoefficientConfig(param);
        KaCoefficientConfigDto tmp = assertDto(data);
        return tmp;
    }

    /**
     * 转化dto
     * @param data
     * @return
     */
    private static KaCoefficientConfigDto assertDto(KaCoefficientConfigPO data) {
        KaCoefficientConfigDto tmp = new KaCoefficientConfigDto();
        tmp.setId(data.getId());
        tmp.setMerchantCode(data.getMerchantCode());
        tmp.setMerchantName(data.getMerchantName());
        tmp.setCoefficient(data.getCoefficient());
        tmp.setStatus(data.getStatus());
        tmp.setStatusName(data.getStatusName());
        tmp.setCreateUser(data.getCreateUser());
        tmp.setCreateUserName(data.getCreateUserName());
        tmp.setUpdateUser(data.getUpdateUser());
        tmp.setUpdateUserName(data.getUpdateUserName());
        tmp.setCreateTime(data.getCreateTime());
        tmp.setUpdateTime(data.getUpdateTime());
        return tmp;
    }

    @Override
    public KaCoefficientConfigDto getInEffectKaCoefficientConfigWithCache(String merchantCode) {
        KaCoefficientConfigPO data = kaCoefficientConfigDao.getInEffectKaCoefficientConfigWithCache(merchantCode);
        KaCoefficientConfigDto tmp = assertDto(data);
        return tmp;
    }
}
