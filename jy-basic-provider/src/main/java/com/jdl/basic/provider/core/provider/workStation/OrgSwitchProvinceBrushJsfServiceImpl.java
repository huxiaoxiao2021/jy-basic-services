package com.jdl.basic.provider.core.provider.workStation;

import com.google.common.collect.Lists;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.cross.SortCrossDetail;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.service.workStation.OrgSwitchProvinceBrushJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.core.dao.cross.SortCrossDetailDao;
import com.jdl.basic.provider.core.dao.workStation.*;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
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
    private SortCrossDetailDao sortCrossDetailDao;

    @Autowired
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfDao;

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
                updateItem.setProvinceAgencyCode(baseSite.getProvinceAgencyCode());
                updateItem.setProvinceAgencyName(baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(baseSite.getAreaCode());
                updateItem.setAreaHubName(baseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = workStationGridDao.brushUpdateById(list);
            updatedCount += singleCount;

            WorkStationGrid workStationGrid = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workStationGrid.getId().intValue();

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
                updateItem.setProvinceAgencyCode(baseSite.getProvinceAgencyCode());
                updateItem.setProvinceAgencyName(baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(baseSite.getAreaCode());
                updateItem.setAreaHubName(baseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = workGridDao.brushUpdateById(list);
            updatedCount += singleCount;

            WorkGrid workGrid = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workGrid.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_grid 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void workGridFlowDirectionBrush() {
        // 起始id
        int offsetId = 0;
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
                updateItem.setProvinceAgencyCode(baseSite.getProvinceAgencyCode());
                updateItem.setProvinceAgencyName(baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(baseSite.getAreaCode());
                updateItem.setAreaHubName(baseSite.getAreaName());
                updateItem.setFlowProvinceAgencyCode(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getProvinceAgencyCode());
                updateItem.setFlowProvinceAgencyName(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getProvinceAgencyName());
                updateItem.setFlowAreaHubCode(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getAreaCode());
                updateItem.setFlowAreaHubName(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = workGridFlowDirectionDao.brushUpdateById(list);
            updatedCount += singleCount;

            WorkGridFlowDirection workGridFlowDirection = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workGridFlowDirection.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_grid_flow_direction 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void workGridFlowDetailOfflineBrush() {
        // 起始id
        int offsetId = 0;
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
                updateItem.setProvinceAgencyCode(baseSite.getProvinceAgencyCode());
                updateItem.setProvinceAgencyName(baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(baseSite.getAreaCode());
                updateItem.setAreaHubName(baseSite.getAreaName());
                updateItem.setFlowProvinceAgencyCode(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getProvinceAgencyCode());
                updateItem.setFlowProvinceAgencyName(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getProvinceAgencyName());
                updateItem.setFlowAreaHubCode(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getAreaCode());
                updateItem.setFlowAreaHubName(flowBaseSite == null ? Constants.EMPTY_FILL : flowBaseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = workGridFlowDetailOfflineDao.brushUpdateById(list);
            updatedCount += singleCount;

            WorkGridFlowDetailOffline workGridFlowDetailOffline = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = workGridFlowDetailOffline.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("work_grid_flow_direction 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void siteWaveScheduleBrush() {
        // 起始id
        int offsetId = 0;
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
                updateItem.setProvinceAgencyCode(baseSite.getProvinceAgencyCode());
                updateItem.setProvinceAgencyName(baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(baseSite.getAreaCode());
                updateItem.setAreaHubName(baseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = siteWaveScheduleDao.brushUpdateById(list);
            updatedCount += singleCount;

            SiteWaveSchedule siteWaveSchedule = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = siteWaveSchedule.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("site_wave_schedule 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void siteAttendPlanBrush() {
        // 起始id
        int offsetId = 0;
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
                updateItem.setProvinceAgencyCode(baseSite.getProvinceAgencyCode());
                updateItem.setProvinceAgencyName(baseSite.getProvinceAgencyName());
                updateItem.setAreaHubCode(baseSite.getAreaCode());
                updateItem.setAreaHubName(baseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = siteAttendPlanDao.brushUpdateById(list);
            updatedCount += singleCount;

            SiteAttendPlan siteAttendPlan = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = siteAttendPlan.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("site_wave_schedule 表省区刷数:{}", updatedCount);
        }
    }

    @Override
    public void sortCrossDetailBrush(Integer startId, Integer maxLoopCount) {
        // 起始id
        int offsetId = startId;
        int updatedCount = 0; // 更新数量
        int loopCount = 0; // 循环次数
        while (loopCount < maxLoopCount) {
            List<SortCrossDetail> singleList = sortCrossDetailDao.brushQueryAllByPage(offsetId);
            if(CollectionUtils.isEmpty(singleList)){
                break;
            }
            List<SortCrossDetail> list = Lists.newArrayList();
            for (SortCrossDetail item : singleList) {
                if(StringUtils.isEmpty(item.getSiteCode())){
                    continue;
                }
                BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(Integer.valueOf(item.getSiteCode()));
                if(baseSite == null){
                    continue;
                }
                SortCrossDetail updateItem = new SortCrossDetail();
                updateItem.setId(item.getId());
                updateItem.setProvinceAgencyCode(baseSite.getProvinceAgencyCode());
                updateItem.setAreaHubCode(baseSite.getAreaCode());
                list.add(updateItem);
            }
            Integer singleCount = sortCrossDetailDao.brushUpdateById(list);
            updatedCount += singleCount;

            SortCrossDetail sortCrossDetail = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = sortCrossDetail.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("sort_cross_detail 表省区刷数:{}", updatedCount);
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
                if(endBaseSite == null){
                    continue;
                }
                CollectBoxFlowDirectionConf updateItem = new CollectBoxFlowDirectionConf();
                updateItem.setId(item.getId());
                updateItem.setStartProvinceAgencyCode(startBaseSite.getProvinceAgencyCode());
                updateItem.setStartProvinceAgencyName(startBaseSite.getProvinceAgencyName());
                updateItem.setStartAreaHubCode(startBaseSite.getAreaCode());
                updateItem.setStartAreaHubName(startBaseSite.getAreaName());
                updateItem.setEndProvinceAgencyCode(endBaseSite.getProvinceAgencyCode());
                updateItem.setEndProvinceAgencyName(endBaseSite.getProvinceAgencyName());
                updateItem.setEndAreaHubCode(endBaseSite.getAreaCode());
                updateItem.setEndAreaHubName(endBaseSite.getAreaName());
                list.add(updateItem);
            }
            Integer singleCount = collectBoxFlowDirectionConfDao.brushUpdateById(list);
            updatedCount += singleCount;

            CollectBoxFlowDirectionConf collectBoxFlowDirectionConf = singleList.stream().max((a, b) -> (int) (a.getId() - b.getId())).get();
            offsetId = collectBoxFlowDirectionConf.getId().intValue();

            loopCount ++;
        }
        if(log.isInfoEnabled()){
            log.info("collect_box_flow_direction_conf 表省区刷数:{}", updatedCount);
        }
    }
}
