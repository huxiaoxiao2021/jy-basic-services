package com.jdl.basic.provider.core.provider.akbox;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.domain.akbox.AkboxConfig;
import com.jdl.basic.api.domain.akbox.AkboxConfigQuery;
import com.jdl.basic.api.service.akbox.AkboxConfigJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.ResultCodeConstant;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.akbox.IAkboxConfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class AkboxConfigJsfServiceImpl implements AkboxConfigJsfService {

    @Resource
    IAkboxConfService akboxConfigService;

    @Override
    public Result<Integer> save(AkboxConfig akboxConfig) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.provider.akbox.AkboxConfigJsfServiceImpl.save",
                Constants.UMP_APP_NAME, false, true);
        Result result = new Result<>();
        try{
            if (akboxConfigService.selectBySiteCode(akboxConfig.getSiteCode().longValue())!=null) {
                result.setCode(500);
                result.setMessage("当前场站已存在，请选中当前场站编辑");
            } else {
                result.toSuccess();
                result.setData(akboxConfigService.save(akboxConfig));
            }
        }catch (Exception e){
            log.error("保存周转筐:{}异常!", JsonHelper.toJSONString(akboxConfig), e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return result;
    }

    @Override
    public Result<Integer> batchSave(List<AkboxConfig> akboxConfigList) {
        StringBuilder message = new StringBuilder();
        Result result = new Result<>();
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.provider.akbox.AkboxConfigJsfServiceImpl.batchSave",
                Constants.UMP_APP_NAME, false, true);
        try{
            akboxConfigList.forEach(akboxConfig -> {
                AkboxConfig akbox = akboxConfigService.selectBySiteCode(akboxConfig.getSiteCode().longValue());
                if (akbox != null) {
                    akbox.setSmallStock(akboxConfig.getSmallStock());
                    akbox.setLargeStock(akboxConfig.getLargeStock());
                    akbox.setUpdateUserErp(akboxConfig.getUpdateUserErp());
                    akbox.setUpdateUser(akboxConfig.getUpdateUser());
                    akbox.setUpdateTime(akboxConfig.getUpdateTime());
                    try {
                        akboxConfigService.updateBySiteCode(akbox);
                    } catch (Exception e) {
                        message.append("更新站点编码 " + akbox.getSiteCode() + " 失败 ");
                        log.error("更新{} 失败 {}", akbox.getSiteCode(), e);
                    }
                } else {
                    try {
                        akboxConfig.setYn(true);
                        akboxConfigService.save(akboxConfig);
                    } catch (Exception e) {
                        message.append("保存站点编码 " + akboxConfig.getSiteCode() + " 失败 ");
                        log.error("保存{} 失败 {}", akboxConfig.getSiteCode(), e);
                    }
                }
            });

            if (StringUtils.isEmpty(message)) {
                result.toSuccess();
            } else {
                result.setCode(ResultCodeConstant.ERROR);
                result.setMessage(message.toString());
            }
        }catch (Exception e){
            log.error("保存周转筐:{}查询库房数据异常!", JsonHelper.toJSONString(akboxConfigList), e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return result;
    }

    @Override
    public Result<Integer> update(AkboxConfig akboxConfig) {
        Result result = new Result<>();
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.provider.akbox.AkboxConfigJsfServiceImpl.update",
                Constants.UMP_APP_NAME, false, true);
        try{
            result.setData(akboxConfigService.update(akboxConfig));
        }catch (Exception e){
            log.error("更新周转筐:{} 数据异常!", JsonHelper.toJSONString(akboxConfig), e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return result;
    }

    @Override
    public Result<Pager<AkboxConfig>> queryPageByCondition(AkboxConfigQuery query) {
        Result result = new Result<>();
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.provider.akbox.AkboxConfigJsfServiceImpl.update",
                Constants.UMP_APP_NAME, false, true);
        try{
            Pager<AkboxConfigQuery> queryPager = new Pager<>();
            queryPager.setPageNo(query.getPageNo());
            queryPager.setPageSize(query.getPageSize());
            queryPager.setSearchVo(query);
            result.setData(akboxConfigService.queryPageByCondition(queryPager));
        }catch (Exception e){
            log.error("查询周转筐:{} 配置数据异常!", JsonHelper.toJSONString(query), e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return result;
    }

    @Override
    public Result<AkboxConfig> queryBySiteCode(Long siteCode) {
        Result result = new Result<>();
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.provider.akbox.AkboxConfigJsfServiceImpl.update",
                Constants.UMP_APP_NAME, false, true);
        try{
            result.toSuccess();
            result.setData(akboxConfigService.selectBySiteCode(siteCode));
        }catch (Exception e){
            log.error("查询周转筐bySiteCode:{} !", siteCode.toString(), e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return result;
    }


}
