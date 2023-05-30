package com.jdl.basic.provider.core.manager;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.dto.BaseStoreInfoDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.common.contants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础资料包装类
 *
 * @author hujiping
 * @date 2022/10/8 11:17 AM
 */
@Slf4j
@Service("baseMajorManager")
public class BaseMajorManagerImpl implements BaseMajorManager {

    @Autowired
    private BasicPrimaryWS basicPrimaryWS;

    @Cache(key = "baseMajorManagerImpl.getBaseSiteBySiteId@args0", memoryEnable = true, memoryExpiredTime = 5 * 60 * 1000,
            redisEnable = true, redisExpiredTime = 10 * 60 * 1000)
    @Override
    public BaseStaffSiteOrgDto getBaseSiteBySiteId(Integer siteCode) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BaseMajorManagerImpl.getBaseSiteBySiteId",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicPrimaryWS.getBaseSiteBySiteId(siteCode);
        }catch (Exception e){
            log.error("根据站点ID：{}查询场地信息异常!", siteCode, e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }

    @Override
    public PageDto<List<Integer>> getBaseSiteCodeNoYnByPage(Integer subType, Integer pageIndex) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BaseMajorManagerImpl.getBaseSiteCodeNoYnByPage",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicPrimaryWS.getBaseSiteCodeNoYnByPage(subType, pageIndex);
        }catch (Exception e){
            log.error("分页查询站点异常!", e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }

    @Override
    public PageDto<List<BaseStoreInfoDto>> getBaseStoreInfoByPage(Integer pageIndex) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BaseMajorManagerImpl.getBaseStoreInfoByPage",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicPrimaryWS.getBaseStoreInfoByPage(pageIndex);
        }catch (Exception e){
            log.error("分页查询站点异常!", e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }
}
