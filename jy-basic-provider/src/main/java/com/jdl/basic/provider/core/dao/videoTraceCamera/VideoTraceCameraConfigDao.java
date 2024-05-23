package com.jdl.basic.provider.core.dao.videoTraceCamera;


import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.stream.Stream;

public interface VideoTraceCameraConfigDao {
    int deleteById(VideoTraceCameraConfig record);
    int insert(VideoTraceCameraConfig record);
    VideoTraceCameraConfig selectByPrimaryKey(Integer id);
    int updateById(VideoTraceCameraConfig record);
    List<VideoTraceCameraConfig> queryByCameraId(VideoTraceCameraConfig query);
    int batchSave(@Param("list") List<VideoTraceCameraConfig> addList);
    int batchDelete(@Param("ids")List<Integer> ids, @Param("updateErp") String updateErp);

    /**
     * 查询摄像头配置信息，网格业务主键不能为空
     */
    List<VideoTraceCameraConfig> queryByCondition(VideoTraceCameraConfig videoTraceCameraConfig);

    /**
     *  查询一时间点有效的 摄像头配置信息
     *
     */
    List<VideoTraceCameraConfig> queryValidByCameraIdsAndTime(VideoTraceCameraConfig videoTraceCameraConfig);

    List<VideoTraceCameraConfig> queryByDevice(VideoTraceCameraConfig videoTraceCameraConfig);

    /**
     * 查询绑定在网格上的摄像头配置
     */
    List<VideoTraceCameraConfig> queryByGrid(VideoTraceCameraConfig videoTraceCameraConfig);

    int deleteByIds(@Param("ids")List<Integer> ids);

    List<VideoTraceCameraConfig> queryByGridKeys(@Param("list")List<String> gridKeys);

    List<VideoTraceCameraConfig> queryValidByCameraIds(VideoTraceCameraConfig videoTraceCameraConfig);

    List<VideoTraceCameraConfig> queryByGridNoYn(VideoTraceCameraConfig condition);

    List<VideoTraceCameraConfig> queryByDeviceNoYn(VideoTraceCameraConfig condition);
}