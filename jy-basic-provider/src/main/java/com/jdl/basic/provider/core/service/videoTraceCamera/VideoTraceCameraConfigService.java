package com.jdl.basic.provider.core.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import java.util.List;

public interface VideoTraceCameraConfigService {

    int deleteById(VideoTraceCameraConfig record);
    int insert(VideoTraceCameraConfig record);
    VideoTraceCameraConfig selectByPrimaryKey(Integer id);
    int updateById(VideoTraceCameraConfig record);
    List<VideoTraceCameraConfig> queryByCameraId(VideoTraceCameraConfig query);
    int batchSave(List<VideoTraceCameraConfig> addList);
    int batchDelete(List<Integer> ids, String updateErp);
    List<VideoTraceCameraConfig> queryByCondition(VideoTraceCameraConfig videoTraceCameraConfig);

    List<VideoTraceCameraConfig> queryByDevice(VideoTraceCameraConfig videoTraceCameraConfig);

    List<VideoTraceCameraConfig> queryByGrid(VideoTraceCameraConfig videoTraceCameraConfig);

    int cancelVideoTraceCameraConfig(VideoTraceCameraConfig videoTraceCameraConfig);
}
