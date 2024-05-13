package com.jdl.basic.api.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface VideoTraceCameraJsfService {
    /**
     * 列表分页查询
     */
    Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery);
    /**
     * 查摄像头绑定数据 入参operateTimeStr不为空时，会查询此时间有效的绑定关系
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
     * 格口、设备工序，网格查摄像头 包含网格编码和是否为主摄像头
     *
     */
    Result<List<VideoTraceCamera>> queryCameraInfo(VideoTraceCameraConfigQuery query);

    /**
     *
     * 查摄像头下绑定数据
     *
     */
    Result<List<VideoTraceCameraConfig>> queryCameraConfigByCameraId(int id);

    int insert(VideoTraceCamera videoTraceCamera);
    int delete(VideoTraceCamera videoTraceCamera);

    int saveOrUpdateCameraStatus(VideoTraceCamera videoTraceCamera);


    Result<Boolean> importCameras(List<VideoTraceCameraImport> list,int type);

    /**
     *
     * 物理删除
     */
    int deleteCameraConfigByIds(List<Integer> ids);
    int deleteCameraByIds(List<Integer> ids, String operate);

    /**
     * 摄像头配置导出查询
     */
    Result<List<CameraConfigExportVo>> exportCameraConfigByGrid(WorkGridQuery query);

    /**
     * 网格信息及其摄像头绑定信息
     *
     */
    Result<PageDto<GridCameraBindingVo>> queryPageListWithWorkGrid(WorkGridQuery query);

    /**
     * 网格下工序设备及摄像头绑定信息
     *
     */
    List<GridCameraBindingVo> getWorkDeviceCameraData(String gridKey);

    /**
     *
     * 查询网格、工序、设备下绑定的摄像头
     *
     */
    Result<List<VideoTraceCameraConfigVo>> getBoundCameraInfo(VideoTraceCameraConfigQuery query);
    /**
     *
     * 查询网格、工序、设备下历史绑定的摄像头
     *
     */
    Result<List<VideoTraceCameraConfigVo>> getHisBoundCameraInfo(VideoTraceCameraConfigQuery query);

    /**
     *
     * 查询摄像头信息用于页面绑定
     *
     */
    Result<List<VideoTraceCameraVo>> queryCameraInfoForBinding(VideoTraceCameraQuery query);

    /**
     * 网格维度绑定摄像头信息保存
     *
     */
    Result<Boolean> saveConfigs(VideoTraceCameraConfigVo videoTraceCameraConfigVo);

    int updateById(VideoTraceCamera record);
}
