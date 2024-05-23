package com.jdl.basic.provider.core.service.videoTraceCamera;

import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCamera;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraConfig;
import com.jdl.basic.api.domain.videoTraceCamera.VideoTraceCameraQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class VideoTraceCameraServiceTest {
    @Autowired
    private VideoTraceCameraService videoTraceCameraService;

    @Test
    public void testInsert() {
        for (int i = 0; i < 100; i++) {
            VideoTraceCamera camera = new VideoTraceCamera();
            camera.setCameraCode("C121"+i);
            camera.setCameraName("C121"+i);
            camera.setNationalChannelCode("VRC121"+i);
            camera.setNationalChannelName("VRC121"+i);
            camera.setProvinceAgencyCode("100000");
            camera.setProvinceAgencyName("物流总部");
            camera.setSiteCode(910);
            camera.setSiteName("北京马驹桥分拣中心");
            camera.setAreaHubName("北京");
            camera.setAreaHubCode("20001");
            camera.setConfigStatus((byte) 0);
            camera.setCreateErp("sjm");
            int result = videoTraceCameraService.insert(camera);
            System.out.println(result);
        }
    }

    @Test
    public void testUpdateById() {
        VideoTraceCamera camera = new VideoTraceCamera();
        camera.setId(2);
        camera.setCameraCode("C1231");
        camera.setCameraName("C1231");
        camera.setNationalChannelCode("VRC1231");
        camera.setNationalChannelName("VRC1231");
//        camera.setProvinceAgencyCode("100000");
//        camera.setProvinceAgencyName("物流总部");
//        camera.setSiteCode(910);
//        camera.setSiteName("北京马驹桥分拣中心");
//        camera.setAreaHubName("北京");
//        camera.setAreaHubCode("20001");
        camera.setConfigStatus((byte) 1);
//        camera.setCreateErp("sjm");
        camera.setUpdateErp("sjm");
        int result = videoTraceCameraService.updateById(camera);
        System.out.println(result);
    }

    @Test
    public void testDeleteById() {
        VideoTraceCamera videoTraceCamera = videoTraceCameraService.selectByPrimaryKey(1);
        VideoTraceCamera camera = new VideoTraceCamera();
        camera.setId(videoTraceCamera.getId());
        camera.setUpdateErp("sjm");
        int i = videoTraceCameraService.deleteById(camera);
        System.out.println(i);
    }

    @Test
    public void testQueryPageList() {
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        long l = videoTraceCameraService.queryCount(videoTraceCameraQuery);
        System.out.println("总数量"+l);

        videoTraceCameraQuery.setLimit(20);
        videoTraceCameraQuery.setPageNumber(2);
        Result<PageDto<VideoTraceCamera>> pageDtoResult = videoTraceCameraService.queryPageList(videoTraceCameraQuery);
        System.out.println(pageDtoResult.getData());
    }

    @Test
    public void queryByConditionTest() {
        VideoTraceCameraQuery query = new VideoTraceCameraQuery();
        List<VideoTraceCamera> result = videoTraceCameraService.queryByCondition(query);
        System.out.println(result);
    }


    @Test
    public void queryVideoTraceCameraConfigTest() {
        VideoTraceCameraQuery videoTraceCameraQuery = new VideoTraceCameraQuery();
        videoTraceCameraQuery.setCameraCode("51011318471180800001");
        videoTraceCameraQuery.setNationalChannelCode("12000000001310045829");
        videoTraceCameraQuery.setOperateTimeStr("2024-04-28 11:11:1");
        Result<List<VideoTraceCameraConfig>> result = videoTraceCameraService.queryVideoTraceCameraConfig(videoTraceCameraQuery);
        System.out.println(result.getData());
    }

}
