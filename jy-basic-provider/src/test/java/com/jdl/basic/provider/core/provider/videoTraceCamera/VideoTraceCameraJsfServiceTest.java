package com.jdl.basic.provider.core.provider.videoTraceCamera;


import com.jdl.basic.api.domain.videoTraceCamera.*;
import com.jdl.basic.api.service.videoTraceCamera.VideoTraceCameraJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class VideoTraceCameraJsfServiceTest {
    @Autowired
    private VideoTraceCameraJsfService videoTraceCameraJsfService;


    @Test
    public void  queryPageListTest(){
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        Result<Integer> count = videoTraceCameraJsfService.getCount(videoTraceCameraQuery);
        Result<PageDto<VideoTraceCamera>> result = videoTraceCameraJsfService.queryPageList(videoTraceCameraQuery);
        System.out.println(result);
    }
    @Test
    public void queryVideoTraceCameraConfigTest(){
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        videoTraceCameraQuery.setCameraCode("11011318471180200005");
        videoTraceCameraQuery.setNationalChannelCode("11011318471320206039");
        videoTraceCameraQuery.setStatus(1);
        videoTraceCameraQuery.setOperateTimeStr("2024-03-22 16:36:33");
        Result<List<VideoTraceCameraConfig>> result = videoTraceCameraJsfService.queryVideoTraceCameraConfig(videoTraceCameraQuery);
        System.out.println(JsonHelper.toJSONString(result));
    }
    @Test
    public void editCameraConfigTest(){
        VideoTraceCameraVo videoTraceCameraVo = new VideoTraceCameraVo();
        videoTraceCameraVo.setId(5);
        ArrayList<VideoTraceCameraConfig> list = new ArrayList<>();
        for (int i = 10; i < 25; i++) {
            VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
            videoTraceCameraConfig.setCameraId(5);
            videoTraceCameraConfig.setMasterCamera((byte) 0);
            videoTraceCameraConfig.setRefWorkGridKey("xx5");
            videoTraceCameraConfig.setMachineCode("MC5"+i);
            videoTraceCameraConfig.setSupplyDwsCode("SDC5"+i);
            videoTraceCameraConfig.setCreateErp("sjm");
            list.add(videoTraceCameraConfig);
        }
        videoTraceCameraVo.setVideoTraceCameraConfigList(list);
        Result<Boolean> result = videoTraceCameraJsfService.editCameraConfig(videoTraceCameraVo);
        System.out.println(result);

    }
    @Test
    public void getWorkMasterCameraTest(){
        String workGridKey="xx5";
        Result<VideoTraceCamera> result = videoTraceCameraJsfService.getWorkMasterCamera(workGridKey);
        System.out.println(JsonHelper.toJSONString(result));
    }
    @Test
    public void cancelVideoTraceCameraConfigTest(){
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setRefWorkGridKey("xx123");
        int result = videoTraceCameraJsfService.cancelVideoTraceCameraConfig(videoTraceCameraConfig);
        System.out.println(JsonHelper.toJSONString(result));
    }


    @Test
    public void changeMasterCameraConfigTest(){
        VideoTraceCameraVo videoTraceCameraVo = new VideoTraceCameraVo();
        videoTraceCameraVo.setId(5);
        videoTraceCameraVo.setGridBusinessKey("xx5");
        videoTraceCameraVo.setMasterCamera((byte) 1);
        ArrayList<VideoTraceCameraConfig> list = new ArrayList<>();
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setCameraId(5);
        videoTraceCameraConfig.setMasterCamera((byte) 1);
        videoTraceCameraConfig.setRefWorkGridKey("xx5");
        videoTraceCameraConfig.setCreateErp("sjm");
        list.add(videoTraceCameraConfig);
        videoTraceCameraVo.setVideoTraceCameraConfigList(list);
        Result<Boolean> result = videoTraceCameraJsfService.changeMasterCameraConfig(videoTraceCameraVo);
        System.out.println(JsonHelper.toJSONString(result));
    }

    @Test
    public void queryCameraTest(){
        VideoTraceCameraConfigQuery videoTraceCameraQuery = new VideoTraceCameraConfigQuery();
        videoTraceCameraQuery.setChuteCode("100101");
        videoTraceCameraQuery.setMachineCode("WZX-TEST-0011");
        videoTraceCameraQuery.setWorkGridKey("CDWG00000022144");
        Result<List<VideoTraceCamera>> result = videoTraceCameraJsfService.queryCamera(videoTraceCameraQuery);
        System.out.println(JsonHelper.toJSONString(result));
    }

    @Test
    public void queryCameraConfigByCameraIdTest(){
        Result<List<VideoTraceCameraConfig>> result = videoTraceCameraJsfService.queryCameraConfigByCameraId(5);
        System.out.println(JsonHelper.toJSONString(result));
    }

    @Test
    public void saveOrUpdateCameraStatusTest() {
        VideoTraceCamera videoTraceCamera = new VideoTraceCamera();
        videoTraceCamera.setSiteCode(910);
        videoTraceCamera.setCameraName("device_code_02");
        videoTraceCamera.setCameraCode("device_name_02");
        videoTraceCamera.setNationalChannelCode("channel_code_02");
        videoTraceCamera.setNationalChannelName("channel_code_02");
        videoTraceCamera.setStatus((byte) 2);
        videoTraceCameraJsfService.saveOrUpdateCameraStatus(videoTraceCamera);
    }

}
