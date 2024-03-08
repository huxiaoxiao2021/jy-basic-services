package com.jdl.basic.provider.core.service.videoTraceCamera.impl;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraVo;
import com.jdl.basic.common.utils.DateHelper;
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

import java.util.Date;
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
            query.setLimit(query.getPageSize());
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        };
        int totalCount = videoTraceCameraDao.queryCount(query);
        PageDto<VideoTraceCamera> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
        pageDto.setTotalRow(totalCount);
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
        if ((StringUtils.isBlank(query.getCameraCode()) || StringUtils.isBlank(query.getNationalChannelCode()))
                && query.getId()<0){
            return Result.fail("参数错误，摄像头编码、通道号存在空值");
        }

        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(query);
        if (CollectionUtils.isEmpty(videoTraceCameras)){
            return Result.fail("摄像头信息不存在");
        }

        List<Integer> cameraIds = videoTraceCameras.stream().map(VideoTraceCamera::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(cameraIds)) {
            VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
            condition.setCameraIds(cameraIds);
            condition.setYn(query.getStatus()==null ? null:query.getStatus().byteValue());
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigDao.queryByCondition(condition);
            result.setData(videoTraceCameraConfigs.stream().filter(x->filter(x.getCreateTime(),x.getUpdateTime(), x.getYn(), query)).collect(Collectors.toList()));
        }
        return result;
    }

    /**
     * 筛选指定时间内有效的摄像头配置信息
     */
    private static boolean filter(Date createTime, Date updateTime, int yn, VideoTraceCameraQuery query) {
        if (StringUtils.isBlank(query.getStartTimeStr()) || StringUtils.isBlank(query.getEndTimeStr())){
            return true;
        }
        Date startTime = DateHelper.parse(query.getStartTimeStr());
        Date endTime = DateHelper.parse(query.getEndTimeStr());
        if (startTime == null || endTime == null){
            return true;
        }
        return endTime.compareTo(createTime) >= 0 && (yn==1 || startTime.compareTo(updateTime) <=0);
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
        if (CollectionUtils.isEmpty(oldList)){
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCamera.getId());
            update.setConfigStatus((byte) 1);
            videoTraceCameraDao.updateById(update);
        }
        List<VideoTraceCameraConfig> newList = videoTraceCameraVo.getVideoTraceCameraConfigList();
        if (CollectionUtils.isEmpty(newList)){
            //清空绑定关系后，修改摄像头配置状态
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCamera.getId());
            update.setConfigStatus((byte) 0);
            videoTraceCameraDao.updateById(update);
        }
        List<VideoTraceCameraConfig> delList = oldList.stream().filter(a -> newList.stream().noneMatch(b -> compare(a, b))).collect(Collectors.toList());
        List<VideoTraceCameraConfig> addList = newList.stream().filter(a -> oldList.stream().noneMatch(b -> compare(a, b))).peek(x-> x.setMasterCamera((byte) 0)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(addList)){
            videoTraceCameraConfigDao.batchSave(addList);
        }
        if (CollectionUtils.isNotEmpty(delList)){
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
    public int queryCount(VideoTraceCameraQuery videoTraceCameraQuery) {
        return videoTraceCameraDao.queryCount(videoTraceCameraQuery);
    }

    @Override
    public List<VideoTraceCamera> queryByCondition(VideoTraceCameraQuery query) {
        return videoTraceCameraDao.queryByCondition(query);
    }

    @Override
    public List<VideoTraceCamera> getByIds(List<Integer> ids) {
        return videoTraceCameraDao.getByIds(ids);
    }

    private boolean compare(VideoTraceCameraConfig v1, VideoTraceCameraConfig v2) {
    return Objects.equals(v1.getRefWorkGridKey(),v2.getRefWorkGridKey())
        && Objects.equals(v1.getRefWorkStationKey(),v2.getRefWorkStationKey())
        && Objects.equals(v1.getMachineCode(),v2.getMachineCode())
        && Objects.equals(v1.getChuteCode(),v2.getChuteCode())
        && Objects.equals(v1.getSupplyDwsCode(),v2.getSupplyDwsCode());
}

}