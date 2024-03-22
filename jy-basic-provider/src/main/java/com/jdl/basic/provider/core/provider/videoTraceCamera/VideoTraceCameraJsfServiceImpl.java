package com.jdl.basic.provider.core.provider.videoTraceCamera;

import com.google.common.collect.Lists;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.service.videoTraceCamera.VideoTraceCameraJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraConfigService;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public int cancelVideoTraceCameraConfig(VideoTraceCameraConfig videoTraceCameraConfig) {

        return videoTraceCameraConfigService.cancelVideoTraceCameraConfig( videoTraceCameraConfig);

    }

    @Override
    public Result<Boolean> changeMasterCameraConfig(VideoTraceCameraVo videoTraceCameraVo) {
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
        log.info("queryCamera,查询入参【{}】", JsonHelper.toJSONString(query));
        if (StringUtils.isBlank(query.getWorkGridKey())){
            return Result.fail("网格业务主键不能为空");
        }
        //按网格查询出所有有效的摄像头配置
        VideoTraceCameraConfig videoTraceCameraConfig =new VideoTraceCameraConfig();
        videoTraceCameraConfig.setRefWorkGridKey(query.getWorkGridKey());
        videoTraceCameraConfig.setYn((byte) 1);
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);


        if (((StringUtils.isNotBlank(query.getChuteCode()) ? 1:0)
                + (StringUtils.isNotBlank(query.getSupplyDwsCode()) ? 1:0)
                + (StringUtils.isNotBlank(query.getWorkStationKey()) ? 1:0)) >1){
            return Result.fail("chuteCode，supplyDwsCode，workStationKey不能同时有值");
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
        if (CollectionUtils.isNotEmpty(collect)){
            List<VideoTraceCamera> res = Lists.newArrayList();
            List<Integer> ids = Lists.newArrayList();
            for (VideoTraceCameraConfig config:collect){
                if (!ids.contains(config.getCameraId())){
                    VideoTraceCamera videoTraceCamera = videoTraceCameraService.selectByPrimaryKey(config.getCameraId());
                    videoTraceCamera.setMaster(config.getMasterCamera() == 1);
                    WorkGrid workGrid = workGridService.queryByWorkGridKey(config.getRefWorkGridKey());
                    if (workGrid != null){
                        videoTraceCamera.setGridCode(workGrid.getGridCode());
                    }
                    res.add(videoTraceCamera);
                    ids.add(config.getCameraId());
                }
            }
//            videoTraceCameraService.getByIds(collect.stream().map(VideoTraceCameraConfig::getCameraId).distinct().collect(Collectors.toList()));
            return Result.success(res);
        }

        return Result.success("未查到摄像头信息");
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
    public int insert(VideoTraceCamera VideoTraceCamera) {
        return videoTraceCameraService.insert(VideoTraceCamera);
    }

    @Override
    public int delete(VideoTraceCamera VideoTraceCamera) {
        return videoTraceCameraService.deleteById(VideoTraceCamera);
    }

    @Override
    public int saveOrUpdateCameraStatus(VideoTraceCamera VideoTraceCamera) {
        return videoTraceCameraService.saveOrUpdateCameraStatus(VideoTraceCamera);
    }

    @Override
    public Result<Boolean> importCameras(List<VideoTraceCameraImport> list, int type) {
        if (type==1){
            videoTraceCameraService.importCameras(list);
        }
        if (type==2){
            videoTraceCameraService.importCameraConfigs(list);
        }
        return null;
    }

    @Override
    public int deleteCameraByIds(List<Integer> ids, String operate) {
        return videoTraceCameraService.deleteCameraByIds(ids,operate);
    }

    @Override
    public int deleteCameraConfigByIds(List<Integer> ids, String operate) {
        return videoTraceCameraService.deleteCameraConfigByIds(ids,operate);
    }

}
