package com.jdl.basic.provider.core.dao.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;

import java.util.List;

public interface VideoTraceCameraDao {
    int deleteById(VideoTraceCamera record);

    int insert(VideoTraceCamera record);

    VideoTraceCamera selectByPrimaryKey(Integer id);

    int updateById(VideoTraceCamera record);

    List<VideoTraceCamera> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery);

    long queryCount(VideoTraceCameraQuery videoTraceCameraQuery);

    VideoTraceCamera queryByCondition(VideoTraceCameraQuery query);
}