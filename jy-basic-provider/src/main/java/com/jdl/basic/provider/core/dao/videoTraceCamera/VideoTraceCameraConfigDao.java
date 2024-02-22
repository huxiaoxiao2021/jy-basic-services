package com.jdl.basic.provider.core.dao.videoTraceCamera;


import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;

public interface VideoTraceCameraConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoTraceCameraConfig record);

    int insertSelective(VideoTraceCameraConfig record);

    VideoTraceCameraConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoTraceCameraConfig record);

    int updateByPrimaryKey(VideoTraceCameraConfig record);
}