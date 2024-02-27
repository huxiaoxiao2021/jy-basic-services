package com.jdl.basic.api.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface VideoTraceCameraJsfService {
    //列表分页查询
    Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery);
    //查摄像头绑定数据
    Result<List<VideoTraceCameraConfig>> queryVideoTraceCameraConfig(VideoTraceCameraQuery query);
    //修改摄像头绑定数据
    Result<Boolean> editCameraConfig(VideoTraceCameraVo videoTraceCameraVo);
    //获取网格主摄像头
    Result<List<VideoTraceCameraConfig>> getWorkMasterCamera(String workGridKey);

    int cancelVideoTraceCameraConfig(VideoTraceCameraConfig videoTraceCameraConfig);

}
