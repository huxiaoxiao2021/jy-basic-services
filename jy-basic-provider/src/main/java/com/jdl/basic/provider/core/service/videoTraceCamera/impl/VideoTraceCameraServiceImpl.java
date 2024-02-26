package com.jdl.basic.provider.core.service.videoTraceCamera.impl;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraVo;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.videoTraceCamera.VideoTraceCameraConfigDao;
import com.jdl.basic.provider.core.dao.videoTraceCamera.VideoTraceCameraDao;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service("videoTraceCameraService")
public class VideoTraceCameraServiceImpl implements VideoTraceCameraService {

    @Autowired
    @Qualifier("videoTraceCameraDao")
    private VideoTraceCameraDao videoTraceCameraDao;

    @Autowired
    @Qualifier("videoTraceCameraConfigDao")
    private VideoTraceCameraConfigDao videoTraceCameraConfigDao;
    @Override
    public Result<PageDto<VideoTraceCamera>> queryPageList(VideoTraceCameraQuery query) {
        Result<PageDto<VideoTraceCamera>> result = Result.success();
        if(query.getPageNumber() > 0) {
            query.setOffset((query.getPageNumber() - 1) * query.getLimit());
        };
        Long totalCount = videoTraceCameraDao.queryCount(query);
        PageDto<VideoTraceCamera> pageDto = new PageDto<>(query.getPageNumber(), query.getLimit());
        pageDto.setTotalRow(totalCount.intValue());
        if(totalCount > 0){
            List<VideoTraceCamera> videoTraceCameraList = videoTraceCameraDao.queryPageList(query);
            pageDto.setResult(videoTraceCameraList);
        }
        result.setData(pageDto);
        return result;
    }

    @Override
    public Result<List<VideoTraceCameraConfig>> queryVideoTraceCameraConfig(VideoTraceCameraQuery query) {
        Result<List<VideoTraceCameraConfig>> result = Result.success();
        if (StringUtils.isBlank(query.getCameraCode()) || StringUtils.isBlank(query.getVideoRecorderCode())){
            return Result.fail("参数错误，摄像头编码或摄像机编码为空");
        }
        VideoTraceCamera videoTraceCamera = videoTraceCameraDao.queryByCondition(query);
        if (videoTraceCamera == null){
            return Result.fail("摄像头信息不存在");
        }
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setCameraId(videoTraceCamera.getId());

        return result.setData(videoTraceCameraConfigDao.queryByCameraId(condition));
    }

    @Override
    public Result<Boolean> editCameraConfig(VideoTraceCameraVo videoTraceCameraVo) {
        VideoTraceCamera videoTraceCamera = videoTraceCameraDao.selectByPrimaryKey(videoTraceCameraVo.getId());
        if (videoTraceCamera == null){
            return Result.fail("摄像头信息不存在");
        }
        VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
        condition.setCameraId(videoTraceCamera.getId());
        List<VideoTraceCameraConfig> oldList = videoTraceCameraConfigDao.queryByCameraId(condition);
        List<VideoTraceCameraConfig> newList = videoTraceCameraVo.getVideoTraceCameraConfigList();
        List<VideoTraceCameraConfig> delList = oldList.stream().filter(a -> newList.stream().noneMatch(b -> compare(a, b))).collect(Collectors.toList());
        List<VideoTraceCameraConfig> addList = newList.stream().filter(a -> oldList.stream().noneMatch(b -> compare(a, b))).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(addList)){
            videoTraceCameraConfigDao.batchSave(addList);
        }
        if (CollectionUtils.isNotEmpty(addList)){
            videoTraceCameraConfigDao.batchDelete(delList.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList()),videoTraceCameraVo.getUpdateErp());
        }
        return Result.success();
    }

    @Override
    public int deleteById(VideoTraceCamera record) {
        return videoTraceCameraDao.deleteById(record);
    }

    @Override
    public int insert(VideoTraceCamera record) {
        return videoTraceCameraDao.insert(record);
    }

    @Override
    public VideoTraceCamera selectByPrimaryKey(Integer id) {
        return videoTraceCameraDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(VideoTraceCamera record) {
        return videoTraceCameraDao.updateById(record);
    }

    @Override
    public long queryCount(VideoTraceCameraQuery videoTraceCameraQuery) {
        return videoTraceCameraDao.queryCount(videoTraceCameraQuery);
    }

    @Override
    public VideoTraceCamera queryByCondition(VideoTraceCameraQuery query) {
        return videoTraceCameraDao.queryByCondition(query);
    }

    private boolean compare(VideoTraceCameraConfig v1, VideoTraceCameraConfig v2) {
    return Objects.equals(v1.getRefWorkGridKey(),v2.getRefWorkGridKey())
        && Objects.equals(v1.getRefGridKey(),v2.getRefGridKey())
        && Objects.equals(v1.getMachineCode(),v2.getMachineCode())
        && Objects.equals(v1.getChuteCode(),v2.getChuteCode())
        && Objects.equals(v1.getSupplyDwsCode(),v2.getSupplyDwsCode())
        && Objects.equals(v1.getMasterCamera(), v2.getMasterCamera());
}

}