package com.jdl.basic.provider.core.provider.videoTraceCamera;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.service.videoTraceCamera.VideoTraceCameraJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraConfigService;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("videoTraceCameraJsfServiceImpl")
public class VideoTraceCameraJsfServiceImpl implements VideoTraceCameraJsfService {
    @Autowired
    private VideoTraceCameraService videoTraceCameraService;

    @Autowired
    private VideoTraceCameraConfigService videoTraceCameraConfigService;

    @Override
    public Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery) {
        return videoTraceCameraService.queryPageList(videoTraceCameraQuery);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "videoTraceCameraJsfServiceImpl.queryVideoTraceCameraConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<VideoTraceCameraConfig>> queryVideoTraceCameraConfig(VideoTraceCameraQuery query) {
        return videoTraceCameraService.queryVideoTraceCameraConfig(query) ;
    }

    @Override
    public Result<Boolean> editCameraConfig(VideoTraceCameraVo videoTraceCameraVo) {
        return videoTraceCameraService.editCameraConfig(videoTraceCameraVo);
    }

    @Override
    public Result<List<VideoTraceCameraConfig>> getWorkMasterCamera(String workGridKey) {
        Result<List<VideoTraceCameraConfig>> result = Result.success();
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setMasterCamera((byte) 1);
        videoTraceCameraConfig.setRefWorkGridKey(workGridKey);
        //根据网格查主摄像头
        List<VideoTraceCameraConfig> list = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        result.setData(list);
        return result;
    }

    @Override
    public int cancelVideoTraceCameraConfig(VideoTraceCameraConfig videoTraceCameraConfig) {

        if (StringUtils.isBlank(videoTraceCameraConfig.getRefWorkGridKey())){
            throw new RuntimeException("网格业务主键不能为空");
        }
        List<VideoTraceCameraConfig> list = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        if (CollectionUtils.isNotEmpty(list)){
            return videoTraceCameraConfigService.batchDelete(list.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList()),"sys");
        }
        return 0;
    }

    @Override
    public Result<Boolean> changeMasterCameraConfig(VideoTraceCameraVo videoTraceCameraVo) {

        Result<Boolean> result = Result.success();
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setMasterCamera(videoTraceCameraVo.getMasterCamera());
        videoTraceCameraConfig.setRefWorkGridKey(videoTraceCameraVo.getGridBusinessKey());
        //根据网格查主摄像头
        List<VideoTraceCameraConfig> list = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        // 删除网格原主摄像头绑定数据
        if (!list.isEmpty()){
             videoTraceCameraConfigService.batchDelete(list.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList()), videoTraceCameraVo.getUpdateErp());
        }
        List<VideoTraceCameraConfig> addList = new ArrayList<>(videoTraceCameraVo.getVideoTraceCameraConfigList());
        for (VideoTraceCameraConfig item : list) {
            VideoTraceCameraConfig add = new VideoTraceCameraConfig();
            add.setCameraId(item.getCameraId());
            add.setRefWorkGridKey(item.getRefWorkGridKey());
            add.setMasterCamera((byte) 0);
            add.setCreateErp(videoTraceCameraVo.getUpdateErp());
            addList.add(add);
        }
        videoTraceCameraConfigService.batchSave(addList);
        return result.setData(true);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "videoTraceCameraJsfService.queryCamera", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    @Cache(key = "videoTraceCameraJsfService.queryCamera@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
    public Result<List<VideoTraceCamera>> queryCamera(VideoTraceCameraConfigQuery query) {
        if (StringUtils.isNotBlank(query.getChuteCode())){
            return Result.fail("网格业务主键不能为空");
        }
        //网格、格口、DWS、工序查配置信息
        if (StringUtils.isNotBlank(query.getChuteCode()) || StringUtils.isNotBlank(query.getSupplyDwsCode()) || StringUtils.isNotBlank(query.getWorkStationKey())){
            VideoTraceCameraConfig videoTraceCameraConfig =new VideoTraceCameraConfig();
            videoTraceCameraConfig.setRefGridKey(query.getWorkStationKey());
            videoTraceCameraConfig.setRefWorkGridKey(query.getWorkGridKey());
            videoTraceCameraConfig.setMachineCode(query.getMachineCode());
            videoTraceCameraConfig.setSupplyDwsCode(query.getSupplyDwsCode());
            videoTraceCameraConfig.setChuteCode(query.getChuteCode());
            videoTraceCameraConfig.setYn((byte) 1);
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
            if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs)){
                return Result.success(videoTraceCameraService.getByIds(videoTraceCameraConfigs.stream().map(VideoTraceCameraConfig::getCameraId).distinct().collect(Collectors.toList())));
            }
        }
        //网格、设备编码查配置信息
        if (StringUtils.isNotBlank(query.getMachineCode())){
            VideoTraceCameraConfig videoTraceCameraConfig =new VideoTraceCameraConfig();
            videoTraceCameraConfig.setRefWorkGridKey(query.getWorkGridKey());
            videoTraceCameraConfig.setMachineCode(query.getMachineCode());
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByDevice(videoTraceCameraConfig);
            if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs)){
                return Result.success(videoTraceCameraService.getByIds(videoTraceCameraConfigs.stream().map(VideoTraceCameraConfig::getCameraId).distinct().collect(Collectors.toList())));
            }
        }
        //网格查配置信息
        VideoTraceCameraConfig videoTraceCameraConfig =new VideoTraceCameraConfig();
        videoTraceCameraConfig.setRefWorkGridKey(query.getWorkGridKey());
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByGrid(videoTraceCameraConfig);
        if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs)){
            return Result.success(videoTraceCameraService.getByIds(videoTraceCameraConfigs.stream().map(VideoTraceCameraConfig::getCameraId).distinct().collect(Collectors.toList())));
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

}
