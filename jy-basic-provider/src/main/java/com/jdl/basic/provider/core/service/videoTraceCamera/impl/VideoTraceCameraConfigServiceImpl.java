package com.jdl.basic.provider.core.service.videoTraceCamera.impl;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.provider.core.dao.videoTraceCamera.VideoTraceCameraConfigDao;
import com.jdl.basic.provider.core.service.videoTraceCamera.VideoTraceCameraConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("videoTraceCameraConfigService")
public class VideoTraceCameraConfigServiceImpl implements VideoTraceCameraConfigService {

    @Autowired
    @Qualifier("videoTraceCameraConfigDao")
    private VideoTraceCameraConfigDao videoTraceCameraConfigDao;

    @Override
    public int deleteById(VideoTraceCameraConfig record) {
        return videoTraceCameraConfigDao.deleteById(record);
    }

    @Override
    public int insert(VideoTraceCameraConfig record) {
        return videoTraceCameraConfigDao.insert(record);
    }

    @Override
    public VideoTraceCameraConfig selectByPrimaryKey(Integer id) {
        return videoTraceCameraConfigDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(VideoTraceCameraConfig record) {
        return videoTraceCameraConfigDao.updateById(record);
    }

    @Override
    public List<VideoTraceCameraConfig> queryByCameraId(VideoTraceCameraConfig query) {
        return videoTraceCameraConfigDao.queryByCameraId(query);
    }

    @Override
    public int batchSave(List<VideoTraceCameraConfig> list) {
        return videoTraceCameraConfigDao.batchSave(list);
    }

    @Override
    public int batchDelete(List<Integer> ids, String updateErp) {
        return videoTraceCameraConfigDao.batchDelete(ids,updateErp);
    }

    @Override
    public List<VideoTraceCameraConfig> queryByCondition(VideoTraceCameraConfig videoTraceCameraConfig) {
        return videoTraceCameraConfigDao.queryByCondition(videoTraceCameraConfig);
    }

    @Override
    public List<VideoTraceCameraConfig> queryByDevice(VideoTraceCameraConfig videoTraceCameraConfig) {
        return videoTraceCameraConfigDao.queryByDevice(videoTraceCameraConfig);
    }

    @Override
    public List<VideoTraceCameraConfig> queryByGrid(VideoTraceCameraConfig videoTraceCameraConfig) {
        return videoTraceCameraConfigDao.queryByGrid(videoTraceCameraConfig);
    }
}
