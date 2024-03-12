package com.jdl.basic.provider.core.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.*;
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

    int updateById(VideoTraceCamera record);

    int queryCount(VideoTraceCameraQuery videoTraceCameraQuery);

    List<VideoTraceCamera> queryByCondition(VideoTraceCameraQuery query);

    List<VideoTraceCamera> getByIds(List<Integer> ids);

    int  importDatas(List<VideoTraceCameraImport> list);

    int saveOrUpdateCameraStatus(VideoTraceCamera videoTraceCamera);
}
