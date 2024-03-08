package com.jdl.basic.provider.core.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class VideoTraceCameraConfigServiceTest {
    @Autowired
    private VideoTraceCameraConfigService videoTraceCameraConfigService;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
//            VideoTraceCamera camera = new VideoTraceCamera();
            VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
            videoTraceCameraConfig.setCameraId(4);
            videoTraceCameraConfig.setRefWorkGridKey("xx123");
            videoTraceCameraConfig.setRefWorkStationKey("111");
            videoTraceCameraConfig.setMachineCode("111");
//            videoTraceCameraConfig.setChuteCode();
//            videoTraceCameraConfig.setSupplyDwsCode();
//            videoTraceCameraConfig.setMasterCamera();
            videoTraceCameraConfig.setCreateErp("sjm");
            int result = videoTraceCameraConfigService.insert(videoTraceCameraConfig);
            System.out.println(result);
        }
    }

    @Test
    public void testUpdateById() {
        VideoTraceCameraConfig videoTraceCameraConfig = videoTraceCameraConfigService.selectByPrimaryKey(1);
        videoTraceCameraConfig.setId(2);
        videoTraceCameraConfig.setUpdateErp("sjm");
        int result = videoTraceCameraConfigService.updateById(videoTraceCameraConfig);
        System.out.println(result);
    }

    @Test
    public void testDeleteById() {
        VideoTraceCameraConfig videoTraceCameraConfig = videoTraceCameraConfigService.selectByPrimaryKey(2);
        VideoTraceCameraConfig camera = new VideoTraceCameraConfig();
        camera.setId(videoTraceCameraConfig.getId());
        camera.setUpdateErp("sjm");
        int i = videoTraceCameraConfigService.deleteById(camera);
        System.out.println(i);
    }

    @Test
    public void testQueryByCameraId() {
        VideoTraceCameraConfig camera = new VideoTraceCameraConfig();
        camera.setCameraId(2);
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByCameraId(camera);
        System.out.println(JsonHelper.toJSONString(videoTraceCameraConfigs));
    }

    @Test
    public void testBatchSave() {
        ArrayList<VideoTraceCameraConfig> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
            videoTraceCameraConfig.setCameraId(5);
            videoTraceCameraConfig.setMasterCamera((byte) 0);
            videoTraceCameraConfig.setRefWorkGridKey("xx123");
            videoTraceCameraConfig.setMachineCode("MC1"+i);
//            videoTraceCameraConfig.setChuteCode();
            videoTraceCameraConfig.setSupplyDwsCode("SDC12"+i);
//            videoTraceCameraConfig.setMasterCamera();
            videoTraceCameraConfig.setCreateErp("sjm");
            list.add(videoTraceCameraConfig);
        }
        int i = videoTraceCameraConfigService.batchSave(list);
        System.out.println(i);
    }

    @Test
    public void testBatchDelete() {

        int i = videoTraceCameraConfigService.batchDelete(Arrays.asList(31,32,33,35,35,36,37,38,39,40), "sjm");
        System.out.println(i);
    }
    @Test
    public void testQueryByCondition() {
        VideoTraceCameraConfig videoTraceCameraConfig = new VideoTraceCameraConfig();
        videoTraceCameraConfig.setCameraId(5);
        List<VideoTraceCameraConfig> videoTraceCameraConfigs = videoTraceCameraConfigService.queryByCondition(videoTraceCameraConfig);
        System.out.println(JsonHelper.toJSONString(videoTraceCameraConfigs));
    }
}
