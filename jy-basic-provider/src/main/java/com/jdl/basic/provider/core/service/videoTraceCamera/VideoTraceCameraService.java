package com.jdl.basic.provider.core.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface VideoTraceCameraService {
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

    int deleteById(VideoTraceCamera record);

    int insert(VideoTraceCamera record);

    VideoTraceCamera selectByPrimaryKey(Integer id);
    /**
     * 通过id获取VideoTraceCamera信息，包括已逻辑删除的数据
     * @param id 要获取信息的id
     * @return VideoTraceCamera 返回对应id的VideoTraceCamera信息
     */
    VideoTraceCamera getByIdNoYn(Integer id);

    int updateById(VideoTraceCamera record);

    int queryCount(VideoTraceCameraQuery videoTraceCameraQuery);

    List<VideoTraceCamera> queryByCondition(VideoTraceCameraQuery query);

    List<VideoTraceCamera> getByIds(List<Integer> ids);

    void importCameras(List<VideoTraceCameraImport> list);

    int saveOrUpdateCameraStatus(VideoTraceCamera videoTraceCamera);

    void importCameraConfigs(List<VideoTraceCameraImport> list);

    int deleteCameraByIds(List<Integer> ids, String operate);

    int deleteCameraConfigByIds(List<Integer> ids, String operate);

    /**
     * 物理删除
     * @param ids
     * @return
     */
    int deleteByIds(List<Integer> ids);

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
     * 获取网格、工序、设备上绑定的摄像头数据
     *
     */
    List<VideoTraceCameraConfigVo> getBoundCameraInfo(VideoTraceCameraConfigQuery query);


    /**
     *
     * 查询摄像头信息用于页面绑定
     *
     */
    List<VideoTraceCameraVo> queryCameraInfoForBinding(VideoTraceCameraQuery query);

    /**
     * 网格维度绑定摄像头信息保存
     *
     */
    Result<Boolean> saveConfigs(VideoTraceCameraConfigVo videoTraceCameraConfigVo);

    /**
     * 获取网格、工序、设备上历史绑定的摄像头数据
     *
     */
    List<VideoTraceCameraConfigVo> getHisBoundCameraInfo(VideoTraceCameraConfigQuery query);

    /**
     * 保存摄像头配置信息、更新配置状态
     */
    void saveConfigAndUpdateConfigStatus(VideoTraceCameraConfigVo save, List<VideoTraceCameraConfig> addList, List<VideoTraceCameraConfig> delList);
}
