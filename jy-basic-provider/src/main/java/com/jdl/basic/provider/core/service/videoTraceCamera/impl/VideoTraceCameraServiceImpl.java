package com.jdl.basic.provider.core.service.videoTraceCamera.impl;

import com.jd.bd.dms.automatic.sdk.common.dto.BaseDmsAutoJsfResponse;
import com.jd.bd.dms.automatic.sdk.modules.device.DeviceConfigInfoJsfService;
import com.jd.bd.dms.automatic.sdk.modules.device.dto.DeviceGridDto;
import com.jd.bd.dms.automatic.sdk.modules.device.dto.DeviceGridQuery;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;
import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.*;
import com.jdl.basic.provider.common.Jimdb.CacheService;
import com.jdl.basic.provider.core.dao.machine.WorkStationGridMachineDao;
import com.jdl.basic.provider.core.dao.videoTraceCamera.VideoTraceCameraConfigDao;
import com.jdl.basic.provider.core.dao.videoTraceCamera.VideoTraceCameraDao;
import com.jdl.basic.provider.core.dao.workStation.WorkGridDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("videoTraceCameraService")
public class VideoTraceCameraServiceImpl implements VideoTraceCameraService {

    @Autowired
    @Qualifier("videoTraceCameraDao")
    private VideoTraceCameraDao videoTraceCameraDao;

    @Autowired
    @Qualifier("videoTraceCameraConfigDao")
    private VideoTraceCameraConfigDao videoTraceCameraConfigDao;

    @Autowired
    private BaseMajorManager baseMajorManager;

    @Autowired
    @Qualifier("workStationGridService")
    private WorkStationGridService workStationGridService;

    @Resource
    @Qualifier("JimdbCacheService")
    private CacheService cacheService;

    @Autowired
    @Qualifier("workGridDao")
    private WorkGridDao workGridDao;

    @Autowired
    private WorkStationDao workStationDao;

    @Autowired
    private WorkStationGridMachineDao workStationGridMachineDao;

    @Autowired
    private DeviceConfigInfoJsfService deviceConfigInfoJsfService;

    private static final String CAMERA_CONFIG_CACHE_KEY_PRE = "camera_config_cache_key_pre";

    @Value("${whether_syn_video_trace_camera:false}")
    private boolean syncChannelState;

    @Override
    public Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery query) {
        Result<PageDto<VideoTraceCamera>> result = Result.success();
        if (query.getPageNumber() > 0) {
            query.setLimit(query.getPageSize());
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        }
        int totalCount = videoTraceCameraDao.queryCount(query);
        PageDto<VideoTraceCamera> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
        pageDto.setTotalRow(totalCount);
        if (totalCount > 0) {
            List<VideoTraceCamera> videoTraceCameraList = videoTraceCameraDao.queryPageList(query);
            pageDto.setResult(videoTraceCameraList);
        }
        result.setData(pageDto);
        return result;
    }

    @Override
    public Result<List<VideoTraceCameraConfig>> queryVideoTraceCameraConfig(VideoTraceCameraQuery query) {
        Result<List<VideoTraceCameraConfig>> result = Result.success();
        if ((StringUtils.isBlank(query.getCameraCode()) || StringUtils.isBlank(query.getNationalChannelCode()))) {
            log.error("参数错误，摄像头编码、通道号存在空值,查询入参【{}】", JsonHelper.toJSONString(query));
            return Result.fail("参数错误，摄像头编码、通道号存在空值");
        }
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        videoTraceCameraQuery.setCameraCode(query.getCameraCode());
        videoTraceCameraQuery.setNationalChannelCode(query.getNationalChannelCode());
        videoTraceCameraQuery.setYn(query.getStatus());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByConditionAndYn(videoTraceCameraQuery);
        if (CollectionUtils.isEmpty(videoTraceCameras)) {
            return Result.fail("摄像头信息不存在");
        }

        List<Integer> cameraIds = videoTraceCameras.stream().map(VideoTraceCamera::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(cameraIds)) {
            VideoTraceCameraConfig condition = new VideoTraceCameraConfig();

            condition.setCameraIds(cameraIds);
            condition.setYn(query.getYn() == null ? null : query.getYn().byteValue());
            String operateTimeStr = query.getOperateTimeStr();
            condition.setOperateTime(StringUtils.isNotEmpty(operateTimeStr) ? DateHelper.parse(operateTimeStr) : null);
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigDao.queryValidByCameraIdsAndTime(condition);
            result.setData(videoTraceCameraConfigs);
        }
        return result;
    }

    @Override
    public Result<Boolean> editCameraConfig(VideoTraceCameraVo videoTraceCameraVo) {
        VideoTraceCamera videoTraceCamera = videoTraceCameraDao.selectByPrimaryKey(videoTraceCameraVo.getId());
        if (videoTraceCamera == null) {
            return Result.fail("摄像头信息不存在");
        }
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setCameraId(videoTraceCamera.getId());
        List<VideoTraceCameraConfig> oldList = videoTraceCameraConfigDao.queryByCameraId(condition);
        List<VideoTraceCameraConfig> newList = videoTraceCameraVo.getVideoTraceCameraConfigList();
        List<String> delCacheKeys = new ArrayList<>();
        delCacheKeys.addAll(oldList.stream().map(VideoTraceCameraConfig::getRefWorkGridKey).collect(Collectors.toList()));
        delCacheKeys.addAll(newList.stream().map(VideoTraceCameraConfig::getRefWorkGridKey).collect(Collectors.toList()));
        delCacheKeys = delCacheKeys.stream().distinct().collect(Collectors.toList());
        //摄像头之前没有配置，新增配置，修改摄像头配置状态为已配置
        if (CollectionUtils.isEmpty(oldList) && CollectionUtils.isNotEmpty(newList)) {
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCamera.getId());
            update.setConfigStatus((byte) 1);
            videoTraceCameraDao.updateById(update);
        }
        //传来的配置信息为空，修改摄像头配置状态为待配置
        if (CollectionUtils.isEmpty(newList)) {
            //清空绑定关系后，修改摄像头配置状态
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCamera.getId());
            update.setConfigStatus((byte) 0);
            videoTraceCameraDao.updateById(update);
        }
        //过滤出之前有的配置，保存是入参中没有的配置信息，这部分为删除数据
        List<VideoTraceCameraConfig> delList = oldList.stream()
                .filter(a -> newList.stream().noneMatch(b -> compare(a, b)))
                .peek(x -> x.setUpdateErp(videoTraceCameraVo.getCreateErp()))
                .collect(Collectors.toList());

        //过滤出之前没有的配置，保存是入参中有的配置信息，这部分为新增数据
        List<VideoTraceCameraConfig> addList = newList.stream()
                .filter(a -> oldList.stream().noneMatch(b -> compare(a, b)))
                .peek(x -> {
                    x.setMasterCamera((byte) 0);
                    x.setCreateErp(videoTraceCameraVo.getCreateErp());
                    x.setStatus(videoTraceCamera.getStatus());
                })
                .collect(Collectors.toList());
        //新增数据落库
        if (CollectionUtils.isNotEmpty(addList)) {
            videoTraceCameraConfigDao.batchSave(addList);
        }
        //删除配置逻辑删除
        if (CollectionUtils.isNotEmpty(delList)) {
            videoTraceCameraConfigDao.batchDelete(delList.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList())
                    , videoTraceCameraVo.getUpdateErp());
        }

        //根据网格删除缓存
        delCameraConfigCache(delCacheKeys);
        return Result.success();
    }

    /**
     * 根据网格主键删除缓存
     */
    private void delCameraConfigCache(List<String> list) {
        for (String workGridKey : list) {
            cacheService.del(getKey(workGridKey));
        }
    }

    private static String getKey(String workGridKey) {
        return CAMERA_CONFIG_CACHE_KEY_PRE + workGridKey;
    }

    @Override
    public int deleteById(VideoTraceCamera record) {
        VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
        condition.setNationalChannelCode(record.getNationalChannelCode());
        condition.setCameraCode(record.getCameraCode());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
        if (videoTraceCameras.size() == 1) {
            return videoTraceCameraDao.deleteById(videoTraceCameras.get(0));
        }
        return 0;
    }

    @Override
    public int insert(VideoTraceCamera record) {
        VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
        condition.setNationalChannelCode(record.getNationalChannelCode());
        condition.setCameraCode(record.getCameraCode());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
        if (CollectionUtils.isNotEmpty(videoTraceCameras)) {
            throw new RuntimeException("摄像头信息已存在！");
        }
        BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(record.getSiteCode());
        if (siteInfo == null) {
            throw new RuntimeException("所属站点在基础资料中不存在！");
        }
        fillSiteInfo(record, siteInfo);
        return videoTraceCameraDao.insert(record);
    }

    /**
     * 补全省区，枢纽信息
     */
    private void fillSiteInfo(VideoTraceCamera record, BaseStaffSiteOrgDto siteInfo) {
        record.setSiteName(siteInfo.getSiteName());
        record.setSiteCode(siteInfo.getSiteCode());
        record.setProvinceAgencyCode(siteInfo.getProvinceAgencyCode());
        record.setProvinceAgencyName(siteInfo.getProvinceAgencyName());
        record.setAreaHubCode(siteInfo.getAreaCode());
        record.setAreaHubName(siteInfo.getAreaName());
    }

    @Override
    public VideoTraceCamera selectByPrimaryKey(Integer id) {
        return videoTraceCameraDao.selectByPrimaryKey(id);
    }

    @Override
    public VideoTraceCamera getByIdNoYn(Integer id) {
        return videoTraceCameraDao.getByIdNoYn(id);
    }

    @Override
    public int updateById(VideoTraceCamera record) {
        return videoTraceCameraDao.updateById(record);
    }

    @Override
    public int queryCount(VideoTraceCameraQuery videoTraceCameraQuery) {
        return videoTraceCameraDao.queryCount(videoTraceCameraQuery);
    }

    @Override
    public List<VideoTraceCamera> queryByCondition(VideoTraceCameraQuery query) {
        return videoTraceCameraDao.queryByCondition(query);
    }

    @Override
    public List<VideoTraceCamera> getByIds(List<Integer> ids) {
        return videoTraceCameraDao.getByIds(ids);
    }

    @Override
    public int saveOrUpdateCameraStatus(VideoTraceCamera videoTraceCamera) {
        VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
        condition.setNationalChannelCode(videoTraceCamera.getNationalChannelCode());
        condition.setCameraCode(videoTraceCamera.getCameraCode());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
        if (CollectionUtils.isEmpty(videoTraceCameras)) {
            BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(videoTraceCamera.getSiteCode());
            if (siteInfo == null) {
                throw new RuntimeException("所属站点在基础资料中不存在！");
            }
            fillSiteInfo(videoTraceCamera, siteInfo);
            return videoTraceCameraDao.insert(videoTraceCamera);
        }
        //新状态与原状态不同时，修改摄像头及配置状态

        if (!Objects.equals(videoTraceCameras.get(0).getStatus(), videoTraceCamera.getStatus()) && syncChannelState) {
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCameras.get(0).getId());
            update.setStatus(videoTraceCamera.getStatus());
            videoTraceCameraDao.updateById(update);

            VideoTraceCameraConfig query = new VideoTraceCameraConfig();
            query.setCameraId(videoTraceCameras.get(0).getId());
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigDao.queryByCameraId(query);
            if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs)) {
                //删除原配置信息，用新状态再次插入
                videoTraceCameraConfigDao.batchDelete(videoTraceCameraConfigs.stream()
                        .map(VideoTraceCameraConfig::getId)
                        .collect(Collectors.toList()), videoTraceCamera.getUpdateErp());
                List<VideoTraceCameraConfig> addList = videoTraceCameraConfigs.stream().peek(x -> {
                    x.setId(null);
                    x.setUpdateErp(videoTraceCamera.getUpdateErp());
                    x.setCreateTime(null);
                    x.setUpdateTime(null);
                    x.setStatus(videoTraceCamera.getStatus());
                }).collect(Collectors.toList());
                videoTraceCameraConfigDao.batchSave(addList);
            }
        }

        return 0;
    }


    /**
     * 更据网格、工序、设备、格口字段判断是不是同一条配置信息
     */
    private boolean compare(VideoTraceCameraConfig v1, VideoTraceCameraConfig v2) {
        return Objects.equals(v1.getRefWorkGridKey(), v2.getRefWorkGridKey())
                && Objects.equals(v1.getRefWorkStationKey(), v2.getRefWorkStationKey())
                && Objects.equals(v1.getMachineCode(), v2.getMachineCode())
                && Objects.equals(v1.getChuteCode(), v2.getChuteCode())
                && Objects.equals(v1.getSupplyDwsCode(), v2.getSupplyDwsCode());
    }


    @Override
    public void importCameraConfigs(List<VideoTraceCameraImport> list) {
        for (VideoTraceCameraImport item : list) {
            VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
            condition.setNationalChannelCode(item.getNationalChannelCode());
            condition.setCameraCode(item.getCameraCode());
            List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
            //摄像头存在时，插入一条配置
            if (CollectionUtils.isNotEmpty(videoTraceCameras)) {
                try {
                    List<VideoTraceCameraConfig> videoTraceCameraConfigs = getVideoTraceCameraConfig(item, videoTraceCameras.get(0));
                    for (VideoTraceCameraConfig videoTraceCameraConfig : videoTraceCameraConfigs) {
                        List<VideoTraceCameraConfig> configs = videoTraceCameraConfigDao.queryByCondition(videoTraceCameraConfig);
                        if (CollectionUtils.isNotEmpty(configs)) {
                            log.error("同步摄像头配置关系失败，该绑定关系已存在。{}", JsonHelper.toJSONString(item));
                            continue;
                        }
                        videoTraceCameraConfigDao.insert(videoTraceCameraConfig);

                    }
                    if (videoTraceCameras.get(0).getConfigStatus() == 0) {
                        VideoTraceCamera update = new VideoTraceCamera();
                        update.setId(videoTraceCameras.get(0).getId());
                        update.setConfigStatus((byte) 1);
                        videoTraceCameraDao.updateById(update);
                    }
                } catch (Exception e) {
                    log.error("同步摄像头配置关系失败。错误信息{}，{}", e.getMessage(), JsonHelper.toJSONString(item), e);
                }
            } else {
                log.error("同步摄像头配置关系失败，摄像头不存在。{}", JsonHelper.toJSONString(item));
            }

        }
    }

    @Override
    public int deleteCameraByIds(List<Integer> ids, String operate) {
        for (Integer id : ids) {
            VideoTraceCamera videoTraceCamera = new VideoTraceCamera();
            videoTraceCamera.setId(id);
            videoTraceCamera.setUpdateErp(operate);
            videoTraceCameraDao.deleteById(videoTraceCamera);
        }
        return ids.size();
    }

    @Override
    public int deleteCameraConfigByIds(List<Integer> ids, String operate) {
        return videoTraceCameraConfigDao.batchDelete(ids, operate);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        return videoTraceCameraConfigDao.deleteByIds(ids);
    }


    @Override
    public void importCameras(List<VideoTraceCameraImport> list) {
        for (VideoTraceCameraImport videoTraceCameraImport : list) {
            VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
            condition.setNationalChannelCode(videoTraceCameraImport.getNationalChannelCode());
            condition.setCameraCode(videoTraceCameraImport.getCameraCode());
            List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
            if (CollectionUtils.isNotEmpty(videoTraceCameras)) {
                log.error("初始化摄像头信息失败摄像头信息已存在！{}", JsonHelper.toJSONString(videoTraceCameraImport));
                continue;
            }
            try {
                VideoTraceCamera videoTraceCamera = getVideoTraceCamera(videoTraceCameraImport);
                if (videoTraceCamera != null) {
                    videoTraceCameraDao.insert(videoTraceCamera);
                }
            } catch (Exception e) {
                log.error("初始化摄像头信息失败,{}", JsonHelper.toJSONString(videoTraceCameraImport), e);
            }
        }
    }


    private List<VideoTraceCameraConfig> getVideoTraceCameraConfig(VideoTraceCameraImport item, VideoTraceCamera videoTraceCamera) {
        List<VideoTraceCameraConfig> list = new ArrayList<>();
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        WorkStationGridQuery workStationGridQuery = new WorkStationGridQuery();
        workStationGridQuery.setBusinessKey(item.getStationGridKey());
        //查询工序
        WorkStationGrid workStationGrid = workStationGridService.queryByGridKeyWithCache(workStationGridQuery);
        if (workStationGrid == null) {
            log.error("同步设备摄像头绑定关系失败，工序不存在，工序:{}", JsonHelper.toJSONString(item));
            return Collections.EMPTY_LIST;
        }
        videoTraceCameraConfig.setRefWorkGridKey(workStationGrid.getRefWorkGridKey());
        videoTraceCameraConfig.setRefWorkStationKey(workStationGrid.getBusinessKey());
        videoTraceCameraConfig.setCameraId(videoTraceCamera.getId());
        videoTraceCameraConfig.setCreateErp(item.getUpdateErp());
        videoTraceCameraConfig.setCreateTime(item.getUpdateTime());
        videoTraceCameraConfig.setUpdateErp(item.getUpdateErp());
        videoTraceCameraConfig.setUpdateTime(item.getUpdateTime());
        videoTraceCameraConfig.setStatus(videoTraceCamera.getStatus());
        videoTraceCameraConfig.setYn((byte) 1);
        list.add(videoTraceCameraConfig);

        //根据工序获取设备
        List<WorkStationGrid> grids = new ArrayList<>();
        grids.add(workStationGrid);
        List<WorkStationGridMachine> machineList = workStationGridMachineDao.getMachineListByRefGridKey(grids);

        List<VideoTraceCameraConfig> videoTraceCameraConfigs = machineList.stream().map(x -> {
            String machineCode = x.getMachineCode();
            DeviceGridQuery deviceGridQuery = new DeviceGridQuery();
            deviceGridQuery.setMachineCode(machineCode);
            //查询设备所在网格
            BaseDmsAutoJsfResponse<DeviceGridDto> response = deviceConfigInfoJsfService.findDeviceGridByMachineInfo(deviceGridQuery);
            if (response.getData() == null) {
                log.error("同步设备摄像头绑定关系失败，设备网格关系不存在，或关联多个网格，自动化设备编码:{}，通道:{}", machineCode, JsonHelper.toJSONString(item));
                return null;
            }
            VideoTraceCameraConfig config = BeanUtils.copy(videoTraceCameraConfig, VideoTraceCameraConfig.class);
            if (config != null) {
                config.setMachineCode(machineCode);
                config.setRefWorkGridKey(response.getData().getBusinessKey());
                config.setRefWorkStationKey(null);
            }
            return config;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        list.addAll(videoTraceCameraConfigs);

        return list;
    }

    private VideoTraceCamera getVideoTraceCamera(VideoTraceCameraImport item) {
        WorkStationGridQuery workStationGridQuery = new WorkStationGridQuery();
        workStationGridQuery.setBusinessKey(item.getStationGridKey());
        WorkStationGrid workStationGrid = workStationGridService.queryByGridKeyWithCache(workStationGridQuery);
        if (workStationGrid == null) {
            log.error("同步摄像头失败，工序不存在，工序:{}", JsonHelper.toJSONString(item));
            return null;
        }
        VideoTraceCamera videoTraceCamera = new VideoTraceCamera();
        videoTraceCamera.setCameraCode(item.getCameraCode());
        videoTraceCamera.setCameraName(item.getCameraName());
        videoTraceCamera.setNationalChannelCode(item.getNationalChannelCode());
        videoTraceCamera.setNationalChannelName(item.getNationalChannelName());
        videoTraceCamera.setCreateErp(item.getUpdateErp());
        videoTraceCamera.setCreateTime(item.getUpdateTime());
        videoTraceCamera.setUpdateErp(item.getUpdateErp());
        videoTraceCamera.setUpdateTime(item.getUpdateTime());
        videoTraceCamera.setStatus(item.getStatus());
        videoTraceCamera.setSiteCode(workStationGrid.getSiteCode());
        videoTraceCamera.setConfigStatus((byte) 0);
        videoTraceCamera.setTs(item.getUpdateTime());
        BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(workStationGrid.getSiteCode());
        if (siteInfo == null) {
            log.error("同步摄像头失败，工序所属站点在基础资料中不存在，工序:{}", JsonHelper.toJSONString(videoTraceCamera));
            return null;
        }
        fillSiteInfo(videoTraceCamera, siteInfo);
        return videoTraceCamera;
    }


    @Override
    public Result<List<CameraConfigExportVo>> exportCameraConfigByGrid(WorkGridQuery query) {
        List<CameraConfigExportVo> cameraConfigExportVos = new ArrayList<>();
        Result<List<CameraConfigExportVo>> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
        if (!checkResult.isSuccess()) {
            return Result.fail(checkResult.getMessage());
        }
        //查询网格数据
        List<WorkGrid> workGridList = workGridDao.queryList(query);
        List<String> gridKeys = workGridList.stream().map(WorkGrid::getBusinessKey).collect(Collectors.toList());
        //根据网格查询摄像头配置数据
        List<VideoTraceCameraConfig> configs = videoTraceCameraConfigDao.queryByGridKeys(gridKeys);

        // 查询网格下工序
        WorkStationGridQuery workStationGridQuery = new WorkStationGridQuery();
        workStationGridQuery.setRefWorkGridKeyList(gridKeys);
        List<WorkStationGrid> workStationGrids = workStationGridService.queryListForRefWorkGridKeyList(workStationGridQuery);

        // 查询网格关联的自动化设备
        BaseDmsAutoJsfResponse<List<DeviceGridDto>> deviceGrid = deviceConfigInfoJsfService.findDeviceGridByBusinessKey(null, gridKeys);


        Map<String, List<VideoTraceCameraConfig>> configMapByGridKey = configs.stream().collect(Collectors.groupingBy(VideoTraceCameraConfig::getRefWorkGridKey));
        Map<String, List<WorkStationGrid>> workStationMap = workStationGrids.stream().collect(Collectors.groupingBy(WorkStationGrid::getRefWorkGridKey));
        Map<String, List<DeviceGridDto>> deviceGridMap = deviceGrid.getData().stream().collect(Collectors.groupingBy(DeviceGridDto::getBusinessKey));

        for (WorkGrid workGrid : workGridList) {
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = configMapByGridKey.getOrDefault(workGrid.getBusinessKey(),Collections.emptyList());
            //网格行数据
            CameraConfigExportVo cameraConfigExportVo = BeanUtils.copy(workGrid, CameraConfigExportVo.class);
            List<VideoTraceCameraConfig> collect = videoTraceCameraConfigs.stream()
                    .filter(x -> StringUtils.isBlank(x.getRefWorkStationKey()) && StringUtils.isBlank(x.getMachineCode()))
                    .collect(Collectors.toList());
            cameraConfigExportVo.setConfigVoList(collect);
            cameraConfigExportVos.add(cameraConfigExportVo);
            //工序列数据
            List<WorkStationGrid> workStationGridList = workStationMap.get(workGrid.getBusinessKey());
            if (CollectionUtils.isNotEmpty(workStationGridList)){
                List<CameraConfigExportVo> extracted = generateCameraConfigExportVoFromWorkStaion(workGrid, workStationGridList, videoTraceCameraConfigs);
                cameraConfigExportVos.addAll(extracted);
            }

            //设备列数据
            List<DeviceGridDto> deviceGrids = deviceGridMap.get(workGrid.getBusinessKey());

            if (CollectionUtils.isNotEmpty(deviceGrids)){
                List<CameraConfigExportVo> configExportVos = generateCameraConfigExportVoFromDevice(workGrid, videoTraceCameraConfigs, deviceGrids);
                cameraConfigExportVos.addAll(configExportVos);
            }

        }
        //组装返回数据
        result.setData(cameraConfigExportVos);
        return result;
    }

    private static List<CameraConfigExportVo> generateCameraConfigExportVoFromDevice(WorkGrid workGrid, List<VideoTraceCameraConfig> videoTraceCameraConfigs, List<DeviceGridDto> deviceGrids) {
        List<CameraConfigExportVo> cameraConfigExportVos = new ArrayList<>();
        if (videoTraceCameraConfigs == null) {
            videoTraceCameraConfigs = Collections.emptyList();
        }
        Map<String, List<VideoTraceCameraConfig>> configMapByGrid = videoTraceCameraConfigs
                .stream().filter(x -> StringUtils.isNotBlank(x.getMachineCode()))
                .collect(Collectors.groupingBy(x -> x.getMachineCode() + x.getChuteCode() + x.getSupplyDwsCode()));

        // 处理设备格数据
        for (DeviceGridDto deviceGridDto : deviceGrids) {
            String key = deviceGridDto.getMachineCode() + deviceGridDto.getChuteCode() + deviceGridDto.getSupplyNo();
            List<VideoTraceCameraConfig> configsForDevice = configMapByGrid.getOrDefault(key, Collections.emptyList());
            configMapByGrid.remove(key);
            cameraConfigExportVos.add(createExportVo(workGrid, deviceGridDto.getMachineCode(), deviceGridDto.getChuteCode(), deviceGridDto.getSupplyNo(), configsForDevice));
        }

        // 处理剩余的设备配置
        configMapByGrid.forEach((key, configs) -> {
            if (!configs.isEmpty()) {
                VideoTraceCameraConfig sampleConfig = configs.get(0);
                cameraConfigExportVos.add(createExportVo(workGrid, sampleConfig.getMachineCode(), sampleConfig.getChuteCode(), sampleConfig.getSupplyDwsCode(), configs));
            }
        });

        return cameraConfigExportVos;
    }

    private static CameraConfigExportVo createExportVo(WorkGrid workGrid, String machineCode, String chuteCode, String supplyDwsCode, List<VideoTraceCameraConfig> configs) {
        CameraConfigExportVo configExportVo = BeanUtils.copy(workGrid, CameraConfigExportVo.class);
        configExportVo.setMachineCode(machineCode);
        configExportVo.setChuteCode(chuteCode);
        configExportVo.setSupplyDwsCode(supplyDwsCode);
        configExportVo.setConfigVoList(configs);
        return configExportVo;
    }


    private static List<CameraConfigExportVo> generateCameraConfigExportVoFromWorkStaion(WorkGrid workGrid, List<WorkStationGrid> workStationGridList, List<VideoTraceCameraConfig> videoTraceCameraConfigs) {
        List<CameraConfigExportVo> cameraConfigExportVos = new ArrayList<>();
        for (WorkStationGrid workStationGrid : workStationGridList) {
            CameraConfigExportVo configExportVo = BeanUtils.copy(workGrid, CameraConfigExportVo.class);
            configExportVo.setWorkName(workStationGrid.getWorkName());
            configExportVo.setWorkCode(workStationGrid.getWorkCode());
            if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs)){
                List<VideoTraceCameraConfig> collect1 = videoTraceCameraConfigs.stream()
                        .filter(x -> Objects.equals(x.getRefWorkGridKey(), workStationGrid.getRefWorkGridKey()))
                        .collect(Collectors.toList());
                configExportVo.setConfigVoList(collect1);
            }
            cameraConfigExportVos.add(configExportVo);
        }
        return cameraConfigExportVos;
    }


    /**
     * 查询参数校验
     */
    public Result<Boolean> checkParamForQueryPageList(WorkGridQuery query) {
        Result<Boolean> result = Result.success();
        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        }
        query.setOffset(0);
        query.setLimit(query.getPageSize());
        if (query.getPageSize() == null || query.getPageNumber() > 0) {
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        }
        return result;
    }

    /**
     * 根据工作网格查询分页列表及其绑定的摄像头配置信息。
     *
     * @param query 工作网格查询条件
     * @return 分页展示的工作网格及其绑定的摄像头配置信息
     */
    @Override
    public Result<PageDto<GridCameraBindingVo>> queryPageListWithWorkGrid(WorkGridQuery query) {
        // 初始化返回结果
        Result<PageDto<GridCameraBindingVo>> result = Result.success();

        Long totalCount = workGridDao.queryCount(query);
        if (totalCount < 1){
            // 如果没有网格数据
            return result;
        }
        // 查询网格数据
        List<WorkGrid> dataList = workGridDao.queryList(query);

        // 查询网格摄像头配置数据
        List<String> workGridKeys = dataList.stream().map(WorkGrid::getBusinessKey).collect(Collectors.toList());
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigDao.queryByGridKeys(workGridKeys);

        // 将摄像头配置数据按网格分组
        Map<String, List<VideoTraceCameraConfig>> cameraConfigMap = videoTraceCameraConfigs.stream()
                .collect(Collectors.groupingBy(VideoTraceCameraConfig::getRefWorkGridKey));

        // 转换网格数据为返回的对象
        List<GridCameraBindingVo> gridCameraBindingVos = dataList.stream()
                .map(workGrid -> {
                    GridCameraBindingVo gridCameraBindingVo = getGridCameraBindingVo(workGrid, cameraConfigMap.get(workGrid.getBusinessKey()));
                    gridCameraBindingVo.setTreeNodeKey(workGrid.getBusinessKey());
                    gridCameraBindingVo.setTreeNodeLabel(workGrid.getGridName());
                    return gridCameraBindingVo;
                })
                .collect(Collectors.toList());

        // 设置分页结果并返回
        PageDto<GridCameraBindingVo> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
        pageDto.setResult(gridCameraBindingVos);
        pageDto.setTotalRow(totalCount.intValue());
        result.setData(pageDto);
        return result;
    }

    /**
     * 根据查询条件获取绑定的摄像头配置信息。查询逻辑分为三种情况：仅根据网格查询、仅根据设备查询、根据工序、格口、供包台查询。
     *
     */
    @Override
    public List<VideoTraceCameraConfigVo> getBoundCameraInfo(VideoTraceCameraConfigQuery query) {
        List<VideoTraceCameraConfigVo> videoTraceCameraConfigVos = getVideoTraceCameraConfigVos(getCameraInfo(query));
        if (CollectionUtils.isNotEmpty(videoTraceCameraConfigVos)){
            videoTraceCameraConfigVos.sort(Comparator.comparing(VideoTraceCameraConfigVo::getMasterCamera).reversed());
        }
        return videoTraceCameraConfigVos;
    }

    @Override
    public List<VideoTraceCameraConfigVo> getHisBoundCameraInfo(VideoTraceCameraConfigQuery query) {
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setRefWorkGridKey(query.getWorkGridKey());

        // 只有网格，无工序和设备，查询网格上绑定数据
        if (StringUtils.isBlank(query.getWorkStationKey()) && StringUtils.isBlank(query.getMachineCode())) {
            return getVideoTraceCameraConfigVos(videoTraceCameraConfigDao.queryByGridNoYn(condition));
        }
        // 只有设备，无格口和供包台，查询自动化设备上绑定数据
        if (StringUtils.isNotBlank(query.getMachineCode())
                && StringUtils.isBlank(query.getSupplyDwsCode())
                && StringUtils.isBlank(query.getChuteCode())) {
            condition.setMachineCode(query.getMachineCode());
            return getVideoTraceCameraConfigVos(videoTraceCameraConfigDao.queryByDeviceNoYn(condition));
        }
        // 查询工序、格口、供包台上绑定数据
        condition.setRefWorkStationKey(query.getWorkStationKey());
        condition.setMachineCode(query.getMachineCode());
        condition.setChuteCode(query.getChuteCode());
        condition.setSupplyDwsCode(query.getSupplyDwsCode());
        condition.setYn((byte) 0);
        return getVideoTraceCameraConfigVos(videoTraceCameraConfigDao.queryByCondition(condition));
    }
    private List<VideoTraceCameraConfig> getCameraInfo(VideoTraceCameraConfigQuery query){
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setRefWorkGridKey(query.getWorkGridKey());

        // 只有网格，无工序和设备，查询网格上绑定数据
        if (StringUtils.isBlank(query.getWorkStationKey()) && StringUtils.isBlank(query.getMachineCode())) {
            return videoTraceCameraConfigDao.queryByGrid(condition);
        }
        // 只有设备，无格口和供包台，查询自动化设备上绑定数据
        if (StringUtils.isNotBlank(query.getMachineCode())
                && StringUtils.isBlank(query.getSupplyDwsCode())
                && StringUtils.isBlank(query.getChuteCode())) {
            condition.setMachineCode(query.getMachineCode());
            return videoTraceCameraConfigDao.queryByDevice(condition);
        }
        // 查询工序、格口、供包台上绑定数据
        condition.setRefWorkStationKey(query.getWorkStationKey());
        condition.setMachineCode(query.getMachineCode());
        condition.setChuteCode(query.getChuteCode());
        condition.setSupplyDwsCode(query.getSupplyDwsCode());
        condition.setYn((byte) 1);
        return videoTraceCameraConfigDao.queryByCondition(condition);
    }

    @Override
    public List<VideoTraceCameraVo> queryCameraInfoForBinding(VideoTraceCameraQuery query) {
        //查询摄像头信息
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryCameraInfoForBinding(query);
        //根据查到的数据，查询配置信息
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setCameraIds(videoTraceCameras.stream().map(VideoTraceCamera::getId).collect(Collectors.toList()));
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigDao.queryValidByCameraIds(videoTraceCameraConfig);
        Map<Integer, List<VideoTraceCameraConfig>> configMap = videoTraceCameraConfigs.stream().collect(Collectors.groupingBy(VideoTraceCameraConfig::getCameraId));
        List<VideoTraceCameraVo> videoTraceCameraVos = videoTraceCameras.stream().map(x-> convertVideoTraceCameraVo(x)).collect(Collectors.toList());
        //按网格对摄像头下绑定关系去重，构建返回对象
        for (VideoTraceCameraVo videoTraceCameraVo : videoTraceCameraVos) {
            List<VideoTraceCameraConfig> distinctList = configMap.getOrDefault(videoTraceCameraVo.getId(), Collections.emptyList()).stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(VideoTraceCameraConfig::getRefWorkGridKey, config -> config, (existing, replacement) -> existing),
                            map -> new ArrayList<>(map.values())
                    ));
            videoTraceCameraVo.setVideoTraceCameraConfigList(distinctList);
        }
        return videoTraceCameraVos;
    }

    private VideoTraceCameraVo convertVideoTraceCameraVo(VideoTraceCamera videoTraceCamera) {
        VideoTraceCameraVo videoTraceCameraVo = new VideoTraceCameraVo();
        videoTraceCameraVo.setCameraCode(videoTraceCamera.getCameraCode());
        videoTraceCameraVo.setNationalChannelCode(videoTraceCamera.getNationalChannelCode());
        videoTraceCameraVo.setNationalChannelName(videoTraceCamera.getNationalChannelName());
        videoTraceCameraVo.setId(videoTraceCamera.getId());
        videoTraceCameraVo.setConfigStatus(videoTraceCamera.getConfigStatus());
        return videoTraceCameraVo;
    }

    @Override
    public Result<Boolean> saveConfigs(VideoTraceCameraConfigVo save) {
        // 初始化查询条件
        VideoTraceCameraConfigQuery query = new VideoTraceCameraConfigQuery();
        query.setWorkGridKey(save.getRefWorkGridKey());
        query.setWorkStationKey(save.getRefWorkStationKey());
        query.setMachineCode(save.getMachineCode());
        query.setChuteCode(save.getChuteCode());
        query.setSupplyDwsCode(save.getSupplyDwsCode());

        List<VideoTraceCameraConfig> oldList = getCameraInfo(query);
        List<VideoTraceCameraConfig> newList = save.getVideoTraceCameraConfigs();
        for (VideoTraceCameraConfig videoTraceCameraConfig : newList) {
            videoTraceCameraConfig.setRefWorkGridKey(save.getRefWorkGridKey());
            videoTraceCameraConfig.setRefWorkStationKey(save.getRefWorkStationKey());
            videoTraceCameraConfig.setMachineCode(save.getMachineCode());
            videoTraceCameraConfig.setChuteCode(save.getChuteCode());
            videoTraceCameraConfig.setSupplyDwsCode(save.getSupplyDwsCode());
            VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
            videoTraceCameraQuery.setCameraCode(videoTraceCameraConfig.getCameraCode());
            videoTraceCameraQuery.setNationalChannelCode(videoTraceCameraConfig.getNationalChannelCode());
            List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(videoTraceCameraQuery);
            if (CollectionUtils.isEmpty(videoTraceCameras)){
                String errorMsg = String.format("保存摄像头配置失败，摄像头不存在,设备编号：{}，通道编号：{}", videoTraceCameraConfig.getCameraCode(), videoTraceCameraConfig.getNationalChannelCode());
                return Result.fail(errorMsg);
            }
            videoTraceCameraConfig.setCameraId(videoTraceCameras.get(0).getId());
        }

        // 分别过滤出新增和删除的配置
        List<VideoTraceCameraConfig> addList = filterConfigs(newList, oldList, save.getUpdateUserErp());
        List<VideoTraceCameraConfig> delList = filterConfigs(oldList, newList, save.getUpdateUserErp());

        // 更新摄像头配置状态
        updateCameraConfigStatus(addList, true);
        updateCameraConfigStatus(delList, false);

        //保存新增配置
        if (!addList.isEmpty()) {
            videoTraceCameraConfigDao.batchSave(addList);
        }
        if (!delList.isEmpty()) {
            List<Integer> idsToDelete = delList.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList());
            videoTraceCameraConfigDao.batchDelete(idsToDelete, save.getUpdateUserErp());
        }

        // 清理缓存
        cacheService.del(getKey(save.getRefWorkGridKey()));

        return Result.success();
    }

    /**
     * 编辑时过滤出新增或删除的配置
     */
    private List<VideoTraceCameraConfig> filterConfigs(List<VideoTraceCameraConfig> sourceList, List<VideoTraceCameraConfig> targetList, String erp) {
        return sourceList.stream()
                .filter(a -> targetList.stream().noneMatch(b -> Objects.equals(a.getCameraId(), b.getCameraId()) && Objects.equals(a.getMasterCamera(), b.getMasterCamera())))
                .peek(x -> {
                    x.setCreateErp(erp);
                    x.setUpdateErp(erp);
                }).collect(Collectors.toList());
    }

    /**
     * 修改摄像头配置状态
     */
    private void updateCameraConfigStatus(List<VideoTraceCameraConfig> list, boolean isAdd) {
        List<Integer> cameraIds = list.stream().map(VideoTraceCameraConfig::getCameraId).collect(Collectors.toList());
        if (!cameraIds.isEmpty()) {
            videoTraceCameraDao.updateCameraConfigStatusBatch(cameraIds, isAdd ? (byte) 1 : (byte) 0);
        }
    }


    /**
     * 获取工作设备与摄像头绑定的数据信息。
     *
     * @param gridKey 网格唯一标识符
     * @return 返回包含工作设备与摄像头绑定信息的视图对象列表
     */
    @Override
    public List<GridCameraBindingVo> getWorkDeviceCameraData(String gridKey) {
        // 查询网格信息
        WorkGrid workGrid = workGridDao.queryByWorkGridKey(gridKey);
        // 查询网格下工序
        WorkStationGridQuery query = new WorkStationGridQuery();
        List<WorkStationGrid> workStationGrids = workStationGridService.queryListForWorkGridVo(query);
        // 查询网格下绑定数据
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setRefWorkGridKey(gridKey);
        condition.setYn((byte) 1);
        List<VideoTraceCameraConfig> allConfigs = videoTraceCameraConfigDao.queryByCondition(condition);
        // 查询网格关联的设备
        BaseDmsAutoJsfResponse<List<DeviceGridDto>> deviceGrid = deviceConfigInfoJsfService.findDeviceGridByBusinessKey(gridKey, null);
        Map<String, GridCameraBindingVo> deviceCameraMap = new HashMap<>();
        //遍历设备，构建设备与摄像头的绑定关系
        for (DeviceGridDto datum : deviceGrid.getData()) {
            GridCameraBindingVo gridCameraBindingVo = deviceCameraMap.computeIfAbsent(datum.getMachineCode(), key -> {
                List<VideoTraceCameraConfig> deviceConfigs = allConfigs.stream()
                        .filter(config -> datum.getMachineCode().equals(config.getMachineCode())
                                && StringUtils.isBlank(config.getSupplyDwsCode()) && StringUtils.isBlank(config.getChuteCode()))
                        .collect(Collectors.toList());
                GridCameraBindingVo newVo = getGridCameraBindingVo(workGrid, deviceConfigs);
                newVo.setTreeNodeKey(datum.getMachineCode());
                newVo.setTreeNodeLabel(datum.getMachineCode());
                newVo.setMachineCode(datum.getMachineCode());
                newVo.setHasChildren(false);
                newVo.setChildren(new ArrayList<>());
                return newVo;
            });
            if (StringUtils.isBlank(datum.getSupplyNo()) && StringUtils.isBlank(datum.getChuteCode())) {
                continue;
            }
            // 过滤格口，供包台上绑定数据
            List<VideoTraceCameraConfig> childrenConfigs = allConfigs.stream()
                    .filter(config -> datum.getMachineCode().equals(config.getMachineCode())
                            && Objects.equals(config.getChuteCode(), datum.getChuteCode()) && Objects.equals(config.getSupplyDwsCode(), datum.getSupplyNo()))
                    .collect(Collectors.toList());
            GridCameraBindingVo children = getGridCameraBindingVo(workGrid, childrenConfigs);
            children.setMachineCode(datum.getMachineCode());
            children.setChuteCode(StringUtils.isNotBlank(datum.getChuteCode()) ? datum.getChuteCode() : null);
            children.setSupplyDwsCode(StringUtils.isNotBlank(datum.getSupplyNo()) ? datum.getSupplyNo() : null);
            children.setTreeNodeKey(StringUtils.isBlank(datum.getSupplyNo()) ? datum.getChuteCode() : datum.getSupplyNo());
            children.setTreeNodeLabel(StringUtils.isBlank(datum.getSupplyNo()) ? datum.getChuteCode() : datum.getSupplyNo());
            gridCameraBindingVo.getChildren().add(children);
            gridCameraBindingVo.setHasChildren(true);
        }

        // 遍历工序，构建工序与摄像头的绑定关系
        List<GridCameraBindingVo> workStationCameraList = workStationGrids.stream().map(workStationGrid -> {
            List<VideoTraceCameraConfig> stationConfigs = allConfigs.stream()
                    .filter(config -> workStationGrid.getBusinessKey().equals(config.getRefWorkGridKey()))
                    .collect(Collectors.toList());
            GridCameraBindingVo stationVo = getGridCameraBindingVo(workGrid, stationConfigs);
            stationVo.setTreeNodeKey(workStationGrid.getBusinessKey());
            stationVo.setTreeNodeLabel(workStationGrid.getWorkName());
            stationVo.setWorkStationKey(workStationGrid.getBusinessKey());
            return stationVo;
        }).collect(Collectors.toList());

        // 工序和绑定关系，构成最终结果
        List<GridCameraBindingVo> result = new ArrayList<>(deviceCameraMap.values());
        result.addAll(workStationCameraList);
        return result;
    }


    /**
     * 根据网格和摄像配置列表，构造并返回一个网格摄像绑定对象。
     */
    private GridCameraBindingVo getGridCameraBindingVo(WorkGrid workGrid, List<VideoTraceCameraConfig> videoTraceCameraConfigs) {

        GridCameraBindingVo gridCameraBindingVo = BeanUtils.copy(workGrid, GridCameraBindingVo.class);
        gridCameraBindingVo.setGridBusinessKey(workGrid.getBusinessKey());
        gridCameraBindingVo.setHasChildren(true);
        if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs)) {
            List<VideoTraceCameraConfigVo> configVos = getVideoTraceCameraConfigVos(videoTraceCameraConfigs);
            gridCameraBindingVo.setConfigs(configVos);
            gridCameraBindingVo.setBindingStatus(1);
            gridCameraBindingVo.setBindingCount(videoTraceCameraConfigs.size());
            for (VideoTraceCameraConfigVo config : configVos) {
                if (config.getMasterCamera() == 1) {
                        gridCameraBindingVo.setCameraCode(config.getCameraCode());
                        gridCameraBindingVo.setNationalChannelCode(config.getNationalChannelCode());
                        gridCameraBindingVo.setNationalChannelName(config.getNationalChannelName());
                    }
                    // 找到主摄像头后退出循环
                    break;
                }
            }
        return gridCameraBindingVo;
    }

    /**
     * 获取视频追踪摄像头配置Vo列表
     * 补充通道号，通道名称等信息
     */
    private List<VideoTraceCameraConfigVo> getVideoTraceCameraConfigVos(List<VideoTraceCameraConfig> videoTraceCameraConfigs) {
        return videoTraceCameraConfigs.stream().map(x -> {
            VideoTraceCameraConfigVo copy = BeanUtils.copy(x, VideoTraceCameraConfigVo.class);
            VideoTraceCamera videoTraceCamera = videoTraceCameraDao.getByIdNoYn(x.getCameraId());
            copy.setCameraCode(videoTraceCamera.getCameraCode());
            copy.setNationalChannelCode(videoTraceCamera.getNationalChannelCode());
            copy.setNationalChannelName(videoTraceCamera.getNationalChannelName());
            return copy;
        }).collect(Collectors.toList());
    }

}