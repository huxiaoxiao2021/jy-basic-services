package com.jdl.basic.provider.core.provider.videoTraceCamera;


import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraVo;
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
    private VideoTraceCameraJsfService videoTraceCameraConfigService;


    @Test
    public void  queryPageListTest(){
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        Result<PageDto<VideoTraceCamera>> result = videoTraceCameraConfigService.queryPageList(videoTraceCameraQuery);
        System.out.println(result);
    }
    @Test
    public void queryVideoTraceCameraConfigTest(){
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        videoTraceCameraQuery.setCameraCode("C1211");
        videoTraceCameraQuery.setVideoRecorderCode("VRC1211");
        Result<List<VideoTraceCameraConfig>> result = videoTraceCameraConfigService.queryVideoTraceCameraConfig(videoTraceCameraQuery);
        System.out.println(result);
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
        Result<Boolean> result = videoTraceCameraConfigService.editCameraConfig(videoTraceCameraVo);
        System.out.println(result);

    }
    @Test
    public void getWorkMasterCameraTest(){
        String workGridKey="xx5";
        Result<List<VideoTraceCameraConfig>> result = videoTraceCameraConfigService.getWorkMasterCamera(workGridKey);
        System.out.println(JsonHelper.toJSONString(result));
    }

}
