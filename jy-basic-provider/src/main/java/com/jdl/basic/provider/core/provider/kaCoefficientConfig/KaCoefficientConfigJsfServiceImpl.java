package com.jdl.basic.provider.core.provider.kaCoefficientConfig;

import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigDto;
import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigQueryDto;
import com.jdl.basic.api.service.kaCoefficientConfig.KaCoefficientConfigJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.kaCoefficientConfig.KaCoefficientConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class KaCoefficientConfigJsfServiceImpl implements KaCoefficientConfigJsfService {

    private static final Integer limitNum = 50;


    @Autowired
    private KaCoefficientConfigService kaCoefficientConfigService;

    @Override
    public Result<PageDto<KaCoefficientConfigDto>> getConfigByMerchantCodeAndStatus(KaCoefficientConfigQueryDto param) {
        Result<PageDto<KaCoefficientConfigDto>> result = new Result<>();
        try {
            PageDto<KaCoefficientConfigDto> data = kaCoefficientConfigService.geConfigByMerchantCodeAndStatus(param);

            result.toSuccess(data,"success");
        }catch (Exception ex){
            log.error("KaCoefficientConfigJsfServiceImpl.getConfigByMerchantCodeAndStatus has error.",ex);
            result.toFail(String.format("查询数据失败，失败原因:{}", ex.getMessage()));
        }
        return result;
    }

    @Override
    public Result<Boolean> addKaCoefficientConfig(KaCoefficientConfigDto param) {
        Result<Boolean> result = new Result<>();
        try {
            //校验有效配置数
            Integer numOfRecords = kaCoefficientConfigService.getCountOfInEffectState();

            if (numOfRecords >= limitNum){
                result.toFail("有效数据最多50条！");
            }else{
                Integer resultData = kaCoefficientConfigService.addKaCoefficientConfig(param);
                result.toSuccess(resultData > 0 ? Boolean.TRUE:Boolean.FALSE,"success");
            }
        }catch (Exception ex){
            result.toFail(String.format("新增数据失败，失败原因:{}", ex.getMessage()));
        }
        return result;
    }

    @Override
    public Result<Boolean> modifyCoefficient(KaCoefficientConfigDto param) {
        Result<Boolean> result = new Result<>();
        try {
            Integer resultData = kaCoefficientConfigService.modifyCoefficient(param);
            result.toSuccess(resultData > 0 ? Boolean.TRUE:Boolean.FALSE,"success");
        }catch (Exception ex){
            result.toFail(String.format("修改系数失败，失败原因:{}", ex.getMessage()));
        }
        return result;
    }

    @Override
    public Result<Boolean> delCoefficientById(KaCoefficientConfigDto param) {
        Result<Boolean> result = new Result<>();
        try {
            Integer resultData = kaCoefficientConfigService.delCoefficientById(param);
            result.toSuccess(resultData > 0 ? Boolean.TRUE:Boolean.FALSE,"success");
        }catch (Exception ex){
            result.toFail(String.format("删除系数失败，失败原因:{}", ex.getMessage()));
        }
        return result;
    }
}
