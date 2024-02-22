package com.jdl.basic.provider.core.dao.videoTraceCamera;

public interface VideoTraceCameraDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoTraceCameraDao record);

    int insertSelective(VideoTraceCameraDao record);

    VideoTraceCameraDao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoTraceCameraDao record);

    int updateByPrimaryKey(VideoTraceCameraDao record);
}