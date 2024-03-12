package com.jdl.basic.provider.core.provider.videoTraceCamera;

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

        VideoTraceCameraConfig query = new VideoTraceCameraConfig();
        query.setCameraId(videoTraceCameraVo.getId());
        List<VideoTraceCameraConfig> list = videoTraceCameraConfigService.queryByCameraId(query);
        if (CollectionUtils.isEmpty(list)){
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCameraVo.getId());
            update.setConfigStatus((byte) 1);
            videoTraceCameraService.updateById(update);
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
        if (StringUtils.isBlank(query.getWorkGridKey())){
            return Result.fail("网格业务主键不能为空");
        }
        //网格、格口、DWS、工序查配置信息
        if (StringUtils.isNotBlank(query.getChuteCode()) || StringUtils.isNotBlank(query.getSupplyDwsCode()) || StringUtils.isNotBlank(query.getWorkStationKey())){
            VideoTraceCameraConfig videoTraceCameraConfig =new VideoTraceCameraConfig();
            videoTraceCameraConfig.setRefWorkStationKey(query.getWorkStationKey());
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
    public Result<Boolean> importDatas(List<VideoTraceCameraImport> list) {
        videoTraceCameraService.importDatas(list);
        return null;
    }

}
