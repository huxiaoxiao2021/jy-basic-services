package com.jdl.basic.provider.core.provider.workStation;

import com.google.common.collect.Lists;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.service.workStation.OrgSwitchProvinceBrushJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.core.dao.easyFreezeSite.EasyFreezeSiteDao;
import com.jdl.basic.provider.core.dao.transferDp.ConfigTransferDpSiteDao;
import com.jdl.basic.provider.core.dao.workStation.*;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.po.EasyFreezeSitePO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 省区切换刷数接口实现
 *
 * @author hujiping
 * @date 2023/6/6 3:08 PM
 */
@Slf4j
@Service("orgSwitchProvinceBrushJsfService")
public class OrgSwitchProvinceBrushJsfServiceImpl implements OrgSwitchProvinceBrushJsfService {
    
    @Autowired
    private WorkStationGridDao workStationGridDao;

    @Autowired
    private WorkGridDao workGridDao;
    
    @Autowired
    private WorkGridFlowDirectionDao workGridFlowDirectionDao;

    @Autowired
    private WorkGridFlowDetailOfflineDao workGridFlowDetailOfflineDao;
    
    @Autowired
    private SiteWaveScheduleDao siteWaveScheduleDao;

    @Autowired
    private SiteAttendPlanDao siteAttendPlanDao;

    @Autowired
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfDao;

    @Autowired
    private EasyFreezeSiteDao easyFreezeSiteDao;

    @Autowired
    private ConfigTransferDpSiteDao configTransferDpSiteDao;

    @Autowired
    private WorkStationAttendPlanDao workStationAttendPlanDao;

    @Autowired
    private BaseMajorManager baseMajorManager;
    
    @Override
    public void workStationGridBrush() {
        // 起始id
        int offsetId = 0;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < 1000) {
            List<WorkStationGrid> singleList = workStationGridDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<WorkStationGrid> list = Lists.newArrayList();
            for (WorkStationGrid item : singleList) {
                if(item.getSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
                if(baseSite == null){
                    continue;
                }
                WorkStationGrid updateItem = new WorkStationGrid();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }

            WorkStationGrid workStationGrid = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workStationGrid.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = workStationGridDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_station_grid 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void workGridBrush() {
        // 起始id
        int offsetId = 0;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < 1000) {
            List<WorkGrid> singleList = workGridDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<WorkGrid> list = Lists.newArrayList();
            for (WorkGrid item : singleList) {
                if(item.getSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
                if(baseSite == null){
                    continue;
                }
                WorkGrid updateItem = new WorkGrid();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }

            WorkGrid workGrid = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workGrid.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = workGridDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_grid 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void workGridFlowDirectionBrush(Integer startId) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < 1000) {
            List<WorkGridFlowDirection> singleList = workGridFlowDirectionDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<WorkGridFlowDirection> list = Lists.newArrayList();
            for (WorkGridFlowDirection item : singleList) {
                if(item.getSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
                if(baseSite == null){
                    continue;
                }
                BaseStaffSiteOrgDto flowBaseSite = null;
                if(item.getFlowSiteCode() != null){
                    flowBaseSite = baseMajorManager.getBaseSiteBySiteId(item.getFlowSiteCode());
                }
                WorkGridFlowDirection updateItem = new WorkGridFlowDirection();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                updateItem.setFlowProvinceAgencyCode(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setFlowProvinceAgencyName(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setFlowAreaHubCode(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setFlowAreaHubName(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }

            WorkGridFlowDirection workGridFlowDirection = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workGridFlowDirection.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = workGridFlowDirectionDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_grid_flow_direction 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void workGridFlowDetailOfflineBrush(Integer startId) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < 1000) {
            List<WorkGridFlowDetailOffline> singleList = workGridFlowDetailOfflineDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<WorkGridFlowDetailOffline> list = Lists.newArrayList();
            for (WorkGridFlowDetailOffline item : singleList) {
                if(StringUtils.isEmpty(item.getSiteCode())){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(Integer.valueOf(item.getSiteCode()));
                if(baseSite == null){
                    continue;
                }
                BaseStaffSiteOrgDto flowBaseSite = null;
                if(StringUtils.isNotEmpty(item.getFlowSiteCode())){
                    flowBaseSite = baseMajorManager.getBaseSiteBySiteId(Integer.valueOf(item.getFlowSiteCode()));
                }
                WorkGridFlowDetailOffline updateItem = new WorkGridFlowDetailOffline();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                updateItem.setFlowProvinceAgencyCode(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setFlowProvinceAgencyName(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setFlowAreaHubCode(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setFlowAreaHubName(flowBaseSite == null ? Constants.EMPTY_FILL : StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }

            WorkGridFlowDetailOffline workGridFlowDetailOffline = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workGridFlowDetailOffline.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = workGridFlowDetailOfflineDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_grid_flow_direction 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void siteWaveScheduleBrush(Integer startId) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < 1000) {
            List<SiteWaveSchedule> singleList = siteWaveScheduleDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<SiteWaveSchedule> list = Lists.newArrayList();
            for (SiteWaveSchedule item : singleList) {
                if(item.getSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
                if(baseSite == null){
                    continue;
                }
                SiteWaveSchedule updateItem = new SiteWaveSchedule();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }

            SiteWaveSchedule siteWaveSchedule = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = siteWaveSchedule.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = siteWaveScheduleDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("site_wave_schedule 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void siteAttendPlanBrush(Integer startId) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < 1000) {
            List<SiteAttendPlan> singleList = siteAttendPlanDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<SiteAttendPlan> list = Lists.newArrayList();
            for (SiteAttendPlan item : singleList) {
                if(item.getSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
                if(baseSite == null){
                    continue;
                }
                SiteAttendPlan updateItem = new SiteAttendPlan();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }

            SiteAttendPlan siteAttendPlan = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = siteAttendPlan.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = siteAttendPlanDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("site_wave_schedule 表省区刷数:{}", updatedCount);
        }
    }
    
    @Override
    public void collectBoxFlowDirectionConfBrush(Integer startId, Integer maxLoopCount) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < maxLoopCount) {
            List<CollectBoxFlowDirectionConf> singleList = collectBoxFlowDirectionConfDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<CollectBoxFlowDirectionConf> list = Lists.newArrayList();
            for (CollectBoxFlowDirectionConf item : singleList) {
                if(item.getStartSiteId() == null){
                    continue;
                }
                BaseStaffSiteOrgDto startBaseSite = baseMajorManager.getBaseSiteBySiteId(item.getStartSiteId());
                if(startBaseSite == null){
                    continue;
                }
                BaseStaffSiteOrgDto endBaseSite = baseMajorManager.getBaseSiteBySiteId(item.getEndSiteId());
                
                CollectBoxFlowDirectionConf updateItem = new CollectBoxFlowDirectionConf();
                updateItem.setId(item.getId());
                updateItem.setStartProvinceAgencyCode(StringUtils.isEmpty(startBaseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : startBaseSite.getProvinceAgencyCode() );
                updateItem.setStartProvinceAgencyName(StringUtils.isEmpty(startBaseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : startBaseSite.getProvinceAgencyName());
                updateItem.setStartAreaHubCode(StringUtils.isEmpty(startBaseSite.getAreaCode()) ? Constants.EMPTY_FILL : startBaseSite.getAreaCode());
                updateItem.setStartAreaHubName(StringUtils.isEmpty(startBaseSite.getAreaName()) ? Constants.EMPTY_FILL : startBaseSite.getAreaName());
                updateItem.setEndProvinceAgencyCode(endBaseSite == null ? Constants.EMPTY_FILL : endBaseSite.getProvinceAgencyCode() );
                updateItem.setEndProvinceAgencyName(endBaseSite == null ? Constants.EMPTY_FILL : endBaseSite.getProvinceAgencyName());
                updateItem.setEndAreaHubCode(endBaseSite == null ? Constants.EMPTY_FILL : endBaseSite.getAreaCode());
                updateItem.setEndAreaHubName(endBaseSite == null ? Constants.EMPTY_FILL : endBaseSite.getAreaName());
                list.add(updateItem);
            }

            CollectBoxFlowDirectionConf collectBoxFlowDirectionConf = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = collectBoxFlowDirectionConf.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = collectBoxFlowDirectionConfDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("collect_box_flow_direction_conf 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void easyFreezeSiteConfBrush(Integer startId, Integer maxLoopCount) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < maxLoopCount) {
            List<EasyFreezeSitePO> singleList = easyFreezeSiteDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<EasyFreezeSitePO> list = Lists.newArrayList();
            for (EasyFreezeSitePO item : singleList) {
                if(item.getSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
                if(baseSite == null){
                    continue;
                }
                EasyFreezeSitePO updateItem = new EasyFreezeSitePO();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode() );
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = easyFreezeSiteDao.brushUpdateById(list);
            updatedCount += singleCount;

            EasyFreezeSitePO easyFreezeSitePO = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = easyFreezeSitePO.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("jy_easy_freeze_site_conf 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void configTransferDpSiteBrush(Integer startId, Integer maxLoopCount) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < maxLoopCount) {
            List<ConfigTransferDpSite> singleList = configTransferDpSiteDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<ConfigTransferDpSite> list = Lists.newArrayList();
            for (ConfigTransferDpSite item : singleList) {
                if(item.getHandoverSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getHandoverSiteCode());
                if(baseSite == null){
                    continue;
                }
                ConfigTransferDpSite updateItem = new ConfigTransferDpSite();
                updateItem.setId(item.getId());
                updateItem.setHandoverProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode());
                updateItem.setHandoverProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setHandoverAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setHandoverAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = configTransferDpSiteDao.brushUpdateById(list);
            updatedCount += singleCount;

            ConfigTransferDpSite configTransferDpSite = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = configTransferDpSite.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("config_transfer_dp_site 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void workStationAttendPlanBrush(Integer startId, Integer maxLoopCount) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < maxLoopCount) {
            List<WorkStationAttendPlan> singleList = workStationAttendPlanDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<WorkStationAttendPlan> list = Lists.newArrayList();
            for (WorkStationAttendPlan item : singleList) {
                if(item.getSiteCode() == null){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
                if(baseSite == null){
                    continue;
                }
                WorkStationAttendPlan updateItem = new WorkStationAttendPlan();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(StringUtils.isEmpty(baseSite.getProvinceAgencyCode()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyCode());
                updateItem.setProvinceAgencyName(StringUtils.isEmpty(baseSite.getProvinceAgencyName()) ? Constants.EMPTY_FILL : baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(StringUtils.isEmpty(baseSite.getAreaCode()) ? Constants.EMPTY_FILL : baseSite.getAreaCode());
                updateItem.setAreaHubName(StringUtils.isEmpty(baseSite.getAreaName()) ? Constants.EMPTY_FILL : baseSite.getAreaName());
                list.add(updateItem);
            }

            WorkStationAttendPlan workStationAttendPlan = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workStationAttendPlan.getId().intValue();

            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            Integer singleCount = workStationAttendPlanDao.brushUpdateById(list);
            updatedCount += singleCount;

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_station_attend_plan 表省区刷数:{}", updatedCount);
        }
    }

}
