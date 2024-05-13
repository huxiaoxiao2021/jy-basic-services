package com.jdl.basic.provider.core.service.boxFlow.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jd.fastjson.JSONObject;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConfChangeLog;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowNoticDto;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.enums.CollectBoxFlowAddTypeEnum;
import com.jdl.basic.common.enums.CollectBoxFlowNoticTypeEnum;
import com.jdl.basic.common.utils.*;
import com.jdl.basic.provider.config.jdq.JDQ4Producer;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowInfoDao;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfService;
import com.jdl.basic.provider.mq.producer.DefaultJMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.jdl.basic.common.enums.CollectBoxFlowInfoOperateTypeEnum.ACTIVATE;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoOperateTypeEnum.ROLLBACK;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoStatusEnum.*;

/**
 * 集包规则配置服务实现类。
 * 实现了集包规则配置相关的业务逻辑处理。
 * <p>
 * 作者：wunan84
 * 日期：2023-04-14
 */
@Slf4j
@Service
public class CollectBoxFlowDirectionConfServiceImpl implements ICollectBoxFlowDirectionConfService {
    private static Integer DELETE_COUNT = 2000;
    @Resource
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfMapper;

    @Autowired
    @Qualifier("colletBoxFlowDirectionJDQ4Producer")
    private JDQ4Producer jdq4Producer;
    
    @Autowired
    private CollectBoxFlowInfoDao collectBoxFlowInfoDao;

    @Autowired(required = false)
    @Qualifier("collectBoxFlowNoticeMQ")
    private DefaultJMQProducer collectBoxFlowNoticeMQ;

    @Value("${mq.topic.collectBoxFlowNotice:}")
    private String collectBoxFlowNoticTopic;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    BasicPrimaryWS basicPrimary;

    /**
     * 根据ID查询集包规则配置。
     *
     * @param id 配置ID
     * @return 查询结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.selectById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<CollectBoxFlowDirectionConf> selectById(Long id) {
        Result<CollectBoxFlowDirectionConf> result = new Result<>();
        result.toFail();
        if(id == null){
            result.setMessage("id 不能为空！");
            return result;
        }
        CollectBoxFlowDirectionConf conf = collectBoxFlowDirectionConfMapper.selectByPrimaryKey(id);
        if(Objects.nonNull(conf)){
            result.toSuccess("获取配置成功！");
            result.setData(conf);
        }
        return result;
    }

    /**
     * 新增集包规则配置。
     *
     * @param conf 配置对象
     * @return 新增结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.newConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();
        try {
            Long id = conf.getId();
            //新规则，id校验
            if (id != null) {
                return Result.fail("新规则不能有id");
            }
            if (conf.getStartSiteId() == null || conf.getEndSiteId() == null
                    || conf.getStartOrgId() == null || conf.getEndOrgId() == null
                    || conf.getFlowType() == null || conf.getTransportType() == null) {
                log.error("newConfig未通过校验version{},参数:{}", conf.getVersion(), JsonHelper.toJSONString(conf));
                return Result.fail("参数错误，不能为空");
            }
            if (!Arrays.asList(CollectBoxFlowDirectionConf.FLOW_TYPE_IN,
                    CollectBoxFlowDirectionConf.FLOW_TYPE_OUT).contains(conf.getFlowType())) {
                return Result.fail("流向类型错误");
            }
            if (!Arrays.asList(CollectBoxFlowDirectionConf.TRANSPORT_TYPE_AIR,
                    CollectBoxFlowDirectionConf.TRANSPORT_TYPE_HIGHWAY).contains(conf.getTransportType())) {
                
                return Result.fail("运输类型错误");
            }
            //存在相同的规则校验
            CollectBoxFlowDirectionConf select = new CollectBoxFlowDirectionConf();
            select.setFlowType(conf.getFlowType());
            select.setTransportType(conf.getTransportType());
            select.setStartSiteId(conf.getStartSiteId());
            select.setEndSiteId(conf.getEndSiteId());
            select.setVersion(conf.getVersion());
            List<CollectBoxFlowDirectionConf> contains = collectBoxFlowDirectionConfMapper.select(select);
            if (CollectionUtils.isNotEmpty(contains)) {
                return Result.fail("已存在此目的网点的规则，不能重复添加");
            }
            //新增
            if (conf.getCreateTime() == null) {
                conf.setCreateTime(new Date());
            }
            //人工添加保存时删除version
            if(CollectBoxFlowAddTypeEnum.USER_ADD.getCode().equals(conf.getAddType())){
                conf.setVersion(null);
            }
            int i = collectBoxFlowDirectionConfMapper.insertSelective(conf);
            if (i != 1) {
                log.info("新增配置失败，参数：{}", JSONObject.toJSONString(conf));
                return Result.fail("新增失败");
            }
            result.toSuccess();
        } catch (Exception e) {
            log.info("新增配置失败", e);
            return Result.fail("新增失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新集包规则配置。
     *
     * @param conf 配置对象
     * @return 更新结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.updateConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();
        if (conf.getStartSiteId() == null || conf.getEndSiteId() == null
                || conf.getStartOrgId() == null || conf.getEndOrgId() == null
                || conf.getFlowType() == null || conf.getTransportType() == null
                || conf.getSupportDeputyReceiveSite() == null) {
            return result.toFail("参数错误，不能为空");
        }
        CollectBoxFlowDirectionConf query = new CollectBoxFlowDirectionConf();
        query.setStartSiteId(conf.getStartSiteId());
        query.setEndSiteId(conf.getEndSiteId());
        query.setTransportType(conf.getTransportType());
        query.setFlowType(conf.getFlowType());
        query.setYn(true);
        query.setVersion(conf.getVersion());
        //存在检验
        List<CollectBoxFlowDirectionConf> collectBoxFlowDirectionConfs = collectBoxFlowDirectionConfMapper.select(query);
        if (CollectionUtils.isEmpty(collectBoxFlowDirectionConfs)) {
            return result.toFail("没有找到该配置");
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
        // add 添加省区，枢纽，场景
        collectBoxFlowDirectionConf.setStartProvinceAgencyCode(conf.getStartProvinceAgencyCode());
        collectBoxFlowDirectionConf.setStartProvinceAgencyName(conf.getStartProvinceAgencyName());
        collectBoxFlowDirectionConf.setStartAreaHubCode(conf.getStartAreaHubCode());
        collectBoxFlowDirectionConf.setStartAreaHubName(conf.getStartAreaHubName());
        collectBoxFlowDirectionConf.setStartProvinceAgencyCode(conf.getStartProvinceAgencyCode());
        collectBoxFlowDirectionConf.setEndProvinceAgencyName(conf.getEndProvinceAgencyName());
        collectBoxFlowDirectionConf.setEndAreaHubCode(conf.getEndAreaHubCode());
        collectBoxFlowDirectionConf.setEndAreaHubName(conf.getEndAreaHubName());
        if (conf.getCollectClaim() != null) {
            collectBoxFlowDirectionConf.setCollectClaim(conf.getCollectClaim());
        }
        if (StringUtils.isNotEmpty(conf.getStartSiteName())) {
            collectBoxFlowDirectionConf.setStartSiteName(conf.getStartSiteName());
        }
        if (StringUtils.isNotEmpty(conf.getEndSiteName())) {
            collectBoxFlowDirectionConf.setEndSiteName(conf.getEndSiteName());
        }
        if (conf.getIfChangeSinceLastUpdate() != null) {
            collectBoxFlowDirectionConf.setIfChangeSinceLastUpdate(conf.getIfChangeSinceLastUpdate());
        }
        collectBoxFlowDirectionConf.setVersion(conf.getVersion());
        if(conf.getRouteErrorType() != null){
            collectBoxFlowDirectionConf.setRouteErrorType(conf.getRouteErrorType());
        }
        if (conf.getSupportDeputyReceiveSite() != null) {
            collectBoxFlowDirectionConf.setSupportDeputyReceiveSite(conf.getSupportDeputyReceiveSite());
        }
        if (conf.getDeputyBoxReceiveId() != null) {
            collectBoxFlowDirectionConf.setDeputyBoxReceiveId(conf.getDeputyBoxReceiveId());
        }
        if (conf.getDeputyBoxReceiveName() != null) {
            collectBoxFlowDirectionConf.setDeputyBoxReceiveName(conf.getDeputyBoxReceiveName());
        }
        if (conf.getDeputyBoxPkgName() != null) {
            collectBoxFlowDirectionConf.setDeputyBoxPkgName(conf.getDeputyBoxPkgName());
        }
        int i = collectBoxFlowDirectionConfMapper.updateByPrimaryKeySelective(collectBoxFlowDirectionConf);
        if (i != 1) {
            return result.toFail("更新失败");
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

        return result.toSuccess();
    }

    /**
     * 根据条件查询集包规则配置列表。
     *
     * @param pager    分页参数
     * @param configed 是否已配置
     * @return 分页查询结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.listByParamAndWhetherConfiged", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
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

    /**
     * 新增更新配置结果
     *
     * @param conf 配置规则
     * @return 新增更新结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.updateOrNewConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> updateOrNewConfig(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();

        try {
            log.info("更新或新规则:{}", JSONObject.toJSONString(conf));
            Integer startSiteId = conf.getStartSiteId();
            Integer endSiteId = conf.getEndSiteId();
            Integer boxReceiveId = conf.getBoxReceiveId();
            Integer flowType = conf.getFlowType();
            Integer transportType = conf.getTransportType();
            // 增加省区，枢纽，场景信息
            addDirectionConfInfo(conf);
            CollectBoxFlowDirectionConf query = new CollectBoxFlowDirectionConf();
            query.setStartSiteId(startSiteId);
            query.setStartOrgId(conf.getStartOrgId());
            query.setEndOrgId(conf.getEndOrgId());
            query.setEndSiteId(endSiteId);
            query.setTransportType(transportType);
            query.setVersion(conf.getVersion());
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
            return Result.fail("更新或新规则异常" + e.getMessage());
        }
        return result;
    }
    private void addDirectionConfInfo(CollectBoxFlowDirectionConf conf) {
        // 通过调用getBaseSiteBySiteId方法查询始发站点和目的站点信息
        BaseStaffSiteOrgDto startSiteInfo = basicPrimary.getBaseSiteBySiteId(conf.getStartSiteId());
        BaseStaffSiteOrgDto endSiteInfo = basicPrimary.getBaseSiteBySiteId(conf.getEndSiteId());
        // 如果始发站点信息和省区编码不为空，则设置始发省区编码和名称
        if (Objects.nonNull(startSiteInfo) && null != startSiteInfo.getProvinceAgencyCode()) {
            conf.setStartProvinceAgencyCode(startSiteInfo.getProvinceAgencyCode());
            conf.setStartProvinceAgencyName(startSiteInfo.getProvinceAgencyName());
        }
        // 如果始发站点信息和区域编码不为空，则设置始发区域枢纽编码和名称
        if (Objects.nonNull(startSiteInfo) && null != startSiteInfo.getAreaCode()) {
            conf.setStartAreaHubCode(startSiteInfo.getAreaCode());
            conf.setStartAreaHubName(startSiteInfo.getAreaName());
        }
        // 如果目的站点信息和省区编码不为空，则设置目的省区编码和名称
        if (Objects.nonNull(endSiteInfo) && null != endSiteInfo.getProvinceAgencyCode()) {
            conf.setEndProvinceAgencyCode(endSiteInfo.getProvinceAgencyCode());
            conf.setEndProvinceAgencyName(endSiteInfo.getProvinceAgencyName());
        }
        // 如果目的站点信息和区域编码不为空，则设置目的区域枢纽编码和名称
        if (Objects.nonNull(endSiteInfo) && null != endSiteInfo.getAreaCode()) {
            conf.setEndAreaHubCode(endSiteInfo.getAreaCode());
            conf.setEndAreaHubName(endSiteInfo.getAreaName());
        }
    }

    /**
     * 根据版本号删除配置。
     *
     * @param version     版本号
     * @param deleteCount 删除数量限制
     * @return 删除计数
     */
    @Override
    public int deleteByVersion(String version, Integer deleteCount){
        return collectBoxFlowDirectionConfMapper.deleteByVersion(version, deleteCount);
    }

    /**
     * 切换到新版本的集包规则配置。
     * 1. 删除历史版本
     * 2. 当前版本修改成历史版本
     * @throws Exception 操作异常
     */
    @Override
    @Transactional
    public void switchNewVersion() throws Exception{
        log.info("小件集包切换版本，开始执行");
        Date todayEndTime = DateHelper.transTimeMaxOfDate(new Date());
        Date todayStartTime = DateHelper.transTimeMinOfDate(new Date());
        //Date startTime = DateHelper.addDays(todayStartTime, -3);
        Date endTime = DateHelper.addDays(todayEndTime, -3);
        CollectBoxFlowInfo unactivated = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, endTime, 
                UNACTIVATED.getCode());
        if(unactivated == null){
            log.info("根据时间查询结束时间:{}未查到待激活版本", 
                    DateHelper.getDateOfyyMMddHHmmss(endTime));
            return;
        }
        
        log.info("根据时间查询结束时间:{}未查到待激活版本", 
                DateHelper.getDateOfyyMMddHHmmss(endTime));
        //历史版本
//        CollectBoxFlowInfo history = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, null,
//                HISTORY.getCode());
//        //删除历史版本
//        if(history != null){
//            collectBoxFlowInfoDao.deleteByPrimaryKey(history.getId());
//            int count = 0;
//            int sum = 0;
//            do {
//                count = deleteByVersion(history.getVersion(), DELETE_COUNT);
//                sum += count;
//            }while (count > 0);
//            log.info("删除历史版本数据version:{},count:{}", history.getVersion(), sum);
//        }
        CollectBoxFlowInfo entity = new CollectBoxFlowInfo();;
        Date updateTime = new Date();
        //当前版本
        CollectBoxFlowInfo current = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, null,
                CURRENT.getCode());
        if(current != null){
            //throw new Exception("未查到激活的版本");
            //当前版本改成历史版本
            
            entity.setId(current.getId());
            entity.setStatus(HISTORY.getCode());
            entity.setOperateType(ACTIVATE.getCode());
            entity.setUpdateTime(updateTime);
            collectBoxFlowInfoDao.updateByPrimaryKeySelective(entity);
        }else {
            log.error("小件集包规则，定时切换时未查到已激活版本");
        }
        
        //待激活的修改状态
        entity.setId(unactivated.getId());
        entity.setStatus(CURRENT.getCode());
        entity.setOperateType(ACTIVATE.getCode());
        entity.setUpdateTime(updateTime);
        collectBoxFlowInfoDao.updateByPrimaryKeySelective(entity);
        
        
    }

    /**
     * 回滚到上一个版本的集包规则配置。
     *
     * @return 操作结果
     */
    @Override
    @Transactional
    public Result<Boolean> rollbackVersion() {
        Result<Boolean> result = new Result<>();
        result.toSuccess();
        //历史版本
        CollectBoxFlowInfo history = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, null,
                HISTORY.getCode());
        if(history == null){
           return result.toFail("无可切换的历史版本，请联系值班研发");
        }

        CollectBoxFlowInfo current = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, null,
                CURRENT.getCode());
        if(current == null){
            return result.toFail("无当前激活版本，请联系值班研发");
        }
        
        Date updateTime = new Date();
        CollectBoxFlowInfo entity = null;
        entity = new CollectBoxFlowInfo();
        entity.setId(current.getId());
        entity.setStatus(HISTORY.getCode());
        entity.setOperateType(ROLLBACK.getCode());
        entity.setUpdateTime(updateTime);
        collectBoxFlowInfoDao.updateByPrimaryKeySelective(entity);
        
        //激活历史版本
        entity.setId(history.getId());
        entity.setStatus(CURRENT.getCode());
        entity.setOperateType(ROLLBACK.getCode());
        entity.setUpdateTime(updateTime);
        collectBoxFlowInfoDao.updateByPrimaryKeySelective(entity);
        
        //发送通知
        CollectBoxFlowNoticDto dto = initCollectBoxFlowNoticDto(history.getVersion());
        try {
            collectBoxFlowNoticeMQ.send(history.getVersion(), JsonHelper.toJSONString(dto));
        } catch (Exception ex) {
            log.error("回滚箱号版本MQ发送通知失败:{}",history.getVersion(), ex);
        }
        return result;
    }

    /**
     * 查询所有集包规则信息。
     *
     * @return 集包规则信息列表
     */
    @Override
    public List<CollectBoxFlowInfo> selectAllCollectBoxFlowInfo() {
        return collectBoxFlowInfoDao.selectAll();
    }


    private CollectBoxFlowNoticDto initCollectBoxFlowNoticDto(String version){
        CollectBoxFlowNoticDto dto = new CollectBoxFlowNoticDto();
        dto.setVersion(version);
        dto.setOperateType(CollectBoxFlowNoticTypeEnum.ROLLBACK.getCode());
        return dto;
    }

    /**
     * 获取当前集包规则配置的版本。
     *
     * @return 当前版本号
     */
    @Override
    public String getCurrentVersion(){
        CollectBoxFlowInfo collectBoxFlowInfo = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, null, CURRENT.getCode());
        if(collectBoxFlowInfo != null){
            return collectBoxFlowInfo.getVersion();
        }
        return null;
    }

    /**
     * 更新集包规则信息。
     *
     * @param collectBoxFlowInfo 集包规则信息
     * @return 更新计数
     */
    @Override
    public int updateCollectBoxFlowInfo(CollectBoxFlowInfo collectBoxFlowInfo) {
        return collectBoxFlowInfoDao.updateByPrimaryKeySelective(collectBoxFlowInfo);
    }

    /**
     * 根据配置状态分页查询集包规则配置。
     *
     * @param pager 分页参数
     * @return 分页结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.listCollectBoxFlowDirectionConf", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Pager<CollectBoxFlowDirectionConf>> listCollectBoxFlowDirectionConf(Pager<CollectBoxFlowDirectionConf> pager) {
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
                .doSelectPage(() -> collectBoxFlowDirectionConfMapper.select(pager.getSearchVo()));
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

    /**
     * 更新现有的集包规则配置。
     *
     * @param conf 待更新的配置信息
     * @return 更新结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfServiceImpl.updateCollectBoxFlowDirectionConf", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> updateCollectBoxFlowDirectionConf(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();
        if (conf.getStartSiteId() == null || conf.getEndSiteId() == null || conf.getStartOrgId() == null
            || conf.getEndOrgId() == null || conf.getFlowType() == null || conf.getTransportType() == null
            || conf.getSupportDeputyReceiveSite() == null) {
            return result.toFail("参数错误，不能为空");
        }
        int i = collectBoxFlowDirectionConfMapper.updateByPrimaryKeySelective(conf);
        if (i != 1) {
            return result.toFail("更新失败");
        }
        return result.success();
    }

    @Override
    public Result<Boolean> deleteConfig(CollectBoxFlowDirectionConf conf) {
        Result<Boolean> result = new Result<>();
        try {
            CollectBoxFlowDirectionConf queryConf = collectBoxFlowDirectionConfMapper.selectByPrimaryKey(conf.getId());
            if (!Objects.equals(CollectBoxFlowDirectionConf.COLLECT_CLAIM_MIX,queryConf.getCollectClaim())){
                log.info("删除失败，集包要求不是可混包，参数：{}", JSONObject.toJSONString(conf));
                return Result.fail("删除失败，集包要求不是可混包");
            }
            int i = collectBoxFlowDirectionConfMapper.updateYnById(conf);
            if (i != 1) {
                log.info("新增配置失败，参数：{}", JSONObject.toJSONString(conf));
                return Result.fail("删除失败");
            }
            result.toSuccess();
        } catch (Exception e) {
            log.info("删除失败配置失败", e);
            return Result.fail("删除失败" + e.getMessage());
        }
        return result;
    }

}
