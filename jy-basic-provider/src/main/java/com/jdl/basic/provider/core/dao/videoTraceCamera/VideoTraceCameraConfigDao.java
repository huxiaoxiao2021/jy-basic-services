package com.jdl.basic.provider.core.dao.videoTraceCamera;


import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface VideoTraceCameraConfigDao {
    int deleteById(VideoTraceCameraConfig record);
    int insert(VideoTraceCameraConfig record);
    VideoTraceCameraConfig selectByPrimaryKey(Integer id);
    int updateById(VideoTraceCameraConfig record);
    List<VideoTraceCameraConfig> queryByCameraId(VideoTraceCameraConfig query);
    int batchSave(@Param("list") List<VideoTraceCameraConfig> addList);
    int batchDelete(@Param("ids")List<Integer> ids, @Param("updateErp") String updateErp);
    List<VideoTraceCameraConfig> queryByCondition(VideoTraceCameraConfig videoTraceCameraConfig);

    List<VideoTraceCameraConfig> queryByDevice(VideoTraceCameraConfig videoTraceCameraConfig);

    List<VideoTraceCameraConfig> queryByGrid(VideoTraceCameraConfig videoTraceCameraConfig);
}