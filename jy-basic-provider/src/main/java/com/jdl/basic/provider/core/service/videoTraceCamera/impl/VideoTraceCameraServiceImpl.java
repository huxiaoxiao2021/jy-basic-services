package com.jdl.basic.provider.core.service.videoTraceCamera.impl;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.videoTraceCamera.VideoTraceCameraConfigDao;
import com.jdl.basic.provider.core.dao.videoTraceCamera.VideoTraceCameraDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
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

    @Autowired
    private BaseMajorManager baseMajorManager;

    @Autowired
    @Qualifier("workStationGridService")
    private WorkStationGridService workStationGridService;

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
            log.error("参数错误，摄像头编码、通道号存在空值,查询入参【{}】",JsonHelper.toJSONString(query));
            return Result.fail("参数错误，摄像头编码、通道号存在空值");
        }
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        videoTraceCameraQuery.setCameraCode(query.getCameraCode());
        videoTraceCameraQuery.setNationalChannelCode(query.getNationalChannelCode());
        videoTraceCameraQuery.setYn(query.getStatus());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByConditionAndYn(videoTraceCameraQuery);
        if (CollectionUtils.isEmpty(videoTraceCameras)){
            return Result.fail("摄像头信息不存在");
        }

        List<Integer> cameraIds = videoTraceCameras.stream().map(VideoTraceCamera::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(cameraIds)) {
            VideoTraceCameraConfig condition = new VideoTraceCameraConfig();
            condition.setCameraIds(cameraIds);
            condition.setStatus((byte) 1);
            condition.setYn(query.getYn()==null ? null:query.getYn().byteValue());
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigDao.queryByCondition(condition);
            result.setData(videoTraceCameraConfigs);
        }
        return result;
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
        //todo
        List<VideoTraceCameraConfig> delList = oldList.stream().filter(a -> newList.stream().noneMatch(b -> compare(a, b))).peek(x -> x.setUpdateErp(videoTraceCameraVo.getCreateErp())).collect(Collectors.toList());

        List<VideoTraceCameraConfig> addList = newList.stream()
                .filter(a -> oldList.stream().noneMatch(b -> compare(a, b)))
                .peek(x -> {
                    x.setMasterCamera((byte) 0);
                    x.setCreateErp(videoTraceCameraVo.getCreateErp());
                    x.setStatus(videoTraceCamera.getStatus());
                })
                .collect(Collectors.toList());
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
        VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
        condition.setNationalChannelCode(record.getNationalChannelCode());
        condition.setCameraCode(record.getCameraCode());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
        if (videoTraceCameras.size()==1){
            return videoTraceCameraDao.deleteById(videoTraceCameras.get(0));
        }
        return 0;
    }

    @Override
    public int insert(VideoTraceCamera record) {
        VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
        condition.setNationalChannelCode(record.getNationalChannelCode());
        condition.setCameraCode(record.getCameraCode());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
        if (CollectionUtils.isNotEmpty(videoTraceCameras)){
            throw new RuntimeException("摄像头信息已存在！");
        }
        BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(record.getSiteCode());
        if(siteInfo == null) {
            throw new RuntimeException("所属站点在基础资料中不存在！");
        }
        fillSiteInfo(record, siteInfo);
        return videoTraceCameraDao.insert(record);
    }

    private void fillSiteInfo(VideoTraceCamera record, BaseStaffSiteOrgDto siteInfo) {
        record.setSiteName(siteInfo.getSiteName());
        record.setProvinceAgencyCode( siteInfo.getProvinceAgencyCode());
        record.setProvinceAgencyName( siteInfo.getProvinceAgencyName());
        record.setAreaHubCode(siteInfo.getAreaCode());
        record.setAreaHubName(siteInfo.getAreaName());
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

    @Override
    public int saveOrUpdateCameraStatus(VideoTraceCamera videoTraceCamera) {
        VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
        condition.setNationalChannelCode(videoTraceCamera.getNationalChannelCode());
        condition.setCameraCode(videoTraceCamera.getCameraCode());
        List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
        if (CollectionUtils.isEmpty(videoTraceCameras)){
            BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(videoTraceCamera.getSiteCode());
            if(siteInfo == null) {
                throw new RuntimeException("所属站点在基础资料中不存在！");
            }
            fillSiteInfo(videoTraceCamera, siteInfo);
            return videoTraceCameraDao.insert(videoTraceCamera);
        }
        if (!Objects.equals(videoTraceCameras.get(0).getStatus(), videoTraceCamera.getStatus())){
            VideoTraceCamera update = new VideoTraceCamera();
            update.setId(videoTraceCameras.get(0).getId());
            update.setStatus(videoTraceCamera.getStatus());
            videoTraceCameraDao.updateById(update);

            VideoTraceCameraConfig query = new VideoTraceCameraConfig();
            query.setCameraId(videoTraceCameras.get(0).getId());
            List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigDao.queryByCameraId(query);
            if (CollectionUtils.isNotEmpty(videoTraceCameraConfigs)){
                videoTraceCameraConfigDao.batchDelete(videoTraceCameraConfigs.stream().map(VideoTraceCameraConfig::getId).collect(Collectors.toList()), videoTraceCamera.getUpdateErp());
                List<VideoTraceCameraConfig> addList = videoTraceCameraConfigs.stream().peek(x -> {
                    x.setId(null);
                    x.setUpdateErp(videoTraceCamera.getUpdateErp());
                    x.setCreateTime(null);
                    x.setUpdateTime(null);
                    x.setStatus(videoTraceCamera.getStatus());
                }).collect(Collectors.toList());
                videoTraceCameraConfigDao.batchSave(addList);
            }
        }

        return 0;
    }



    private boolean compare(VideoTraceCameraConfig v1, VideoTraceCameraConfig v2) {
    return Objects.equals(v1.getRefWorkGridKey(),v2.getRefWorkGridKey())
        && Objects.equals(v1.getRefWorkStationKey(),v2.getRefWorkStationKey())
        && Objects.equals(v1.getMachineCode(),v2.getMachineCode())
        && Objects.equals(v1.getChuteCode(),v2.getChuteCode())
        && Objects.equals(v1.getSupplyDwsCode(),v2.getSupplyDwsCode());
}


    @Override
    public void importCameraConfigs(List<VideoTraceCameraImport> list) {
        for (VideoTraceCameraImport item : list) {
            VideoTraceCameraQuery condition = new VideoTraceCameraQuery();
            condition.setNationalChannelCode(item.getNationalChannelCode());
            condition.setCameraCode(item.getCameraCode());
            List<VideoTraceCamera> videoTraceCameras = videoTraceCameraDao.queryByCondition(condition);
            //摄像头消息不存在时，插入一条
            if (CollectionUtils.isNotEmpty(videoTraceCameras)){
                VideoTraceCameraConfig videoTraceCameraConfig = getVideoTraceCameraConfig(item, videoTraceCameras.get(0));
                videoTraceCameraConfigDao.insert(videoTraceCameraConfig);
            } else {
                log.error("同步摄像头配置关系失败，摄像头不存在。{}", JsonHelper.toJSONString(item));
            }

        }
    }


    @Override
    public void importCameras(List<VideoTraceCameraImport> list) {
        videoTraceCameraDao.batchInsert(list.stream().map(this::getVideoTraceCamera).collect(Collectors.toList()));
    }



    private VideoTraceCameraConfig getVideoTraceCameraConfig(VideoTraceCameraImport item, VideoTraceCamera videoTraceCamera) {
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        WorkStationGridQuery workStationGridQuery = new WorkStationGridQuery();
        workStationGridQuery.setBusinessKey(item.getStationGridKey());
        //查询工序
        WorkStationGrid workStationGrid = workStationGridService.queryByGridKeyWithCache(workStationGridQuery);
        videoTraceCameraConfig.setRefWorkGridKey(workStationGrid.getRefWorkGridKey());
        videoTraceCameraConfig.setCameraId(videoTraceCamera.getId());
        videoTraceCameraConfig.setCreateErp(item.getUpdateErp());
        videoTraceCameraConfig.setCreateTime(item.getUpdateTime());
        videoTraceCameraConfig.setUpdateErp(item.getUpdateErp());
        videoTraceCameraConfig.setUpdateTime(item.getUpdateTime());
        videoTraceCameraConfig.setStatus(item.getStatus());
        videoTraceCameraConfig.setTs(item.getUpdateTime());
        return videoTraceCameraConfig;
    }

    private  VideoTraceCamera getVideoTraceCamera(VideoTraceCameraImport item) {
        VideoTraceCamera videoTraceCamera = new VideoTraceCamera();
        videoTraceCamera.setCameraCode(item.getCameraCode());
        videoTraceCamera.setCameraName(item.getCameraName());
        videoTraceCamera.setNationalChannelCode(item.getNationalChannelCode());
        videoTraceCamera.setNationalChannelName(item.getNationalChannelName());
        videoTraceCamera.setCreateErp(item.getUpdateErp());
        videoTraceCamera.setCreateTime(item.getUpdateTime());
        videoTraceCamera.setUpdateErp(item.getUpdateErp());
        videoTraceCamera.setUpdateTime(item.getUpdateTime());
        videoTraceCamera.setStatus(item.getStatus());
        videoTraceCamera.setSiteCode(item.getSiteCode());
        videoTraceCamera.setTs(item.getUpdateTime());
        BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(item.getSiteCode());
        if(siteInfo == null) {
            throw new RuntimeException("所属站点在基础资料中不存在！");
        }
        fillSiteInfo(videoTraceCamera, siteInfo);
        return videoTraceCamera;
    }

}