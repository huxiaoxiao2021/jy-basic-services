package com.jdl.basic.provider.core.service.boxFlow.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jd.fastjson.JSONObject;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConfChangeLog;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.jdq.JDQ4Producer;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("collectBoxFlowDirectionConfService")
@Slf4j
public class CollectBoxFlowDirectionConfServiceImpl implements ICollectBoxFlowDirectionConfService {

    @Resource
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfMapper;

    @Autowired
    @Qualifier("colletBoxFlowDirectionJDQ4Producer")
    private JDQ4Producer jdq4Producer;

    @Override
    public CollectBoxFlowDirectionConf selectById(Long id) {
        return collectBoxFlowDirectionConfMapper.selectByPrimaryKey(id);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.newConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();
        try {
            Long id = conf.getId();
            //新规则，id校验
            if (id != null) {
                result.fail("新规则不能有id");
                return result;
            }
            if (conf.getStartSiteId() == null || conf.getEndSiteId() == null
                    || conf.getStartOrgId() == null || conf.getEndOrgId() == null
                    || conf.getFlowType() == null || conf.getTransportType() == null) {
                result.fail("参数错误，不能为空");
                return result;
            }
            if (!Arrays.asList(CollectBoxFlowDirectionConf.FLOW_TYPE_IN,
                    CollectBoxFlowDirectionConf.FLOW_TYPE_OUT).contains(conf.getFlowType())) {
                result.fail("流向类型错误");
                return result;
            }
            if (!Arrays.asList(CollectBoxFlowDirectionConf.TRANSPORT_TYPE_AIR,
                    CollectBoxFlowDirectionConf.TRANSPORT_TYPE_HIGHWAY).contains(conf.getTransportType())) {
                result.fail("运输类型错误");
                return result;
            }
            //存在相同的规则校验
            CollectBoxFlowDirectionConf select = new CollectBoxFlowDirectionConf();
            select.setFlowType(conf.getFlowType());
            select.setTransportType(conf.getTransportType());
            select.setStartSiteId(conf.getStartSiteId());
            select.setEndSiteId(conf.getEndSiteId());

            List<CollectBoxFlowDirectionConf> contains = collectBoxFlowDirectionConfMapper.select(select);
            if (CollectionUtils.isNotEmpty(contains)) {
                result.fail("已存在相同的规则");
                return result;
            }
            //新增
            if (conf.getCreateTime() == null) {
                conf.setCreateTime(new Date());
            }

            int i = collectBoxFlowDirectionConfMapper.insertSelective(conf);
            if (i != 1) {
                log.info("新增配置失败，参数：{}", JSONObject.toJSONString(conf));
                result.fail("新增失败");
                return result;
            }
            result.toSuccess();
        } catch (Exception e) {
            log.info("新增配置失败", e);
            result.fail("新增失败" + e.getMessage());
        }
        return result;
    }
    @Override
    public Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();
        if (conf.getStartSiteId() == null || conf.getEndSiteId() == null
                || conf.getStartOrgId() == null || conf.getEndOrgId() == null
                || conf.getFlowType() == null || conf.getTransportType() == null) {
            result.fail("参数错误，不能为空");
            return result;
        }
        CollectBoxFlowDirectionConf query = new CollectBoxFlowDirectionConf();
        query.setStartSiteId(conf.getStartSiteId());
        query.setEndSiteId(conf.getEndSiteId());
        query.setTransportType(conf.getTransportType());
        query.setFlowType(conf.getFlowType());
        query.setYn(true);

        //存在检验
        List<CollectBoxFlowDirectionConf> collectBoxFlowDirectionConfs = collectBoxFlowDirectionConfMapper.select(query);
        if (CollectionUtils.isEmpty(collectBoxFlowDirectionConfs)) {
            result.fail("没有找到该配置");
            return result;
        }

        CollectBoxFlowDirectionConf collectBoxFlowDirectionConf = collectBoxFlowDirectionConfs.get(0);
        CollectBoxFlowDirectionConfChangeLog log = CollectBoxFlowDirectionConfChangeLog.builder()
                .id(collectBoxFlowDirectionConf.getId())
                .startOrgId(collectBoxFlowDirectionConf.getStartOrgId()).endOrgId(collectBoxFlowDirectionConf.getEndOrgId())
                .startSiteId(collectBoxFlowDirectionConf.getStartSiteId()).endSiteId(collectBoxFlowDirectionConf.getEndSiteId())
                .startOrgName(collectBoxFlowDirectionConf.getStartOrgName()).endOrgName(collectBoxFlowDirectionConf.getEndOrgName())
                .startOrgName(collectBoxFlowDirectionConf.getStartOrgName()).endOrgName(collectBoxFlowDirectionConf.getEndOrgName())
                .originalBoxReceiveId(collectBoxFlowDirectionConf.getBoxReceiveId())
                .originalBoxPkgName(collectBoxFlowDirectionConf.getBoxPkgName())
                .originalBoxReceiveName(collectBoxFlowDirectionConf.getBoxReceiveName())

                .build();
        collectBoxFlowDirectionConf.setBoxReceiveId(conf.getBoxReceiveId());
        collectBoxFlowDirectionConf.setBoxPkgName(conf.getBoxPkgName());
        collectBoxFlowDirectionConf.setBoxReceiveName(conf.getBoxReceiveName());
        collectBoxFlowDirectionConf.setUpdateTime(new Date());
        collectBoxFlowDirectionConf.setUpdateUserErp(conf.getUpdateUserErp());
        int i = collectBoxFlowDirectionConfMapper.updateByPrimaryKeySelective(collectBoxFlowDirectionConf);
        if (i != 1) {
            result.fail("更新失败");
            return result;
        }
        log.setNewBoxReceiveId(conf.getBoxReceiveId());
        log.setNewBoxPkgName(conf.getBoxPkgName());
        log.setNewBoxReceiveName(conf.getBoxReceiveName());
        log.setUpdateTime(System.currentTimeMillis());
        log.setTs(System.currentTimeMillis());
        log.setFlowType(conf.getFlowType());
        log.setTransportType(conf.getTransportType());
        log.setUpdateUserErp(conf.getUpdateUserErp());
        log.setStartSiteName(conf.getStartSiteName());
        log.setEndSiteName(conf.getEndSiteName());

        jdq4Producer.produce(collectBoxFlowDirectionConf.getId().toString(), log);//日志消息发送到消息队列

        result.success(true);
        return result;
    }

    @Override
    public Result<Pager<CollectBoxFlowDirectionConf>> listByParamAndWhetherConfiged(Pager<CollectBoxFlowDirectionConf> pager, Boolean configed) {
        Result<Pager<CollectBoxFlowDirectionConf>> result = Result.success();
        if(pager == null){
            return Result.fail("必填参数不能为空");
        }
        if(pager.getPageNo() == null){
            pager.setPageNo(1);
        }
        if(pager.getPageSize() == null){
            pager.setPageSize(10);
        }
        Page<CollectBoxFlowDirectionConf> collectBoxFlowDirectionConfs = null;
        try {
            collectBoxFlowDirectionConfs = PageHelper.startPage(pager.getPageNo(), pager.getPageSize())
                    .doSelectPage(() -> collectBoxFlowDirectionConfMapper.selectByParamAndWhetherConfiged(pager.getSearchVo(), configed));
        } catch (Exception e) {
            log.error("分页查询异常，参数:" + JSONObject.toJSONString(pager), e);
        }
        Pager<CollectBoxFlowDirectionConf> resultPager = new Pager<>();
        if(collectBoxFlowDirectionConfs != null){
            resultPager.setData(collectBoxFlowDirectionConfs.getResult());
            resultPager.setTotal(collectBoxFlowDirectionConfs.getTotal());
            resultPager.setPageNo(collectBoxFlowDirectionConfs.getPageNum());
        }
        result.setData(resultPager);
        return result;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updateOrNewConfig(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();

        try {
            log.info("更新或新规则:{}", JSONObject.toJSONString(conf));
            Integer startSiteId = conf.getStartSiteId();
            Integer endSiteId = conf.getEndSiteId();
            Integer boxReceiveId = conf.getBoxReceiveId();
            Integer flowType = conf.getFlowType();
            Integer transportType = conf.getTransportType();

            CollectBoxFlowDirectionConf query = new CollectBoxFlowDirectionConf();
            query.setStartSiteId(startSiteId);
            query.setStartOrgId(conf.getStartOrgId());
            query.setEndOrgId(conf.getEndOrgId());
            query.setEndSiteId(endSiteId);
            query.setTransportType(transportType);

            List<CollectBoxFlowDirectionConf> select = collectBoxFlowDirectionConfMapper.select(query);
            if (CollectionUtils.isNotEmpty(select)) {
                return updateConfig(conf);
            } else {
                conf.setId(null);

                Result<Boolean> insertResult = newConfig(conf);
                if (insertResult.isSuccess()) {
                    result.toSuccess();
                } else {
                    result.toFail(insertResult.getMessage());
                }

            }

        } catch (Exception e) {
            log.error("更新或新规则异常，参数:{}", JSONObject.toJSONString(conf), e);
            result.fail("更新或新规则异常" + e.getMessage());
        }
        return result;
    }

}
