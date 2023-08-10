package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.domain.PsStoreInfo;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.dto.PsStoreInfoRequest;
import com.jd.ql.basic.util.PageUtil;
import com.jd.ql.basic.ws.BasicWareHouseWS;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库房查询接口
 *
 * @author hujiping
 * @date 2023/6/28 4:02 PM
 */
@Slf4j
@Service("basicWareHouseWSManager")
public class BasicWareHouseWSManagerImpl implements BasicWareHouseWSManager{

    @Autowired
    private BasicWareHouseWS basicWareHouseWS;

    @Override
    public PageDto<List<PsStoreInfo>> query(PsStoreInfoRequest psStoreInfoRequest, PageUtil pageUtil) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BasicWareHouseWSManager.query",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicWareHouseWS.query(psStoreInfoRequest, pageUtil);
        }catch (Exception e){
            log.error("根据条件:{}查询库房数据异常!", JsonHelper.toJSONString(psStoreInfoRequest), e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }
}
