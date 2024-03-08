package com.jdl.basic.api.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface VideoTraceCameraJsfService {
    /**
     * 列表分页查询
     */
    Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery);
    /**
     * 查摄像头绑定数据
     */
    Result<List<VideoTraceCameraConfig>> queryVideoTraceCameraConfig(VideoTraceCameraQuery query);
    /**
     * 修改摄像头绑定数据
     */
    Result<Boolean> editCameraConfig(VideoTraceCameraVo videoTraceCameraVo);
    /**
     * 获取网格主摄像头
     */
    Result<VideoTraceCamera> getWorkMasterCamera(String workGridKey);

    /**
     * 根据网格、设备、工序删除摄像头绑定消息
     *
     */
    int cancelVideoTraceCameraConfig(VideoTraceCameraConfig videoTraceCameraConfig);

    /**
     *
     * 修改、绑定网格主摄像头
     */
    Result<Boolean> changeMasterCameraConfig(VideoTraceCameraVo videoTraceCameraVo);
    Result<Integer> getCount(VideoTraceCameraQuery query);

    /**
     * 格口、设备工序，网格查摄像头
     *
     */
    Result<List<VideoTraceCamera>> queryCamera(VideoTraceCameraConfigQuery query);

    /**
     *
     * 查摄像头下绑定数据
     *
     */
    Result<List<VideoTraceCameraConfig>> queryCameraConfigByCameraId(int id);

}
