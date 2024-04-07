package com.jdl.basic.provider.core.dao.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoTraceCameraDao {
    int deleteById(VideoTraceCamera record);

    int insert(VideoTraceCamera record);

    VideoTraceCamera selectByPrimaryKey(Integer id);
    /**
     * 通过ID获取VideoTraceCamera对象，包括已删除的记录
     * @param id 输入的ID参数
     * @return 返回对应ID的VideoTraceCamera对象
     */
    VideoTraceCamera getByIdNoYn(Integer id);

    int updateById(VideoTraceCamera record);

    List<VideoTraceCamera> queryPageList(VideoTraceCameraQuery videoTraceCameraQuery);

    int queryCount(VideoTraceCameraQuery videoTraceCameraQuery);

    List<VideoTraceCamera>  queryByCondition(VideoTraceCameraQuery query);
    List<VideoTraceCamera>  queryByConditionAndYn(VideoTraceCameraQuery query);

    List<VideoTraceCamera> getByIds(@Param("ids")List<Integer> ids);

    int batchInsert(@Param("list") List<VideoTraceCamera> list);
}