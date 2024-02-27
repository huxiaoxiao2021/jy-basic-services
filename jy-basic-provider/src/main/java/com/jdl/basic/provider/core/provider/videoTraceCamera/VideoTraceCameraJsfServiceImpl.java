package com.jdl.basic.provider.core.provider.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraVo;
import com.jdl.basic.api.service.videoTraceCamera.VideoTraceCameraJsfService;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraConfigService;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("videoTraceCameraJsfService")
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

        if (StringUtils.isBlank(videoTraceCameraConfig.getRefGridKey())){
            throw new RuntimeException("网格业务主键不能为空");
        }
        List<VideoTraceCameraConfig> list = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        if (CollectionUtils.isNotEmpty(list)){
            return videoTraceCameraConfigService.batchDelete(list.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList()),"sys");
        }
        return 0;
    }
}
