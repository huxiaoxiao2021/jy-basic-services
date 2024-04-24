package com.jdl.basic.provider.core.provider.videoTraceCamera;

import com.google.common.collect.Lists;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.service.videoTraceCamera.VideoTraceCameraJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.*;
import com.jdl.basic.provider.common.Jimdb.CacheService;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraConfigService;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service("videoTraceCameraJsfServiceImpl")
public class VideoTraceCameraJsfServiceImpl implements VideoTraceCameraJsfService {
    @Autowired
    private VideoTraceCameraService videoTraceCameraService;

    @Autowired
    private VideoTraceCameraConfigService videoTraceCameraConfigService;

    @Autowired
    private WorkGridService workGridService;

    @Resource
    @Qualifier("JimdbCacheService")
    private CacheService cacheService;

    /**
     * 缓存前缀
     */
    private static final String CAMERA_CONFIG_CACHE_KEY_PRE="camera_config_cache_key_pre";

    /**
     * 缓存时间
     */
    private static final Long  CAMERA_CACHE_TIME_EXPIRE= 60*10L ;



    @Override
    public Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery) {
        return videoTraceCameraService.queryPageList(videoTraceCameraQuery);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "videoTraceCameraJsfServiceImpl.queryVideoTraceCameraConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<VideoTraceCameraConfig>> queryVideoTraceCameraConfig(VideoTraceCameraQuery query) {
        log.info("queryVideoTraceCameraConfig,查询入参【{}】", JsonHelper.toJSONString(query));
        return videoTraceCameraService.queryVideoTraceCameraConfig(query) ;
    }

    @Override
    public Result<Boolean> editCameraConfig(VideoTraceCameraVo videoTraceCameraVo) {

        return videoTraceCameraService.editCameraConfig(videoTraceCameraVo);
    }

    @Override
    public Result<VideoTraceCamera> getWorkMasterCamera(String workGridKey) {
        Result<VideoTraceCamera> result = Result.success();
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setMasterCamera((byte) 1);
        videoTraceCameraConfig.setRefWorkGridKey(workGridKey);
        videoTraceCameraConfig.setYn((byte) 1);
        //根据网格查主摄像头
        List<VideoTraceCameraConfig> list = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        if (CollectionUtils.isNotEmpty(list)){
            VideoTraceCamera videoTraceCamera = videoTraceCameraService.selectByPrimaryKey(list.get(0).getCameraId());
            result.setData(videoTraceCamera);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "videoTraceCameraJsfService.cancelVideoTraceCameraConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public int cancelVideoTraceCameraConfig(VideoTraceCameraConfig videoTraceCameraConfig) {
        //根据网格删除缓存
        delCameraConfigCache(videoTraceCameraConfig.getRefWorkGridKey());
        return videoTraceCameraConfigService.cancelVideoTraceCameraConfig( videoTraceCameraConfig);

    }

    @Override
    public Result<Boolean> changeMasterCameraConfig(VideoTraceCameraVo videoTraceCameraVo) {

        //根据网格删除缓存
        delCameraConfigCache(videoTraceCameraVo.getGridBusinessKey());

        List<Integer> delIds = new ArrayList<>();
        Result<Boolean> result = Result.success();
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setMasterCamera(videoTraceCameraVo.getMasterCamera());
        videoTraceCameraConfig.setRefWorkGridKey(videoTraceCameraVo.getGridBusinessKey());
        videoTraceCameraConfig.setYn((byte) 1);
        //根据网格查主摄像头
        List<VideoTraceCameraConfig> masterCamera = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        // 删除网格原主摄像头绑定数据
        if (!masterCamera.isEmpty()){
            delIds.addAll(masterCamera.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList()));
        }

        //查摄像头下已绑定数据
        VideoTraceCameraConfig query = new VideoTraceCameraConfig();
        query.setCameraId(videoTraceCameraVo.getId());
        List<VideoTraceCameraConfig> list = videoTraceCameraConfigService.queryByCameraId(query);
        if (CollectionUtils.isEmpty(list)){
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCameraVo.getId());
            update.setConfigStatus((byte) 1);
            videoTraceCameraService.updateById(update);
        }
        if (CollectionUtils.isNotEmpty(videoTraceCameraVo.getVideoTraceCameraConfigList())){
            list.addAll(videoTraceCameraVo.getVideoTraceCameraConfigList());
        }
        if (list.stream().map(VideoTraceCameraConfig::getRefWorkGridKey).distinct().count() >3){
            throw new RuntimeException("一个摄像头，最多只能绑定3个网格");
        }

        //绑定的主摄像头，原已存在绑定关系，先删除再绑定
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setRefWorkGridKey(videoTraceCameraVo.getGridBusinessKey());
        condition.setCameraId(videoTraceCameraVo.getId());
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByGrid(condition);
        delIds.addAll(videoTraceCameraConfigs.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList()));
        videoTraceCameraConfigService.batchDelete(delIds, videoTraceCameraVo.getUpdateErp());

        List<VideoTraceCameraConfig> addList = new ArrayList<>(videoTraceCameraVo.getVideoTraceCameraConfigList());
        for (VideoTraceCameraConfig item : masterCamera) {
            VideoTraceCameraConfig add = new VideoTraceCameraConfig();
            add.setCameraId(item.getCameraId());
            add.setRefWorkGridKey(item.getRefWorkGridKey());
            add.setMasterCamera((byte) 0);
            add.setCreateErp(videoTraceCameraVo.getUpdateErp());
            add.setStatus(item.getStatus());
            addList.add(add);
        }
        addList.forEach(x->x.setCreateErp(videoTraceCameraVo.getUpdateErp()));
        videoTraceCameraConfigService.batchSave(addList);
        return result.setData(true);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "videoTraceCameraJsfService.queryCamera", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<VideoTraceCamera>> queryCamera(VideoTraceCameraConfigQuery query) {
        Result<String> result = verifyParam(query);
        if (result.isFail()){
            return Result.fail(result.getMessage());
        }

        //查摄像头配置信息 规则为先查格口绑定数据，未查到再查设备、最后查网格
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = getCameraConfigs(query);

        //根据配置信息查到摄像头，并返回
        if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs) ){
            List<Integer> cameraIds = videoTraceCameraConfigs.stream().map(VideoTraceCameraConfig::getCameraId).distinct().collect(Collectors.toList());
            List<VideoTraceCamera> cameras = getCamerasWithCache(cameraIds);
            return Result.success(cameras);

        }
        return Result.success("未查到摄像头信息");
    }

    private List<VideoTraceCamera> getCamerasWithCache(List<Integer> cameraIds) {
        String key = getCacheKey(cameraIds.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));

        String cameraConfigS = cacheService.get(key);
        List<VideoTraceCamera> cameras = JsonHelper.toList(cameraConfigS, VideoTraceCamera.class);
        if (cameras!=null){
            return cameras;
        }

        //缓存中不存在时 查询出所有有效的摄像头
        cameras = videoTraceCameraService.getByIds(cameraIds);
        //查到的数据加入缓存
        cacheService.setEx(key,JsonHelper.toJSONString(cameras),CAMERA_CACHE_TIME_EXPIRE);
        return cameras;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "videoTraceCameraJsfService.queryCameraInfo", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<VideoTraceCamera>> queryCameraInfo(VideoTraceCameraConfigQuery query) {
        log.info("queryCamera,查询入参【{}】", JsonHelper.toJSONString(query));
        Result<String> result = verifyParam(query);
        if (result.isFail()){
            return Result.fail(result.getMessage());
        }

        //查摄像头配置信息
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = getCameraConfigsNew(query);

        if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs) ){
            List<VideoTraceCamera> res = Lists.newArrayList();
            List<Integer> ids = Lists.newArrayList();
            for (VideoTraceCameraConfig config:videoTraceCameraConfigs){
                if (!ids.contains(config.getCameraId())){
                    //查询包含yn为0的数据
                    VideoTraceCamera videoTraceCamera = videoTraceCameraService.getByIdNoYn(config.getCameraId());
                    videoTraceCamera.setMaster(config.getMasterCamera() == 1);
                    WorkGrid workGrid = workGridService.queryByWorkGridKeyWithCache(config.getRefWorkGridKey());
                    if (workGrid != null){
                        videoTraceCamera.setGridCode(workGrid.getGridCode());
                    }
                    res.add(videoTraceCamera);
                    ids.add(config.getCameraId());
                }
            }
            return Result.success(res);
        }

        return Result.success("未查到摄像头信息");
    }

    private List<VideoTraceCameraConfig> getCameraConfigsNew(VideoTraceCameraConfigQuery query) {

        VideoTraceCameraConfig videoTraceCameraConfig =new VideoTraceCameraConfig();
        videoTraceCameraConfig.setRefWorkGridKey(query.getWorkGridKey());
        videoTraceCameraConfig.setOperateTime(query.getOperaterTime());
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        if (CollectionUtils.isEmpty(videoTraceCameraConfigs)){
            return null;
        }

        List<VideoTraceCameraConfig> collect = null;
        //查询条件中格口不为空时，按格口筛选摄像头配置
        if (StringUtils.isNotBlank(query.getChuteCode())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getChuteCode(), x.getChuteCode()))
                    .collect(Collectors.toList());
        }
        //以上步骤没有筛选到数据，且查询条件中dws不为空时，按dws筛选摄像头配置
        if (CollectionUtils.isEmpty(collect) && StringUtils.isNotBlank(query.getSupplyDwsCode())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getSupplyDwsCode(), x.getSupplyDwsCode()))
                    .collect(Collectors.toList());
        }

        //以上步骤没有筛选到数据，且查询条件中工序key不为空时，按工序筛选摄像头配置
        if (CollectionUtils.isEmpty(collect) && StringUtils.isNotBlank(query.getWorkStationKey())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getWorkStationKey(),
                            x.getRefWorkStationKey()))
                    .collect(Collectors.toList());
        }

        //以上步骤没有筛选到数据，且查询条件中工序设备编码不为空时，按设备编码筛选摄像头配置
        if (CollectionUtils.isEmpty(collect) && StringUtils.isNotBlank(query.getMachineCode())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getMachineCode(), x.getMachineCode())
                            && StringUtils.isBlank(x.getSupplyDwsCode())
                            && StringUtils.isBlank(x.getChuteCode()))
                    .collect(Collectors.toList());
        }
        //以上步骤没有筛选到数据，且查询条件中工序设备编码不为空时，按设备编码筛选摄像头配置
        if (CollectionUtils.isEmpty(collect)){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getWorkGridKey(), x.getRefWorkGridKey())
                            && StringUtils.isBlank(x.getMachineCode())
                            && StringUtils.isBlank(x.getRefWorkStationKey()))
                    .collect(Collectors.toList());
        }

        return collect;
    }

    /**
     * 查摄像头信息入参校验
     */
    private static Result<String> verifyParam(VideoTraceCameraConfigQuery query) {
        if (StringUtils.isBlank(query.getWorkGridKey())){
            return Result.fail("网格业务主键不能为空");
        }
        return Result.success();
    }

    /**
     * 按格口、工序，设备、网格查摄像头配置信息
     * 先查格口绑定数据，未查到再查设备、最后查网格 查到即返回
     */
    private List<VideoTraceCameraConfig> getCameraConfigs(VideoTraceCameraConfigQuery query) {
        
        //按网格查询摄像头配置信息，先查缓存
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = getCameraConfigByWorkGridKeyCache(query.getWorkGridKey());

        List<VideoTraceCameraConfig> collect = null;
        //查询条件中格口不为空时，按格口筛选摄像头配置
        if (StringUtils.isNotBlank(query.getChuteCode())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getChuteCode(), x.getChuteCode()))
                    .collect(Collectors.toList());
        }
        //以上步骤没有筛选到数据，且查询条件中dws不为空时，按dws筛选摄像头配置
        if (CollectionUtils.isEmpty(collect) && StringUtils.isNotBlank(query.getSupplyDwsCode())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getSupplyDwsCode(), x.getSupplyDwsCode()))
                    .collect(Collectors.toList());
        }

        //以上步骤没有筛选到数据，且查询条件中工序key不为空时，按工序筛选摄像头配置
        if (CollectionUtils.isEmpty(collect) && StringUtils.isNotBlank(query.getWorkStationKey())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getWorkStationKey(),
                            x.getRefWorkStationKey()))
                    .collect(Collectors.toList());
        }

        //以上步骤没有筛选到数据，且查询条件中工序设备编码不为空时，按设备编码筛选摄像头配置
        if (CollectionUtils.isEmpty(collect) && StringUtils.isNotBlank(query.getMachineCode())){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getMachineCode(), x.getMachineCode())
                            && StringUtils.isBlank(x.getSupplyDwsCode())
                            && StringUtils.isBlank(x.getChuteCode()))
                    .collect(Collectors.toList());
        }
        //以上步骤没有筛选到数据，且查询条件中工序设备编码不为空时，按设备编码筛选摄像头配置
        if (CollectionUtils.isEmpty(collect)){
            collect = videoTraceCameraConfigs.stream()
                    .filter(x -> Objects.equals(query.getWorkGridKey(), x.getRefWorkGridKey())
                            && StringUtils.isBlank(x.getMachineCode())
                            && StringUtils.isBlank(x.getRefWorkStationKey()))
                    .collect(Collectors.toList());
        }
        return collect;
    }

    /**
     * 使用网格业务主键从缓存中查摄像头配置，不存在时查库并加入缓存
     */
    private List<VideoTraceCameraConfig> getCameraConfigByWorkGridKeyCache(String workGridKey) {
        String key = getCacheKey(workGridKey);
        String cameraConfigS = cacheService.get(key);
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = JsonHelper.toList(cameraConfigS, VideoTraceCameraConfig.class);
        if (videoTraceCameraConfigs!=null){
            return videoTraceCameraConfigs;
        }

        //缓存中不存在时 按网格查询出所有有效的摄像头配置
        VideoTraceCameraConfig videoTraceCameraConfig =new VideoTraceCameraConfig();
        videoTraceCameraConfig.setRefWorkGridKey(workGridKey);
        videoTraceCameraConfig.setYn((byte) 1);
        videoTraceCameraConfigs = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        //查到的数据加入缓存
        cacheService.setEx(key,JsonHelper.toJSONString(videoTraceCameraConfigs),CAMERA_CACHE_TIME_EXPIRE);
        return videoTraceCameraConfigs;
    }

    /**
     * 根据网格主键删除缓存
     */
    private boolean delCameraConfigCache(String workGridKey){
        String key = getCacheKey(workGridKey);
        return cacheService.del(key);
    }

    /**
     * 获取缓存key
     */
    private String getCacheKey(String key) {
        return CAMERA_CONFIG_CACHE_KEY_PRE+key;
    }

    @Override
    public Result<Integer> getCount(VideoTraceCameraQuery query) {
        return Result.success(videoTraceCameraService.queryCount(query));
    }

    @Override
    public Result<List<VideoTraceCameraConfig>> queryCameraConfigByCameraId(int id) {
        Result<List<VideoTraceCameraConfig>> result = Result.success();
        VideoTraceCamera videoTraceCameras = videoTraceCameraService.selectByPrimaryKey(id);
        if (videoTraceCameras==null){
            return Result.fail("摄像头信息不存在");
        }
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setCameraId(id);
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByCameraId(condition);
        result.setData(videoTraceCameraConfigs);
        return result;
    }

    @Override
    public int insert(VideoTraceCamera videoTraceCamera) {
        return videoTraceCameraService.insert(videoTraceCamera);
    }

    @Override
    public int delete(VideoTraceCamera videoTraceCamera) {
        return videoTraceCameraService.deleteById(videoTraceCamera);
    }

    @Override
    public int saveOrUpdateCameraStatus(VideoTraceCamera videoTraceCamera) {
        return videoTraceCameraService.saveOrUpdateCameraStatus(videoTraceCamera);
    }

    @Override
    public Result<Boolean> importCameras(List<VideoTraceCameraImport> list, int type) {
        if (type==1){
            videoTraceCameraService.importCameras(list);
        }
        if (type==2){
            videoTraceCameraService.importCameraConfigs(list);
        }
        return Result.success();
    }

    @Override
    public int deleteCameraByIds(List<Integer> ids, String operate) {
        return videoTraceCameraService.deleteCameraByIds(ids,operate);
    }

    @Override
    public int deleteCameraConfigByIds(List<Integer> ids) {
        return videoTraceCameraService.deleteByIds(ids);
    }

}
