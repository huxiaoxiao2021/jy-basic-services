package com.jdl.basic.provider.core.manager;

import com.google.common.collect.Lists;
import com.jd.ql.basic.domain.BaseOrganStruct;
import com.jd.ql.basic.ws.BasicOrganStructWS;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.common.contants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2023/6/15 2:28 PM
 */
@Slf4j
@Service("basicOrganStructWSManager")
public class BasicOrganStructWSManagerImpl implements BasicOrganStructWSManager {
    
    @Autowired
    private BasicOrganStructWS basicOrganStructWS;

    @Override
    public List<BaseOrganStruct> getBaseOrganStructByParentCode(String parentCode) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BasicOrganStructWSManager.getBaseOrganStructByParentCode",
                Constants.UMP_APP_NAME, false, true);
        List<BaseOrganStruct> list = Lists.newArrayList();
        try{
            list.addAll(basicOrganStructWS.getBaseOrganStructByParentCode(parentCode));
        }catch (Exception e){
            log.error("根据父级编码:{}获取下级机构异常!", parentCode, e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return list;
    }
}
