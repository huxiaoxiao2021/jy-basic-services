package com.jdl.basic.provider.core.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface VideoTraceCameraService {
    Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery);

    Result<List<VideoTraceCameraConfig>> queryVideoTraceCameraConfig(VideoTraceCameraQuery query);

    Result<Boolean> editCameraConfig(VideoTraceCameraVo videoTraceCameraVo);

    int deleteById(VideoTraceCamera record);

    int insert(VideoTraceCamera record);

    VideoTraceCamera selectByPrimaryKey(Integer id);

    int updateById(VideoTraceCamera record);

    int queryCount(VideoTraceCameraQuery videoTraceCameraQuery);

    List<VideoTraceCamera> queryByCondition(VideoTraceCameraQuery query);
}
